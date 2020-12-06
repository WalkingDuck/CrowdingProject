package com.wtw.crowd.mvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wtw.crowd.constraint.CrowdConstraint;
import com.wtw.crowd.entity.Admin;
import com.wtw.crowd.exception.AccessForbiddenException;

/**
 * 进行登录检查
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//获取session
		HttpSession session = request.getSession();
		
		//从session域中尝试获取Admin
		Admin admin = (Admin)session.getAttribute(CrowdConstraint.ATTR_NAME_lOGIN_ADMIN);
		
		//没有获取到 -> 没有登录 -> 抛异常
		if(admin == null) {
			throw new AccessForbiddenException(CrowdConstraint.MESSAGE_ACCESS_FORBIDDEN);
		}
		
		//Admin不为空 -> 已登录 -> 放行
		return true;
	}
	
}
