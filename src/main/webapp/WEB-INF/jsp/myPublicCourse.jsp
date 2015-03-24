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
		<div id="pageContent">
			<div class="container">
				<div class="row">
					<div class="col-xs-12 col-lg-12">
						<div class="maincontent singlePage clearfix">
							<div class="content-title">
								<h2>
									<span class="label label-primary">选课统计</span>
								</h2>

							</div>
							<div class="content-left col-lg-3">
								<c:choose>
									<c:when test="${loginUser==null}">
										<h4 class="text-danger">
											请用微博登录后操作！</a>
										</h4>
									</c:when>
									<c:when test="${loginUser.student==null}">
										<h4 class="text-danger">
											你可能未绑定教务网账户 <a href="passport?tab=basic">去绑定</a>
										</h4>
									</c:when>
									<c:otherwise>
										<h4>
											<span>姓名:</span><span>${loginUser.student.name}</span>
										</h4>
										<h4>
											<span>学号:</span><span>${loginUser.student.sNo}</span>
										</h4>
									</c:otherwise>
								</c:choose>


								<div class="action_result">
									<div class="result_text"></div>
									<div class="result_loading" style="display:none;">
										<img src="${pageContext.request.contextPath}/static/images/result_loading.gif"> <span
											class="text-danger">正在为你登录中..</span>
									</div>
								</div>
							</div>

							<div class="col-lg-5 pull-right">
								<div class="form-horizontal" role="form">
									<div class="form-group">
										<label for="academy_pwd" class="col-sm-3 control-label">教务网密码</label>
										<div class="login_formBox">
											<div class="col-sm-5">
												<input type="password" value="" class="form-control"
													id="academy_pwd" placeholder="Password">
											</div>

											<div class="col-sm-4">
												<button class="btn btn-danger btn-sm bt_update"
													data-loading-text="更新中..."
													<c:if test="${loginUser==null||loginUser.student==null}">disabled</c:if>>
													更新</button>

											</div>
										</div>
										
										<div class="col-sm-10 vCodeBox clearfix">
											<label for="academy_code" class="col-sm-3 control-label">验证码</label>
									
												<div class="vCodeIpt col-sm-4">
													<input type="text" value="" class="form-control"
														id="academy_code" placeholder="">
												</div>
												<div class="col-sm-4 vCodeimg">
													<img id="vCode" onclick="changeValidateCode(this)"
														alt="验证码" style="width:70px;"
														src="${pageContext.request.contextPath}/network/validateCode">
												</div>
										

										</div>
								


									</div>
									<div class="col-sm-offset-1 col-sm-11 use_notice">
										<h4 class="text-danger">请输入你的教务网密码，并点击更新，系统会帮你登录教务网并并获取数据.</h4>
									</div>
								</div>
							</div>

						</div>
						<div id="course_form" name="form1" style="display:none;">
							<div class="course_data table-responsive maincontent"></div>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>

	<jsp:include page="footer.jsp"></jsp:include>
	<script src="${pageContext.request.contextPath}/static/js/myPublicCourse/myPublicCoursePage.js"></script>
</body>
<c:if test="${loginUser!=null}">
	<script type="text/javascript">
		MyPublicCoursePage.showMyPublicCourse();
	</script>

</c:if>
</html>