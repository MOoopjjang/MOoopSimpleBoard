package com.mooop.board.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;


/**
 * Upload 프로퍼티 
 * 
 * @author MOoop
 *
 */

@Getter
@Setter
@Component("mUploadProperties")
@ConfigurationProperties(prefix = "msp.upload")
public class MUploadProperties {
	
	/* Upload 기능 활성화 여부 */
	private Boolean enable;
	
	/* 서버에 파일이 저장되는 경로 */
	private String path;
	
	/* Upload 파일 최대 크기 ( M단위 ) */
	private Long maxSize;
	
	/* 최대 upload 카운트 */
	private Integer maxCount;

}
