<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>选课助手</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="../static/css/admin/bootstrap.min.css" />
<link rel="stylesheet"
	href="../static/css/admin/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="../static/css/admin/login.css" />
<link rel="stylesheet"
	href="../static/font-awesome/css/font-awesome.css" />
<!-- <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,800' rel='stylesheet' type='text/css'> -->
</head>
<body>
	<div id="loginbox">
		<form id="loginform" class="form-vertical" action="login"
			method="post" onsubmit="return check();">
			<div class="control-group normal_text">
				<h3>
					<img src="../static/img/admin/admin_logo.png" alt="Logo" />
				</h3>
			</div>
			<div class="control-group">
				<div class="controls">
					<div class="main_input_box">
						<span class="add-on bg_lg"><i class="icon-user"></i> </span><input
							type="text" name="username" placeholder="用户名" />
					</div>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<div class="main_input_box">
						<span class="add-on bg_ly"><i class="icon-lock"></i> </span><input
							type="password" name="password" placeholder="密码" />
					</div>
				</div>
			</div>

			<div class="error-summary" style="display:none;">
				<ul>
				</ul>
			</div>
			<c:if test="${result!=null}">
				<div class="error-summary">
					<ul>
						<li>${result}</li>
					</ul>
				</div>
			</c:if>
			<div class="form-actions">
				<a class="weibo_login pull-left" href="${pageContext.request.contextPath}/weibo/login"><span>使用微博登陆
				</span><i></i> </a>
				<!-- <span class="pull-left"><a href="#" class="flip-link btn btn-info" id="to-recover">Lost password?</a></span> -->
				<span class="pull-right"><a type="submit" href="javascript:;"
					class="btn btn-success"> 登陆</a> </span>
			</div>
		</form>

	</div>
	<script src="../static/js/admin/lib/jquery.min.js"></script>
	<script src="../static/js/admin/page/login/login.js"></script>
</body>
</html>