package com.wtw.crowd.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wtw.crowd.constraint.CrowdConstraint;
import com.wtw.crowd.entity.Admin;
import com.wtw.crowd.entity.AdminExample;
import com.wtw.crowd.entity.AdminExample.Criteria;
import com.wtw.crowd.exception.LoginFailedException;
import com.wtw.crowd.mapper.AdminMapper;
import com.wtw.crowd.service.api.AdminService;
import com.wtw.crowd.util.CrowdUtil;

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

	@Override
	public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
		
		// 创建example对象
		AdminExample example = new AdminExample();
		
		// 创建criteria对象
		Criteria criteria = example.createCriteria();
		criteria.andLoginAcctEqualTo(loginAcct);
		
		// 根据账号获取管理员
		List<Admin> admins = adminMapper.selectByExample(example);
		
		// 判断管理员对象是否为空
		// 如果为空就抛出异常
		if(admins == null || admins.size() == 0) {
			throw new LoginFailedException(CrowdConstraint.MESSAGE_LOGIN_FAILED);
		}
		
		// 账号不唯一
		if(admins.size() > 1) {
			throw new RuntimeException(CrowdConstraint.MESSAGE_SYSTEM_ERROR_LOGIN_ACCT_NOT_UNIQUE);
		}
		
		Admin admin = admins.get(0);
		
		if(admin == null) {
			throw new LoginFailedException(CrowdConstraint.MESSAGE_LOGIN_FAILED);
		}
		
		// 不为空则将对象的密码取出
		String pswd = admin.getUserPswd();
		
		// 将传入的密码与对象的密码进行比较
		userPswd = CrowdUtil.md5(userPswd);
		
		// 如果密码不一致就抛出异常
		if(!Objects.equals(userPswd, pswd)) {
			throw new LoginFailedException(CrowdConstraint.MESSAGE_LOGIN_FAILED);
		}
		
		// 密码一致就返回对象

		return admin;
	}

	@Override
	public PageInfo<Admin> getPageInfo(String key, Integer pageNum, Integer pageSize) {

		// 开启分页功能
		PageHelper.startPage(pageNum, pageSize);
		
		// 执行查询
		List<Admin> list = adminMapper.selectAdminByKeyword(key);
		
		// 封装pageInfo
		PageInfo<Admin> pageInfo = new PageInfo<>(list);
		
		return pageInfo;
	}

}
