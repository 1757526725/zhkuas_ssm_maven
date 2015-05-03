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
								${notice.content}
							</article>
						</div>
					</div>
					<div id="cards" class="col-xs-12 col-lg-3">
						<div>

							<a href="javascript:void(0)" class="card"
								data-name="user/passport"> <img
								src="${pageContext.request.contextPath}/static/images/bindSchool.png"
								alt="绑定教务网" data-view="channel_image" data-height="24"
								data-width="22">
								<div class="description">
									<h2>绑定教务网</h2>
									<p>使用你的学号密码进行教务网的对接.</p>
								</div> </a>
						</div>
						

						<div>

							<a href="javascript:void(0)" class="card" data-name="user/curriculum/scheme">
								<img
								src="${pageContext.request.contextPath}/static/images/myScheme.png"
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
								data-name="user/course/statistics"> <img
								src="${pageContext.request.contextPath}/static/images/Course_statistics.png"
								alt="Add your own" data-view="channel_image"
								data-name="Add your own" data-height="24" data-width="22">
								<div class="count"></div>
								<div class="description">
									<h2>选课统计</h2>

									<p>你可以查看你已经修过的各类选修课以及学分统计.</p>

								</div> </a>
						</div>
						<div>

							<a href="javascript:void(0)" class="card"
								data-name="campus/course"> <img
								src="${pageContext.request.contextPath}/static/images/addMyScheme.png"
								alt="公选课表" data-view="channel_image" data-height="24"
								data-width="22">
								<div class="count"></div>
								<div class="description">
									<h2>添加选课方案</h2>

									<p>在公共选修课列表将自己喜欢的课程加入你的选课方案.</p>

								</div> </a>
						</div>
						<div>

							<a href="javascript:void(0)" class="card"
								data-name="user/course/public/zx"> <img
								src="${pageContext.request.contextPath}/static/images/zhengxuan.png"
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
								<img
								src="${pageContext.request.contextPath}/static/images/mySchedule.png"
								alt="确认个人课表" data-view="channel_image" data-height="24"
								data-width="22">
								<div class="count"></div>
								<div class="description">
									<h2>正选结果 / 退选</h2>

									<p>这里可以查看你的选课结果，和进行退选操作.</p>

								</div> </a>
						</div>

						
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/static/js/index.js"></script>
</body>

</html>