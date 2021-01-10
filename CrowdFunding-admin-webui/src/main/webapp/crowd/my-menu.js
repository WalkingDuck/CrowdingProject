/**
 * 生成树性菜单的函数
 */

function generateTree() {
	$.ajax({
		"url" : "menu/get/whole/tree",
		"type" : "post",
		"dataType" : "json",
		"success" : function(response) {
			var result = response.result;
			if (result == "SUCCESS") {
				var zNodes = response.data;

				// 生命对象存放zTree的设置
				var setting = {
					"view" : {
						"addDiyDom" : myAddDivDom,
						"addHoverDom" : myAddHoverDom,
						"removeHoverDom" : myRemoveHoverDom
					},
					"data" : {
						"key" : {
							"url" : "wtw"
						}
					}
				};

				// 生成树形菜单
				$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			} else {
				layer.msg(response.message);
			}
		}
	});
}

/**
 * 该函数负责更改菜单元素图标 treeId是当前整个树形菜单依附的
 * <ul>
 * 标签的id treeNode是树形菜单的每一个节点，包括后端返回的menu对象全部属性
 */
function myAddDivDom(treeId, treeNode) {
	/**
	 * zTree生成图标的规则：
	 * <ul>
	 * 标签的id_当前节点序号(treeNode.tId)_功能
	 */
	// 找到控制菜单对象图标的span标签，通过更改class属性就可以更改菜单图标
	var spanId = treeNode.tId + "_ico";
	// 删除旧的class
	// 添加新的class
	$("#" + spanId).removeClass().addClass(treeNode.icon);
}

// 鼠标停留在菜单节点时，显示按钮组
function myAddHoverDom(treeId, treeNode) {
	/**
	 * 按钮组标签结构 -> <span><a><i><i></a> <a><i><i></a></span> <a><i><i></a>取决于有多少个按钮
	 */

	// 为了准确找到span标签，给span添加一个有规律的id
	var btnGroupId = treeNode.tId + "_btnGrp";

	// 防止重复生成span标签，对标签数进行判断 > 0则说明已经生成，那么就不执行之后的操作
	if ($("#" + btnGroupId).length > 0) {
		return;
	}

	// 找到菜单元素的a标签，通过该标签编辑按钮组的span标签
	// span标签和a标签同级
	var anchorId = treeNode.tId + "_a";

	// 准备按钮的标签
	var editBtn = "<a id='"
			+ treeNode.id
			+ "' class='editBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='修改权限信息'>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg '></i></a>";
	var addBtn = "<a id='"
			+ treeNode.id
			+ "' class='addBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='添加权限信息'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>";
	var removeBtn = "<a id='"
			+ treeNode.id
			+ "' class='removeBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='删除权限信息'>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg '></i></a>";

	// 获取当前节点的级别
	var level = treeNode.level;

	// 保存当前节点的标签内容
	var btnHtml = "";

	// 根据级别选择鼠标停留时显示的选项
	if (level == 0) { // 根节点
		btnHtml = addBtn; // 鼠标放在根节点时只能有添加按钮显示
	}

	if (level == 1) { // 子节点
		btnHtml = addBtn + " " + editBtn; // 鼠标放在根节点时添加和编辑会显示

		var len = treeNode.children.length; // 获取当前节点的子节点的个数
		if (len == 0) { // 没有子节点，那么该节点可以删除
			btnHtml = btnHtml + " " + removeBtn;
		}
	}

	if (level == 2) { // 叶子节点
		btnHtml = editBtn + " " + removeBtn;
	}

	// 添加按钮组span标签
	$("#" + anchorId).after(
			"<span id='" + btnGroupId + "'>" + btnHtml + "</span>");
}

// 鼠标离开菜单节点时，关闭按钮组
function myRemoveHoverDom(treeId, treeNode) {
	// 找到按钮组span标签id
	var btnGroupId = treeNode.tId + "_btnGrp";

	// 删除该标签
	$("#" + btnGroupId).remove();
}