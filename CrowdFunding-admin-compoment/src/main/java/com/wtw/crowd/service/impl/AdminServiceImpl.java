package com.wtw.crowd.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wtw.crowd.constraint.CrowdConstraint;
import com.wtw.crowd.entity.Admin;
import com.wtw.crowd.entity.AdminExample;
import com.wtw.crowd.entity.AdminExample.Criteria;
import com.wtw.crowd.exception.LoginAcctAlreadyInUseException;
import com.wtw.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.wtw.crowd.exception.LoginFailedException;
import com.wtw.crowd.mapper.AdminMapper;
import com.wtw.crowd.service.api.AdminService;
import com.wtw.crowd.util.CrowdUtil;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper adminMapper;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public void saveAdmin(Admin admin) {
		
		// 对密码进行加密
		String userPswd = admin.getUserPswd();
//		String pswd = CrowdUtil.md5(userPswd);
		String pswd = passwordEncoder.encode(userPswd);
		admin.setUserPswd(pswd);
		
		// 生成创建时间
		Date date = new Date();
		String pattern = "yyyy-MM-dd hh:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String createTime = simpleDateFormat.format(date);
		admin.setCreateTime(createTime);
		
		try {
			// 插入用户
			adminMapper.insert(admin);
		} catch(Exception e) {
			if(e instanceof DuplicateKeyException) {
				// 账号被使用
				throw new LoginAcctAlreadyInUseException(CrowdConstraint.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
			}
		}
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

	@Override
	public void remove(Integer adminId) {
		adminMapper.deleteByPrimaryKey(adminId);
	}

	@Override
	public Admin getAdminById(Integer adminId) {
		// TODO Auto-generated method stub
		return adminMapper.selectByPrimaryKey(adminId);
	}

	@Override
	public void update(Admin admin) {
		// TODO Auto-generated method stub
		try {
			adminMapper.updateByPrimaryKeySelective(admin);
		}catch(Exception e) {
			// loginAcct已经存在
			if(e instanceof DuplicateKeyException) {
				throw new LoginAcctAlreadyInUseForUpdateException(CrowdConstraint.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
			}
		}
	}

	@Override
	public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {
	
		// 为避免插入重复数据 所以先删除adminId相关的数据后 重新插入
		
		// 删除旧数据
		adminMapper.deleteOldRelationship(adminId);
		
		
		// 判断当前用户是否还有权限
		if(roleIdList != null && roleIdList.size() > 0) {
			adminMapper.insertNewRelationship(adminId, roleIdList);
		}
		
	}

	@Override
	public Admin getAdminByLoginAcct(String username) {
		
		AdminExample adminExample = new AdminExample();
		Criteria criteria = adminExample.createCriteria();
		criteria.andLoginAcctEqualTo(username);
		List<Admin> admins = adminMapper.selectByExample(adminExample);
		Admin admin = admins.get(0);
		return admin;
	}

}
