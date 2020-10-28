package com.mooop.board.service.web;

import org.springframework.data.domain.Page;

import com.mooop.board.domain.web.UserItemVO;

public interface UserManagerService {

	/**
	 * 등록된 USER 목록을 가져온다
	 * 
	 * @param category
	 * @param text
	 * @param page
	 * @param size
	 * @return
	 */
	Page<UserItemVO> getUserList(String category , String text , Integer page , Integer size);
	
	
	/**
	 * USER 상세정보를 가져온다
	 * 
	 * @param email
	 * @return
	 */
	UserItemVO getUserInfo(String email);
	
	
	/**
	 * 사용자 정보 갱신
	 * 
	 * @param uivo
	 * @return
	 */
	boolean updateUserInfo(UserItemVO uivo);
	
}
