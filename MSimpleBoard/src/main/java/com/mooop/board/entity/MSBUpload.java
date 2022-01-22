package com.mooop.board.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.mooop.board.enums.UPLOAD_P_TYPE;
import com.mooop.board.enums.USER_STATUS;
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
public class MSBUpload extends BaseEntity{
	
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
	@Enumerated(EnumType.STRING)
	private UPLOAD_P_TYPE utype;
	
	@Column(name = "SIZE" , nullable = false)
	private Long size;
}
