package com.mooop.board.domain;

import java.io.Serializable;

import com.mooop.board.domain.web.AuthenticationResponseVO;
import com.mooop.board.domain.web.SearchResponseVO;
import com.mooop.board.domain.web.ViewInfoVO;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ViewResponse<T> implements Serializable{
	
	private ViewInfoVO viewInfo;
	
	private AuthenticationResponseVO authentication;
	
	private SearchResponseVO search;
	
	private T data;

}
