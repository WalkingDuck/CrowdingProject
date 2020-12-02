package com.wtw.crowd.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import com.wtw.crowd.constraint.CrowdConstraint;

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
	
	/**
	 * 对字符串进行加密处理
	 * @param source
	 * @return
	 */
	public static String md5(String source) {
		
		if(source == null || source.isEmpty()) {
			
			//字符串不合法
			throw new RuntimeException(CrowdConstraint.MESSAGE_STRING_INVALIDATE);
		}
		
		String algorithm = "md5";
		
		try {
			
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			
			// 获取source对应的字符数组
			byte[] input = source.getBytes();
			
			// 进行加密
			byte[] output = messageDigest.digest(input);
			
			// 创建biginteger
			int signum = 1;
			BigInteger bigInteger = new BigInteger(signum, output);
			
			// 将biginteger转换为16进制字符串
			int radix = 16;
			String encoded = bigInteger.toString(radix);
			
			return encoded;
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
