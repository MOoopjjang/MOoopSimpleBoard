package com.mooop.board.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


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
public class MSBUser extends BaseEntity implements Serializable{
	
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
	
	@OneToMany(fetch = FetchType.LAZY , mappedBy = "user" ,cascade = CascadeType.ALL)
	private List<MSBBoard> boards = new ArrayList<>();

}
