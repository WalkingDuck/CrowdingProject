package com.wtw.crowd.service.api;

import java.util.List;

import com.wtw.crowd.entity.Admin;


public interface AdminService {
	void saveAdmin(Admin admin);

	List<Admin> getAll();

	Admin getAdminByLoginAcct(String loginAcct, String userPswd);
}
