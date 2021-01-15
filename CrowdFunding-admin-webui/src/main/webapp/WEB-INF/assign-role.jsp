<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">

<%@ include file="/WEB-INF/include-head.jsp"%>

<script type="text/javascript">

	$(function() {
		
		// 绑定向右按钮的响应函数
		// :eq(0) 表示选择第一个select元素
		// > 表示选择子元素
		$("#toRightBtn").click(function() {
			// 找到左边select中被选中的元素然后放进右边的select中
			$("select:eq(0) > option:selected").appendTo("select:eq(1)");
			
		});
		
		// 绑定向左按钮的响应函数
		$("#toLeftBtn").click(function() {
			// 同上
			$("select:eq(1) > option:selected").appendTo("select:eq(0)");
		});
		
		// 绑定提交按钮响应函数，点击保存后将右边select中的所有元素传入后端
		$("#submitBtn").click(function() {
			// 将右边select的所有元素都设置为selected
			$("select:eq(1) > option:selected").prop("selected", "selected");
		});
		
	});

</script>

<body>

	<%@ include file="/WEB-INF/include-nav.jsp"%>
	<div class="container-fluid">
		<div class="row">

			<%@ include file="/WEB-INF/include-sidebar.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<ol class="breadcrumb">
					<li><a href="#">首页</a></li>
					<li><a href="#">数据列表</a></li>
					<li class="active">分配角色</li>
				</ol>
				<div class="panel panel-default">
					<div class="panel-body">
						<form role="form" class="form-inline" action="assign/do/role/assign.html" method="post">
							<input type="hidden" name="adminId" value="${param.adminId }"/>
							<input type="hidden" name="pageNum" value="${param.pageNum }"/>
							<input type="hidden" name="keyword" value="${param.keyword }"/>
							<div class="form-group">
								<label for="exampleInputPassword1">未分配角色列表</label><br> <select
									class="form-control" multiple="multiple" size="10"
									style="width: 100px; overflow-y: auto;">
									<c:forEach items="${requestScope.unAssignedRoleList }" var="role">
										<option value="${role.id }">${role.name }</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
								<ul>
									<li id="toRightBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
									<br>
									<li id="toLeftBtn" class="btn btn-default glyphicon glyphicon-chevron-left"
										style="margin-top: 20px;"></li>
								</ul>
							</div>
							<div class="form-group" style="margin-left: 40px;">
								<label for="exampleInputPassword1">已分配角色列表</label><br> <select
									class="form-control" multiple="multiple" size="10"
									style="width: 100px; overflow-y: auto;" name="roleIdList">
									<c:forEach items="${requestScope.assignedRoleList }" var="role">
										<option value="${role.id }">${role.name }</option>
									</c:forEach>
								</select>
							</div>
							<button id="submitBtn" style="width: 150px" class="btn btn-lg btn-success btn-block" type="submit">保存</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
