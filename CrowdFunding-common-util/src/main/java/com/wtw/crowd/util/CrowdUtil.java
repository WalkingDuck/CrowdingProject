package com.wtw.crowd.util;

import javax.servlet.http.HttpServletRequest;

public class CrowdUtil {
	
	/**
		判断请求方式是普通请求还是Ajax请求
	 */
	public static boolean judgeRequestType(HttpServletRequest request) {
		//获取请求头
		String accept = request.getHeader("Accept");
		String xHeader = request.getHeader("X-Requested-With");
		
		//进行判断
		if(accept != null && accept.contains("application/json")) {
			return true;
		}
		
		if(xHeader != null && xHeader.contains("XNLHttpRequest")) {
			return true;
		}
		
		return false;
	}
}
