package com.mooop.board.config;

import com.mooop.board.aop.ErrorInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 
 * 
 * 
 * @author MOoop
 *
 */
@Configuration
public class WebConfig  implements WebMvcConfigurer {
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		/* error page처리를 위한 interceptor 등록 */
		registry.addInterceptor(new ErrorInterceptor()).addPathPatterns("/**");
	}
}
