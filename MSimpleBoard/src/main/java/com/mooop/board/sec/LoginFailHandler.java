package com.mooop.board.sec;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


/**
 * 
 * @author MOoop
 *
 */
public class LoginFailHandler implements AuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		if(exception instanceof BlockAuthenticationException){
			response.sendRedirect(request.getContextPath()+"/common/error/block");
		}else if(exception instanceof HoldAuthenticationException){
			response.sendRedirect(request.getContextPath()+"/common/error/hold");
		}else{
			response.sendRedirect(request.getContextPath()+"/login?error=Y");
		}
	}

}
