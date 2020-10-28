package com.mooop.board.exception;

public class GlobalException extends Exception{
	
	private String status;
	private String errorCode;
	
	public GlobalException(String status , String errorCode ) {
		super();
		this.status = status;
		this.errorCode = errorCode;
	}
	
	
	public String getStatus() {
		return this.status;
	}
	
	public String getErrorCode() {
		return this.errorCode;
	}

}
