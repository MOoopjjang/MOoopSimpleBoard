package com.mooop.board.controller.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.mooop.board.component.AuthenticationComponent;
import com.mooop.board.repo.DaoManager;
import com.mooop.board.service.web.AuthServiceImpl;
import com.mooop.board.service.web.BoardManagerServiceImpl;
import com.mooop.board.service.web.BoardServiceImpl;
import com.mooop.board.service.web.EventManagerServiceImpl;
import com.mooop.board.service.web.SettingServiceImpl;
import com.mooop.board.service.web.UserManagerServiceImpl;
import com.mooop.board.utils.MStringUtil;



/**
 * 공통 controller
 * 
 * @author MOoop
 *
 */
public class MSBBaseController {
	
	public static Logger errorLogger = LoggerFactory.getLogger("ERROR_LOG");
	
	
	@Autowired
	AuthServiceImpl authService;
	
	@Autowired
	BoardServiceImpl boardService;
	
	@Autowired
	UserManagerServiceImpl userManagerService;
	
	@Autowired
	EventManagerServiceImpl eventManagerService;
	
	@Autowired
	BoardManagerServiceImpl boardManagerService;
	
	@Autowired
	SettingServiceImpl settingService;
	
	@Autowired
	AuthenticationComponent authenticationComponent;
	
	@Autowired
	DaoManager daoManager;
	
	
	/**
	 * View 연동 공통 ModelAndView 객체 생성 메소드
	 * 
	 * @param viewName
	 * @param keys
	 * @param data
	 * @return
	 */
	public static ModelAndView makeModelAndView(String viewName , String[] keys , Object[] data) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		
		if(MStringUtil.validCheck(keys)) {
			for(int i = 0 ; i < keys.length ; i++) {
				mav.addObject(keys[i], data[i]);
			}
		}
		return mav;
	}

}
