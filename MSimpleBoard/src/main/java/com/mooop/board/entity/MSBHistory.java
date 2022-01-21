package com.mooop.board.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "HIS_ID")
	private Long id;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING , pattern="yyyy-MM-ddHH:mm:ss" , timezone="Asia/Seoul")
	@CreationTimestamp
	@Column(name = "CREATE_DT")
	private LocalDateTime createDt;
	
	
	@JoinColumn(name = "AUTH_ID")
	@OneToOne
	private MSBAuth auth;
	
	@Column(name = "RETRY_COUNT")
	private int retryCount;

	@Column(name = "DEVICE")
	private String device;

	@Column(name = "CLIENT_IP")
	private String clientIp;

	@Column(name = "CONFIRM_TOKEN")
	private String confirmToken;

	@PrePersist
	public void preInit(){
		retryCount = 0;
		device = "";
		clientIp = "";
		confirmToken = "";
	}

}
