<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.zhku.web.Constants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="header.jsp"></jsp:include>

<body>

	<!-- Wrap all page content here -->
	<div id="wrap">

		<jsp:include page="topBar.jsp"></jsp:include>

		<!-- Begin page content -->
		<div id="pageContent" class="clearfix">

			<div class="col-xs-12 ">
				<div id="selectBar">
					<div type="button" class="bt-circle" data-toggle="tooltip"
						data-placement="right" title="" data-original-title="全部">
						<a href="${pageContext.request.contextPath}/main/campus/${campus.id }/course/term/${term.no}">◆</a>
					</div>
					<div type="button" class="bt-circle" data-toggle="tooltip"
						data-placement="right" title="" data-original-title="人文社科类">
						<a
							href="${pageContext.request.contextPath}/main/campus/${campus.id }/course/term/${term.no}/type/<%=Constants.CourseType.RENWENG.getCode()%>">人</a>
					</div>
					<div type="button" class="bt-circle" data-toggle="tooltip"
						data-placement="right" title="" data-original-title="科学技术类">
						<a
							href="${pageContext.request.contextPath}/main/campus/${campus.id }/course/term/${term.no}/type/<%=Constants.CourseType.KEJI.getCode()%>">科</a>
					</div>
					<div type="button" class="bt-circle" data-toggle="tooltip"
						data-placement="right" title="" data-original-title="外语类">
						<a
							href="${pageContext.request.contextPath}/main/campus/${campus.id }/course/term/${term.no}/type/<%=Constants.CourseType.WAIYU.getCode()%>">外</a>
					</div>
					<div type="button" class="bt-circle" data-toggle="tooltip"
						data-placement="right" title="" data-original-title="艺术体育类">
						<a
							href="${pageContext.request.contextPath}/main/campus/${campus.id }/course/term/${term.no}/type/<%=Constants.CourseType.YISHU.getCode()%>">艺</a>
					</div>
				</div>
				<div class="container courseTable">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h3 class="panel-title">
								${campus.name}>>>>>>${publicCourseType.name}<span style="float:right;">${term.name}</span>
								<input type="hidden" name="termNo" value="${term.no}"/>
								<input type="hidden" name="campusId" value="${campus.id}"/>
							</h3>
						</div>
						<div class="table-responsive">
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td width="5%" align="center">序号</td>
										<td width="25%" align="center">课程</td>
										<td width="6%" align="center">学分</td>
										<td width="14%" align="center">任课教师</td>
										<td width="7%" align="center">上课班号</td>
										<td width="10%" align="center">周次</td>
										<td width="10%" align="center">时间</td>
										<td width="16%" align="center">上课地点</td>
										<td width="7%" align="center">操作</td>
									</tr>
									<%
										int count = 1;
									%>
									<c:forEach var="publicCourse" items="${publicCourses }" varStatus="status">
										<tr>
											<td width="5%" align="center"><%=count++%><br></td>

											<c:choose>
												<c:when test="${status.index!=0&&(publicCourse.termCourse.course.no==publicCourses[status.index-1].termCourse.course.no)}">
													<td width="25%" align="left"><br></td>
													<td width="6%" align="center"><br></td>
												</c:when>
												<c:otherwise>
													<td width="25%" align="left"><a href="public_course_detail?cNo=${publicCourse.termCourse.course.no}" target="_blank">[${publicCourse.termCourse.course.nameNo}]${publicCourse.termCourse.course.name}</a><br></td>
													<td width="6%" align="center">${publicCourse.termCourse.course.credits}<br></td>
												</c:otherwise>
											</c:choose>

											<td width="14%" align="left">${publicCourse.termCourse.teacher.name}<br></td>
											<td width="7%" align="center">${publicCourse.termCourse.courseClassNo}<br></td>
											<td width="10%" align="left">${publicCourse.termCourse.periods}<br></td>
											<td width="10%" align="left">${publicCourse.termCourse.weekOfChinese}[${publicCourse.termCourse.classSection}节]<br></td>
											<td width="16%" align="left">${publicCourse.termCourse.classroom.name}<br></td>
												<c:choose>
												<c:when
													test="${status.index!=0&&(publicCourse.termCourse.course.no==publicCourses[status.index-1].termCourse.course.no)}">
													<td width="5%" align="left"><br></td>
												</c:when>

												<c:otherwise>
													<td width="5%" align="center" ><a cNo="${publicCourse.termCourse.course.no}" href="javascript:void(0)" ><span class="glyphicon glyphicon-plus-sign text-primary opera_bt"></span> </a><br></td>
												</c:otherwise>
											</c:choose>
											
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>

					</div>

				</div>
			</div>
		</div>
	</div>
	<div style="display:none;" id="js_initData">
		<c:forEach var="scheme" items="${schemes }">${scheme.cNo},</c:forEach>0
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
	<script src="${pageContext.request.contextPath}/static/js/module/publicCoruse.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/publicCourse/publicCoursePage.js"></script>
</body>
	
</html>