package com.mooop.board.domain.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ViewInfoVO {
	
	private String mode;
	
	private String currentTime;
	
	public ViewInfoVO(String mode , String currentTime) {
		this.mode = mode;
		this.currentTime = currentTime;
	}

}
