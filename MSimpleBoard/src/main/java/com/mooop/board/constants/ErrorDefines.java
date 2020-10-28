package com.mooop.board.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Error코드 관련 기능제공
 * 
 * @author MOoop
 *
 */
public class ErrorDefines {
	
	
	/**
	 * errorcode와 해당 메세지를 맵핑한 map
	 */
	private static Map<String , String> errorMap = new HashMap<>();
	
	
	/**
	 * errorcode 와 message를 맵핑한다
	 */
	public static void makeErrorMessage() {
		errorMap.put(GlobalError.MSB_ERROR_400, "파라미터가 잘못되었습니다.");
		errorMap.put(GlobalError.MSB_ERROR_403, "접근권한이 없습니다");
		errorMap.put(GlobalError.MSB_ERROR_404, "Page Not Found!!!");
		errorMap.put(GlobalError.MSB_ERROR_500, "내부오류가 발생하였습니다.");
	}
	
	/**
	 * errorCode에 해당되는 메세지를 반환한다.
	 * 
	 * @param errorCode
	 * @return
	 */
	public static String getErrorMessage(String errorCode) {
		return errorMap.get(errorCode);
	}

}
