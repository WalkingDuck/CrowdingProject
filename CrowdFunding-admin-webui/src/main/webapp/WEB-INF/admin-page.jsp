<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="zh-CN">

<%@ include file="/WEB-INF/include-head.jsp"%>
<link rel="stylesheet" href="css/pagination.css">
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript">

	$(function() {
		initPagination();
	});
	
	function initPagination() {
		
		// 获取总记录数
		var totalRecord = ${requestScope.pageInfo.total};
		
		// 生成json对象来配置导航条
		var prop = {
				num_edge_entries: 3,	// 边缘页数
				num_display_entries: 5,	// 主体页数
				callback: pageSelectCallback,	// 回调函数
				items_per_page: ${requestScope.pageInfo.pageSize},	// 每页显示多少条
				current_page: ${requestScope.pageInfo.pageNum - 1},	// 当前页数(从0开始)
				prev_text: "上一页",		// 上一页按钮显示的文本
				next_text: "上一页"		// 下一页按钮显示的文本
		};
		
		// 生成页码导航条
		$("#Pagination").pagination(totalRecord, prop);
		
	}

	// 回调函数
	// 该函数得作用是设置当用户点击 "1 2 3"这样的按钮时应该进行的响应
	// pageIndex是pagination传入的从0开始的页码
	function pageSelectCallback(pageIndex, jQuery) {
		
		// 根据pageIndex计算pageNum
		var pageNum = pageIndex + 1;
		
		// 跳转页面
		window.location.href = "admin/get/page.html?pageNum=" + pageNum + "&keyword=${param.keyword}";
		
		// 取消超链接的默认行为，否则跳转的地方会有误
		return false;
		
	}
	
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
						<form action="admin/get/page.html" method="post"
							class="form-inline" role="form" style="float: left;">
							<div class="form-group has-feedback">
								<div class="input-group">
									<div class="input-group-addon">查询条件</div>
									<input name="keyword" class="form-control has-success"
										type="text" placeholder="请输入查询条件">
								</div>
							</div>
							<button type="submit" class="btn btn-warning">
								<i class="glyphicon glyphicon-search"></i> 查询
							</button>
						</form>
						<button type="button" class="btn btn-danger"
							style="float: right; margin-left: 10px;">
							<i class=" glyphicon glyphicon-remove"></i> 删除
						</button>
						<!-- <button type="button" class="btn btn-primary"
							style="float: right;" onclick="window.location.href='add.html'">
							<i class="glyphicon glyphicon-plus"></i> 新增
						</button> -->
						
						<a style="float: right;" class="btn btn-primary" href="admin/to/add/page.html">
							<i class="glyphicon glyphicon-plus"></i> 新增
						</a>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<table class="table  table-bordered">
								<thead>
									<tr>
										<th width="30">#</th>
										<th width="30"><input type="checkbox"></th>
										<th>账号</th>
										<th>名称</th>
										<th>邮箱地址</th>
										<th width="100">操作</th>
									</tr>
								</thead>
								<tbody>

									<c:if test="${empty requestScope.pageInfo.list }">
										<tr>
											<td colspan="6" align="center">抱歉! 没有找到相关数据</td>
										</tr>
									</c:if>

									<c:if test="${!empty requestScope.pageInfo.list }">
										<c:forEach items="${requestScope.pageInfo.list }" var="admin"
											varStatus="status">
											<tr>
												<td>${status.count }</td>
												<td><input type="checkbox"></td>
												<td>${admin.loginAcct }</td>
												<td>${admin.userName }</td>
												<td>${admin.email }</td>
												<td>
													<button type="button" class="btn btn-success btn-xs">
														<i class=" glyphicon glyphicon-check"></i>
													</button>
													<!-- <button type="button" class="btn btn-primary btn-xs">
														<i class=" glyphicon glyphicon-pencil"></i>
													</button> -->
													<!-- <button type="button" class="btn btn-danger btn-xs">
														<i class=" glyphicon glyphicon-remove"></i>
													</button>  -->
													<a href="admin/to/edit/page.html?adminId=${admin.id }&pageNum=${requestScope.pageInfo.pageNum }&keyword=${param.keyword }" class="btn btn-primary btn-xs">
														<i class=" glyphicon glyphicon-pencil"></i>
													</a>
													<a href="admin/remove/${admin.id }/${requestScope.pageInfo.pageNum }/${param.keyword }.html" class="btn btn-danger btn-xs">
														<i class=" glyphicon glyphicon-remove"></i>
													</a>
												</td>
											</tr>
										</c:forEach>
									</c:if>

									<!-- <tr>
										<td>16</td>
										<td><input type="checkbox"></td>
										<td>sodales</td>
										<td>ligula</td>
										<td>in</td>
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
											<div id="Pagination" class="pagination">
												<!-- 这里显示分页 -->

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
</body>
</html>
