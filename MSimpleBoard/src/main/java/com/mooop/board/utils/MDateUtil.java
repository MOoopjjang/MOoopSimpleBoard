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
	 * Date type의 시간을 LocalDateTime으로 변경후 formating하여 반환
	 * 
	 * @param date
	 * @return
	 */
	public static String convertDateTimeString(Date date) {
		LocalDateTime ldt = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();
		return DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm").format(ldt);
	}

}
