package com.wtw.crowd.mvc.handler;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wtw.crowd.entity.Auth;
import com.wtw.crowd.entity.Role;
import com.wtw.crowd.service.api.AdminService;
import com.wtw.crowd.service.api.AuthService;
import com.wtw.crowd.service.api.RoleService;
import com.wtw.crowd.util.ResultEntity;

/**
 * 权限分配控制器
 */
@Controller
public class AssignController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AuthService authService;
	
	/**
	 * 保存角色的对应权限
	 */
	@ResponseBody
	@PostMapping("/assign/do/role/assign/auth")
	public ResultEntity<String> saveRoleAuthRelationship(@RequestBody Map<String, List<Integer>> map) {
		
		authService.saveRoleAuthRelationship(map);
		return ResultEntity.successWithoutData();
	}
	
	/**
	 * 通过roleId查找到对应的authId
	 */
	@ResponseBody
	@PostMapping("/assign/get/assigned/auth/id/by/role/id")
	public ResultEntity<List<Integer>> getAssignedAuthIdByRoleId(@RequestParam("roleId") Integer roleId) {
		
		List<Integer> authIdList = authService.getAssignedAuthIdByRoleId(roleId);
		
		return ResultEntity.successWithData(authIdList);
	}
	
	/**
	 * 查询所有权限
	 */
	@ResponseBody
	@PostMapping("/assign/get/all/auth")
	public ResultEntity<List<Auth>> getAllAuth() {
		
		List<Auth> authList = authService.getAll();
		return ResultEntity.successWithData(authList);
	}

	/**
	 * 保存当前用户拥有的权限
	 */
	@PostMapping("assign/do/role/assign.html")
	public String saveAdminRoleRelationship(@RequestParam("adminId") Integer adminId,
			@RequestParam("pageNum") Integer pageNum, @RequestParam("keyword") String keyword,
			@RequestParam(value = "roleIdList", required = false) List<Integer> roleIdList) {
		
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
