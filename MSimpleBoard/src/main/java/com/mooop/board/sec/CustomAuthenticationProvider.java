package com.mooop.board.sec;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider{


	private final SecurityDetailService securityDetailService;
	public CustomAuthenticationProvider(AuthenticationUserDetailService detailService
		, SecurityDetailService securityDetailService){
		this.securityDetailService = securityDetailService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try{
			return securityDetailService.authenticate(authentication);
		}catch (BadCredentialsException e ){
			throw e;
		}catch (BlockAuthenticationException e){
			throw  e;
		}catch (HoldAuthenticationException e){
			throw e;
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.equals(authentication);
	}

}
