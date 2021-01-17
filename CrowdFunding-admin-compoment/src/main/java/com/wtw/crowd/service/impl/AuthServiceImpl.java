package com.wtw.crowd.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wtw.crowd.entity.Auth;
import com.wtw.crowd.entity.AuthExample;
import com.wtw.crowd.mapper.AuthMapper;
import com.wtw.crowd.service.api.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private AuthMapper authMapper;

	@Override
	public List<Auth> getAll() {
		return authMapper.selectByExample(new AuthExample());
	}

	@Override
	public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {
		return authMapper.selectAssignedAuthIdByRoleId(roleId);
	}

	@Override
	public void saveRoleAuthRelationship(Map<String, List<Integer>> map) {
		
		// 获取roleId
		List<Integer> roleIdList = map.get("roleId");
		Integer roleId = roleIdList.get(0);
		
		// 防止数据重复 先删除旧数据 再重新插入
		authMapper.deleteOldRelationship(roleId);
		// 获取authIdArray
		List<Integer> authIdList = map.get("authIdArray");
		
		// 判断authIdList是否有效
		if(authIdList != null && authIdList.size() > 0) {
			
			// 插入新数据
			authMapper.insertNewRelationship(roleId, authIdList);
		}
		
		
	}

	
}
