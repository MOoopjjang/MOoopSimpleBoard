package com.mooop.board.service.web;

import com.mooop.board.component.AttachFileService;
import com.mooop.board.domain.web.AuthenticationVO;
import com.mooop.board.domain.web.UploadFileInfoVO;
import com.mooop.board.domain.web.UserItemVO;
import com.mooop.board.entity.MSBAuth;
import com.mooop.board.entity.MSBHistory;
import com.mooop.board.entity.MSBUpload;
import com.mooop.board.entity.MSBUser;
import com.mooop.board.enums.UPLOAD_P_TYPE;
import com.mooop.board.enums.USER_ROLES;
import com.mooop.board.enums.USER_STATUS;
import com.mooop.board.repo.*;
import com.mooop.board.repo.DaoManager.DAO_TYPE;
import com.mooop.board.utils.MSecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * 
 * @author MOoop
 *
 */


@Service("authService")
public class AuthServiceImpl implements AuthService{
	private static Logger logger = LoggerFactory.getLogger("AuthServiceImpl");

	private DaoManager daoManager;
	private AttachFileService attachFileService;
	public AuthServiceImpl(DaoManager daoManager
	,AttachFileService attachFileService){
		this.daoManager = daoManager;
		this.attachFileService = attachFileService;
	}
	


	@Override
	public boolean register(UserItemVO rvo) throws Exception {
		
		AuthRepository repository = (AuthRepository) daoManager.getRepository(DAO_TYPE.AUTH);
		
		MSBAuth authInfo = new MSBAuth();
		authInfo.setEnable(rvo.getEnable());
		authInfo.setEmail(rvo.getEmail());
		authInfo.setUserRole(USER_ROLES.GUEST);
		authInfo.setStatus(USER_STATUS.ACTIVE);
		authInfo.setPassword(MSecurityUtil.makeGeneratePassowrd(rvo.getEmail() , rvo.getPassword()));
		MSBUser userInfo = new MSBUser();
		userInfo.setUserName(rvo.getUserName());
		userInfo.setUserNick(rvo.getNickName());
		userInfo.setUserAddr(rvo.getAddr());
		userInfo.setUserDesc(rvo.getDesc());
		userInfo.setAuth(authInfo);
		authInfo.setUser(userInfo);

		MSBAuth saveInfo = repository.saveAndFlush(authInfo);

		//첨부된 사진이 있을경우
		procAttachFile(rvo , saveInfo);
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

			//첨부된 사진이 있을경우
			procAttachFile(rvo , authInfo);
			return true;
		}).orElse(false);
	}
	
	@Override
	public boolean unregister(String email) throws Exception {
		AuthRepository repository = (AuthRepository) daoManager.getRepository(DAO_TYPE.AUTH);
		Optional.ofNullable(repository.findByEmail(email))
			.ifPresent(auth->{
				repository.delete(auth);
				repository.flush();
			});
		return true;
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
			if(auth.getHistory() == null){
				logger.info("getHistory is Null");
				MSBHistory history = new MSBHistory();
				hisRepository.save(history);
			}else{
				logger.info("getHistory is Not Null");
			}


			return true;
		}).orElse(false);
	}


	private void procAttachFile(UserItemVO rvo , MSBAuth authInfo){
		if(!rvo.getFiles().isEmpty()){
			List<UploadFileInfoVO> list = attachFileService.upload(rvo.getFiles() , authInfo.getEmail());
			for(UploadFileInfoVO ufv : list){
				UploadRepository uploadRepository = (UploadRepository) daoManager.getRepository(DAO_TYPE.UPLOAD);
				MSBUpload info =  uploadRepository.findByBrdIdxAndUtype(authInfo.getId() , UPLOAD_P_TYPE.REG);
				if(info == null){ //신규등록
					MSBUpload uploadData = new MSBUpload();
					uploadData.setBrd_idx(authInfo.getId());
					uploadData.setCname(ufv.getCname());
					uploadData.setOname(ufv.getOname());
					uploadData.setPath(ufv.getPath());
					uploadData.setUtype(UPLOAD_P_TYPE.REG);
					uploadData.setSize(ufv.getSize());
					uploadRepository.saveAndFlush(uploadData);
				}else{	//변경
					info.setCname(ufv.getCname());
					info.setOname(ufv.getOname());
					info.setPath(ufv.getPath());
					info.setSize(ufv.getSize());
					uploadRepository.saveAndFlush(info);
				}
			}
		}
	}

}
