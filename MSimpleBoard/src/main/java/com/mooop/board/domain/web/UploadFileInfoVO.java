package com.mooop.board.domain.web;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class UploadFileInfoVO {
	
	
	@JsonProperty("idx")
	private Long idx;
	
	@JsonProperty("brd_idx")
	private Long brd_idx;
	
	@JsonProperty("path")
	private String path;
	
	@JsonProperty("cname")
	private String cname;
	
	@JsonProperty("oname")
	private String oname;
	
	@JsonProperty("size")
	private Long size;
	

	private UploadFileInfoVO(Builder builder) {
		this.idx = builder.idx;
		this.path = builder.path;
		this.cname = builder.cname;
		this.oname = builder.oname;
		this.size = builder.size;
		this.brd_idx = builder.brd_idx;
	}
	
	
	public static Builder builder() {
		return new Builder();
	}
	
	
	
	public static class Builder{
		private Long idx;
		private Long brd_idx;
		private String path;
		private String cname;
		private String oname;
		private Long size;
		
		
		public Builder idx(Long idx) {
			this.idx = idx;
			return this;
		}
		
		public Builder brd(Long brd_idx) {
			this.brd_idx = brd_idx;
			return this;
		}
		
		public Builder path(String path) {
			this.path = path;
			return this;
		}
		
		public Builder cname(String cname) {
			this.cname = cname;
			return this;
		}
		
		public Builder oname(String oname) {
			this.oname = oname;
			return this;
		}
		
		public Builder size(Long size) {
			this.size = size;
			return this;
		}
		
		public UploadFileInfoVO build() {
			return new UploadFileInfoVO(this);
		}
		
	}

}
