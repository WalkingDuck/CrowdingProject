package com.wtw.crowd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wtw.crowd.entity.Menu;
import com.wtw.crowd.entity.MenuExample;
import com.wtw.crowd.mapper.MenuMapper;
import com.wtw.crowd.service.api.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuMapper menuMapper;
	
	@Override
	public List<Menu> getAll() {
		MenuExample example = new MenuExample();
		List<Menu> menus = menuMapper.selectByExample(example);
		return menus;
	}

	@Override
	public void saveMenu(Menu menu) {
		menuMapper.insert(menu);
	}

	@Override
	public void updateMenu(Menu menu) {
		
		// 没有传入pid 所以要调用本方法防止数据库中pid被置为null
		menuMapper.updateByPrimaryKeySelective(menu);
	}

	@Override
	public void removeMenu(Integer id) {
		menuMapper.deleteByPrimaryKey(id);
	}

}
