package com.mooop.board.domain.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationResponseVO {
	
	private String email;
	
	private String name;
	
	private String nick;
	
	private String role;
	
	
	public AuthenticationResponseVO(String email , String name , String nick , String role) {
		this.email = email;
		this.name = name;
		this.nick = nick;
		this.role = role;
	}
	
}
