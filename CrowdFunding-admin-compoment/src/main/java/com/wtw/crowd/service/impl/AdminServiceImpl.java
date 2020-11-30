package com.wtw.crowd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wtw.crowd.entity.Admin;
import com.wtw.crowd.entity.AdminExample;
import com.wtw.crowd.mapper.AdminMapper;
import com.wtw.crowd.service.api.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper adminMapper;
	
	@Override
	public void saveAdmin(Admin admin) {
		adminMapper.insert(admin);
	}

	@Override
	public List<Admin> getAll() {
		List<Admin> example = adminMapper.selectByExample(new AdminExample());
		return example;
	}
	
	
}
