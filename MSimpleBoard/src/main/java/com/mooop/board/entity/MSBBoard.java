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
public class MSBBoard extends BaseEntity implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	
//	@Column(name = "DT_CREATE")
//	@Temporal(TemporalType.TIMESTAMP)
//	@ColumnDefault("CURRENT_TIMESTAMP")
//	private Date dtCreate;
//
//
//	@Column(name = "DT_UPDATE")
//	@Temporal(TemporalType.TIMESTAMP)
//	@ColumnDefault("CURRENT_TIMESTAMP")
//	private Date dtUpdate;
	
	
	@JoinColumn(name = "USER_ID" )
	@ManyToOne
	private MSBUser user;
}
