package com.wtw.crowd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wtw.crowd.entity.Role;
import com.wtw.crowd.mapper.RoleMapper;
import com.wtw.crowd.service.api.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {
		
		// 开启分页功能
		PageHelper.startPage(pageNum, pageSize);
		
		// 查询
		List<Role> roles = roleMapper.selectRoleByKeyword(keyword);
		
		// 封装pageInfo
		PageInfo<Role> pageInfo = new PageInfo<>(roles);
		
		return pageInfo;
	}

	@Override
	public void saveRole(Role role) {
		roleMapper.insert(role);
	}

	@Override
	public void updateRole(Role role) {
		roleMapper.updateByPrimaryKey(role);
	}
}
