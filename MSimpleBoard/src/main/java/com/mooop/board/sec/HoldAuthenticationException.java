package com.mooop.board.sec;

import org.springframework.security.core.AuthenticationException;

/**
 * Project : MSimpleBoard
 * Package :com.mooop.board.sec
 * Author :  MOoop
 * Date : 19/01/2022
 * Desc : 계정 hold exception
 */
public class HoldAuthenticationException extends AuthenticationException {
    public HoldAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public HoldAuthenticationException(String msg) {
        super(msg);
    }
}
