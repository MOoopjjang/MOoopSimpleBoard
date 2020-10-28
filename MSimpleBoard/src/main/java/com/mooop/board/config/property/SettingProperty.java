package com.mooop.board.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * 관리자페이지의 설정 property
 * 
 * @author MOoop
 *
 */

@Getter
@Setter
@ConfigurationProperties(prefix = "msb.setting")
public class SettingProperty {
	
	/**
	 * 관리자권한 최대 개수
	 */
	private Integer authorityMaxCount;
	
	/**
	 * 관리자권한 최소 개수
	 */
	private Integer authorityMinCount;
	
	/**
	 * 계시판에 보여주는 목록개수
	 */
	private Integer pagingCount;

}
