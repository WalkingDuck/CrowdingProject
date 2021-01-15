package com.wtw.crowd.mvc.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wtw.crowd.entity.Role;
import com.wtw.crowd.service.api.AdminService;
import com.wtw.crowd.service.api.RoleService;

/**
 * 权限分配控制器
 */
@Controller
public class AssignController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private RoleService roleService;

	/**
	 * 保存当前用户拥有的权限
	 */
	@PostMapping("assign/do/role/assign.html")
	public String saveAdminRoleRelationship(@RequestParam("adminId") Integer adminId,
			@RequestParam("pageNum") Integer pageNum, @RequestParam("keyword") String keyword,
			@RequestParam(value = "roleIdList", required = false) List<Integer> roleIdList) {
		
		System.out.println(roleIdList);
		adminService.saveAdminRoleRelationship(adminId, roleIdList);
		
		return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
	}

	/**
	 * 跳转到权限分配页面
	 */
	@GetMapping("/assign/to/assign/role/page.html")
	public String toAssignRolePage(@RequestParam("adminId") Integer adminId, ModelMap modelMap) {

		// 查询已分配角色
		List<Role> assignedRoleList = roleService.getAssignedRole(adminId);

		// 查询未分配角色
		List<Role> unAssignedRoleList = roleService.getUnAssignedRole(adminId);

		// 存入模型
		modelMap.addAttribute("assignedRoleList", assignedRoleList);
		modelMap.addAttribute("unAssignedRoleList", unAssignedRoleList);

		return "assign-role";
	}

}
