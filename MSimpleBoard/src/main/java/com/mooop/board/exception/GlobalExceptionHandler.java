package com.mooop.board.exception;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.mooop.board.constants.ErrorDefines;
import com.mooop.board.domain.ResponseErrorVO;

/**
 * 
 * 전역적인 Exception Handler.
 * 
 * @author MOoop
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(GlobalException.class)
	public ModelAndView viewException(HttpServletRequest request , GlobalException ex){
		ResponseErrorVO errorVo = new ResponseErrorVO(ex.getStatus() 
													, ex.getErrorCode() 
													, ErrorDefines.getErrorMessage(ex.getErrorCode()));
		ModelAndView mv = new ModelAndView();
		mv.setViewName("common/error");
		mv.addObject("xdata", errorVo);
		return mv;
	}
}
