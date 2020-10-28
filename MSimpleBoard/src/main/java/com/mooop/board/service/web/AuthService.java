package com.mooop.board.service.web;

import com.mooop.board.domain.web.AuthenticationVO;
import com.mooop.board.domain.web.UserItemVO;
import com.mooop.board.entity.MSBAuth;

public interface AuthService {
	
	public boolean register(UserItemVO rvo) throws Exception;
	
	public boolean save(UserItemVO rvo) throws Exception;
	
	public boolean unregister(String email) throws Exception;
	
	public boolean checkEmail(String email) throws Exception;
	
	public boolean checkNick(String nick) throws Exception;
	
	public boolean checkAuthentication(AuthenticationVO avo) throws Exception;
	
	public String getRole(String email) throws Exception;
	
	public MSBAuth getEmailNick() throws Exception;
	
	public UserItemVO getAuthInfoFromEmail(String email) throws Exception;
	
	public boolean restoreLoginHistory();

}
