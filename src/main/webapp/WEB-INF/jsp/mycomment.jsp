<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.zhku.web.Constants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:include page="header.jsp"></jsp:include>

<body>

	<!-- Wrap all page content here -->
	<div id="wrap">

		<jsp:include page="topBar.jsp"></jsp:include>

		<!-- Begin page content -->
		<div id="pageContent">
			<div class="container-narrow clearfix">
				<ul class="nav nav-tabs">
					<li class="active"><a href="${pageContext.request.contextPath}/main/user/comment/me">我的评论</a></li>
					<li><a href="${pageContext.request.contextPath}/main/user/comment/receive">回复我的评论</a></li>

				</ul>

				<div class="tab-content">
					<div class="tab-pane active" id="MyComment">

						<c:forEach var="comment" items="${pagination.pageDataList}"
							begin="${pagination.begin}" end="${pagination.end}">
							<div class="panel panel-default ctrl-panel comment-box">
								<div class="media">
									<div class="pull-left" href="#">
										<c:choose>
											<c:when test="${comment.commentUser.isDIYAvater==true}">
												<img
													src="<%=Constants.Avater.AVATER_80%>${comment.commentUser.uid}.jpg"
													alt="${comment.commentUser.nickName}"
													class="media-object img-rounded avatar">
											</c:when>
											<c:otherwise>
												<img
													src='<c:out value="${comment.commentUser.avatorUrl}"
												default="http://bcs.duapp.com/zhkuas/avater%2Fdefault_avt.jpg"></c:out>'
													alt="${comment.commentUser.nickName}"
													class="media-object img-rounded avatar">

											</c:otherwise>
										</c:choose>
									</div>
									<div class="media-body">
										<h4 class="media-heading text-success">
											${comment.commentUser.nickName}
											<div class="pull-right">
												课程:<a
													href="${pageContext.request.contextPath}/main/course/detail/${comment.cNo}#comment-${comment.id}"><span
													class="label label-primary">${comment.courseName}</span> </a>
											</div>
										</h4>
										<c:if test="${comment.replyUser!=null}">回复 <span
												class="text-success">${comment.replyUser.nickName}</span>:</c:if>
										<a
											href="${pageContext.request.contextPath}/main/course/detail/${comment.cNo}#comment-${comment.id}">${comment.content}</a>
										<br> <span class="text-warning"><fmt:formatDate
												value="${comment.commentTime}" pattern="yyyy-MM-dd HH:mm" />
										</span> <span class="pull-right"> <a href="javascript:void(0)"
											onclick="deleteComment(${comment.id})">删除</a> </span>
									</div>
								</div>
							</div>
						</c:forEach>
						<c:if test="${pagination!=null && pagination.pageCount>1}">
							<ul class="pagination pull-right">
								<c:choose>
									<c:when test="${1==pagination.currentPage}">
										<li class="disabled"><a href="javacript:void(0)">«</a>
										</li>
									</c:when>
									<c:otherwise>
										<li><a
											href="comment?tab=myComment&currentPage=${pagination.currentPage-1}">«</a>
										</li>
									</c:otherwise>
								</c:choose>
								<c:forEach var="page" begin="1" end="${pagination.pageCount}">
									<c:choose>
										<c:when test="${page==pagination.currentPage}">
											<li class="active"><a href="javacript:void(0)">${page}</a>
											</li>
										</c:when>
										<c:otherwise>
											<li><a
												href="comment?tab=myComment&currentPage=${page}">${page}</a>
											</li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								<c:choose>
									<c:when test="${pagination.pageCount==pagination.currentPage}">
										<li class="disabled"><a href="javacript:void(0)">»</a>
										</li>
									</c:when>
									<c:otherwise>
										<li><a
											href="comment?tab=myComment&currentPage=${pagination.currentPage+1}">»</a>
										</li>
									</c:otherwise>
								</c:choose>
							</ul>

						</c:if>
					</div>
				</div>
			</div>
			<div style="clear:both;"></div>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>