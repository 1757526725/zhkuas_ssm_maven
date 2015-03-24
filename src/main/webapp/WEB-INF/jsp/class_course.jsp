<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="header.jsp"></jsp:include>

<body>

	<!-- Wrap all page content here -->
	<div id="wrap">

		<jsp:include page="topBar.jsp"></jsp:include>

		<!-- Begin page content -->
		<div id="pageContent">
			<div id="classMenu" class="clearfix">

				<div class="bs-sidebar hidden-print affix clearfix" role="complementary"
					style="">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<div class="input-group">
								<input type="text" id="keyWord" class="form-control"> <span
									class="input-group-btn">
									<button class="btn btn-default" id="bt-search" type="button">查找</button> </span>
							</div>
							<!-- /input-group -->
						</div>
						<div id="sidlerbar" style="height:500px; overflow:auto;">
							<ul class="nav bs-sidenav">
								<c:forEach var="clazz" items="${classes}">
									<li value="${clazz.classNo}"><a href="javascript:void(0);">${clazz.className}</a>
										<ul class="nav" style="display: none; ">
											<li value="20130"><a href="javascript:void(0);">2013-2014第一学期</a>
											</li>
											<li value="20131"><a href="javascript:void(0);">2013-2014第二学期</a>
											</li>
											<li value="20140"><a href="javascript:void(0);">2014-2015第一学期</a>
											</li>
										</ul></li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div id="content-right" class="col-xs-10 ">
				<div class="container courseTable">
					<h1>请选择班级与学期</h1>
				</div>
			</div>
		</div>
		<div style="clear:both;"></div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
	<script type="text/javascript" src="../js/jquery.mousewheel.js"></script>
	<script type="text/javascript" src="../js/jquery.jscrollpane.min.js"></script>
	<script type="text/javascript" src="../js/app.js"></script>
	<script type="text/javascript">
		$(function() {
			var settings = {
				mouseWheelSpeed : 20,
				contentWidth : 210,
				autoReinitialise : true
			};
			$("#sidlerbar").jScrollPane(settings);

		});
	</script>
</body>
</html>