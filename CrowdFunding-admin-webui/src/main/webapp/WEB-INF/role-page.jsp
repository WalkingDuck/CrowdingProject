<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">

<%@ include file="/WEB-INF/include-head.jsp"%>
<link rel="stylesheet" href="css/pagination.css">
<script type="text/javascript">
	$(function() {
		// 为分页操作准备初始化数据
		window.pageNum = 1;
		window.pageSize = 5;
		window.keyword = "";

		generatePage();

		// 搜索关键词后进行分页
		$("#search").click(function() {

			window.keyword = $("#keywordInput").val();

			generatePage();
		});

		// 点击新增后展示模态框
		$("#showAddModalBtn").click(function() {
			$("#addModal").modal("show");
		});

		// 点击保存后新增角色
		$("#saveRoleBtn").click(function() {

			// 获取文本框中的角色名称
			// 选择id为addModal的后代中 name属性=roleName的标签
			var roleName = $.trim($("#addModal [name = roleName]").val());

			// ajax
			$.ajax({
				"url" : "role/save.json",
				"type" : "post",
				"data" : {
					"roleName" : roleName
				},
				"dataType" : "json",
				"success" : function(response) {
					var result = response.result;

					// 保存成功
					if (result == "SUCCESS") {
						layer.msg("SUCCESS");

						// 重新加载分页
						window.pageNum = 999999; // 为了跳转到最后一页显示新增的角色，设置一个无限大的pageNum
						generatePage();
					}

					// 保存失败
					if (result == "FAILED") {
						layer.msg("FAILED! " + response.message);
					}

				},
				"error" : function(response) {
					layer.msg(response.status + " " + response.statusText);
				}
			});

			// 关闭模态框
			$("#addModal").modal("hide");

			// 清理模态框
			$("#addModal [name = roleName]").val("");

		});

		// 绑定rolePageBody标签的所有子标签中class=pencilBtn的标签的click事件函数
		// 点击铅笔按钮后显示模态框
		$("#rolePageBody").on("click", ".pencilBtn", function() {

			// 打开模态框
			$("#editModal").modal("show");

			// 获取修改对象的名称
			var roleName = $(this).parent().prev().text();

			// 获取id
			window.roleId = this.id;

			// 将roleName显示在模态框中
			$("#editModal [name = roleName]").val(roleName);
		});

		// 点击更新按钮后进行更新操作
		$("#updateRoleBtn").click(function() {

			// 获取更新后的roleName
			var roleName = $("#editModal [name = roleName]").val();
			$.ajax({
				"url" : "role/update.json",
				"type" : "post",
				"data" : {
					"id" : window.roleId,
					"name" : roleName
				},
				"dataType" : "json",
				"success" : function(response) {
					var result = response.result;

					// 保存成功
					if (result == "SUCCESS") {
						layer.msg("SUCCESS");

						// 重新加载分页
						generatePage();
					}

					// 保存失败
					if (result == "FAILED") {
						layer.msg("FAILED! " + response.message);
					}

				},
				"error" : function(response) {
					layer.msg(response.status + " " + response.statusText);
				}
			});

			// 关闭模态框
			$("#editModal").modal("hide");
		});

		// 当用户点击删除按钮后，弹出模态框询问是否确定删除
		$("#removeRoleBtn").click(function() {

			var requestbody = JSON.stringify(window.roleIdArray);
			$.ajax({
				"url" : "role/delete/by/role/id/array.json",
				"type" : "post",
				"data" : requestbody,
				"contentType" : "application/json;charset=UTF-8",
				"dataType" : "json",
				"success" : function(response) {
					var result = response.result;

					// 删除成功
					if (result == "SUCCESS") {
						layer.msg("SUCCESS");

						// 重新加载分页
						generatePage();
					}

					// 保存失败
					if (result == "FAILED") {
						layer.msg("FAILED! " + response.message);
					}

				},
				"error" : function(response) {
					layer.msg(response.status + " " + response.statusText);
				}
			});

			// 将全选框状态设置为 未被选中
			$("#summaryBox").prop("checked", false);
			
			// 关闭模态框
			$("#confirmModal").modal("hide");
		});

		// 当用户点击用户一行的删除图标后的响应函数 -> 单条删除
		$("#rolePageBody").on("click", ".removeBtn", function() {

			// 获取删除对象的roleName
			var name = $(this).parent().prev().text();
			
			// 创建一个数组，并将删除的对象放入数组
			var roleArray = [{
				"id": this.id,
				"roleName": name
			}];
			
			showConfirmModal(roleArray)
		});
		
		// 实现全选功能
		$("#summaryBox").click(function() {
			
			// 查看当前全选是否被选中
			var status = this.checked;
			
			// 根据status来设置其他多选框的状态
			$(".itemBox").prop("checked", status);
		});
		
		// 当本页所有用户被选中时，全选会自动选中
		$("#rolePageBody").on("click", ".itemBox", function() {
			
			// 获取被选中的元素的数量
			var checkedCount = $(".itemBox:checked").length;
			
			// 获取总的元素数量
			var totalCount = $(".itemBox").length;
			
			// 根据两者比较来设置全选框的状态
			$("#summaryBox").prop("checked", checkedCount == totalCount);
			
		});
		
		// 批量删除按钮的响应函数
		$("#batchRemoveBtn").click(function() {
			
			var roleArray = [];
			
			// 遍历每一个被选中的元素进行操作
			$(".itemBox:checked").each(function() {
				var roleId = this.id;
				
				// 获取名字
				var name = $(this).parent().next().text();
				
				roleArray.push({
					"id": roleId,
					"roleName": name
				});
			});
			
			// 检查用户是否没有选择元素就点击删除按钮
			if(roleArray.length == 0) {
				layer.msg("请至少选择一个元素后，再进行删除！");
				return ;
			}
			
			// 调用函数打开模态框
			showConfirmModal(roleArray);
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
						<h3 class="panel-title">
							<i class="glyphicon glyphicon-th"></i> 数据列表
						</h3>
					</div>
					<div class="panel-body">
						<form class="form-inline" role="form" style="float: left;">
							<div class="form-group has-feedback">
								<div class="input-group">
									<div class="input-group-addon">查询条件</div>
									<input id="keywordInput" class="form-control has-success"
										type="text" placeholder="请输入查询条件">
								</div>
							</div>
							<button id="search" type="button" class="btn btn-warning">
								<i class="glyphicon glyphicon-search"></i> 查询
							</button>
						</form>
						<button type="button" class="btn btn-danger"
							style="float: right; margin-left: 10px;" id="batchRemoveBtn">
							<i class=" glyphicon glyphicon-remove"></i> 删除
						</button>
						<button type="button" id="showAddModalBtn" class="btn btn-primary"
							style="float: right;">
							<i class="glyphicon glyphicon-plus"></i> 新增
						</button>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<table class="table  table-bordered">
								<thead>
									<tr>
										<th width="30">#</th>
										<th width="30"><input id="summaryBox" type="checkbox"></th>
										<th>名称</th>
										<th width="100">操作</th>
									</tr>
								</thead>
								<tbody id="rolePageBody">
									<!-- <tr>
										<td>8</td>
										<td><input type="checkbox"></td>
										<td>CMO / CMS - 配置管理员</td>
										<td>
											<button type="button" class="btn btn-success btn-xs">
												<i class=" glyphicon glyphicon-check"></i>
											</button>
											<button type="button" class="btn btn-primary btn-xs">
												<i class=" glyphicon glyphicon-pencil"></i>
											</button>
											<button type="button" class="btn btn-danger btn-xs">
												<i class=" glyphicon glyphicon-remove"></i>
											</button>
										</td>
									</tr> -->
								</tbody>
								<tfoot>
									<tr>
										<td colspan="6" align="center">
											<div id="pagination" class="pagination">
												<!-- 												<li class="disabled"><a href="#">上一页</a></li>
												<li class="active"><a href="#">1 <span
														class="sr-only">(current)</span></a></li>
												<li><a href="#">2</a></li>
												<li><a href="#">3</a></li>
												<li><a href="#">4</a></li>
												<li><a href="#">5</a></li>
												<li><a href="#">下一页</a></li> -->
											</div>
										</td>
									</tr>

								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/modal-role-add.jsp"%>
	<%@ include file="/WEB-INF/modal-role-edit.jsp"%>
	<%@ include file="/WEB-INF/modal-role-confirm.jsp"%>
</body>
</html>
