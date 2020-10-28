package com.mooop.board.enums;


/**
 * 
 * @author MOoop
 *
 */
public enum USER_DVIEW_MODE {
	REGISTER ("register"),
	BOARD ("board"),
	ADMIN ("admin");
	
	String mode;
	
	USER_DVIEW_MODE(String mode){
		this.mode = mode;
	}
	
	public String getMode() {
		return this.mode;
	}

}
