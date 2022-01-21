package com.mooop.board.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Project : MSimpleBoard
 * Package :com.mooop.board.utils
 * Author :  MOoop
 * Date : 20/01/2022
 * Desc :
 */
public class HttpUtil {

    private HttpUtil(){}


    /**
     * Client Device 정보를 반환한다.
     *
     * @param request
     * @return
     */
    public static String getUserAgent(HttpServletRequest request){
        return request.getHeader("user-agent");
    }
}
