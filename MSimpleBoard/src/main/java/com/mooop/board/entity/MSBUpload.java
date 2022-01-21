package com.mooop.board.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



/**
 * File Upload 정보 테이블
 * 
 * @author MOoop
 *
 */


@Getter
@Setter
@ToString(exclude = {"regDate"})
@Entity
@Table(name = "MSB_UPLOAD")
public class MSBUpload {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "IDX")
	private Long idx;
	
	@Column(name = "BRD_IDX" , nullable = false)
	private Long brd_idx;
	
	@Column(name = "PATH" , nullable = false)
	private String path;
	
	@Column(name = "CNAME" , nullable = false)
	private String cname;
	
	@Column(name = "ONAME" , nullable = false)
	private String oname;

	@Column(name = "UTYPE" , nullable = false)
	private String utype;
	
	@Column(name = "SIZE" , nullable = false)
	private Long size;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
	@CreationTimestamp
	@Column(name = "REG_DATE")
	private LocalDateTime regDate;

}
