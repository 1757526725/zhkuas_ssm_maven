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
				<div class="panel panel-primary ctrl-panel">
					<button disabled id="bt-saveAll" type="button"
						class="btn btn-success " data-loading-text="保存中...">
						<span class="glyphicon glyphicon-floppy-saved"></span> 保存
					</button>
					<button <c:if test="${fn:length(schemes)==0}">disabled</c:if>
						id="bt-scan-conflict" type="button" class="btn btn-success "
						data-loading-text="检测中...">
						<span class="glyphicon glyphicon-exclamation-sign"></span> 冲突检测
					</button>
					<button <c:if test="${fn:length(schemes)==0}">disabled</c:if>
						id="bt-deleteAll" type="button" class="btn btn-success "
						data-loading-text="清空中...">
						<span class="glyphicon glyphicon-trash"></span> 清空
					</button>

					<button id="bt-addMore" type="button" class="btn btn-success">
						<span class="glyphicon glyphicon-plus"></span> 添加
					</button>

					<button id="bt-oneKey" type="button"
						class="btn btn-success pull-right">
						<span class="glyphicon glyphicon-circle-arrow-right"></span> 一键选课
					</button>
				</div>
				<form id="mySchemeForm">
					<div class="panel panel-primary">
						<!-- Default panel contents -->
						<div class="panel-heading">
							选课方案<span class="pull-right"><c:out value="${term.name}"
									default="2013-2014第二学期"></c:out>
							</span>
						</div>
						<!-- Table -->
						<table class="table">
							<thead>
								<tr>
									<th>#</th>
									<th>课程</th>
									<th>学分</th>
									<th>上课时间</th>
									<th>老师</th>
									<th class="text-right">操作&nbsp;&nbsp;&nbsp;</th>
								</tr>
							</thead>
							<tbody>
								<%
									int count = 1;
								%>
								<c:forEach var="scheme" items="${schemes}">
									<tr msid="${scheme.id}" cNo="${scheme.cNo}"
										formValue="${scheme.formValue}">
										<td class="order_num"><%=count++%></td>
										<td><a href="${pageContext.request.contextPath}/main/course/detail/${scheme.course.no}"
											target="_blank">[${scheme.cNo}]${scheme.course.name} </a><span
											class="label label-info">${scheme.course.courseProfiles.pcType.name
												}</span>
										</td>
										<td>${scheme.course.credits}</td>
										<td>${scheme.schoolTime}</td>
										<td>${scheme.displayValue}</td>
										<td class="text-right"><a href="javascript:void(0)"
											actionCommand="editScheme"><span
												class="glyphicon glyphicon-edit"></span> </a> | <a
											href="javascript:void(0)" actionCommand="deleteScheme"><span
												class="glyphicon glyphicon-remove"></span> </a> | <a
											href="javascript:void(0)" actionCommand="moveUp"><span
												class="glyphicon glyphicon-arrow-up"></span> </a> <input
											hidden="true" name="msid" value="${scheme.id}" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<input name="_method" type="hidden" value="POST"/>
					<input name="termNo" type="hidden" value="${term.no}"/>
					<input name="campusId" type="hidden"  value="${loginUser.student.classBelongTo.campus.id}"/>
				</form>

				<div class="maincontent clearfix onekey" style="display:none;">
					<c:if test="${acadmey_had_logined}"><div style="display:none;" id="jsd_login_state"></div></c:if>
					<div class="content-left col-lg-5">
						<h4>
							<span>姓名:</span> <span>${loginUser.student.name}</span>
						</h4>
						<h4>
							<span>学号:</span> <span id="v_academy_sno">${loginUser.student.sNo}</span>
						</h4>
						<div class="action_result">
							<div class="timer">
								<h2>
									<span class="text-info">离选课还有:</span><br> <span
										class="text-danger time">10时20分01秒</span>
								</h2>
							</div>
							<div class="result_text"></div>
							<div class="result_loading" style="display:none;">
								<img src="${pageContext.request.contextPath}/static/images/result_loading.gif"> <span
									class="text-danger">正在为你登录中..</span>
							</div>
						</div>
					</div>

					<div class="col-lg-7 pull-right">
						<div class="form-horizontal" role="form">
							<div class="form-group">
								<label for="academy_pwd" class="col-sm-4 control-label">教务网密码</label>
								<div class="login_formBox">
									<div class="col-sm-6">
										<input type="password" value=""  class="form-control"
											id="academy_pwd" placeholder="Password">
									</div>

									<div class="col-sm-2">
										<button  class="btn btn-danger btn-sm bt_login_test"
											data-loading-text="测试中...">登录测试</button>
										<span style="display:none;" class="label label-warning login_state">已登录</span>
									</div>
								</div>
							</div>

							<div class="form-group">
								<label for="systemPassword" class="col-sm-4 control-label">方式</label>
								<div class="col-sm-6">
									<select class="form-control submitWay">
										<option value="true">一起提交</option>
										<option value="false">一个一个来</option>
									</select>
								</div>
								<div class="col-sm-2">
									<button class="btn btn-primary btn-sm bt_begin"
										data-loading-text="启动中...">自动提交</button>

								</div>
							</div>
							<div class="form-group">

								<label for="academy_code" class="col-sm-4 control-label">验证码</label>

								<div class="col-sm-3">
									<input type="text" value="" class="form-control"
										id="academy_code" placeholder="">
								</div>

								<div class="col-sm-3 _padding_0">
									<img id="vCode" style="width:70px;" onclick="changeValidateCode(this)" alt="验证码" src="">
								</div>
								<div class="col-sm-2">
									<button class="btn btn-primary btn-sm bt_go_now"
										data-loading-text="go..go...">立马提交</button>
								</div>
								<input name="loginState" value="${studentNo!=null }" type="hidden">
							</div>
						</div>
					</div>

					<div class="col-lg-12 submit-items"></div>
				</div>
			</div>
		</div>
		<div style="clear:both;"></div>
	
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
	<script
		src="${pageContext.request.contextPath}/static/js/module/publicCoruse.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/module/oneKey.js"></script>
	<script
		src="${pageContext.request.contextPath}/static/js/myScheme/mySchemePage.js"></script>
</body>
</html>