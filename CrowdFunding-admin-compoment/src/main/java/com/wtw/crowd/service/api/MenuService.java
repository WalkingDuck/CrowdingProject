package com.wtw.crowd.service.api;

import java.util.List;

import com.wtw.crowd.entity.Menu;

public interface MenuService {

	List<Menu> getAll();

	void saveMenu(Menu menu);

	void updateMenu(Menu menu);

	void removeMenu(Integer id);
	
}
