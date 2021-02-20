package com.wtw.crowd.mvc.handler;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.wtw.crowd.constraint.CrowdConstraint;
import com.wtw.crowd.entity.Admin;
import com.wtw.crowd.service.api.AdminService;

@Controller
public class AdminHandler {

	@Autowired
	private AdminService adminService;

	/**
	 * 更新用户
	 */
	@RequestMapping("/admin/update.html")
	public String update(Admin admin, @RequestParam("pageNum") Integer pageNum,
			@RequestParam("keyword") String keyword) {

		adminService.update(admin);
		
		// 更新完成，跳转
		return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
	}

	/**
	 * 用户发起更新请求 获取到要更新的admin对象然后跳转到更新页面
	 */
	@RequestMapping("/admin/to/edit/page.html")
	public String toEditPage(@RequestParam("adminId") Integer adminId, @RequestParam("pageNum") Integer pageNum,
			@RequestParam("keyword") String keyWord, ModelMap modelMap) {

		// 获取到admin对象
		Admin admin = adminService.getAdminById(adminId);

		// 将admin对象存入modelMap中
		modelMap.addAttribute("admin", admin);

		// 跳转到更新页面
		return "admin-edit";
	}

	/**
	 * 新增用户
	 */
	@PreAuthorize("hasAuthority('user:save')")
	@RequestMapping("/admin/save.html")
	public String saveAdmin(Admin admin) {

		adminService.saveAdmin(admin);

		// 确保管理员可以看见新增的用户 -> 分页插件会将Integer.MAX_VALUE转换成合法的最大页码
		return "redirect:/admin/get/page.html?pageNum=" + Integer.MAX_VALUE;
	}

	/**
	 * 删除用户请求
	 */
	@RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
	public String remove(@PathVariable("adminId") Integer adminId, @PathVariable("pageNum") Integer pageNum,
			@PathVariable("keyword") String keyword) {

		// 执行删除
		adminService.remove(adminId);

		// 跳转
		return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
	}

	/**
	 * 人员维护请求
	 */
	@RequestMapping("/admin/get/page.html")
	public String getPageInfo(@RequestParam(value = "keyword", defaultValue = "") String key,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize, ModelMap modelMap) {

		PageInfo<Admin> pageInfo = adminService.getPageInfo(key, pageNum, pageSize);

		modelMap.addAttribute(CrowdConstraint.ATTR_NAME_PAGE_INFO, pageInfo);

		return "admin-page";
	}

	/**
	 * 用户登出请求
	 */
	@RequestMapping("/admin/do/logout.html")
	public String doLogout(HttpSession session) {

		// 让session失效
		session.invalidate();

		// 返回登录页面
		return "redirect:/admin/to/login/page.html";
	}

	/**
	 * 用户登录请求
	 */
	@RequestMapping("/admin/do/login.html")
	public String doLogin(@RequestParam("loginAcct") String loginAcct, @RequestParam("userPswd") String userPswd,
			HttpSession session) {

		// 根据账号来查找管理员
		// 如果管理员不存在就抛出异常
		Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);

		// 登录成功后将对象放入session
		session.setAttribute(CrowdConstraint.ATTR_NAME_lOGIN_ADMIN, admin);

		// 请求转发会导致每刷新一次页面就要重新提交一次表单影响性能
		// 所以推荐使用重定向这样 只需要提交一次表单
		return "redirect:/admin/to/main/page.html";
	}
}
