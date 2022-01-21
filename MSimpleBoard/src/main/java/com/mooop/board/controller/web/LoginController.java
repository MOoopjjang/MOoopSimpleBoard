package com.mooop.board.controller.web;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mooop.board.constants.Defines;
import com.mooop.board.constants.GlobalError;
import com.mooop.board.domain.ViewResponse;
import com.mooop.board.domain.web.AuthenticationResponseVO;
import com.mooop.board.domain.web.UserItemVO;
import com.mooop.board.domain.web.ViewInfoVO;
import com.mooop.board.enums.USER_DVIEW_MODE;
import com.mooop.board.exception.GlobalException;
import com.mooop.board.utils.MDateUtil;
import com.mooop.board.utils.MSecurityUtil;
import com.mooop.board.utils.MStringUtil;

/**
 * 로그인 , 사용자 등록 페이지 연동
 * 
 * @author MOoop
 *
 */

@Controller
public class LoginController extends MSBBaseController{
	private static Logger logger = LoggerFactory.getLogger("LoginController");
	
	@GetMapping("/")
	public String root(){
		return (MSecurityUtil.isAuthenticated())?"redirect:"+Defines.AUTHENTICATED_REDIRECT_URL:"redirect:/login";
	}
	
	@GetMapping(value = "/login")
	public ModelAndView login(HttpServletRequest request , HttpServletResponse response) throws GlobalException{
		
		String viewUrl = null;
		
		try {
			if(MSecurityUtil.isAuthenticated()) {
				viewUrl = "redirect:"+Defines.AUTHENTICATED_REDIRECT_URL;
				return makeModelAndView(viewUrl ,null, null);
			}else {
				viewUrl = "login/login";
				return makeModelAndView(viewUrl , new String[] {"error"}
				, new Object[] {MStringUtil.defaultIfEmptyString(request.getParameter("error"), "N")});
			}
		}catch(Exception e) {
			throw new GlobalException("500", GlobalError.MSB_ERROR_500);
		}
	}
	

	/**
	 * 로그아웃 처리
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest request , HttpServletResponse response) {
		Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
					.ifPresent(auth->{
						new SecurityContextLogoutHandler().logout(request, response, auth);
					});
		
		return makeModelAndView("redirect:"+Defines.AUTHENTICATED_REDIRECT_URL ,null, null);
	}
	
	
	/**
	 * 새로운 USER등록 화면이동
	 * 
	 * @return
	 * @throws GlobalException
	 */
	@GetMapping("/login/registry")
	public ModelAndView registy() throws GlobalException {
		try {
			ViewResponse<UserItemVO> resViewData = new ViewResponse<>(); 
			resViewData.setViewInfo(new ViewInfoVO(USER_DVIEW_MODE.REGISTER.getMode(), MDateUtil.currentDateTime("yyyy-MM-dd HH:mm")));
			return makeModelAndView("login/registry", new String[] {"xdata"}, new Object[] {resViewData});
		}catch(Exception e) {
			throw new GlobalException("500" , GlobalError.MSB_ERROR_500);
		}
		
	}
	
	/**
	 * 메인페이지 -> 유저상세보기
	 * 
	 * @return
	 */
	@GetMapping("/login/dview")
	public ModelAndView dview() throws GlobalException{
		
		ViewResponse<UserItemVO> resViewData = new ViewResponse<>(); 
		try {
			UserItemVO rvoData = authService.getAuthInfoFromEmail(MSecurityUtil.username());
			resViewData.setAuthentication(authenticationComponent.makeCurrentAuthentication());
			
			resViewData.setViewInfo(new ViewInfoVO(USER_DVIEW_MODE.BOARD.getMode(), MDateUtil.currentDateTime("yyyy-MM-dd HH:mm")));
			resViewData.setData(rvoData);
		}catch(Exception e) {
			throw new GlobalException("500" , GlobalError.MSB_ERROR_500);
		}
		
		
		return makeModelAndView("login/registry", new String[] {"xdata"}, new Object[] {resViewData});
	}

}
