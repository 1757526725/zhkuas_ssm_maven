<%@page import="com.zhku.service.db.impl.MyCourseSchemeService"%>
<%@page import="com.zhku.service.db.IMyCourseSchemeService"%>
<%@page import="com.zhku.web.Constants"%>
<%@page import="com.zhku.bean.User"%>
<%@page import="com.zhku.bean.MyCourseScheme"%>
<%@page import="com.zhku.utils.ServiceUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
/* 	User user = (User) session.getAttribute("loginUser");
	int size = 0;
	if (user != null) {
		IMyCourseSchemeService service = new MyCourseSchemeService();
		Map<String, String> params = new HashMap<String, String>();
		params.put("termNo", Constants.CURRENT_TERMNO);
		params.put("uid", String.valueOf(user.getUid()));
		List<MyCourseScheme> list = (List<MyCourseScheme>)(request.getSession().getAttribute("mySchemes"));
		
		if (list != null) {
			size = list.size();
		}
	}
	
	String termName =ServiceUtils.termNo2TermName(Constants.CURRENT_TERMNO);
	session.setAttribute("termName", termName); */
%>
<!-- Fixed navbar -->
<div class="navbar navbar-default navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${pageContext.request.contextPath}/index"> <strong>选课助手</strong> </a>
		</div>
		<div id="topBar" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
<%-- 				<li id="class_course"><a href="${pageContext.request.contextPath}/main/class/course">班级课表</a>
				</li> --%>
				<li id="public_course" class="dropdown"><a href="#"
					class="dropdown-toggle" data-toggle="dropdown"> 公选课表 <b
						class="caret"></b> </a>
					<ul class="dropdown-menu">
						<li class="dropdown-header">海珠校区</li>
						<c:forEach  var="term" items ="${terms}">
							<li><a href="${pageContext.request.contextPath}/main/campus/1/course/term/${term.no}">${term.name}</a>
						</li>
						</c:forEach>
						<li class="divider"></li>
						<li class="dropdown-header">白云校区</li>
						<c:forEach  var="term" items ="${terms}">
							<li><a href="${pageContext.request.contextPath}/main/campus/3/course/term/${term.no}">${term.name}</a>
						</li>
						</c:forEach>
					</ul>
				</li>
				<li id="my_project"><a href="${pageContext.request.contextPath}/main/user/curriculum/scheme"> 
	<%-- 			<%
 	if (size != 0) {
 		%> <span class="badge pull-right"><%=size %></span> <%
 	}
 %> --%> 我的选课方案</a></li>
<%--  				<li id="myPublicCourse"><a href="${pageContext.request.contextPath}/main/myPublicCourse">选课统计</a>
 --%>				<li id="illustrate"><a href="${pageContext.request.contextPath}/main/illustrate">选课说明</a>
				<li id="illustrate"><a href="${pageContext.request.contextPath}/main/user/curriculum/scheme"><span class="text-primary">一键选课</span></a>
				</li>
			</ul>

			<c:choose>
				<c:when test="${loginUser!=null}">
					<ul class="nav navbar-nav navbar-right loginInfo">

						<li class="dropdown">
						<a class="dropdown-toggle"
							data-toggle="dropdown" href="javascript:void(0)">
							<c:choose>
							<c:when test="${loginUser.isDIYAvater==true}"><img class="radiusShadow nav-avt"
									src="<%=Constants.Avater.AVATER_30%>${loginUser.uid}.jpg">
							</c:when>
							<c:otherwise><img class="radiusShadow nav-avt"
									src='<c:out value="${loginUser.avatorUrl}" default="http://bcs.duapp.com/zhkuas/avater%2Fdefault_avt.jpg"></c:out>'>
							
							</c:otherwise>
						</c:choose>
							<span class="userName"><c:out value="${loginUser.nickName}" default="${loginUser.student.name}"></c:out></span><span class="caret"></span> </a>
							<ul class="dropdown-menu">
								<li><a href="${pageContext.request.contextPath}/main/user/comment/me">我的评论</a>
								</li>
								<li><a href="${pageContext.request.contextPath}/main/user/comment/receive">收到的评论</a>
<%-- 								</li>
								<li><a href="${pageContext.request.contextPath}/main/user/avator">修改头像</a>
								</li> --%>
								<li><a href="${pageContext.request.contextPath}/main/user/curriculum/schedule">我的课表</a>
								</li>
								<li><a href="${pageContext.request.contextPath}/main/user/passport">个人资料</a>
								</li>
								<li><a href="${pageContext.request.contextPath}/account/logout">退出登录</a>
								</li>
							</ul>
						</li>
					</ul>

				</c:when>

				<c:otherwise>
					<div id="topBarLoginPanel" class="nav navbar-nav navbar-right">
						<div class="btn-group">
							<button id="sina-login" type="button"
								class="btn btn-danger btn-sm "
								data-loading-text='<span class="icon-sign icon-sign-sina"></span>微博登录...'>
								<span class="icon-sign icon-sign-sina"></span>微博登录
							</button>
						</div>
					</div>
				</c:otherwise>
			</c:choose>

		</div>
		<!--/.nav-collapse -->
	</div>
</div>


<!-- alert-dialog -->
<div class="modal fade" id="alert-dialog" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" >
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">消息</h4>
			</div>
			<div class="modal-body">评论内容不能为空</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- choose_Course_time-dialog -->
<div class="modal fade" id="CourseChoose-dialog" tabindex="-2"
	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">
					请选择上课时间 &nbsp;<span class="text-warning"><c:out
							value="(${term.name})" default=" "></c:out> </span>
				</h4>
			</div>
			<div class="modal-body">
				<div id="initMsgPanel" style="display:none;">
					<span>正在查询上课信息...</span>
					<div class="loading"></div>
				</div>

				<div id="noticePanel">
					<p class="text-danger">注意:人数一栏不是即时更新的,每个选课时间段更新一次，所以同学们可以大胆加入选课方案！</p>
				</div>

				<div id="optionList"></div>
				<div id="dialog-summary" class="text-danger"></div>
			</div>
			<div class="modal-footer">
				<button disabled id="bt-scheme-ensure" action-data-cNo="" type="button"
					data-loading-text="提交中..." class="btn btn-primary">确定</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->