package com.wtw.crowd.mvc.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.wtw.crowd.constraint.CrowdConstraint;
import com.wtw.crowd.exception.LoginAcctAlreadyInUseException;
import com.wtw.crowd.exception.LoginFailedException;
import com.wtw.crowd.util.CrowdUtil;
import com.wtw.crowd.util.ResultEntity;

//基于注解的异常处理器
@ControllerAdvice
public class CrowdExceptionResolver {

	@ExceptionHandler(LoginAcctAlreadyInUseException.class)
	public ModelAndView resolveLoginAcctAlreadyInUseException(LoginAcctAlreadyInUseException exception, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String viewName = "admin-add";
		return commonResolver(viewName, exception, request, response);
	}
	
	@ExceptionHandler(LoginFailedException.class)
	public ModelAndView resolveLoginFailedException(LoginFailedException exception, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String viewName = "admin-login";
		return commonResolver(viewName, exception, request, response);
	}

	@ExceptionHandler(NullPointerException.class)
	public ModelAndView resolveNullPointerException(NullPointerException exception, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		return commonResolver("system-error", exception, request, response);
	}

	private ModelAndView commonResolver(String viewName, Exception exception, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		boolean requestType = CrowdUtil.judgeRequestType(request);
		String message = exception.getMessage();

		// ajax请求
		if (requestType) {
			ResultEntity<Object> resultEntity = ResultEntity.failed(message);

			// 转换成json
			Gson gson = new Gson();
			String json = gson.toJson(resultEntity);

			// 返回响应
			response.getWriter().write(json);

			// 上面用response进行了响应 所以不返回ModelAndView
			return null;
		}

		ModelAndView mv = new ModelAndView();
		mv.addObject(CrowdConstraint.ATTR_NAME_EXCEPTION, exception);
		mv.setViewName(viewName);

		return mv;
	}
}
