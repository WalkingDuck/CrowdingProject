package com.wtw.crowd.mvc.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wtw.crowd.entity.Menu;
import com.wtw.crowd.service.api.MenuService;
import com.wtw.crowd.util.ResultEntity;

@Controller
public class MenuController {

	@Autowired
	private MenuService menuService;

	
	/**
	 * 删除菜单
	 */
	@ResponseBody
	@PostMapping("/menu/remove")
	public ResultEntity<String> removeMenu(@RequestParam("id") Integer id) {
		menuService.removeMenu(id);
		return ResultEntity.successWithoutData();
	}
	
	/**
	 * 更新菜单
	 */
	@ResponseBody
	@PostMapping("/menu/update")
	public ResultEntity<String> updateMenu(@RequestParam("id") Integer id, @RequestParam("name") String name,
			@RequestParam("url") String url, @RequestParam("icon") String icon) {
		Menu menu = new Menu();
		menu.setId(id);
		menu.setName(name);
		menu.setUrl(url);
		menu.setIcon(icon);
		
		menuService.updateMenu(menu);
		return ResultEntity.successWithoutData();
	}
	
	/**
	 * 新增菜单
	 */
	@ResponseBody
	@PostMapping("/menu/save")
	public ResultEntity<String> saveMenu(@RequestParam("pid") Integer pid, @RequestParam("name") String name,
			@RequestParam("url") String url, @RequestParam("icon") String icon) {
		Menu menu = new Menu();
		menu.setPid(pid);
		menu.setName(name);
		menu.setUrl(url);
		menu.setIcon(icon);
		
		menuService.saveMenu(menu);
		
		return ResultEntity.successWithoutData();
	}

	// 将菜单以树形结构返回 -> 只返回根节点即可获取到整个菜单结构
	@ResponseBody
	@PostMapping("/menu/get/whole/tree")
	public ResultEntity<Menu> getWholeTree() {

		// 获取到数据库中的所有菜单节点
		List<Menu> menus = menuService.getAll();

		Menu root = null;

		// 生成一个map来存储id和menu节点的映射
		Map<Integer, Menu> map = new HashMap<Integer, Menu>();

		for (Menu menu : menus) {
			Integer id = menu.getId();
			map.put(id, menu);
		}

		for (Menu menu : menus) {
			Integer pid = menu.getPid();

			// pid == null说明当前节点是根节点， root = menu即可
			if (pid == null) {
				root = menu;
				continue;
			}

			// 根据pid获取到对应的父节点
			Menu father = map.get(pid);

			// 将当前节点加入到父节点的children中
			father.getChildren().add(menu);

		}

		return ResultEntity.successWithData(root);
	}

}
