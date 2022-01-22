package com.mooop.board.service.web;

import java.util.Optional;
import java.util.function.Function;

import com.mooop.board.enums.USER_STATUS;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mooop.board.domain.web.UserItemVO;
import com.mooop.board.entity.MSBAuth;
import com.mooop.board.entity.MSBHistory;
import com.mooop.board.enums.USER_ROLES;
import com.mooop.board.repo.AuthRepository;
import com.mooop.board.repo.DaoManager;
import com.mooop.board.repo.DaoManager.DAO_TYPE;
import com.mooop.board.utils.MDateUtil;
import com.mooop.board.utils.MStringUtil;

@Service("userManagerService")
public class UserManagerServiceImpl implements UserManagerService{
	private final DaoManager daoManager;
		public UserManagerServiceImpl(DaoManager daoManager) {
		this.daoManager = daoManager;
	}
	
	
	Function<MSBAuth , UserItemVO> convertAtoU = (auth)->{
		MSBHistory his = auth.getHistory();
		
		UserItemVO uivo = new UserItemVO();
		uivo.setEmail(auth.getEmail());
		uivo.setPassword(auth.getPassword());
		uivo.setRole(auth.getUserRole().getRole());
		uivo.setStatus(auth.getStatus().getStatus());
		uivo.setEnable(auth.getEnable());
		uivo.setLastLogin(MDateUtil.convertDateTimeString(his.getCreateDt()));
		
		uivo.setUserName(auth.getUser().getUserName());
		uivo.setNickName(auth.getUser().getUserNick());
		uivo.setAddr(auth.getUser().getUserAddr());
		uivo.setDesc(auth.getUser().getUserDesc());
		return uivo;
	};



	@Override
	public Page<UserItemVO> getUserList(String category, String text, Integer page, Integer size) {
		
		AuthRepository authRepository = (AuthRepository) daoManager.getRepository(DAO_TYPE.AUTH);
		
		Page<UserItemVO> info = null;
		Sort sort = new Sort(Direction.DESC , "createDt");
		if(MStringUtil.validCheck(category) && MStringUtil.validCheck(text)) {
			if(category.equals("role")) {
				info = authRepository.findAllByRole(USER_ROLES.valueOf(text) , PageRequest.of(page, size, sort)).map(convertAtoU);
			}else if(category.equals("name")) {
				info = authRepository.findByNameLike("%"+text+"%" , PageRequest.of(page, size, sort)).map(convertAtoU);
			}else if(category.equals("status")) {
				info = authRepository.findAllByStatus(text , PageRequest.of(page, size, sort)).map(convertAtoU);
			}else { // "enable"
				info = authRepository.findAllByEnable(text , PageRequest.of(page, size, sort)).map(convertAtoU);
			}
		}else {
			info = authRepository.findAll(PageRequest.of(page, size, sort)).map(convertAtoU);
		}
		return info;
	}

	@Override
	public UserItemVO getUserInfo(String email) {
		AuthRepository authRepository = (AuthRepository) daoManager.getRepository(DAO_TYPE.AUTH);
		return Optional.ofNullable(authRepository.findByEmail(email))
					.map(auth->{
						MSBHistory his = auth.getHistory();
						UserItemVO uivo = new UserItemVO();
						uivo.setEmail(auth.getEmail());
						uivo.setPassword(auth.getPassword());
						uivo.setRole(auth.getUserRole().getRole());
						uivo.setStatus(auth.getStatus().getStatus());
						uivo.setEnable(auth.getEnable());
						uivo.setLastLogin(MDateUtil.convertDateTimeString(his.getCreateDt()));
						
						uivo.setUserName(auth.getUser().getUserName());
						uivo.setNickName(auth.getUser().getUserNick());
						uivo.setAddr(auth.getUser().getUserAddr());
						uivo.setDesc(auth.getUser().getUserDesc());
						return uivo;
					}).orElse(new UserItemVO());
		
	}

	@Override
	public boolean updateUserInfo(UserItemVO uivo) {
		AuthRepository authRepository = (AuthRepository) daoManager.getRepository(DAO_TYPE.AUTH);
		return Optional.ofNullable(authRepository.findByEmail(uivo.getEmail())).map(auth->{
			auth.setUserRole( USER_ROLES.valueOf( uivo.getRole()));
			USER_STATUS setStatus = (uivo.getEnable().equals("Y"))?USER_STATUS.ACTIVE:USER_STATUS.BLOCK;
			auth.setStatus(setStatus);
			auth.setEnable(uivo.getEnable());
			authRepository.flush();
			return true;
		}).orElse(false);
	}
}
