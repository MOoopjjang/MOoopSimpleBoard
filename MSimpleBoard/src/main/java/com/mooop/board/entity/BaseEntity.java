package com.mooop.board.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Project : MSimpleBoard
 * Package :com.mooop.board.entity
 * Author :  MOoop
 * Date : 21/01/2022
 * Desc :
 */
@Getter
@Setter
@MappedSuperclass
abstract public class BaseEntity {

    @JsonFormat(shape=JsonFormat.Shape.STRING , pattern="yyyy-MM-ddHH:mm:ss" , timezone="Asia/Seoul")
	@CreationTimestamp
	@Column(name = "CREATE_DT")
    LocalDateTime createDt;

    @JsonFormat(shape=JsonFormat.Shape.STRING , pattern="yyyy-MM-ddHH:mm:ss" , timezone="Asia/Seoul")
	@CreationTimestamp
	@Column(name = "UPDATE_DT")
    LocalDateTime updateDt;

    @PreUpdate
    public void preUpdate(){
        updateDt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

}
