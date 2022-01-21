package com.mooop.board.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mooop.board.constants.GlobalError;
import com.mooop.board.controller.web.MSBBaseController;
import com.mooop.board.exception.GlobalException;
import com.mooop.board.service.web.BoardServiceImpl;
import com.mooop.board.utils.MSecurityUtil;


@Controller
@RequestMapping(value="/test")
public class TestController{
	
	private static Logger logger = LoggerFactory.getLogger(TestController.class.getSimpleName());
	
	@GetMapping("/hello")
	public ModelAndView hello(HttpServletRequest request) throws GlobalException{
		
		Map<String , Object> m = new HashMap<>();
		try {
			m.put("result", "OK");
			ModelAndView mav = new ModelAndView();
			mav.setViewName("test/hello");
			return mav;
		}catch(Exception e) {
			throw new GlobalException(request.getParameter("status"), GlobalError.MSB_ERROR_500);
		}
		
	}
	
	
//	@GetMapping("/register")
//	public ModelAndView registerPage(HttpServletRequest request) throws Exception{
//		
//		ViewResponse<UserItemVO> cr = new ViewResponse<>();
//		cr.setMode(Optional.ofNullable(request.getParameter("mode")).orElse("register"));
//		cr.setRole(Optional.ofNullable(request.getParameter("role")).orElse("USER"));
//		
//		if(cr.getMode().equals("dview")) {
//			UserItemVO meta = new UserItemVO();
//			meta.setUserName("김철우");
//			meta.setAddr("궈월동");
//			meta.setEmail("aaa@bbb.com");
//			meta.setNickName("길손");
//			meta.setRole("USER");
//			meta.setDesc("하이 everyone!!!");
//			
//			cr.setData(meta);
//		}
//		
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("xdata", cr);
//		mav.setViewName("login/registry");
//		return mav;
//	}
	
	
	@GetMapping("/login")
	public ModelAndView loginPage(HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login/login");
		return mav;
	}
	
	
	@Autowired
	private BoardServiceImpl boardService;
	
	
//	@GetMapping("/main")
//	public ModelAndView main(@RequestParam("pvo") PagingSearchVO pvo , HttpServletRequest request) {
//		Integer nPage = Optional.ofNullable(pvo.getPage()).orElse(0);
//		Integer nSize = Optional.ofNullable(pvo.getSize()).orElse(10);
//		CommonRequest<Page<BoardItemVO>> requestData = new CommonRequest<>();
//		try {
//			Page<BoardItemVO> result = boardService.getBoardItemList(pvo);
//			
//			logger.info("=====================================================================");
//			logger.info("getNumber :: "+result.getNumber());
//			logger.info("getSize :: "+result.getSize());
//			logger.info("getTotalElements :: "+result.getTotalElements());
//			logger.info("getTotalPages :: "+result.getTotalPages());
//			logger.info("=====================================================================");
//			
//			requestData.setData(result);
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		
//		
//		
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("xdata", requestData);
//		mav.setViewName("board/main");
//		return mav;
//	}
	
	
	@GetMapping("/sec/username")
	@ResponseBody
	public Map<String , Object> getSecTest(){
		
		Map<String , Object> r = new HashMap<>();
		try {
			logger.info("username : "+MSecurityUtil.username());
			logger.info("password : "+MSecurityUtil.authority());
			
//			Map<String , Object> r = new HashMap<String , Object>();
			r.put("username", MSecurityUtil.username());
			r.put("password", MSecurityUtil.authority());
			r.put("result", "OK");
		}catch(Exception e) {
			e.printStackTrace();
			r.put("result", "FAILED");
		}
		
		return r;
	}
	
	
	@GetMapping("/log")
	public void loggerLevelTest() {
		
		logger.info("INFO level");
		logger.debug("DEBUG level");
		logger.warn("WARN level");
		
		
	}
	
	
	@GetMapping("/popup1")
	public ModelAndView popupTest(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("test/popup_test");
		return mav;
	}
	
	
	@PostMapping("/popup_template")
	public ModelAndView popup_1(@RequestParam(required = true) String title , @RequestParam(required = false) String bodyHTML
			,@RequestParam (required = false) String buttonTexts) {
		logger.info("/popup_template");
		
		return MSBBaseController.makeModelAndView("test/popup_template", new String[] {"title" , "body" , "buttons"}
								, new Object[] {title , bodyHTML , buttonTexts});
	}
	
	
	@PostMapping("/simple1")
	public ModelAndView simple1(@RequestParam(required = true) String title , @RequestParam(required = false) String bodyHTML
			,@RequestParam (required = false) String buttonTexts) {
		logger.info("/popup_1");
		
		ModelAndView mav = new ModelAndView();
//		mav.addObject("xdata", vpiv);
		mav.setViewName("test/simple1");
		return mav;
	}


	@GetMapping("/edit")
	public ModelAndView edit(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("test/edit_tst");
		return mav;
	}

}
