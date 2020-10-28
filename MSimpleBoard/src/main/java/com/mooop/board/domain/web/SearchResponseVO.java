package com.mooop.board.domain.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchResponseVO {
	
	private String category;
	
	private String text;
	
	public SearchResponseVO(String category , String text) {
		this.category = category;
		this.text = text;
	}

}
