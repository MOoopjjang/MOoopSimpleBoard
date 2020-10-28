package com.mooop.board.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseVO<T> {
	
	
	private String result;
	
	private String reason;
	
	private T data;
	
	
	public ResponseVO(Builder<T> builder) {
		this.result = builder.result;
		this.reason = builder.reason;
		this.data = builder.data;
	}
	
	
	public static Builder builder() {
		return new Builder();
	}
	
	
	public static class Builder<T>{
		private String result;
		private String reason;
		private T data;
		
		
		public Builder result(String result) {
			this.result = result;
			return this;
		}
		
		public Builder reason(String reason) {
			this.reason = reason;
			return this;
		}
		
		public Builder data(T data) {
			this.data = data;
			return this;
		}
		
		public ResponseVO<T> build() {
			return new ResponseVO<T>(this);
		}
		
	}

}
