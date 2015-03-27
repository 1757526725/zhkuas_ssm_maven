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
					<div class="col-xs-12 col-lg-9">
						<div class="maincontent">
							<article class="post tag-bs2 clearfix">

								<h1>本地选课，免验证码登录</h1>

								<section class="post-content">
									<blockquote>
										<p class="text-danger">1.第一步输入教务网学号密码。点击登录</p>
										<p class="text-danger">2.会弹出一个窗口，可能是教务网的主页，也可能是一个登录页面，如果显示 正在加载权限... 就表示已经登录成功。</p>
										<p class="text-danger">3.立即打开Step2的链接直接进去正选页面。</p>
									</blockquote>

									<p></p>
								</section>

								<section>
									
									<form id="loginForm" target="_blank" action="http://202.192.94.172/jwmis/_data/index_login.aspx" class="form-horizontal col-sm-12" method="post" role="form">
										<h3>Step 1：免验证码登录</h3>
										<input name="Sel_Type" value="STU" type="hidden"/>
										<div class="form-group">
										<label for="systemSno" class="col-sm-3 control-label">教务网学号</label>
										<div class="col-sm-7">
											<input type="text" name="UserID" value="<c:out value="${current_sNo}" default="${loginUser.student.sNo}"></c:out>" class="form-control" id="systemSno" placeholder="学号">
										</div>
					
									</div>
									<div class="form-group">
										<label for="systemPassword" class="col-sm-3 control-label">教务网密码</label>
										<div class="login_formBox">
											<div class="col-sm-7">
												<input type="password" value="" name ="PassWord" class="form-control"
													id="systemPassword" placeholder="Password">
											</div>

											<div class="col-sm-2">
												<button class="btn btn-danger btn-sm bt_login_academic"
													data-loading-text="登录中..." >登录</button>
												<h2 style="display:none;"
													class="label label-warning login_state">已登录</h2>
											</div>
										</div>
									</div>
									<div class="col-sm-12 use_notice">
										<h4 class="text-danger">请输入你的教务网密码，并点击登录，系统会弹出一个窗口自动登录教务网.<br><br>如果显示正在 加载权限... 就表示登录成功了，立即进行第二步。</h4>
									</div>
									</form>
									<div class="form-group col-sm-12">
									<h3>Step 2：直接打开正选页面</h3>
									<h4>马上打开下面的链接，直接进去系统正选页面。</h4>
									<p><a target="_blank" href="http://202.192.94.172/jwmis/wsxk/stu_xszx.aspx">http://202.192.94.172/jwmis/wsxk/stu_xszx.aspx</a></p>
									</div>
								</section>
							</article>
						</div>
					</div>
					<div id="cards" class="col-xs-12 col-lg-3">
						<div>

							<a href="javascript:void(0)" class="card"
								data-name="passport?tab=basic"> <img
								src="http://zhkuas.cdn.duapp.com/images/bindSchool.png"
								alt="绑定教务网" data-view="channel_image" data-height="24"
								data-width="22">
								<div class="description">
									<h2>绑定教务网</h2>
									<p>使用你的学号密码进行教务网的对接.</p>
								</div> </a>
						</div>

						<div>

							<a href="javascript:void(0)" class="card"
								data-name="public_course"> <img
								src="http://zhkuas.cdn.duapp.com/images/addMyScheme.png"
								alt="公选课表" data-view="channel_image" data-height="24"
								data-width="22">
								<div class="count"></div>
								<div class="description">
									<h2>添加选课方案</h2>

									<p>在公共选修课列表将自己喜欢的课程加入你的选课方案.</p>

								</div> </a>
						</div>
						<div>

							<a href="javascript:void(0)" class="card" data-name="myScheme">
								<img src="http://zhkuas.cdn.duapp.com/images/myScheme.png"
								alt="我的选课方案" data-view="channel_image" data-name="Add your own"
								data-height="24" data-width="22">
								<div class="count"></div>
								<div class="description">
									<h2>我的选课方案</h2>

									<p>你可以查看和编辑你已经添加的选课方案，并可以对选课顺序进行修改.</p>

								</div> </a>
						</div>
						<div>

							<a href="javascript:void(0)" class="card"
								data-name="confirmedSelect"> <img
								src="http://zhkuas.cdn.duapp.com/images/zhengxuan.png"
								alt="Add your own" data-view="channel_image"
								data-name="Add your own" data-height="24" data-width="22">
								<div class="count"></div>
								<div class="description">
									<h2>正选</h2>
									<p>非一键选课功能，操作流程类似与教务网。</p>
								</div> </a>
						</div>
						<div>
							<a href="javascript:void(0)" class="card" data-name="dropCourse">
								<img src="http://zhkuas.cdn.duapp.com/images/mySchedule.png"
								alt="确认个人课表" data-view="channel_image" data-height="24"
								data-width="22">
								<div class="count"></div>
								<div class="description">
									<h2>正选结果 / 退选</h2>

									<p>这里可以查看你的选课结果，和进行退选操作.</p>

								</div> </a>
						</div>
								<div>

							<a href="javascript:void(0)" class="card" data-name="local"> <img
								src="http://zhkuas.cdn.duapp.com/images/Course_statistics.png" alt="Add your own"
								data-view="channel_image" data-name="Add your own"
								data-height="24" data-width="22">
								<div class="count"></div>
								<div class="description">
									<h2>免验证码登录教务网</h2>

									<p>利用校内网的优势，免验证码登录，抢课。</p>

								</div> </a>
						</div>
						<div>

							<a href="javascript:void(0)" class="card"
								data-name="myPublicCourse"> <img
								src="http://zhkuas.cdn.duapp.com/images/Course_statistics.png"
								alt="Add your own" data-view="channel_image"
								data-name="Add your own" data-height="24" data-width="22">
								<div class="count"></div>
								<div class="description">
									<h2>选课统计</h2>

									<p>你可以查看你已经修过的各类选修课以及学分统计.</p>

								</div> </a>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/illustrate.js"></script>
</body>

</html>