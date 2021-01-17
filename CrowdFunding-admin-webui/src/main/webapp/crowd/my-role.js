// 显示auth的树性结构
function fillAuthTree() {
	
	// 查询auth数据
	var ajaxReturn = $.ajax({
		"url": "assign/get/all/auth",
		"type": "post",
		"dataType": "json",
		"async": false
	});
	
	// 发生异常
	if(ajaxReturn.status != 200) {
		layer.msg("Failed! :" + ajaxReturn.status + ", " + ajaxReturn.statusText);
		return ;
	}
	
	// 获取auth列表
	var authList = ajaxReturn.responseJSON.data;
	
	// ztree配置
	var setting = {
		"data": {
			"simpleData": {
				// 开启simpleData 让ztree自动显示树形结构
				"enable": true,
				"pIdKey": "categoryId"	// 让ztree用categoryId字段 来作为pid进行分层
			},
			"key": {
				"name": "title"	// 设置让ztree将返回对象的title属性作为显示的名称
			} 
		},	
		"check": {
			"enable": true		// 让树形菜单每个元素前显示多选框
		}
	};
	
	// 生成树形菜单
	$.fn.zTree.init($("#authTreeDemo"), setting, authList);
	
	// 获取ztree对象
	var treeObj=$.fn.zTree.getZTreeObj("authTreeDemo");
	
	// 让树形菜单每个节点默认展开
	treeObj.expandAll(true);
	
	// 查询已分配的auth
	ajaxReturn = $.ajax({
		"url": "assign/get/assigned/auth/id/by/role/id",
		"type": "post",
		"data": {
			"roleId": window.roleId
		},
		"dataType": "json",
		"async": false
	});
	
	// 发生异常
	if(ajaxReturn.status != 200) {
		layer.msg("Failed! :" + ajaxReturn.status + ", " + ajaxReturn.statusText);
		return ;
	}
	
	var authIdArray = ajaxReturn.responseJSON.data;
	
	// 将已分配的auth打勾
	for(var i = 0; i < authIdArray.length; i++) {
		var authId = authIdArray[i];
		
		// 根据authId找到对应的节点
		var treeNode = treeObj.getNodeByParam("id", authId);
		
		// 将对应节点设置为选中状态
		var checked =  true;	// 设置节点是否选中
		var checkTypeFlag = false;	// 设置节点是否联动
		treeObj.checkNode(treeNode, checked, checkTypeFlag);
	}
	
}


// 显示模态框，让用户确认是否删除
function showConfirmModal(roleArray) {
	
	// 显示模态框
	$("#confirmModal").modal("show");
	
	// 清楚旧数据，防止叠加
	$("#roleNameDiv").empty();
	
	// 声明全局变量存放要删除的role的id，方便后续传参
	window.roleIdArray = [];
	
	// 遍历role数组，让roleName显示在模态框中
	for(var i = 0; i < roleArray.length; i++) {
		var role = roleArray[i];
		var roleId = role.id;
		var roleName = role.roleName;
		$("#roleNameDiv").append(roleName + "<br/>");
		
		roleIdArray.push(roleId);
	}
}


// 展示分页效果
function generatePage() {
	
	// 获取分页数据
	var pageInfo = getPageInfoRemote();
	
	// 填充标签
	fillTableBody(pageInfo);
	
}

// 获取pageinfo
function getPageInfoRemote() {
	$.ajax({
		"url": "role/get/page/info.json",
		"type": "post",
		"data": {
			"pageSize": window.pageSize,
			"pageNum": window.pageNum,
			"keyword": window.keyword
		},
		"dataType": "json",
		"success": function(response) {
			var pageInfo = response.data;
			
			fillTableBody(pageInfo);
		},
		"error": function(response) {
			layer.msg("服务器出错！");
		}
	});
}

// 填充tbody标签
function fillTableBody(pageInfo) {
	
	$("#rolePageBody").empty();
	
	// 当没有找到相关数据时 不显示分页
	$("#pagination").empty();
	
	if(pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0) {
		$("#rolePageBody").append("<tr><td colspan='4'>抱歉！没有查找到相关信息</td></tr>");
		
		return ;
	}
	
	var list = pageInfo.list;
	
	for(var i = 0; i < list.length; i++) {
		
		var role = list[i];
		
		var roleId = role.id;
		
		var roleName = role.name;
		
		var numberTd = "<td>" + (i + 1) + "</td>";
		
		var checkBoxTd = "<td><input id='" + roleId + "' class='itemBox' type='checkbox'></td>";
		
		var roleNameTd = "<td>" + roleName + "</td>";
		
		var checkBtn = "<button id='" + roleId + "'type='button' class='btn btn-success btn-xs checkBtn'><i class=' glyphicon glyphicon-check'></i></button>"
		
		// 将roleId作为该标签的id值，方便用户点击按钮时做出响应
		var pencilBtn = "<button id='" + roleId + "'type='button' class='btn btn-primary btn-xs pencilBtn'><i class=' glyphicon glyphicon-pencil'></i></button>";
		
		var removeBtn = "<button id='" + roleId + "'type='button' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>";
			
		var buttonTd = "<td>" + checkBtn + " " + pencilBtn + " " + removeBtn + "</td>";
		
		var tr = "<tr>" + numberTd + " " + checkBoxTd + " " + roleNameTd + " " + buttonTd + "</tr>";
		
		$("#rolePageBody").append(tr);
	}
	
	gernerateNavigator(pageInfo);
}

// 执行翻页
function gernerateNavigator(pageInfo) {
	
	// 获取总记录数
	var totalRecord = pageInfo.total;
	
	var prop = {
			"num_edge_entries": 3,	// 边缘页数
			"num_display_entries": 5,	// 主体页数
			"callback": paginationCallBack,	// 回调函数
			"items_per_page": pageInfo.pageSize,	// 每页显示多少条
			"current_page": pageInfo.pageNum - 1,	// 当前页数(从0开始)
			"prev_text": "上一页",		// 上一页按钮显示的文本
			"next_text": "上一页"		// 下一页按钮显示的文本
	};
	
	$("#pagination").pagination(totalRecord, prop);
	
}

// 回调函数
function paginationCallBack(pageIndex, jQuery) {
	window.pageNum = pageIndex + 1;
	
	generatePage();
	
	return false;
}