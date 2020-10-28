package com.mooop.board.aop;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.mooop.board.model.RequestUserParam;


/**
 * 
 * AOP 적용
 *  - 응답시간이 늦은 IF log 출력
 *  - GET , POST 적용
 *  - RestController 범위에만 적용
 * 
 * @author MOoop
 *
 */
@Aspect
@Component
public class LogAspect {
	
	private static Logger logger = LoggerFactory.getLogger(LogAspect.class);
	
	
	@Value("${lateif.timeout}")
	private int timeout;
	
	
	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void restController() {
	}
	
	@Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
	public void postmapping() {
		
	}
	
	@Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
	public void getmapping() {
		
	}
	
	
	/**
	 * 
	 * GET 요청에 대한 aop Device 적용
	 * 
	 * @param pjp
	 * @return
	 * @throws Exception
	 */
	@Around("restController() && getmapping()")
	public Object lateGetReplyLogging(ProceedingJoinPoint pjp ) throws Exception{
		HttpServletRequest request = null;
		Object responseData = null;
		
		for(Object arg : pjp.getArgs()) {
			if(arg instanceof HttpServletRequest) {
				request = (HttpServletRequest) arg;
				break;
			}
		}
		
		try {
			long startTime = System.nanoTime();
			responseData = pjp.proceed();
			loggingLateReply(startTime , request.getRequestURL().toString() , null);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		
		return responseData;
	
	}
	
	/**
	 * 
	 * POST 요청에 대한 Device 적용
	 * 
	 * @param pjp
	 * @param request
	 * @param body
	 * @return
	 * @throws Exception
	 */
	@Around("restController() && postmapping() && args(..,@javax.servlet.http.HttpServletRequest request , @org.springframework.web.bind.annotation.RequestBody body)")
	public Object lateReplyPostLogging(ProceedingJoinPoint pjp ,HttpServletRequest request  , Object body ) throws Exception{
		Object responseData = null;
		try {
			long startTime = System.nanoTime();
			responseData = pjp.proceed();
			loggingLateReply(startTime , request.getRequestURL().toString() , body);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		
		return responseData;
	}
	
	
	
	//  로그 출력
	private void loggingLateReply(long startTime , String url  , Object body) {
		long durationTime = TimeUnit.NANOSECONDS.toSeconds( System.nanoTime() - startTime);
		if(durationTime > timeout) {
			logger.info("########################################################################");
			logger.info("Late I/F "+url+" Duration Time ::"+durationTime);
			if(body != null) {
				if(body instanceof RequestUserParam) {
					RequestUserParam rup = (RequestUserParam) body;
					logger.info("name :"+rup.getName());
					logger.info("age :"+rup.getAge());
				}
			}
			logger.info("########################################################################");
		}
	}

}
