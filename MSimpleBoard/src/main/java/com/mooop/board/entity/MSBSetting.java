package com.mooop.board.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import lombok.Getter;
import lombok.Setter;

/**
 * 계시판 설정
 * 
 * @author MOoop
 *
 */


@Getter
@Setter
@Entity
@Table(name = "MSB_SETTING")
@DynamicInsert
public class MSBSetting extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SETTING_ID")
	private Long idx;
	
	@Column(name = "BOARD_PAGING_COUNT" , columnDefinition = "integer default 10")
	private Integer boardPagingCount;

	@Column(name = "N_ETC_1")
	private Integer nEtc1;
	
	@Column(name = "N_ETC_2")
	private Integer nEtc2;
	
	@Column(name = "N_ETC_3")
	private Integer nEtc3;
	
	@Column(name = "STR_ETC_1")
	private String strEtc1;
	
	@Column(name = "STR_ETC_2")
	private String strEtc2;
	
	@Column(name = "STR_ETC_3")
	private String strEtc3;
	
}
