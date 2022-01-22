package com.mooop.board.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.BaseSessionEventListener;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import lombok.Getter;
import lombok.Setter;


/**
 * 공지사항
 * 
 * @author MOoop
 *
 */

@Getter
@Setter
@Entity
@Table(name = "MSB_EVENT")
@DynamicInsert
public class MSBEvent extends BaseEntity {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EVENT_ID")
	private Long idx;
	
	@Column(name = "TITLE" , nullable = false)
	private String title;
	
	@Column(name = "CONTENT" , nullable = false)
	private String content;
	
	@Column(name = "DT_START" , nullable = false)
	private Date dtStart;
	
	@Column(name = "DT_END" , nullable = false)
	private Date dtEnd;
	
	@Column(name = "ENABLE")
	private String enable;

}
