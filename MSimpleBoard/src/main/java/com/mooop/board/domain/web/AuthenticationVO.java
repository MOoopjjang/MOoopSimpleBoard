package com.mooop.board.domain.web;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationVO {
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("password")
	private String password;

}
