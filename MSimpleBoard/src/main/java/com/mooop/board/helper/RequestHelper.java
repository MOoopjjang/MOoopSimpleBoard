package com.mooop.board.helper;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * Request Header의 정보를 제공하는 Helper 객체
 * 
 * @author MOoop
 *
 */
public class RequestHelper {
	
	public static final String REQUEST_HEADER_AJAX_VALUE = "xmlhttprequest";
	
	private RequestHelper() {}
	
	/**
	 * 
	 * request가 Ajax를 통한 요청인지 판별
	 *   - [[  &&&&& 확인필요 &&&&& ]]
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String requestsWith = request.getHeader("X-Requested-With");
		return requestsWith != null && requestsWith.toLowerCase().equals(REQUEST_HEADER_AJAX_VALUE);
		
	}
	
	
	/**
	 * 
	 * 요청한 Client의 IP를 반환
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemoteRequestIp(HttpServletRequest request) {
		String remoteIp = request.getHeader("X-Forwarded-For");
		if(remoteIp == null || "".equals(remoteIp)) {
			remoteIp = request.getRemoteAddr();
		}
		
		return remoteIp.toLowerCase();
	}
	
	/**
	 * 
	 * Request Protocol type (HTTP , HTTPS )을 반환한다.
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestSchema(HttpServletRequest request) {
		
		String schema = request.getHeader("X-Forwarded-Proto");
		
		if( schema == null || "".equals(schema)) {
			schema = request.getScheme();
		}
		
		return schema.toLowerCase();
	}
	

	/**
	 * 
	 * 요청한 URL ( context path 제외 )
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestURI(HttpServletRequest request ) {
		return request.getRequestURI();
	}

}
