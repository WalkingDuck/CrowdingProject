<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<base href="http://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath }/"/>
<script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript">

	$(function() {
		$("#btn1").click(function() {
			$.ajax({
				"url": "send/array.html",
				"type": "post",
				"data": {
					"array": [5,8,12]
				},
				"datatype": "text",
				"success": function(reponse) {
					alert(reponse);
				},
				"error": function(reponse) {
					alert(reponse);
				}
			});
		});
	});

</script>
</head>
<body>
	<h1><a href="${pageContext.request.contextPath }/test">hello</a></h1>
	<br/>
	<button id="btn1">test Jquery</button>
</body>
</html>