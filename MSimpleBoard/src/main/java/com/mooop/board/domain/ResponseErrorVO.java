package com.mooop.board.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * error page에 출력될 정보객체
 * 
 * @author MOoop
 *
 */

@Getter
@Setter
@NoArgsConstructor
public class ResponseErrorVO {
	
	private String httpStatus;
	
	private String errorCode;
	
	private String reason;
	
	public ResponseErrorVO(String httpStatus , String errorCode , String reason) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.reason = reason;
	}

}
