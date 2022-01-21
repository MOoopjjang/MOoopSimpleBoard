package com.mooop.board.aop;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mooop.board.constants.ErrorDefines;
import com.mooop.board.constants.GlobalError;
import com.mooop.board.domain.ResponseErrorVO;
import com.mooop.board.utils.MStringUtil;

public class ErrorInterceptor extends HandlerInterceptorAdapter{
	
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if(modelAndView != null && MStringUtil.validCheck(statusCode)) {
			if(statusCode.intValue() == 400) {
				ResponseErrorVO errorVo = new ResponseErrorVO("400" , GlobalError.MSB_ERROR_400 , ErrorDefines.getErrorMessage( GlobalError.MSB_ERROR_400));
				modelAndView.setViewName("common/error");
				modelAndView.addObject("xdata", errorVo);
			}else if(statusCode.intValue() == 403) {
				ResponseErrorVO errorVo = new ResponseErrorVO("403" , GlobalError.MSB_ERROR_403 , ErrorDefines.getErrorMessage( GlobalError.MSB_ERROR_403));
				modelAndView.setViewName("common/error");
				modelAndView.addObject("xdata", errorVo);
			}else if(statusCode.intValue() == 404) {
				ResponseErrorVO errorVo = new ResponseErrorVO("404" , GlobalError.MSB_ERROR_404 , ErrorDefines.getErrorMessage( GlobalError.MSB_ERROR_404));
				modelAndView.setViewName("common/error");
				modelAndView.addObject("xdata", errorVo);
			}
		}
		super.postHandle(request, response, handler, modelAndView);
	}

}
