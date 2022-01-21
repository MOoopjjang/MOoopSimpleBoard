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
	@GeneratedValue(strategy = GenerationType.AUTO)
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

//	@OneToOne(mappedBy = "user")
//	private MSBUserDetail userDetail;
}
