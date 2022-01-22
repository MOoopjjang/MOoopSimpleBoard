package com.mooop.board.controller.web;

import com.mooop.board.constants.GlobalError;
import com.mooop.board.domain.AdmViewResponse;
import com.mooop.board.domain.web.*;
import com.mooop.board.enums.EVENT_DVIEW_MODE;
import com.mooop.board.enums.USER_LIST_MODE;
import com.mooop.board.exception.GlobalException;
import com.mooop.board.utils.MDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


/**
 * 관리자페이지 연동
 * 
 * @author MOoop
 *
 */

@Controller
@RequestMapping(value="/admin")
public class AdminController extends MSBBaseController{
	
	private static Logger logger = LoggerFactory.getLogger("AdminController");
	
	
	@RequestMapping(value="/test")
	public ModelAndView admin() {
		return makeModelAndView("admin/content/index", null, null);
	}
	
	
	/**
	 * User 목록조회
	 * 
	 * @param category
	 * @param text
	 * @param page
	 * @param size
	 * @param request
	 * @return
	 */
	@GetMapping("/user/list")
	public ModelAndView userList(@RequestParam String category 
			, @RequestParam String text  
			, @RequestParam Integer page
			, @RequestParam Integer size
			, @RequestParam String mode
			, HttpServletRequest request) throws GlobalException{
		
		
		logger.info(request.getQueryString());
		AdmViewResponse<Page<UserItemVO>> admViewResponse = new AdmViewResponse<>();
		try {
			Page<UserItemVO> pInfo =  userManagerService.getUserList(category, text, page, size);
			admViewResponse.setData(pInfo);
			admViewResponse.setAuthenticationInfo(authenticationComponent.makeCurrentAuthentication());
			admViewResponse.setSearch(new SearchResponseVO(category, text));
			admViewResponse.setResult(new ViewResultVO("OK", ""));
			admViewResponse.setAdmViewInfo(new AdmViewInfoVO(
					"/admin/user/list?"+request.getQueryString()
					,Optional.ofNullable(mode).orElse(USER_LIST_MODE.ALL.getMode())
					, MDateUtil.currentDateTime("YYYY-MM-dd HH:mm")));
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new GlobalException("500" , GlobalError.MSB_ERROR_500);
		}
		
		return makeModelAndView("admin/content/member/list", new String[] {"xdata"}, new Object[] {admViewResponse});
	}
	
	/**
	 * User 상세보기
	 * 
	 * @param email
	 * @return
	 */
	@GetMapping("/user/dview")
	public ModelAndView userDetailView(@RequestParam("email") String email , @RequestParam("callUri") String callUri
			, @RequestParam("mode") String mode ,  HttpServletRequest request) throws GlobalException{
		AdmViewResponse<UserItemVO> admViewResponse = new AdmViewResponse<>();
		try {
			String callUrl = "";
			if(callUri.equals("/admin/user/list")) {
				String category = "";
				String text = "";
				if(mode.trim().equals("GUEST")) {
					category = "role";
					text = "GUEST";
				}else if(mode.trim().equals("BLOCK")){
					category = "enable";
					text = "N";
				}
				 callUrl = "/admin/user/list?category="+category+"&text="+text+"&page=0&size=10&mode="+mode;
			}else {
				callUrl = callUri;
			}
			
			UserItemVO userInfo = userManagerService.getUserInfo(email);
			admViewResponse.setData(userInfo);
			admViewResponse.setAuthenticationInfo(authenticationComponent.makeCurrentAuthentication());
			admViewResponse.setAdmViewInfo(new AdmViewInfoVO(
					callUrl
					,"", MDateUtil.currentDateTime("YYYY-MM-dd HH:mm")));
			admViewResponse.setResult(new ViewResultVO("OK", ""));
		}catch(Exception e) {
			e.printStackTrace();
			throw new GlobalException("500" , GlobalError.MSB_ERROR_500);
		}
		
		return makeModelAndView("admin/content/member/dview", new String[] {"xdata"}, new Object[] {admViewResponse});
	}
	
