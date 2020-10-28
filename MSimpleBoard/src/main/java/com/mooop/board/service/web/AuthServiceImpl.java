package com.mooop.board.service.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mooop.board.constants.Defines;
import com.mooop.board.domain.web.AuthenticationVO;
import com.mooop.board.domain.web.UserItemVO;
import com.mooop.board.entity.MSBAuth;
import com.mooop.board.entity.MSBHistory;
import com.mooop.board.entity.MSBUser;
import com.mooop.board.enums.USER_ROLES;
import com.mooop.board.enums.USER_STATUS;
import com.mooop.board.repo.AuthRepository;
import com.mooop.board.repo.DaoManager;
import com.mooop.board.repo.HistoryRespository;
import com.mooop.board.repo.DaoManager.DAO_TYPE;
import com.mooop.board.repo.UserRepository;
import com.mooop.board.utils.MSecurityUtil;
import com.mooop.board.utils.MStringUtil;


/**
 * 
 * @author MOoop
 *
 */


@Service("authService")
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	private DaoManager daoManager;

	@Override
	public boolean register(UserItemVO rvo) throws Exception {
		
		AuthRepository repository = (AuthRepository) daoManager.getRepository(DAO_TYPE.AUTH);
		
		MSBAuth authInfo = new MSBAuth();
		authInfo.setEnable(rvo.getEnable());
		authInfo.setEmail(rvo.getEmail());
		authInfo.setUserRole(USER_ROLES.GUEST);
		authInfo.setStatus(USER_STATUS.ACCESSION);
		authInfo.setPassword(MSecurityUtil.makeGeneratePassowrd(rvo.getEmail() , rvo.getPassword()));
		MSBUser userInfo = new MSBUser();
		userInfo.setUserName(rvo.getUserName());
		userInfo.setUserNick(rvo.getNickName());
		userInfo.setUserAddr(rvo.getAddr());
		userInfo.setUserDesc(rvo.getDesc());
		userInfo.setAuth(authInfo);
		authInfo.setUser(userInfo);
		
		repository.save(authInfo);
		
		return true;
	}
	
	@Override
	public boolean save(UserItemVO rvo) throws Exception {
		AuthRepository repository = (AuthRepository) daoManager.getRepository(DAO_TYPE.AUTH);
		return Optional.ofNullable(repository.findByEmail(rvo.getEmail())).map(authInfo->{
			authInfo.setEnable(rvo.getEnable());
			authInfo.setUserRole(USER_ROLES.valueOf(rvo.getRole()));
			authInfo.setStatus(USER_STATUS.valueOf(rvo.getStatus()));
			authInfo.getUser().setUserAddr(rvo.getAddr());
			authInfo.getUser().setUserDesc(rvo.getDesc());
			
			authInfo.getUser().setAuth(authInfo);
			repository.flush();
			return true;
		}).orElse(false);
	}
	
	@Override
	public boolean unregister(String email) throws Exception {
		AuthRepository repository = (AuthRepository) daoManager.getRepository(DAO_TYPE.AUTH);
		return Optional.of(repository.findByEmail(email)).map(removeAuth->{
			/**
			 * - User 상태 변경 ( ACCESSION -- > SECESSION ) 
			 * -      패스워드 변경
			 * -      계정 비활성화
			 */
			removeAuth.setEnable("N");
			removeAuth.setStatus(USER_STATUS.SECESSION);
			removeAuth.setPassword(MStringUtil.randomStringGenerator(Defines.RANDOM_STRING_GEN_LEN));
			repository.flush();
			return true;
		}).orElse(false);
	}
	
	

	@Override
	public boolean checkEmail(String email) throws Exception {
		AuthRepository repository = (AuthRepository) daoManager.getRepository(DAO_TYPE.AUTH);
		return Optional.ofNullable(repository.findByEmail(email)).map(d->true).orElse(false);
	}

	@Override
	public boolean checkNick(String nick) throws Exception {
		UserRepository repository = (UserRepository) daoManager.getRepository(DAO_TYPE.USER);
		return Optional.ofNullable(repository.findByUserNick(nick)).map(d->true).orElse(false);
	}

	@Override
	public boolean checkAuthentication(AuthenticationVO avo) throws Exception {
		AuthRepository repository = (AuthRepository) daoManager.getRepository(DAO_TYPE.AUTH);
		MSBAuth auth = repository.findByEmail(avo.getEmail());
		return MSecurityUtil.matches(MSecurityUtil.makeRawPassword(avo.getEmail() , avo.getPassword())
				, auth.getPassword());
	}

	@Override
	public String getRole(String email) throws Exception {
		AuthRepository repository = (AuthRepository) daoManager.getRepository(DAO_TYPE.AUTH);
		return repository.getRole(email);
	}

	@Override
	public MSBAuth getEmailNick() throws Exception {
		AuthRepository repository = (AuthRepository) daoManager.getRepository(DAO_TYPE.AUTH);
		return repository.findByEmail(MSecurityUtil.username());
	}

	@Override
	public UserItemVO getAuthInfoFromEmail(String email) throws Exception {
		AuthRepository repository = (AuthRepository) daoManager.getRepository(DAO_TYPE.AUTH);
		return Optional.ofNullable(repository.findByEmail(email)).map(auth->{
			UserItemVO rvo = new UserItemVO() ;
			rvo.setUserName(auth.getUser().getUserName());
			rvo.setEmail(auth.getEmail());
			rvo.setNickName(auth.getUser().getUserNick());
			rvo.setAddr(auth.getUser().getUserAddr());
			rvo.setDesc(auth.getUser().getUserDesc());
			rvo.setEnable(auth.getEnable());
			rvo.setRole(auth.getUserRole().getRole());
			rvo.setStatus(auth.getStatus().getStatus());
			rvo.setPassword(auth.getPassword());
			return rvo;
		}).orElse(new UserItemVO());
	}

	@Override
	public boolean restoreLoginHistory(){
		return Optional.of(MSecurityUtil.username()).map(email->{
			HistoryRespository hisRepository =  (HistoryRespository) daoManager.getRepository(DAO_TYPE.HISTORY);
			AuthRepository authRepository =  (AuthRepository) daoManager.getRepository(DAO_TYPE.AUTH);
			
			MSBAuth auth = authRepository.findByEmail(email);
			MSBHistory history = new MSBHistory();
			history.setAuth(auth);
			
			auth.getHistorys().add(history);
			
			hisRepository.save(history);
			return true;
		}).orElse(false);
	}

}
