<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.zhku.web.Constants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="header.jsp"></jsp:include>
<style>
.error_ex {
	text-align: center;
}

.error_ex h1 {
	font-size: 140px;
	font-weight: bold;
	padding: 50px 0;
	color: #28B779;
}
</style>
<body>

	<!-- Wrap all page content here -->
	<div id="wrap">

		<jsp:include page="topBar.jsp"></jsp:include>

		<!-- Begin page content -->
		<div id="pageContent">
			<div class="container">
				
			<div class="error_ex">
              <h1>出错啦!</h1>
              <h3>${message}</h3>
              <%session.removeAttribute("message") ;%>
              <p>页面错误，你可以点击下面的连接返回首页</p>
              <a class="btn btn-warning btn-big" href="${pageContext.request.contextPath}/index">返回首页</a> </div>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/index.js"></script>
</body>

</html>