<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">

<%@ include file="/WEB-INF/include-head.jsp"%>
<link rel="stylesheet" href="ztree/zTreeStyle.css">
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my-menu.js"></script>
<script type="text/javascript">
	$(function() {
		
		// 生成树形菜单
		generateTree();
		
		// 绑定点击添加按钮后的响应函数
		$("#treeDemo").on("click", ".addBtn", function() {
			
			// 获取当前节点id 作为将要添加的节点的pid
			window.pid = this.id;
			
			// 显示模态框 让用户选择要添加的菜单对象
			$("#menuAddModal").modal("show");
			
			// 防止点击按钮后跳转到其他页面
			return false;
		});
		
		// 绑定点击模态框保存后的响应函数
		$("#menuSaveBtn").click(function() {
			
			// 获取要添加的菜单的名字 以及链接和图标
			var name = $.trim($("#menuAddModal [name=name]").val());
			var url = $.trim($("#menuAddModal [name=url]").val());
			var icon = $("#menuAddModal [name=icon]:checked").val();
			
			$.ajax({
				"url": "menu/save",
				"type": "post",
				"data": {
					"pid": window.pid,
					"name": name,
					"url": url,
					"icon": icon
				},
				"dataType": "json",
				"success": function(response) {
					var result = response.result;
					if(result == "SUCCESS") {
						layer.msg("SUCCESS! ");
						
						// 重新生成树形菜单
						generateTree();
					} else {
						layer.msg("FAILED! " + response.message);
					}
				},
				"error": function(response) {
					layer.msg(response.status + " " + response.statusText);
				}
			});
			
			// 关闭模态框
			$("#menuAddModal").modal("hide");
			
			// 清空模态框表单内容，防止用户再次点击时模态框不是初始状态
			// 调用重置按钮的click函数即可
			$("#menuResetBtn").click();
		});
		
		// 绑定点击编辑按钮后的响应函数
		$("#treeDemo").on("click", ".editBtn", function() {
			
			// 获取当前节点id 方便获取节点信息
			window.id = this.id;
			
			// 显示模态框 让用户选择要添加的菜单对象
			$("#menuEditModal").modal("show");
			
			// 获取zTreeObj对象
			var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
			
			// 通过zTreeObj来获取到要更新的菜单节点的信息
			// 用来查询节点的属性名
			var key = "id";
			
			// 用来查询节点的属性值
			var value = window.id;
			
			// 获取到要修改的节点
			var targetNode = zTreeObj.getNodeByParam(key, value);
			
			// 回显表单数据
			$("#menuEditModal [name=name]").val(targetNode.name);
			$("#menuEditModal [name=url]").val(targetNode.url);
			
			// 回显radio 将被选中的选项的value放进数组传进val()中即可
			$("#menuEditModal [name=icon]").val([targetNode.icon]);
			
			// 防止点击按钮后跳转到其他页面
			return false;
		});
		
		// 绑定更新后点击更新按钮的响应函数
		$("#menuEditBtn").click(function() {
			
			// 获取更改后的内容
			var name = $("#menuEditModal [name=name]").val();
			var url = $("#menuEditModal [name=url]").val();
			var icon = $("#menuEditModal [name=icon]:checked").val();
			
			$.ajax({
				"url": "menu/update",
				"type": "post",
				"data": {
					"id": window.id,
					"name": name,
					"url": url,
					"icon": icon
				},
				"dataType": "json",
				"success": function(response) {
					var result = response.result;
					if(result == "SUCCESS") {
						layer.msg("SUCCESS! ");
						
						// 重新生成树形菜单
						generateTree();
					} else {
						layer.msg("FAILED! " + response.message);
					}
				},
				"error": function(response) {
					layer.msg(response.status + " " + response.statusText);
				}
			});
			
			// 关闭模态框
			$("#menuEditModal").modal("hide");
			
		});
		
		
		// 绑定删除响应函数
		$("#treeDemo").on("click", ".removeBtn", function() {
			
			// 获取当前节点id 方便获取节点信息
			window.id = this.id;
			
			// 显示模态框 让用户选择要添加的菜单对象
			$("#menuConfirmModal").modal("show");
			
			// 获取zTreeObj对象
			var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
			
			// 通过zTreeObj来获取到要更新的菜单节点的信息
			// 用来查询节点的属性名
			var key = "id";
			
			// 用来查询节点的属性值
			var value = window.id;
			
			// 获取到要修改的节点
			var targetNode = zTreeObj.getNodeByParam(key, value);
			
			// 获取到要删除节点的name
			var name = targetNode.name;
			
			// 在模态框中显示出name
			$("#removeNodeSpan").text(name);
			
			// 防止点击按钮后跳转到其他页面
			return false;
		});
		
		// 绑定更新后点击更新按钮的响应函数
		$("#confirmBtn").click(function() {
			
			$.ajax({
				"url": "menu/remove",
				"type": "post",
				"data": {
					"id": window.id,
				},
				"dataType": "json",
				"success": function(response) {
					var result = response.result;
					if(result == "SUCCESS") {
						layer.msg("SUCCESS! ");
						
						// 重新生成树形菜单
						generateTree();
					} else {
						layer.msg("FAILED! " + response.message);
					}
				},
				"error": function(response) {
					layer.msg(response.status + " " + response.statusText);
				}
			});
			
			// 关闭模态框
			$("#menuConfirmModal").modal("hide");
			
		});
		
		
	});
</script>
<body>

	<%@ include file="/WEB-INF/include-nav.jsp"%>
	<div class="container-fluid">
		<div class="row">

			<%@ include file="/WEB-INF/include-sidebar.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

				<div class="panel panel-default">
					<div class="panel-heading">
						<i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
						<div style="float: right; cursor: pointer;" data-toggle="modal"
							data-target="#myModal">
							<i class="glyphicon glyphicon-question-sign"></i>
						</div>
					</div>
					<div class="panel-body">
						<ul id="treeDemo" class="ztree">

						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<%@include file="/WEB-INF/modal-menu-add.jsp" %>
	<%@include file="/WEB-INF/modal-menu-confirm.jsp" %>
	<%@include file="/WEB-INF/modal-menu-edit.jsp" %>
</body>
</html>
