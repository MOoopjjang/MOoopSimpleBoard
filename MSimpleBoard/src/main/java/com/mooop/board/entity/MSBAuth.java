package com.mooop.board.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import com.mooop.board.enums.USER_ROLES;
import com.mooop.board.enums.USER_STATUS;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 계정정보
 * 
 * @author MOoop
 *
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "MSB_AUTH")
@DynamicInsert
public class MSBAuth implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AUTH_ID")
	private Long id;
	
	@Column(name = "EMAIL" , length = 20)
	private String email;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "ENABLE" , length = 2)
	private String enable;
	
	@Column(name = "STATUS" , length = 20)
	@Enumerated(EnumType.STRING)
	private USER_STATUS status;
	
	@Column(name = "ROLE")
	@Enumerated(EnumType.STRING)
	private USER_ROLES userRole; 
	
	@Column(name = "DT_CREATE")
	@Temporal(TemporalType.TIMESTAMP)
	@ColumnDefault("CURRENT_TIMESTAMP")
	private Date dtCreate;
	
	
	@Column(name = "DT_UPDATE")
	@Temporal(TemporalType.TIMESTAMP)
	@ColumnDefault("CURRENT_TIMESTAMP")
	private Date dtUpdate;
	
	@OneToMany(mappedBy = "auth" , cascade = CascadeType.PERSIST)
	private List<MSBHistory> historys = new ArrayList<>();
	
	@OneToOne(mappedBy ="auth" , cascade = CascadeType.PERSIST)
	private MSBUser user;
	
	
//	public MSBAuth( Builder builder) {
//		this.userId = builder.userId;
//		this.password = builder.password;
//		this.userRole = builder.userRole;
//		this.enable = builder.enable;
//	}
	
	
//	public static class Builder{
//		private String userId;
//		private String password;
//		private String enable;
//		private USER_ROLES userRole; 
//		
//		public Builder userid(String userid) {
//			this.userId = userid;
//			return this;
//		}
//		
//		public Builder password(String password) {
//			this.password = password;
//			return this;
//		}
//		
//		public Builder enable(String enable) {
//			this.enable = enable;
//			return this;
//		}
//		
//		public Builder role(USER_ROLES role) {
//			this.userRole = role;
//			return this;
//		}
//		
//		public MSBAuth build() {
//			return new MSBAuth( this );
//		}
//	}

}