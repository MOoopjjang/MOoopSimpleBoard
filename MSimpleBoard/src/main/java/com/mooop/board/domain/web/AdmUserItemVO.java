package com.mooop.board.domain.web;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdmUserItemVO extends UserItemVO{
	
	@JsonProperty("uploadCount")
	private Integer uploadCount;

}
