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
			<div class="container-narrow clearfix">
				<c:choose>
					<c:when test="${fn:length(schedules)==0&&clazz==null}">
						<div class="page-header">
							<h2>
								提示:<small class="text-danger"> 你还未绑定教务网账户，无法获取你的个人课程表信息。<a href="../passport">去绑定</a><br><br>
									</small>
							</h2>
						</div>
					</c:when>

					<c:otherwise>
						<div class="page-header">
							<h2>
								提示:<small class="text-danger">
									由于大二以上年级的学生有专业选修的可选课，请同学们自行确认你要选的专业选修课。</small>
							</h2>
						</div>
						<div class="panel panel-primary ctrl-panel">
							<button <c:if test="${clazz==null}">disabled</c:if> id="bt-saveSchedule" type="button"
								class="btn btn-success " data-loading-text="保存中...">
								<span class="glyphicon glyphicon-floppy-saved"></span> 保存
							</button>

							<button id="bt-selectAll" type="button" class="btn btn-success ">
								<span class="glyphicon glyphicon-ok"></span> 全选
							</button>

							<button id="bt-inverse" type="button" class="btn btn-success">
								<span class="glyphicon glyphicon-remove"></span> 反选
							</button>

							<button id="bt-edit" type="button"
								class="btn btn-success pull-right">
								<span class="glyphicon glyphicon-edit"></span><span class="bt-name"> 修改</span>
							</button>
						</div>
						<form id="mySchueduleForm" role="form">
							<div class="panel panel-primary">
								<!-- Default panel contents -->
								<div class="panel-heading">
									<span class="panel-title">个人基础课程表</span><span class="pull-right">${term.name}</span>
								</div>
								<!-- Table -->
								<table class="table table-bordered" id="oTable">
									<tbody>
										<tr align="center" class="T">
											<td width="3%">选定</td>
											<td align="center" width="29%">课程</td>
											<td width="4%">学分</td>
											<td width="18%">类别</td>
										</tr>
										<c:choose>
											<c:when test="${fn:length(schedules)==0}">
												<c:forEach var="termCourse" items="${clazz.courseList}"
												varStatus="status">
												<c:if test="${!(status.index!=0&&(termCourse.course.no==clazz.courseList[status.index-1].course.no))}">
												<tr class="B">
															<td align="center"><input name="cNo"
																type="checkbox" value="${termCourse.course.no}" checked ></td>
															<td align="left">[${termCourse.course.no}]${termCourse.course.name}
															</td>
															<td align="right">${termCourse.course.credits}<br>
															</td>
															<td align="center">${termCourse.course.courseType}<br>
															</td>
														</tr>
												</c:if>
												
											</c:forEach>
											</c:when>
											<c:otherwise>
											<c:forEach var="schedule" items="${schedules}"
												varStatus="status">
												<c:if test="${!(status.index!=0&&(schedule.termCourse.course.no==schedules[status.index-1].termCourse.course.no))}">
												<tr class="B">
															<td align="center"><input name="cNo"
																type="checkbox" value="${schedule.termCourse.course.no}" <c:if test="${schedule.state==1}">checked</c:if> disabled></td>
															<td align="left">[${schedule.termCourse.course.no}]${schedule.termCourse.course.name}
															</td>
															<td align="right">${schedule.termCourse.course.credits}<br>
															</td>
															<td align="center">${schedule.termCourse.course.courseType}<br>
															</td>
														</tr>
												</c:if>
												
											</c:forEach>
											</c:otherwise>
										</c:choose>
										
									</tbody>
								</table>
							</div>
						</form>
					</c:otherwise>
				</c:choose>

			</div>
		</div>
		<div style="clear:both;"></div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
	<script src="${pageContext.request.contextPath}/static/js/mySchedule/mySchedulePage.js"></script>
</body>
</html>