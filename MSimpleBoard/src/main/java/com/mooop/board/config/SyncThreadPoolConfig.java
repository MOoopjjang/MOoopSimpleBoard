package com.mooop.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class SyncThreadPoolConfig {
	
	/**
	 *  Worker Thread Pool 설정
	 *  
	 * @return
	 */
	@Bean(name = "syncThreadPoolTaskExecutor")
	public ThreadPoolTaskExecutor poolTaskExecutor() {
		ThreadPoolTaskExecutor tpte = new ThreadPoolTaskExecutor();
		tpte.setCorePoolSize(2);
		tpte.setMaxPoolSize(10);
		tpte.setQueueCapacity(20);
		tpte.setThreadNamePrefix("syncThreadPoolTaskExecutor=>");
		tpte.initialize();
		return tpte;
	}

}
