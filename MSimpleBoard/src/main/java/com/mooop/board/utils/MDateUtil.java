package com.mooop.board.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;


/**
 * 
 * @author MOoop
 *
 */
public class MDateUtil {
	
	private MDateUtil() {}
	
	
	/**
	 * 현재 시간을 format방식의 string으로 반환
	 * 
	 * @param format
	 * @return
	 */
	public static String currentDateTime(String format) {
		LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
		return DateTimeFormatter.ofPattern(format).format(ldt);
	}
	

	/**
	 *  LocalDateTime 을 String 형태로 반환한다.
	 * @param dateTime
	 * @return
	 */
	public static String convertDateTimeString(LocalDateTime dateTime){
		return DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm").format(dateTime);
	}

}
