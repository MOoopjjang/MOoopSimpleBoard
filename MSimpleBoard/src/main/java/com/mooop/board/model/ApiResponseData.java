package com.mooop.board.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseData<T> {
	
	private String result;
	
	private T responseData;

}
