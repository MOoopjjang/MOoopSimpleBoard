package com.mooop.board.sec;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.mooop.board.utils.MSecurityUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider{
	
	@Autowired
	private AuthenticationUserDetailService detailService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		log.debug("#####  authenticate username : "+username+" , password : "+password);
		
		return Optional.ofNullable(detailService.loadUserByUsername(username))
					.filter(userDetails->userDetails.getUsername().equals(username))
					.map(userDetails->{
						Map<String , Object> m = new HashMap<>();
						m.put("pwd" , MSecurityUtil.makeRawPassword(username , password));
						m.put("userDetails", userDetails);
						return m;
					})
					.filter(m->MSecurityUtil.matches((String)m.get("pwd"), ((UserDetails)m.get("userDetails")).getPassword()))
					.map(m->{
						UserDetails userDetails = (UserDetails) m.get("userDetails");
						return (Collection<GrantedAuthority>) userDetails.getAuthorities();
					}).filter(gl->gl != null && gl.size() > 0)
					.map(gl->new UsernamePasswordAuthenticationToken(username,MSecurityUtil.makeRawPassword(username , password), gl))
					.orElseThrow(()->new BadCredentialsException(username));
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.equals(authentication);
	}

}
