package com.mooop.board.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mooop.board.domain.web.AuthenticationResponseVO;
import com.mooop.board.service.web.AuthServiceImpl;
import com.mooop.board.utils.MSecurityUtil;


/**
 * 인증과 관련된 기능제공
 * 
 * 
 * @author MOoop
 *
 */


@Component("authenticationComponent")
public class AuthenticationComponent {
	
	@Autowired
	AuthServiceImpl authService;
	
	/**
	 * 현재 로그인사용자의 정보를 AuthenticationResponseVO로 반환한다
	 * 
	 * @return AuthenticationResponseVO
	 */
	public AuthenticationResponseVO makeCurrentAuthentication() {
		AuthenticationResponseVO arvo = null;
		try {
			arvo = new AuthenticationResponseVO();
			arvo.setEmail(MSecurityUtil.username());
			arvo.setRole(authService.getRole(MSecurityUtil.username()));
			arvo.setName(authService.getEmailNick().getUser().getUserName());
			arvo.setNick(authService.getEmailNick().getUser().getUserNick());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return arvo;
	}

}
