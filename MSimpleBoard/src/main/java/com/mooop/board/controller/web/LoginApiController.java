package com.mooop.board.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mooop.board.domain.ResponseVO;
import com.mooop.board.domain.web.AuthenticationVO;
import com.mooop.board.domain.web.UserItemVO;
import com.mooop.board.service.web.AuthServiceImpl;


/**
 * 사용자 인증 , 등록
 * 
 * @author MOoop
 *
 */


@RestController
@RequestMapping(value="/login/api")
public class LoginApiController extends MSBBaseController{
	
	
	/**
	 * User등록
	 * 
	 * @param rvo
	 * @return
	 */
	@PostMapping("/register")
	@SuppressWarnings("unchecked")
	public ResponseVO<String> register(UserItemVO rvo , HttpServletRequest request , HttpServletResponse response) {
		ResponseVO<String> result = new ResponseVO<>();
		try {
			if(authService.register(rvo)) {
				result = ResponseVO.builder().result("OK").reason("").build();
			}else {
				result = ResponseVO.builder().result("FAIL").reason("").build();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * User 정보변경
	 * 
	 * @param rvo
	 * @return
	 */
	@PostMapping("/save")
	public ResponseVO<String> save(UserItemVO rvo , HttpServletRequest request , HttpServletResponse response){
		ResponseVO<String> result = new ResponseVO<>();
		try {
			if(authService.save(rvo)) {
				result = ResponseVO.builder().result("OK").reason("").build();
			}else {
				result = ResponseVO.builder().result("FAIL").reason("").build();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	/**
	 * User 탈퇴
	 * 
	 * @param email
	 * @return
	 */
	@PostMapping("/unregister")
	public ResponseVO<String> unregister(@RequestBody String email){
		ResponseVO<String> result = new ResponseVO<>();
		try {
			if(authService.unregister(email)) {
				result = ResponseVO.builder().result("OK").reason("").build();
			}else {
				result = ResponseVO.builder().result("FAIL").reason("").build();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	
	
	/**
	 * 
	 * @param email
	 * @return
	 */
	@GetMapping("/check/email/{email:.+}/")
	@SuppressWarnings("unchecked")
	public ResponseVO<String> checkEmail(@PathVariable String email  , HttpServletRequest request){
		
		ResponseVO<String> result = null;
		try {
			String strExist = authService.checkEmail(email)?"exist":"not";
			result = ResponseVO.builder().result("OK").reason(strExist).data("").build();
		}catch(Exception e) {
			e.printStackTrace();
			result = ResponseVO.builder().result("FAIL").reason("exception").data("").build();
		}
		return result;
	}
	
	/**
	 * 
	 * @param nick
	 * @return
	 */
	@GetMapping("/check/nick/{nick}")
	@SuppressWarnings("unchecked")
	public ResponseVO<String> checkNick(@PathVariable String nick , HttpServletRequest request){
		
		ResponseVO<String> result = null;
		try {
			String strExist = authService.checkNick(nick)?"exist":"not";
			result = ResponseVO.builder().result("OK").reason(strExist).data("").build();
		}catch(Exception e) {
			e.printStackTrace();
			result = ResponseVO.builder().result("FAIL").reason("exception").data("").build();
		}
		return result;
	}
	
	/**
	 * 사용자 인증
	 * 
	 * @param avo
	 * @param request
	 * @return
	 */
	@PostMapping("/check/auth")
	public ResponseVO<String> checkAuthentication(@RequestBody AuthenticationVO avo , HttpServletRequest request){
		
		String strResult = null;
		String reason = null;
		try {
			strResult = "OK";
			reason = authService.checkAuthentication(avo)?"equal":"nequal";
		}catch(Exception e) {
			e.printStackTrace();
			strResult = "OK";
			reason = "exception";
		}
		return ResponseVO.builder().result(strResult).reason(reason).data(null).build();
	}
	

}
