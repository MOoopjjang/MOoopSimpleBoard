package com.mooop.board.enums;


/**
 * 계시글 상세화면 접근모드
 * 
 * 
 * @author MOoop
 *
 */
public enum BOARD_DVIEW_MODE {
	
	REGISTER ("register"),
	DVIEW ("dview");
	
	
	String mode;
	
	BOARD_DVIEW_MODE(String mode){
		this.mode = mode;
	}
	
	public String getMode() {
		return this.mode;
	}

}
