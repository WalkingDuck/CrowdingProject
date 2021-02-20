package com.wtw.crowd.mvc.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.wtw.crowd.entity.Admin;

/**
 * 对spring security中的User对象进行扩展
 */
public class SecurityAdmin extends User{
	private static final long serialVersionUID = 1L;

	private Admin admin;
	
	public SecurityAdmin(Admin admin, List<GrantedAuthority> authorities) {
		super(admin.getLoginAcct(), admin.getUserPswd(), authorities);
		this.admin = admin;
		
		// 擦除admin对象的密码 增加安全性
		this.admin.setUserPswd(null);
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

}
