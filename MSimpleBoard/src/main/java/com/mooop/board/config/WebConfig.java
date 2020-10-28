package com.mooop.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.mooop.board.aop.ErrorInterceptor;


/**
 * 
 * 
 * 
 * @author MOoop
 *
 */
@Configuration
@EnableWebMvc
public class WebConfig  implements WebMvcConfigurer {
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		/* error page처리를 위한 interceptor 등록 */
		registry.addInterceptor(new ErrorInterceptor()).addPathPatterns("/**");
	}
	
	
	/**
	 * JSP 에서 접근할 resources경로를 설정한다
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/static/");
	}
	
	/**
	 * JSP ViewResolver 설정
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setCache(false);
		viewResolver.setViewClass(JstlView.class);
		registry.viewResolver(viewResolver);
	}
	
	
}
