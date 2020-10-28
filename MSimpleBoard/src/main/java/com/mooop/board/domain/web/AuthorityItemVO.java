package com.mooop.board.domain.web;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthorityItemVO {
	
	@JsonProperty("authorityName")
	private String authorityName;
	
	@JsonProperty("authorityDesc")
	private String authorityDesc;

}
