<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="modal fade" tabindex="-1" role="dialog" id="addModal">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">系统弹窗</h4>
			</div>
			<div class="modal-body">
				<form class="form-signin" role="form">
					<div class="form-group has-success has-feedback">
						<input type="text" class="form-control" id="inputSuccess4"
							name="roleName" placeholder="请输入角色名称" autofocus>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="saveRoleBtn">保存</button>
			</div>
		</div>
	</div>
</div>