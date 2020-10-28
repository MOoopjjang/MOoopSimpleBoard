package com.mooop.board.domain;

import com.mooop.board.domain.web.AdmViewInfoVO;
import com.mooop.board.domain.web.AuthenticationResponseVO;
import com.mooop.board.domain.web.SearchResponseVO;
import com.mooop.board.domain.web.ViewInfoVO;
import com.mooop.board.domain.web.ViewResultVO;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author MOoop
 *
 * @param <T>
 */
@Getter
@Setter
public class AdmViewResponse<T> {
	
	private ViewResultVO result;
	
	private SearchResponseVO search;
	
	private AuthenticationResponseVO authenticationInfo;
	
	private AdmViewInfoVO admViewInfo;
	
	private T data;
}
