package com.mooop.board.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 
 * User 상세정보
 * 
 * @author MOoop
 *
 */

@Getter
@Setter
@Entity
//@NoArgsConstructor
@Table(name = "MSB_USER")
@DynamicInsert
public class MSBUser implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long id;
	
	@Column(name = "NAME")
	private String userName;
	
	@Column(name = "NICK")
	private String userNick;
	
	@Column(name = "ADDR")
	private String userAddr;
	
	@Column(name = "DESCRPT")
	private String userDesc;
	
	
	@OneToOne
	@JoinColumn(name = "AUTH_ID")
	private MSBAuth auth;
	
	@OneToMany(mappedBy = "user" ,cascade = CascadeType.PERSIST)
	private List<MSBBoard> boards = new ArrayList<>();
	
	
//	public MSBUser(Builder builder) {
//		this.userName = builder.userName;
//		this.userAge = builder.userAge;
//		this.userNick = builder.userNick;
//		this.userAddr = builder.userAddr;
//		this.userDesc = builder.userDesc;
//		this.auth = builder.auth;
//	}
//	
//	
//	public static Builder builder() {
//		return new Builder();
//	}
//	
//	
//	public static class Builder{
//		private String userName;
//		private Integer userAge;
//		private String userNick;
//		private String userAddr;
//		private String userDesc;
//		private MSBAuth auth;
//		
//		
//		public Builder name(String name) {
//			this.userName = name;
//			return this;
//		}
//		
//		public Builder age(int age) {
//			this.userAge = age;
//			return this;
//		}
//		
//		public Builder nick(String nick) {
//			this.userNick = nick;
//			return this;
//		}
//		
//		public Builder addr(String addr) {
//			this.userAddr = addr;
//			return this;
//		}
//		
//		public Builder desc(String desc ) {
//			this.userDesc = desc;
//			return this;
//		}
//		
//		public Builder auth(MSBAuth auth) {
//			this.auth = auth;
//			return this;
//		}
//		
//		public MSBUser build() {
//			return new MSBUser( this );
//		}
//		
//		
//	}

}
