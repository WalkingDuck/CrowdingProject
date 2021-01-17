package com.wtw.crowd.service.api;

import java.util.List;
import java.util.Map;
import com.wtw.crowd.entity.Auth;


public interface AuthService {

	List<Auth> getAll();

	List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

	void saveRoleAuthRelationship(Map<String, List<Integer>> map);

}
