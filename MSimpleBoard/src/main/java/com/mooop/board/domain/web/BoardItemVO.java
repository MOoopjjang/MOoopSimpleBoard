package com.mooop.board.domain.web;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardItemVO {
	
	@JsonProperty("idx")
	private Long boardIdx;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("name")
	private String userName;
	
	@JsonProperty("nick")
	private String nick;
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("content")
	private String content;
	
	@JsonProperty("secyn")
	private String secYn;
	
//	@JsonProperty("create")
//	private Date dtCreate;

	@JsonProperty("create")
	private LocalDateTime dtCreate;
	
	@JsonProperty("hit")
	private Integer hit;
	
	@JsonProperty("uploadFileInfos")
	private List<UploadFileInfoVO> uploadFileInfos;
	
	
	public BoardItemVO(Builder builder) {
		this.boardIdx = builder.boardIdx;
		this.email = builder.email;
		this.userName = builder.userName;
		this.nick = builder.nick;
		this.title = builder.title;
		this.content = builder.content;
		this.secYn = builder.secYn;
		this.dtCreate = builder.dtCreate;
		this.hit = builder.hit;
		this.uploadFileInfos = builder.uploadFileInfos;
	}
	
	
	public static Builder builder() {
		return new Builder();
	}
	
	
	public static class Builder{
		private Long boardIdx;
		private String email;
		private String userName;
		private String nick;
		private String title;
		private String content;
		private String secYn;
//		private Date dtCreate;
		private LocalDateTime dtCreate;
		private Integer hit;
		private List<UploadFileInfoVO> uploadFileInfos;
		
		public Builder idx(Long idx) {
			this.boardIdx = idx;
			return this;
		}
		
		public Builder email(String email) {
			this.email = email;
			return this;
		}
		
		public Builder name(String userName) {
			this.userName = userName;
			return this;
		}
		
		public Builder nick(String nick) {
			this.nick = nick;
			return this;
		}
		
		public Builder title(String title) {
			this.title = title;
			return this;
		}
		
		public Builder content(String content) {
			this.content = content;
			return this;
		}
		
		public Builder sec(String secYn) {
			this.secYn = secYn;
			return this;
		}
		
//		public Builder create(Date date) {
//			this.dtCreate = date;
//			return this;
//		}

		public Builder create(LocalDateTime date) {
			this.dtCreate = date;
			return this;
		}
		
		public Builder hit(Integer hit) {
			this.hit = hit;
			return this;
		}
		
		public Builder uploadFile(List<UploadFileInfoVO> infos) {
			this.uploadFileInfos = infos;
			return this;
		}
		
		public BoardItemVO build() {
			return new BoardItemVO(this);
		}
	}
	

}
