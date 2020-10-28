package com.mooop.board.exception;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

/**
 * 
 * Async Thread에서 exception 발생시 catch하는 bean 
 * 
 * @author MOoop
 *
 */
@Component("asyncExceptionHandler")
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler{
	
	private static Logger errorLogger = LoggerFactory.getLogger(AsyncExceptionHandler.class);

	@Override
	public void handleUncaughtException(Throwable ex, Method method, Object... params) {
		errorLogger.error("handleUncaughtException at:"+method.getDeclaringClass().getName()+"."+method.getName() , ex);
		
	}

}
