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
 * 로그인 이력
 * 
 * @author MOoop
 *
 */

@Getter
@Setter
@Entity
@Table(name = "MSB_HISTORY")
@DynamicInsert
public class MSBHistory implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "HIS_ID")
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "DT_LOGIN")
	private Date dtLogin;
	
	
	@JoinColumn(name = "AUTH_ID")
	@ManyToOne
	private MSBAuth auth;
	
	

}
