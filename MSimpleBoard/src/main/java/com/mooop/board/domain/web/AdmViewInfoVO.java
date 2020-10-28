package com.mooop.board.domain.web;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdmViewInfoVO extends ViewInfoVO{
	
	@JsonProperty("callUrl")
	private String callUrl;
	
	
	public AdmViewInfoVO(String callUrl , String mode , String currentTime) {
		super(mode , currentTime);
		this.callUrl = callUrl;
	}
}
