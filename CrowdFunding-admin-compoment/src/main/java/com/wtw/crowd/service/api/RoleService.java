package com.wtw.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.wtw.crowd.entity.Role;

public interface RoleService {

	PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword);
	
}
