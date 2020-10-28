package com.mooop.board.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.mooop.board.model.ApiResponseData;
import com.mooop.board.utils.MStringUtil;

/**
 * 
 * AOP
 *  - 데이타 삭제/변조시에는 인증키 체크
 * 
 * @author MOoop
 *
 */

@Aspect
@Component
public class FilterAspect {
	
	@Value("${api.key}")
	private String API_KEY;
	
	@Value("${api.value}")
	private String API_VALUE;
	
	
	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void restController() {}
	
	
	@Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping) || "+
			"@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
	public void mapping() {}
	
	
	
	@Around("restController() && mapping() ")
	public Object filter(ProceedingJoinPoint pjp ) throws Throwable{
		
		HttpServletRequest request = null;
		Object responseData = null;
		for(Object arg : pjp.getArgs()) {
			if(arg instanceof HttpServletRequest) {
				request = (HttpServletRequest) arg;
				break;
			}
		}
		
		String apiToken = request.getHeader(API_KEY);
		if(MStringUtil.validCheck(apiToken) && apiToken.equals(API_VALUE)) {
			responseData = pjp.proceed();
		}else {
			ApiResponseData<String> repData = new ApiResponseData();
			repData.setResult("FAIL");
			repData.setResponseData("인증키가 유효하지 않습니다.!!!");
			responseData = new ResponseEntity<>(repData , HttpStatus.OK);
		}
		
		return responseData;
	}

}
