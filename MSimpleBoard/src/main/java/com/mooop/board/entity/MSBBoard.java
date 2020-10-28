package com.mooop.board.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 글 정보
 * 
 * @author MOoop
 *
 */

@Getter
@Setter
//@NoArgsConstructor
@Entity
@Table(name = "MSB_BOARD")
@DynamicInsert
public class MSBBoard implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BRD_IDX")
	private Long id;
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "CONTENT")
	private String content;
	
	@Column(name = "SEC_YN" , length = 2)
	private String secYN;
	
	@Column(name = "HIT")
	private Integer hit;
	
	@Column(name = "DT_CREATE")
	@Temporal(TemporalType.TIMESTAMP)
	@ColumnDefault("CURRENT_TIMESTAMP")
	private Date dtCreate;
	
	
	@Column(name = "DT_UPDATE")
	@Temporal(TemporalType.TIMESTAMP)
	@ColumnDefault("CURRENT_TIMESTAMP")
	private Date dtUpdate;
	
	
	@JoinColumn(name = "USER_ID" )
	@ManyToOne
	private MSBUser user;
	
	
//	public MSBBoard( Builder builder) {
//		this.title = builder.title;
//		this.content = builder.content;
//		this.secYN = builder.secYN;
//		this.hit = new Integer(builder.hit);
//		this.user = builder.user;
//	}
//	
//	
//	public static Builder builder() {
//		return new Builder();
//	}
//	
//	public static class Builder{
//		private String title;
//		private String content;
//		private String secYN;
//		private int hit;
//		private MSBUser user;
//		
//		public Builder title(String title) {
//			this.title = title;
//			return this;
//		}
//		
//		public Builder content(String content) {
//			this.content = content;
//			return this;
//		}
//		
//		public Builder secyn(String secyn) {
//			this.secYN = secyn;
//			return this;
//		}
//		
//		public Builder hit(int hit) {
//			this.hit = hit;
//			return this;
//		}
//		
//		public Builder user(MSBUser user) {
//			this.user = user;
//			return this;
//		}
//		
//		public MSBBoard build() {
//			return new MSBBoard(this);
//		}
//	}

}
