package com.wtw.crowd.mvc.handler;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wtw.crowd.constraint.CrowdConstraint;
import com.wtw.crowd.entity.Admin;
import com.wtw.crowd.service.api.AdminService;

@Controller
public class AdminHandler {

	@Autowired
	private AdminService adminService;

	@RequestMapping("/admin/do/logout.html")
	public String doLogout(HttpSession session) {
		
		//让session失效
		session.invalidate();
		
		//返回登录页面
		return "redirect:/admin/to/login/page.html";		
	}
	
	@RequestMapping("/admin/do/login.html")
	public String doLogin(@RequestParam("loginAcct") String loginAcct, @RequestParam("userPswd") String userPswd,
			HttpSession session) {

		
		// 根据账号来查找管理员
		// 如果管理员不存在就抛出异常
		Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);
		
		// 登录成功后将对象放入session
		session.setAttribute(CrowdConstraint.ATTR_NAME_lOGIN_ADMIN, admin);
		
		//请求转发会导致每刷新一次页面就要重新提交一次表单影响性能
		//所以推荐使用重定向这样 只需要提交一次表单
		return "redirect:/admin/to/main/page.html";
	}
}
