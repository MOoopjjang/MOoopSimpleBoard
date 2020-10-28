package com.mooop.board.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

public class MStringUtil {
	private static final String STRING_LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
	private static final String STRING_UPPER_CASE = STRING_LOWER_CASE.toUpperCase();
	private static final String NUM_CASE = "0123456789";
	private static String GENERATOR_STRING = STRING_LOWER_CASE+STRING_UPPER_CASE+NUM_CASE;
	private static SecureRandom random = new SecureRandom();
	
	
	private MStringUtil() {}
	
	
	/**
	 * 
	 * 객체의 유효성 체크
	 * 
	 * @param value
	 * @return
	 */
	public static boolean validCheck(Object value) {
		boolean isValid = true;
		if(value == null) {
			return false;
		}
		
		if(value instanceof String) {
			String org = (String)value;
			if("".equals(org.trim()) || org.trim().length() == 0) {
				isValid = false;
			}
		}else if(value instanceof List) {
			List org = (List) value;
			if(!org.isEmpty()) {
				isValid = false;
			}
		}else if(value instanceof Map) {
			Map org = (Map) value;
			if(!org.isEmpty()) {
				isValid = false;
			}
		}else if(value instanceof String[]) {
			String[] org = (String[])value;
			if(org.length == 0) {
				isValid = false;
			}
		}else if(value instanceof Integer) {
			Integer org = (Integer)value;
			if(org == null) {
				isValid = false;
			}
		}
		
		
		return isValid;
	}
	
	
	/**
	 * 객체 Copy 메소드
	 * 
	 * @param orgData
	 * @return
	 */
	private static Object deepCopy(Object orgData) throws Exception{
		if(orgData == null) {
			throw new NullPointerException("orgData is NULL");
		}
		
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		
		oos.writeObject(orgData);
		bais = new ByteArrayInputStream(baos.toByteArray());
		ois = new ObjectInputStream(bais);
		return ois.readObject();
	}
	
	/**
	 * count 길이의 랜덤 string 생성 메소드
	 * 
	 * @param count
	 * @return
	 */
	public static  String randomStringGenerator(int count){
		if(count < 4) {
			throw new NullPointerException();
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < count ; i++) {
			int index = random.nextInt(GENERATOR_STRING.length());
			sb.append(GENERATOR_STRING.charAt(index));
		}
		return sb.toString();
	}
	
	/**
	 * 빈문자일 경우 defString 를 반환하는 메소드
	 * 
	 * @param org
	 * @param defString
	 * @return
	 */
	public static String defaultIfEmptyString(String org , String defString) {
		if(org == null || "".equals(org)) {
			return defString;
		}
		return org;
	}

}
