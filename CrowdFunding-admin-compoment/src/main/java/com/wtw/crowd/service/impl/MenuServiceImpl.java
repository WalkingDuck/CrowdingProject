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

}
