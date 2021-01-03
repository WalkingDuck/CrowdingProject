package com.wtw.crowd.mvc.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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

	@ResponseBody
	@PostMapping("/role/update.json")
	public ResultEntity<String> updateRole(@RequestParam("id") Integer id, @RequestParam("name") String name) {
		
		Role role = new Role();
		
		role.setId(id);
		role.setName(name);
		
		roleService.updateRole(role);
		
		return ResultEntity.successWithoutData();
	}
	
	@ResponseBody
	@PostMapping("/role/save.json")
	public ResultEntity<String> saveRole(@RequestParam("roleName") String name) {
		
		Role role = new Role();
		
		role.setName(name);
		
		roleService.saveRole(role);
		
		return ResultEntity.successWithoutData();
	}
	
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
