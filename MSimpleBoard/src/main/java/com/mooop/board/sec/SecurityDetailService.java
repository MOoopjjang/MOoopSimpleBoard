package com.mooop.board.sec;

import com.mooop.board.config.property.ConfirmProperties;
import com.mooop.board.constants.Defines;
import com.mooop.board.entity.MSBAuth;
import com.mooop.board.entity.MSBHistory;
import com.mooop.board.entity.MSBUser;
import com.mooop.board.enums.USER_STATUS;
import com.mooop.board.repo.AuthRepository;
import com.mooop.board.repo.DaoManager;
import com.mooop.board.repo.HistoryRespository;
import com.mooop.board.utils.EmailUtil;
import com.mooop.board.utils.HttpUtil;
import com.mooop.board.utils.MSecurityUtil;
import com.mooop.board.utils.MStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Project : MSimpleBoard
 * Package :com.mooop.board.sec
 * Author :  MOoop
 * Date : 19/01/2022
 * Desc : 인증관련 부가서비스
 */
@Slf4j
@Service("securityDetailService")
public class SecurityDetailService {
    private final DaoManager daoManager;
    private final AuthenticationUserDetailService detailService;
    private final ConfirmProperties confirmProperties;
    public SecurityDetailService(DaoManager daoManager, AuthenticationUserDetailService detailService
        , ConfirmProperties confirmProperties) {
        this.daoManager = daoManager;
        this.detailService = detailService;
        this.confirmProperties = confirmProperties;
    }


    /**
     * 로그인 인증 진행
     *
     * @param authentication
     * @return Authentication
     * @throws AuthenticationException
     */
    public Authentication authenticate( Authentication authentication ) throws AuthenticationException{
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		log.debug("#####  authenticate username : "+username+" , password : "+password);
        UserDetails userDetails = detailService.loadUserByUsername(username);
        /** 존재하지 않는 사용자 체크 */
        if(userDetails == null){
            throw new BadCredentialsException(username+" 존재하지 않습니다.");
        }

        AuthRepository respository = (AuthRepository) daoManager.getRepository(DaoManager.DAO_TYPE.AUTH);
        HistoryRespository historyRespository = (HistoryRespository) daoManager.getRepository(DaoManager.DAO_TYPE.HISTORY);
        MSBAuth auth = respository.findByEmail(username);
        MSBHistory history = auth.getHistory();
        if(history == null){
            history = new MSBHistory();
            history.setAuth(auth);
            historyRespository.save(history);
            history.getAuth().setHistory(history);
//            auth.setHistory(history);
        }

        int retryCount = history.getRetryCount();
        String makePassword = MSecurityUtil.makeRawPassword(username , password);
        boolean isMatched = MSecurityUtil.matches(makePassword, userDetails.getPassword());


        if(!checkClientIp(httpServletRequest , historyRespository , history)){
            auth.setStatus(USER_STATUS.HOLD);
            respository.save(auth);
        }


        /** 계정잠금 상태라면 ... */
        if(auth.getStatus() == USER_STATUS.BLOCK){
            throw new BlockAuthenticationException(userDetails.getUsername()+"는 패스워드 5회오류로 계정잠금 되었습니다.");
        }

        /** 계정 일시정지 상태라면 ... */
        if(auth.getStatus() == USER_STATUS.HOLD){
            String confirmToken = MStringUtil.randomStringGenerator(20);
            history.setConfirmToken(confirmToken);
            historyRespository.save(history);
            EmailUtil.sendConfirmUrl(
                 confirmProperties.getFrom_id()
                ,confirmProperties.getFrom_pwd()
                ,auth.getEmail()
                ,confirmProperties.getConfirm_url()+confirmToken
            );

            throw new HoldAuthenticationException(userDetails.getUsername()+"는 계정 일시정지 상태입니다.");
        }

        /** 패스워드 불일치 체크 */
        if(!isMatched){
            retryCount += 1;
            if(retryCount == confirmProperties.getMax_retry_count()){
                changeUserStatus(respository , USER_STATUS.BLOCK , auth);
            }
            history.setRetryCount(retryCount);
            historyRespository.save(history);
            throw new BadCredentialsException(username+" 존재하지 않습니다.");
        }


        /** 로그인 성공 */
        history.setRetryCount(0);
        historyRespository.save(history);

         /** 다른기기에서 로그인했을 경우 email 발송... */
        if(!checkDevice( httpServletRequest , historyRespository , history)){
            EmailUtil.sendNotiOtherDevice(
                confirmProperties.getFrom_id()
                ,confirmProperties.getFrom_pwd()
                ,auth.getEmail()
                ,HttpUtil.getUserAgent(httpServletRequest)
            );
        }

        return new UsernamePasswordAuthenticationToken(userDetails.getUsername()
				,userDetails.getPassword()
				, userDetails.getAuthorities());
    }


    /**
     * User상태를 HOLD --> ACTIVE 상태로 변경한다.
     *
     * @param confirmToken
     * @return
     */
    public MSBUser userActivation(String confirmToken){

        HistoryRespository historyRespository = (HistoryRespository) daoManager.getRepository(DaoManager.DAO_TYPE.HISTORY);
        MSBHistory history = historyRespository.findByConfirmToken(confirmToken);
        if(history == null){
            return null;
        }
        history.setConfirmToken(Defines.EMPTY_STRING);
        history.setClientIp(Defines.EMPTY_STRING);

        AuthRepository authRepository = (AuthRepository) daoManager.getRepository(DaoManager.DAO_TYPE.AUTH);
        MSBAuth auth = authRepository.findByEmail(history.getAuth().getEmail());
        auth.setEnable("Y");
        auth.setStatus(USER_STATUS.ACTIVE);
        authRepository.save(auth);

        return auth.getUser();
    }


    private void changeUserStatus(AuthRepository repository , USER_STATUS status , MSBAuth auth){
        auth.setEnable("N");
        auth.setStatus(status);
        repository.save(auth);
    }


    /** device 일치 체크   */
    private boolean checkDevice(HttpServletRequest request , HistoryRespository respository , MSBHistory history){
        String requestDevice = HttpUtil.getUserAgent(request);
        String registryDevice = MStringUtil.defaultIfEmptyString(history.getDevice() , "");
        boolean isDiff = (registryDevice.equals("") || registryDevice.equals(requestDevice))?true:false;
        history.setDevice(requestDevice);
        respository.save(history);
        return isDiff;
    }


    /** client ip 체크 */
    private boolean checkClientIp(HttpServletRequest request , HistoryRespository respository , MSBHistory history){
        String requestIp = request.getRemoteAddr();
        String registryIp = history.getClientIp();
         /** client IP 가 등록되어 있지 않을경우 device 등록... */
        if(MStringUtil.defaultIfEmptyString(registryIp , "").equals("")){
            history.setClientIp(requestIp);
            respository.save(history);
        }
        return history.getClientIp().equals(requestIp);
    }
}
