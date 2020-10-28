package com.mooop.board.config;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.mooop.board.exception.AsyncExceptionHandler;


/**
 * 
 * Async Thread에 대한 Pool설정
 * 
 * @author MOoop
 *
 */

@Configuration
@EnableAsync
public class AsyncThreadPoolConfig extends AsyncConfigurerSupport{
	
	@Autowired
	private AsyncExceptionHandler asyncExceptionHandler;
	
	@Bean(name = "asyncThreadPoolTaskExecutor")
	public Executor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor tp = new ThreadPoolTaskExecutor();
		tp.setCorePoolSize(2);
		tp.setMaxPoolSize(10);
		tp.setQueueCapacity(10);
		tp.setThreadNamePrefix("asyncThreadPoolTaskExecutor-");
		tp.initialize();
		return tp;
	}
	
	/**
	 * AsyncThread에서 exception 발생시 비지니스 로직을 추가하기 위한 custom handler 반환
	 */
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return asyncExceptionHandler;
	}
}
