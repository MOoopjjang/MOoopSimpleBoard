package com.mooop.board.domain.web;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserItemVO {
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("password")
	private String password;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("role")
	private String role;
	
	@JsonProperty("userName")
	private String userName;
	
	@JsonProperty("nickName")
	private String nickName;
	
	@JsonProperty("addr")
	private String addr;
	
	@JsonProperty("enable")
	private String enable;
	
	@JsonProperty("desc")
	private String desc;
	
	@JsonProperty("lastLogin")
	private String lastLogin;

}
