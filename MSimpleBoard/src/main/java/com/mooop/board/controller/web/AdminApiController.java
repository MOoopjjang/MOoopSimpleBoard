package com.mooop.board.controller.web;

import javax.servlet.http.HttpServletRequest;

import com.mooop.board.constants.Defines;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mooop.board.domain.ResponseVO;
import com.mooop.board.domain.web.AuthorityItemVO;
import com.mooop.board.domain.web.BoardItemVO;
import com.mooop.board.domain.web.EventItemVO;
import com.mooop.board.domain.web.PagingItemVO;
import com.mooop.board.domain.web.UserItemVO;


/**
 * 관리자페이지 연동
 * 
 * @author MOoop
 *
 */

@RestController
@RequestMapping(value="/admin/api")
public class AdminApiController extends MSBBaseController{
	
	
	/**
	 * 사용자 개인정보를 갱신한다
	 * 
	 * @param uivo
	 * @return
	 */
	@PostMapping("/user/update")
	public ResponseVO<String> userInfoUpdate(@RequestBody UserItemVO uivo ){
		
		ResponseVO<String> result = null;
		try {
			if(userManagerService.updateUserInfo(uivo)) {
				result = ResponseVO.builder().result("OK").reason("").data(null).build();
			}else {
				result = ResponseVO.builder().result("FAILED").reason("").data(null).build();
			}
		}catch(Exception e) {
			e.printStackTrace();
			result = ResponseVO.builder().result("FAILED").reason(e.toString()).data(null).build();
		}
		
		return result;
	}
	
	/**
	 * 새로운 공지사항을 등록한다
	 * 
	 * @param eivo
	 * @return
	 */
	@PostMapping("/event/register")
	public ResponseVO<String> eventRegister(@RequestBody EventItemVO eivo){
		ResponseVO<String> result = null;
		try {
			if(eventManagerService.registerEventItem(eivo)) {
				result = ResponseVO.builder().result("OK").reason("").data(null).build();
			}else {
				result = ResponseVO.builder().result("FAILED").reason("").data(null).build();
			}
		}catch(Exception e) {
			e.printStackTrace();
			result = ResponseVO.builder().result("FAILED").reason(e.toString()).data(null).build();
		}
		
		return result;
	}
	
	/**
	 * 변경된 공지사항을 갱신한다
	 * 
	 * @param eivo
	 * @return
	 */
	@PostMapping("/event/update")
	public ResponseVO<String> eventUpdate(@RequestBody EventItemVO eivo){
		ResponseVO<String> result = null;
		try {
			if(eventManagerService.updateEventItem(eivo)) {
				result = ResponseVO.builder().result("OK").reason("").data(null).build();
			}else {
				result = ResponseVO.builder().result("FAILED").reason("").data(null).build();
			}
		}catch(Exception e) {
			e.printStackTrace();
			result = ResponseVO.builder().result("FAILED").reason(e.toString()).data(null).build();
		}
		
		return result;
	}
	
	
	/**
	 * 공지사항을 삭제한다
	 * 
	 * @param eivo
	 * @return
	 */
	@PostMapping("/event/delete")
	public ResponseVO<String> eventDelete(@RequestBody EventItemVO eivo){
		ResponseVO<String> result = null;
		try {
			if(eventManagerService.deleteEventItem(eivo)) {
				result = ResponseVO.builder().result("OK").reason("").data(null).build();
			}else {
				result = ResponseVO.builder().result("FAILED").reason("").data(null).build();
			}
		}catch(Exception e) {
			e.printStackTrace();
			result = ResponseVO.builder().result("FAILED").reason(e.toString()).data(null).build();
		}
		
		return result;
	}
	
	
	/**
	 * 계시글 삭제한다
	 * 
	 * @param idx
	 * @return
	 */
	@PostMapping("/board/remove")
	public ResponseVO<String> removeBoardItem(@RequestBody BoardItemVO bivo  , HttpServletRequest request){
		ResponseVO<String> result = null;
		try {
			if(boardManagerService.removeBoardItemInfo(bivo.getBoardIdx())) {
				result = ResponseVO.builder().result("OK").reason(Defines.EMPTY_STRING).data(null).build();
			}else {
				result = ResponseVO.builder().result("FAILED").reason(Defines.EMPTY_STRING).data(null).build();
			}
		}catch(Exception e) {
			e.printStackTrace();
			result = ResponseVO.builder().result("FAILED").reason(e.toString()).data(null).build();
		}
		
		return result;
	}
	
	/**
	 * 새로운 권한을 추가한다
	 * 
	 * @param aivo
	 * @param request
	 * @return
	 */
	@PostMapping("/setting/authority/add")
	public ResponseVO<String> newAuthorityAdd(@RequestBody AuthorityItemVO aivo , HttpServletRequest request){
		ResponseVO<String> result = null;
		try {
			if(!settingService.isPossibleNewAuthority(aivo)) {
				return ResponseVO.builder().result("FAILED").reason("권한을 추가할수있는 최대개수에 도달했거나,중복된 권한이 있습니다.\n다시 등록해주세요").data(null).build();
			}
			
			if(settingService.addNewAuthority(aivo)) {
				result = ResponseVO.builder().result("OK").reason("").data(null).build();
			}else {
				result = ResponseVO.builder().result("FAILED").reason("").data(null).build();
			}
		}catch(Exception e) {
			e.printStackTrace();
			result = ResponseVO.builder().result("FAILED").reason(e.toString()).data(null).build();
		}
		
		return result;
	}
	
	/**
	 * 권한을 삭제한다
	 * 
	 * @param aivo
	 * @param request
	 * @return
	 */
	@PostMapping("/setting/authority/del")
	public ResponseVO<String> removeAuthority(@RequestBody  AuthorityItemVO aivo , HttpServletRequest request){
		ResponseVO<String> result = null;
		try {
			if(!settingService.isPossibleRemoveAuthority()) {
				return ResponseVO.builder().result("FAILED").reason("더이상권한을 삭제할수 없습니다").data(null).build();
			}
			
			if(settingService.removeAuthority(aivo)) {
				result = ResponseVO.builder().result("OK").reason("").data(null).build();
			}else {
				result = ResponseVO.builder().result("FAILED").reason("").data(null).build();
			}
		}catch(Exception e) {
			e.printStackTrace();
			result = ResponseVO.builder().result("FAILED").reason(e.toString()).data(null).build();
		}
		
		return result;
	}
	
	
	@PostMapping("/setting/paging/update")
	public ResponseVO<String> updatePaging(@RequestBody PagingItemVO pivo){
		ResponseVO<String> result = null;
		try {
			if(settingService.updatePagingInfo(pivo)) {
				result = ResponseVO.builder().result("OK").reason("").data(null).build();
			}else {
				result = ResponseVO.builder().result("FAILED").reason("").data(null).build();
			}
		}catch(Exception e) {
			e.printStackTrace();
			result = ResponseVO.builder().result("FAILED").reason(e.toString()).data(null).build();
		}
		
		return result;
	}

}
