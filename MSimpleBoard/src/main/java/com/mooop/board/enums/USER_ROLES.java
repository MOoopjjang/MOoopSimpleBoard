package com.mooop.board.enums;

public enum USER_ROLES {
	
	ADMIN ("ADMIN"),
	USER ("USER"),
	GUEST ("GUEST");
	
	String role;
	
	USER_ROLES(String role){
		this.role = role;
	}
	
	
	public String getRole() {
		return this.role;
	}

}
