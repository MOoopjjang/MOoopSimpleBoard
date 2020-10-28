package com.mooop.board.enums;

/**
 * 관리자페이지 -> 회원관리 -> list모드
 * 
 * @author MOoop
 *
 */
public enum USER_LIST_MODE {
	ALL("ALL") , GUEST("GUEST") , BLOCK("BLOCK");
	
	String mode;
	
	USER_LIST_MODE(String mode){
		this.mode = mode;
	}
	
	public String getMode() {
		return this.mode;
	}

}
