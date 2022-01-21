package com.mooop.board.sec;

import org.springframework.security.core.AuthenticationException;

/**
 * Project : MSimpleBoard
 * Package :com.mooop.board.sec
 * Author :  MOoop
 * Date : 19/01/2022
 * Desc : 계정잠금 exception
 */
public class BlockAuthenticationException extends AuthenticationException {
    public BlockAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public BlockAuthenticationException(String msg) {
        super(msg);
    }
}
