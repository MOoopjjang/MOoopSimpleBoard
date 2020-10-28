package com.mooop.board.utils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 사용자 security 정보제공
 * 
 * @author MOoop
 *
 */

public class MSecurityUtil {
	
	public static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	private MSecurityUtil() {}
	
	/**
	 * 현재 로그인 계정반환
	 * 
	 * @return
	 */
	public static String username() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return Optional.ofNullable(authentication.getPrincipal()).map(principal->(String)principal).orElse(null);
	}
	
	
	/**
	 * 로그인 권한반환
	 * 
	 * @return
	 */
	public static List<GrantedAuthority> authority() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return  authentication.getAuthorities().stream().collect(Collectors.toList());
	}
	
	/**
	 * 로그인 유무 반환
	 * 
	 * @return
	 * @throws Exception
	 */
	public static boolean isAuthenticated(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return Optional.ofNullable(authentication.getPrincipal()).filter(principal->principal.equals("anonymousUser")==false)
																.map(principal->true).orElse(false);
	}
	
	/**
	 * 패스워드 암호화
	 * 
	 * @param rawPassword
	 * @return
	 */
	public static String encode(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}
	
	/**
	 * 패스워드 매칭
	 * 
	 * @param rawPassword
	 * @param encodedPassword
	 * @return
	 */
	public static boolean matches(CharSequence rawPassword , String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
	
	/**
	 * 암호화된 password를 생성후 반환한다
	 * 
	 * @param datas
	 * @return
	 */
	public static String makeGeneratePassowrd(String... datas) {
		StringBuilder sb = new StringBuilder();
		for(String data : datas) {
			sb.append(data);
		}
		return encode(sb.toString());
	}
	
	/**
	 *  email + password조합의 암호를 반환한다.
	 * 
	 * @param datas
	 * @return
	 */
	public static String makeRawPassword(String... datas) {
		StringBuilder sb = new StringBuilder();
		for(String data : datas) {
			sb.append(data);
		}
		return sb.toString();
	}

}
