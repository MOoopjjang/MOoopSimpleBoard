package com.mooop.board.controller.web;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mooop.board.constants.Defines;
import com.mooop.board.constants.GlobalError;
import com.mooop.board.domain.ViewResponse;
import com.mooop.board.domain.web.BoardItemVO;
import com.mooop.board.domain.web.SearchResponseVO;
import com.mooop.board.domain.web.ViewInfoVO;
import com.mooop.board.entity.MSBAuth;
import com.mooop.board.enums.BOARD_DVIEW_MODE;
import com.mooop.board.exception.GlobalException;
import com.mooop.board.utils.MDateUtil;


/**
 * 
 * 계시판 연동
 * 
 * @author MOoop
 *
 */

@Controller
@RequestMapping(value="/board")
public class BoardController extends MSBBaseController{
	
	private static Logger logger = LoggerFactory.getLogger("BoardController");
	
	
	
	/**
	 *  계시판 첫페이지로 redirect
	 *  
	 * @param request
	 * @return
	 */
	@GetMapping("/")
	public String root(HttpServletRequest request) {
		return "redirect:"+Defines.AUTHENTICATED_REDIRECT_URL;
	}
	
	
	/**
	 * 계시판 목록조회
	 * 
	 * @param page
	 * @param size
	 * @param request
	 * @return
	 */
	@GetMapping("/main")
	public ModelAndView main(
			@RequestParam String category 
			, @RequestParam String text  
			, @RequestParam Integer page
			, @RequestParam Integer size
			, HttpServletRequest request) throws GlobalException{
		
		ViewResponse<Page<BoardItemVO>> resViewData = new ViewResponse<>();
		
		try {
			Page<BoardItemVO> result = boardService.getBoardItemList(category , text , page , size);
		
			resViewData.setViewInfo(new ViewInfoVO("", MDateUtil.currentDateTime("yyyy-MM-dd HH:mm")));
			resViewData.setSearch(new SearchResponseVO(category, text));
			resViewData.setAuthentication(authenticationComponent.makeCurrentAuthentication());
			
			resViewData.setData(result);
		}catch(Exception e) {
			e.printStackTrace();
			throw new GlobalException("500" , GlobalError.MSB_ERROR_500);
		}
		
		return makeModelAndView("board/main", new String[] {"xdata"}, new Object[] {resViewData});
	}
	
	
	/**
	 * 상세보기
	 * 
	 * @param idx
	 * @param request
	 * @return
	 */
	@GetMapping("/dview")
	public ModelAndView dview(@RequestParam Long idx ,HttpServletRequest request) throws GlobalException{
		Long nIdx = Optional.ofNullable(idx).orElse(0L);
		logger.info("nIdx : "+nIdx);
		ViewResponse<BoardItemVO> resViewData = new ViewResponse<>();
		resViewData.setViewInfo(new ViewInfoVO(BOARD_DVIEW_MODE.DVIEW.getMode(), MDateUtil.currentDateTime("yyyy-MM-dd HH:mm")));
		resViewData.setAuthentication(authenticationComponent.makeCurrentAuthentication());
		try {
			BoardItemVO boardInfo = boardService.getBoardItem(idx);
			resViewData.setData(boardInfo);
		}catch(Exception e) {
			e.printStackTrace();
			throw new GlobalException("500" , GlobalError.MSB_ERROR_500);
		}
		
		return makeModelAndView("board/boardform", new String[] {"xdata"}, new Object[] {resViewData});
	}
	
	/**
	 * 새글 작성
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping("/nview")
	public ModelAndView nview(HttpServletRequest request ) throws GlobalException{
		
		ViewResponse<BoardItemVO> resViewData = new ViewResponse<>();
		try {
			
			MSBAuth auth = authService.getEmailNick();
			BoardItemVO bio = BoardItemVO.builder()
								.email(auth.getEmail())
								.nick(auth.getUser()
								.getUserNick())
								.build();
			
			resViewData.setViewInfo(new ViewInfoVO(BOARD_DVIEW_MODE.REGISTER.getMode(), MDateUtil.currentDateTime("yyyy-MM-dd HH:mm")));
			resViewData.setAuthentication(authenticationComponent.makeCurrentAuthentication());
			
			resViewData.setData(bio);
		}catch(Exception e) {
			e.printStackTrace();
			throw new GlobalException("500" , GlobalError.MSB_ERROR_500);
		}
		
		return makeModelAndView("board/boardform", new String[] {"xdata"}, new Object[] {resViewData});
	}
	
}
