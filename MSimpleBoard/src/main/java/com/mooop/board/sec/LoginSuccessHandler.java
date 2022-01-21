package com.mooop.board.sec;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mooop.board.constants.Defines;
import com.mooop.board.service.web.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

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

		RequestCache requestCache = new HttpSessionRequestCache();
		SavedRequest savedRequest = requestCache.getRequest(request , response);
		String redirectUrl = Optional.ofNullable(savedRequest).map(sr->{
			String t = sr.getRedirectUrl();
			requestCache.removeRequest(request , response);
			return t;
		})
		.orElseGet(()-> Defines.AUTHENTICATED_REDIRECT_URL);

		logger.info("Login Success -- "+redirectUrl);
		response.sendRedirect(redirectUrl);
	}

}
