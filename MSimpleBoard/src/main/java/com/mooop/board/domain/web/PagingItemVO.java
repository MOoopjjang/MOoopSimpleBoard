package com.mooop.board.domain.web;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PagingItemVO {
	
	@JsonProperty("boardPagingCount")
	private Integer boardPagingCount;
	
	
	public PagingItemVO(Integer boardPagingCount) {
		this.boardPagingCount = boardPagingCount;
	}

}