	/**
	 * 공지사항 목록보기
	 * 
	 * @param category
	 * @param text
	 * @param page
	 * @param size
	 * @param request
	 * @return
	 */
	@GetMapping("/event/list")
	public ModelAndView eventList(
			@RequestParam("category") String category
			,@RequestParam("text") String text
			,@RequestParam("page") Integer page
			,@RequestParam("size") Integer size
			,HttpServletRequest request) throws GlobalException{
		
		AdmViewResponse<Page<EventItemVO>> admViewResponse = new AdmViewResponse<>();
		try {
			Page<EventItemVO> eventInfo =  eventManagerService.getEventItemList(category, text, page, size);
			admViewResponse.setData(eventInfo);
			admViewResponse.setResult(new ViewResultVO("OK", ""));
			admViewResponse.setSearch(new SearchResponseVO(category, text));
			admViewResponse.setAuthenticationInfo(authenticationComponent.makeCurrentAuthentication());
			admViewResponse.setAdmViewInfo(new AdmViewInfoVO(
					"/admin/event/list"
					,"", MDateUtil.currentDateTime("YYYY-MM-dd HH:mm")));
		}catch(Exception e) {
			e.printStackTrace();
			throw new GlobalException("500" , GlobalError.MSB_ERROR_500);
		}
		return makeModelAndView("admin/content/event/list", new String[] {"xdata"}, new Object[] {admViewResponse});
	}
	
	/**
	 * 공지사항 등록
	 * 
	 * @return
	 */
	@GetMapping("/event/register")
	public ModelAndView eventRegisterView() throws GlobalException{
		AdmViewResponse<EventItemVO> admViewResponse = new AdmViewResponse<>();
		try {
			admViewResponse.setResult(new ViewResultVO("OK", ""));
			admViewResponse.setAuthenticationInfo(authenticationComponent.makeCurrentAuthentication());
			admViewResponse.setAdmViewInfo(new AdmViewInfoVO(
					"/admin/event/register"
					,EVENT_DVIEW_MODE.REGISTER.getMode(), MDateUtil.currentDateTime("YYYY-MM-dd HH:mm")));
		}catch(Exception e) {
			e.printStackTrace();
			throw new GlobalException("500" , GlobalError.MSB_ERROR_500);
		}
		return makeModelAndView("admin/content/event/register", new String[] {"xdata"}, new Object[] {admViewResponse});
	}
	
	
	/**
	 *  공지사항 상세보기
	 *  
	 * @param idx
	 * @param request
	 * @return
	 */
	@GetMapping("/event/dview")
	public ModelAndView eventDetailView(@RequestParam("idx") Long idx , HttpServletRequest request) throws GlobalException{
		
		AdmViewResponse<EventItemVO> admViewResponse = new AdmViewResponse<>();
		try {
			EventItemVO eventItem = eventManagerService.getEventItemInfo(idx);
			admViewResponse.setData(eventItem);
			admViewResponse.setResult(new ViewResultVO("OK", ""));
			admViewResponse.setAuthenticationInfo(authenticationComponent.makeCurrentAuthentication());
			admViewResponse.setAdmViewInfo(new AdmViewInfoVO(
					"/admin/event/list"
					,EVENT_DVIEW_MODE.DVIEW.getMode(), MDateUtil.currentDateTime("YYYY-MM-dd HH:mm")));
		}catch(Exception e) {
			e.printStackTrace();
			throw new GlobalException("500" , GlobalError.MSB_ERROR_500);
		}
		return makeModelAndView("admin/content/event/register", new String[] {"xdata"}, new Object[] {admViewResponse});
	}
	
	
	/**
	 * 최다 조회글 목록조회
	 * 
	 * @return
	 */
	@GetMapping("/board/most/hit")
	public ModelAndView mostBoardItemHitList() throws GlobalException{
		AdmViewResponse<Page<BoardItemVO>> admViewResponse = new AdmViewResponse<>();
		try {
			Page<BoardItemVO> pageInfo = boardManagerService.getMostHitsBoardItemList();
			admViewResponse.setData(pageInfo);
			admViewResponse.setResult(new ViewResultVO("OK", ""));
			admViewResponse.setAuthenticationInfo(authenticationComponent.makeCurrentAuthentication());
			admViewResponse.setAdmViewInfo(new AdmViewInfoVO(
					"/admin/board/most/hit"
					,"", MDateUtil.currentDateTime("YYYY-MM-dd HH:mm")));
		}catch(Exception e) {
			e.printStackTrace();
			throw new GlobalException("500" , GlobalError.MSB_ERROR_500);
		}
		return makeModelAndView("admin/content/board/hit-list", new String[] {"xdata"}, new Object[] {admViewResponse});
	}
	
	
	/**
	 * 최다 uploader 목록조회
	 * 
	 * @return
	 */
	@GetMapping("/board/most/uploader")
	public ModelAndView mostBoardItemUploaderList() throws GlobalException{
		AdmViewResponse<Page<AdmUserItemVO>> admViewResponse = new AdmViewResponse<>();
		try {
			Page<AdmUserItemVO> pageInfo = boardManagerService.getMostUploaderList();
			admViewResponse.setData(pageInfo);
			admViewResponse.setResult(new ViewResultVO("OK", ""));
			admViewResponse.setAuthenticationInfo(authenticationComponent.makeCurrentAuthentication());
			admViewResponse.setAdmViewInfo(new AdmViewInfoVO(
					"/admin/board/most/uploader"
					,"", MDateUtil.currentDateTime("YYYY-MM-dd HH:mm")));
		}catch(Exception e) {
			e.printStackTrace();
			throw new GlobalException("500" , GlobalError.MSB_ERROR_500);
		}
		return makeModelAndView("admin/content/board/uploader-list", new String[] {"xdata"}, new Object[] {admViewResponse});
	}
	
