<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">
			<c:out value="${clazz.className}" default="没有课程信息"></c:out><span style="float:right;">${termName }</span>
		</h3>
	</div>
	<div class="table-responsive">
		<table class="table table-bordered">
			<tbody>
				<tr>
					<td width="25%" align="center">课程</td>
					<td width="5%" align="center">学分</td>
					<td width="5%" align="center">总 学时</td>
					<td width="17%" align="center">教师</td>
					<td width="5%" align="center">上课 班号</td>
					<td width="5%" align="center">上课 人数</td>
					<td width="8%" align="center">周次</td>
					<td width="13%" align="center">节次</td>
					<td width="10%" align="center">地点</td>
				</tr>


				<c:forEach var="termcourse" items="${clazz.courseList}"
					varStatus="status">
					<tr>

						<c:choose>
							<c:when
								test="${status.index!=0&&(termcourse.course.cNo==(clazz.courseList)[status.index-1].course.cNo)}">
								<td width="25%" align="left"><br>
								</td>
								<td width="5%" align="right"><br>
								</td>
								<td width="5%" align="right">
							</c:when>

							<c:otherwise>
								<td width="25%" align="left">[${termcourse.course.cNameNo}]${termcourse.course.cName}<br>
								</td>
								<td width="5%" align="right">${termcourse.course.credits}<br>
								</td>
								<td width="5%" align="right">${termcourse.course.qualityHours}<br>
							</c:otherwise>
						</c:choose>

						<td width="17%" align="left">[${termcourse.teacher.tNameNo}]${termcourse.teacher.tName}
							<br>
						</td>
						<td width="5%" align="center">${termcourse.courseClassNo}<br>
						</td>
						<td width="5%" align="right">${termcourse.studentNum}<br>
						</td>
						<td width="8%" align="left">${termcourse.periods}<br>
						</td>
						<td width="13%" align="left">${termcourse.weekOfChinese
							}[${termcourse.classSection}节] <br>
						</td>
						<td width="10%" align="left">${termcourse.classRoom}<br>
						</td>
					</tr>

				</c:forEach>

			</tbody>
		</table>
	</div>

</div>

