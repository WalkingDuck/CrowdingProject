package com.wtw.crowd.mvc.handler;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.wtw.crowd.entity.Role;
import com.wtw.crowd.service.api.RoleService;
import com.wtw.crowd.util.ResultEntity;

@Controller
public class RoleController {

	@Autowired
	private RoleService roleService;

	/**
	 * 删除角色
	 */
	@ResponseBody
	@PostMapping("/role/delete/by/role/id/array.json")
	public ResultEntity<String> removeRoleByIdArray(@RequestBody List<Integer> roleIdList) {
		roleService.removeRole(roleIdList);
		
		return ResultEntity.successWithoutData();
	}
	
	/**
	 * 更新角色
	 */
	@ResponseBody
	@PostMapping("/role/update.json")
	public ResultEntity<String> updateRole(@RequestParam("id") Integer id, @RequestParam("name") String name) {
		
		Role role = new Role();
		
		role.setId(id);
		role.setName(name);
		
		roleService.updateRole(role);
		
		return ResultEntity.successWithoutData();
	}
	
	/**
	 * 新增角色
	 */
	@ResponseBody
	@PostMapping("/role/save.json")
	public ResultEntity<String> saveRole(@RequestParam("roleName") String name) {
		
		Role role = new Role();
		
		role.setName(name);
		
		roleService.saveRole(role);
		
		return ResultEntity.successWithoutData();
	}
	
	/**
	 * 分页查询角色
	 * 当使用@PreAuthorize注解时对权限不足的处理应当在异常映射器(CrowdExceptionResolver)中解决
	 * 如果权限校验是在WebAppSecurityConfig中设置时权限不足的处理就应该在WebAppSecurityConfig解决
	 */
	@PreAuthorize("hasRole('部长')")
	@ResponseBody
	@RequestMapping("/role/get/page/info.json")
	public ResultEntity<PageInfo<Role>> getPageInfo(
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {

		// 调用service方法
		PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);
		
		ResultEntity<PageInfo<Role>> success = ResultEntity.successWithData(pageInfo);
		
		return success;
		
	}
}
