<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.github.pagehelper.Page"%> 
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
			<a href="index.html" title="Go to Home" class="tip-bottom"><i
				class="icon-home"></i> 首页</a> <a href="${pageContext.request.contextPath}/admin/course/public" class="current">公选课程</a>
		</div>
	</div>
	<!--End-breadcrumbs-->

	<!--container-fluid-->
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="btn-tools">
					<!--Action tools-->
					<input id="iptTermNo" type="hidden" value="${currentTerm.no}">
					<input id="iptCampusId" type="hidden" value="${currentCampus.name}">
					<div class="btn-group">
						<button data-toggle="dropdown" class="btn btn-success dropdown-toggle">
							${currentTerm.name}
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
						<c:forEach var="term" items="${terms}">
							<c:if test="${term.no!=currentTerm.no}">
							<li><a href="${pageContext.request.contextPath}/admin/course/public/term/${term.no}/campus/${currentCampus.id}">${term.name}</a></li>
							</c:if>
						</c:forEach>
						</ul>
					</div>
					<div class="btn-group">
						<button data-toggle="dropdown" class="btn btn-success dropdown-toggle">
							${currentCampus.name}
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
						<c:forEach var="campus" items="${campuses}">
							<c:if test="${campus.id!=currentCampus.id }">
							<li><a href="${pageContext.request.contextPath}/admin/course/public/term/${currentTerm.no}/campus/${campus.id}">${campus.name}</a></li>
							</c:if>
						</c:forEach>
						</ul>
					</div>
					<button class="btn btn-danger" id="btn-empty">
						<i class="icon-refresh"></i> 清空
					</button>
					<button class="btn btn-success" id="btn-update">
						<i class="icon-refresh"></i> 更新数据
					</button>
					<div class="update-message" style="display:none;">
						<div>
							<span class="icon24 icomoon-icon-arrow-up-2 green">LOG:</span>
							<span class="message">... </span>
							<div class="progress progress-success active active progress-striped ">
								<div style="width: 72%;" class="bar">72%</div>
							</div>
						</div>
					</div>

					<!--End Action tools-->
				</div>
				<div class="widget-box">
					<div class="widget-content nopadding">
						<form action="#" method="post" class="form-horizontal"
							id="academy_form">
							<table class="table table-bordered table-striped with-check">
								<thead>
									<tr>
										<th>序号</th>
										<th>课程</th>
										<th>学分</th>
										<th>任课老师</th>
										<th>上课班号</th>
										<th>周次</th>
										<th>时间</th>
										<th>上课地点</th>
									</tr>
								</thead>
								<tbody>
									<%-- <c:forEach varStatus="index" var="publicCourse" items="${publicCourseList}">
										<tr>
											<td>${index+1}</td>
											<td class="text-center">${publicCourse.course.name}</td>
											<td class="text-center">${publicCourse.course.no}</td>
											<td class="text-center"><c:out value="${major.academy.name}" default="${currentAcademy.name}"></c:out></td>
											<td class="text-center"><a href="javascript:;">查看班级</a></td>
										</tr>
									</c:forEach> --%>
									
									<% int count=0; %>
									<c:forEach var="publicCourse" items="${publicCourses }" varStatus="status">
										<tr>
											<td width="5%" align="center"><%=++count%><br></td>
											<c:choose>
												<c:when test="${status.index!=0&&(publicCourse.termCourse.course.no==publicCourses[status.index-1].termCourse.course.no)}">
													<td width="25%" align="left"><br></td>
													<td width="6%" align="center"><br></td>
												</c:when>
												<c:otherwise>
													<td width="25%" align="left"><a href="${pageContext.request.contextPath}/main/course/detail/${publicCourse.termCourse.course.no}" target="_blank">[${publicCourse.termCourse.course.nameNo}]${publicCourse.termCourse.course.name}</a><br></td>
													<td width="6%" align="center">${publicCourse.termCourse.course.credits}<br></td>
												</c:otherwise>
											</c:choose>

											<td width="14%" align="left">${publicCourse.termCourse.teacher.name}<br></td>
											<td width="7%" align="center">${publicCourse.termCourse.courseClassNo}<br></td>
											<td width="10%" align="left">${publicCourse.termCourse.periods}<br></td>
											<td width="10%" align="left">${publicCourse.termCourse.weekOfChinese}[${publicCourse.termCourse.classSection}节]<br></td>
											<td width="16%" align="left">${publicCourse.termCourse.classroom.name}<br></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--End container-fluid-->
</div>


<!--end-main-container-part-->
<jsp:include  page="admin_footer.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/static/js/admin/page/school/major.js"></script>
</body>
</html>