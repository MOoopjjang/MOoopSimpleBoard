package com.mooop.board.enums;

/**
 * User 상태
 * 
 * @author MOoop
 *
 */
public enum USER_STATUS {
	
	WAITING ("WAITING"),		//대기
	ACTIVE ("ACTIVE"),	//가입중
	HOLD("HOLD"),
	BLOCK ("BLOCK"),			//block
	SECESSION ("SECESSION");	//탈퇴
	
	
	String status;
	
	USER_STATUS(String status){
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}

}
