package com.mooop.board.utils;

import java.io.File;

public class MFileUtil {
	
	private MFileUtil() {}
	
	
	/**
	 * 
	 * @param prefix
	 * @param dirName
	 * @param existOnDelete
	 * @return
	 */
	public static boolean makeDirectory(final String prefix , final String dirName , final boolean existOnDelete) {
		
		File f = new File(prefix+"/"+dirName);
		if(f.exists() && f.isDirectory()) {
			if(existOnDelete) {
				for(File subFile : f.listFiles()) subFile.delete();
				f.delete();
			}else {
				return true;
			}
			
		}
		
		return f.mkdir();
	}
	
	
	
	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean removeFile(final String filePath) {
		File f = new File(filePath);
		if(f.exists())f.delete();
		return true;
	}

}
