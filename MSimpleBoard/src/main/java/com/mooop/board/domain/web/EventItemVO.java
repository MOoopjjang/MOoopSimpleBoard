package com.mooop.board.domain.web;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



/**
 * 공지사항 view용 객체
 * 
 * @author MOoop
 *
 */

@Getter
@Setter
@NoArgsConstructor
public class EventItemVO {
	
	@JsonProperty("idx")
	private Long idx;
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("content")
	private String content;
	
	@JsonProperty("start")
	private Date dtStart;
	
	@JsonProperty("end")
	private Date dtEnd;
	
	@JsonProperty("enable")
	private String enable;

	public EventItemVO(Builder builder) {
		this.idx = builder.idx;
		this.title = builder.title;
		this.content = builder.content;
		this.dtStart = builder.dtStart;
		this.dtEnd = builder.dtEnd;
		this.enable = builder.enable;
	}
	
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder{
		private Long idx;
		private String title;
		private String content;
		private Date dtStart;
		private Date dtEnd;
		private String enable;
		
		public Builder idx(Long idx) {
			this.idx = idx;
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
		
		public Builder dtStart(Date dtStart) {
			this.dtStart = dtStart;
			return this;
		}
		
		public Builder dtEnd(Date dtEnd) {
			this.dtEnd = dtEnd;
			return this;
		}
		
		public Builder idx(String title) {
			this.idx = idx;
			return this;
		}
		
		public Builder enable(String enable) {
			this.enable = enable;
			return this;
		}
		
		public EventItemVO build() {
			return new EventItemVO(this);
		}
	}
}
