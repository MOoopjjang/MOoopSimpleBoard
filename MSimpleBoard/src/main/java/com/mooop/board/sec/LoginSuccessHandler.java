package com.mooop.board.sec;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 
 * @author MOoop
 *
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler{
	private static Logger logger = LoggerFactory.getLogger("LoginSuccessHandler");

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		logger.debug("Login Success -- "+authentication.getName()+" - "+request.getContextPath());
		
		response.sendRedirect(request.getContextPath()+"/loginproc");
	}

}
