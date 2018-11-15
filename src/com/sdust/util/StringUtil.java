package com.sdust.util;

public class StringUtil {
	//ÅÐ¶ÏÊÇ·ñ¿Õ×Ö·û´®£¨"" or null£©
	public static boolean isNullOrEmpty(String info){
		if(info==null || info.isEmpty())
			return true;
		else return false;
	}
}
