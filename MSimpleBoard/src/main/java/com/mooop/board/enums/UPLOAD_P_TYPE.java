package com.mooop.board.enums;

import lombok.Getter;

/**
 * Project : MSimpleBoard
 * Package :com.mooop.board.enums
 * Author :  MOoop
 * Date : 22/09/2021
 * Desc : 첨부파일이 첨부된 경로
 */
@Getter
public enum UPLOAD_P_TYPE {

    BOARD ("board") , ADMIN("admin") , REG ("registry");

    String type;

    UPLOAD_P_TYPE(String type){
        this.type = type;
    }

}
