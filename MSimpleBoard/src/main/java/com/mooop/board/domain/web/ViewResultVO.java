package com.mooop.board.domain.web;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ViewResultVO {
	
	@JsonProperty("result")
	private String result;
	
	@JsonProperty("reason")
	private String reason;
	
	public ViewResultVO(String result , String reason) {
		this.result = result;
		this.reason = reason;
	}

}
