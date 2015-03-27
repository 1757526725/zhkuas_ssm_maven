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
									<span class="label label-primary">正选</span>
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

							<div class="col-lg-6 pull-right">
								<div class="form-horizontal" role="form">
									<div class="form-group">
										<label for="systemSno" class="col-sm-3 control-label">教务网学号</label>
										<div class="col-sm-7">
											<input type="text"
												value="<c:out value="${current_sNo}" default="${loginUser.student.sNo}"></c:out>"
												<c:if test="${current_sNo==null || loginUser.student.sNo==current_sNo}">disabled</c:if>
												class="form-control" id="systemSno" placeholder="学号">
										</div>
										<div class="col-sm-2">
											<button class="btn btn-warning btn-sm bt_switch_account "
												data-loading-text="切换中..." oSno="${loginUser.student.sNo}">
												<c:choose>
													<c:when
														test="${current_sNo==null || loginUser.student.sNo==current_sNo}">其他账户</c:when>
													<c:otherwise>我的账户</c:otherwise>
												</c:choose>
											</button>
										</div>
									</div>
									<div class="form-group">
										<label for="systemPassword" class="col-sm-3 control-label">教务网密码</label>
										<div class="login_formBox">
											<div class="col-sm-7">
												<input type="password" value="" class="form-control" id="systemPassword" placeholder="Password">
											</div>
											<div class="col-sm-2">
												<button class="btn btn-danger btn-sm bt_login_academic"
													data-loading-text="登录中..."
													<c:if test="${loginUser==null||loginUser.student==null}">disabled</c:if>>登录</button>
												<h2 style="display:none;"
													class="label label-warning login_state">已登录</h2>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label for="academy_code" class="col-sm-3 control-label">验证码</label>

										<div class="col-sm-3">
											<input type="text" value="" class="form-control"
												id="academy_code" placeholder="">
										</div>

										<div class="col-sm-5">
											<img style="width:70px;" id="vCode" onclick="changeValidateCode(this)"  alt="验证码" src="${pageContext.request.contextPath}/network/validateCode">
										</div>

									</div>
									<div class="col-sm-offset-3 col-sm-9 use_notice">
										<h4 class="text-danger">请输入你的教务网密码，并点击登录，系统会帮你登录教务网并并获取数据.</h4>
									</div>
									<div class="zx_option" style="display:none;">
										<div class="form-group">
											<label for="inputEmail3" class="col-sm-3 control-label">选课范围</label>
											<div class="col-sm-7">
												<select name="sel_lx" id="sel_lx" class="form-control"
													onchange="showListWSXK(this)">
													<option value="0">主修(本年级/专业)</option>
													<option value="2" selected="selected">主修(公共任选)</option>
												</select>
											</div>
											<div class="col-sm-2">
												<button class="btn btn-danger btn-sm bt-search"
													data-loading-text="检索中...">检索</button>
											</div>
										</div>
										<div class="form-group " >
											<div class="col-sm-offset-3 col-sm-7">
												<select name="campus" class="form-control">
											 		<option value="1" selected="selected">海珠校区</option>
											 		<option value="3" >白云校区</option>
										 		</select>
										 		<input type ="hidden" name="term" value ="${term.no }" termName="${term.name }"/>
											</div>
										</div>
										<div class="form-group " style="display:none;">
											<div class="col-sm-offset-3 col-sm-7">
												<select name="SelSpeciality" class="form-control">
													<%-- <option
														value="${fn:substring(loginUser.student.classBelongTo.no,0,8)}"
														selected="selected">${fn:substring(loginUser.student.classBelongTo.no,0,4)}|${loginUser.student.major}</option>
										 --%>		
										 		<option
														value="${loginUser.student.classBelongTo.major.no}"
														selected="selected">${loginUser.student.classBelongTo.major.no}|${loginUser.student.classBelongTo.major.name}</option>
										
										 		</select>
											</div>
										</div>
										<div class="form-group">
											<div class="col-sm-offset-10 col-sm-8">
												<button type="button"
													class="btn btn-primary bt_submit_course"
													data-loading-text="提交中..." disabled>提交</button>
											</div>
										</div>
									</div>

								</div>
							</div>

						</div>
						<form id="course_form" name="FormAdd" style="display:none;">
							<div class="course_data table-responsive maincontent">
								<table id="oTable" class="table table-bordered">

								</table>
							</div>
						</form>
					</div>

				</div>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
	<script src="${pageContext.request.contextPath}/static/js/zhengxuan_old.js"></script>
	<c:if test="${loginUser!=null&&studentNo!=null}">
		<script type="text/javascript">
			showLoginSuccessState();
		</script>
	</c:if>
</body>

</html>