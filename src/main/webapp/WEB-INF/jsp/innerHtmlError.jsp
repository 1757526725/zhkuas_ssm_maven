<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.zhku.web.Constants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<html>
<style>
.error_ex {
	text-align: center;
}
.error_ex h1 {
	font-size: 200%;
	font-weight: bold;
	padding: 20px 0;
	color: #28B779;
}
.container{
	margin: 0 auto;
	width: 500px;
}
</style>
<body>

	<!-- Wrap all page content here -->
	<div id="wrap">
		<!-- Begin page content -->
		<div id="pageContent">
			<div class="container">
				<div class="error_ex">
					<h1>出错啦!</h1>
					<h3>${message}</h3>
					<%
						session.removeAttribute("message");
					%>
					<p>页面错误，请联系@仲恺选课助手</p>
				</div>
			</div>
		</div>
	</div>
</body>

</html>