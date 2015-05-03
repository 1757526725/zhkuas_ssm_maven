<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="admin_header.jsp"></jsp:include>
<jsp:include page="admin_sideBar.jsp"></jsp:include>


<!--main-container-part-->
<div id="content">
	<!--breadcrumbs-->
	<div id="content-header">
		<div id="breadcrumb">
			<a href="admin/index" title="Go to Home" class="tip-bottom"><i
				class="icon-home"></i> 首页</a> <a href="${pageContext.request.contextPath}/admin/school/students" class="current">所有学生</a>
		</div>
	</div>
	<!--End-breadcrumbs--> 

	<!--container-fluid-->
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="btn-tools">
					<!--Action tools-->
<!-- 					<button class="btn btn-success" id="btn-removeBind" disabled>
						<i class="icon-refresh"></i> 解除绑定
					</button> -->
					<span class="text-primary text-large" >提示:使用右上角的搜索功能可以快速查找到相应的学生！</span>
					<!--End Action tools-->
				</div>
				<div class="widget-box">
					<div class="widget-content nopadding">
						<form action="#" method="post" class="form-horizontal"
							id="student_form">
							<table class="table table-bordered table-striped with-check">
								<thead>
									<tr>
										<th><input type="checkbox" id="title-checkbox"
											name="title-checkbox">
										</th>
										<th>ID</th>
										<th>学号</th>
										<th>姓名</th>
										<th>班级</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="student" items="${dataList}">
										<tr>
											<td><input type="checkbox"></td>
											<td class="text-center">${student.id }</td>
											<td class="text-center">${student.name}</td>
											<td class="text-center">${student.sNo}</td>
											<td class="text-center">${student.classBelongTo.name}</td>
											<td class="text-center"><a href="javascript:void(0)">查看学生详情</a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</form>
					</div>
				</div>
			<jsp:include page="include/admin_pagation.jsp"></jsp:include>
			</div>
		</div>
	</div>
	<!--End container-fluid-->
</div>


<!--end-main-container-part-->
<jsp:include page="admin_footer.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/static/js/admin/page/school/student.js"></script>
</body>
</html>