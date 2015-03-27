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

								<h1>选课说明</h1>

								<section class="post-content">
									<blockquote>
										<p class="text-danger">1.一键选课功能未经过测试，无法保证提交成功率，推荐使用正选功能。</p>
										<p class="text-danger">2.海珠校区的数据已经更新，白云校园的数据预计19号更新。</p>
										<p class="text-danger">3.请同学们务必提前绑定教务网帐号，选课当天教务网会十分拥挤。</p>
									</blockquote>

									<p></p>
								</section>

								<section>
									<h2>选课方式:</h2>
									<h3>一键选课：</h3>
									<p>提前先将选课方案添加进我的选课方案。 按一键选课即可。</p>
									<p class="text-danger">注意：一键选课功能未经过测试，可能无法正常使用。推荐使用正选功能。</p>
									<h3>正选：</h3>
									<p>正选功能与教务网的选课方式差不多，唯一不同的是，你可以选择检索即时的数据，也可以使用选课助手的数据。</p>
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
								</div>
								</a>
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