	/**
	 * board 상세보기
	 * 
	 * @param idx
	 * @return
	 */
	@GetMapping("/board/dview")
	public ModelAndView boardDView(@RequestParam("idx") Long idx) throws GlobalException{
		AdmViewResponse<BoardItemVO> admViewResponse = new AdmViewResponse<>();
		try {
			BoardItemVO dviewItem = boardManagerService.getBoardDetailInfo(idx);
			admViewResponse.setData(dviewItem);
			admViewResponse.setResult(new ViewResultVO("OK", ""));
			admViewResponse.setAuthenticationInfo(authenticationComponent.makeCurrentAuthentication());
			admViewResponse.setAdmViewInfo(new AdmViewInfoVO(
					"/admin/board/most/hit"
					,"", MDateUtil.currentDateTime("YYYY-MM-dd HH:mm")));
		}catch(Exception e) {
			e.printStackTrace();
			throw new GlobalException("500" , GlobalError.MSB_ERROR_500);
		}
		return makeModelAndView("admin/content/board/dview", new String[] {"xdata"}, new Object[] {admViewResponse});
	}
	
	
	/**
	 *  권한관리 페이지
	 * 
	 * @return
	 */
	@GetMapping("/setting/authority")
	public ModelAndView authorityManagerView() throws GlobalException{
		AdmViewResponse<Page<AuthorityItemVO>> admViewResponse = new AdmViewResponse<>();
		try {
			Page<AuthorityItemVO> pageInfo = settingService.getAuthorityItemList();
			admViewResponse.setData(pageInfo);
			admViewResponse.setResult(new ViewResultVO("OK", ""));
			admViewResponse.setAuthenticationInfo(authenticationComponent.makeCurrentAuthentication());
			admViewResponse.setAdmViewInfo(new AdmViewInfoVO(
					"/admin/setting/authority"
					,"", MDateUtil.currentDateTime("YYYY-MM-dd HH:mm")));
		}catch(Exception e) {
			e.printStackTrace();
			throw new GlobalException("500" , GlobalError.MSB_ERROR_500);
		}
		return makeModelAndView("admin/content/setting/authority", new String[] {"xdata"}, new Object[] {admViewResponse});
	}
	
	/**
	 * 페이징 관리페이지
	 * 
	 * @return
	 */
	@GetMapping("/setting/paging")
	public ModelAndView pagingManagerView() throws GlobalException{
		AdmViewResponse<PagingItemVO> admViewResponse = new AdmViewResponse<>();
		try {
			PagingItemVO pivo = settingService.getPagingInfo();
			admViewResponse.setData(pivo);
			admViewResponse.setResult(new ViewResultVO("OK", ""));
			admViewResponse.setAuthenticationInfo(authenticationComponent.makeCurrentAuthentication());
			admViewResponse.setAdmViewInfo(new AdmViewInfoVO(
					"/admin/setting/paging"
					,"", MDateUtil.currentDateTime("YYYY-MM-dd HH:mm")));
		}catch(Exception e) {
			e.printStackTrace();
			throw new GlobalException("500" , GlobalError.MSB_ERROR_500);
		}
		return makeModelAndView("admin/content/setting/paging", new String[] {"xdata"}, new Object[] {admViewResponse});
	}
}
