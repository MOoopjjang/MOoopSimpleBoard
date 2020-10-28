package com.mooop.board.enums;

/**
 * 공지사항 등록/보기/편집 모드
 * 
 * @author MOoop
 *
 */
public enum EVENT_DVIEW_MODE {
	REGISTER("register"),DVIEW("dview");
	
	String mode;
	
	EVENT_DVIEW_MODE(String mode){
		this.mode = mode;
	}
	
	public String getMode() {
		return this.mode;
	}

}
