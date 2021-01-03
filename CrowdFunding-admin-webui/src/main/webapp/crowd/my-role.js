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
		
		var checkBoxTd = "<td><input type='checkbox'></td>";
		
		var roleNameTd = "<td>" + roleName + "</td>";
		
		var checkBtn = "<button type='button' class='btn btn-success btn-xs'><i class=' glyphicon glyphicon-check'></i></button>"
		
		// 将roleId作为该标签的id值，方便用户点击按钮时做出响应
		var pencilBtn = "<button id='" + roleId + "'type='button' class='btn btn-primary btn-xs pencilBtn'><i class=' glyphicon glyphicon-pencil'></i></button>";
		
		var removeBtn = "<button type='button' class='btn btn-danger btn-xs'><i class=' glyphicon glyphicon-remove'></i></button>";
			
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