<%@page import="com.zhku.web.Constants"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="header.jsp"></jsp:include>
<%
	String currentTermNo = Constants.CURRENT_TERMNO;
%>
<body>
	<!-- Wrap all page content here -->
	<div id="wrap">

		<jsp:include page="topBar.jsp"></jsp:include>

		<!-- Begin page content -->
		<div id="pageContent">
			<div id="selectBar" class="selectBar-fixed-margin">
				<div type="button" class="bt-circle" data-toggle="tooltip"
					data-placement="right" title="" data-original-title="全部">
					<a href="${pageContext.request.contextPath}/main/campus/${campus.id }/course/term/${term.no}">◆</a>
				</div>
				<div type="button" class="bt-circle" data-toggle="tooltip"
					data-placement="right" title="" data-original-title="人文社科类">
					<a
						href="${pageContext.request.contextPath}/main/campus/${campus.id }/course/term/${term.no}/type/<%=Constants.CourseType.RENWENG.getCode()%>">人</a>
				</div>
				<div type="button" class="bt-circle" data-toggle="tooltip"
					data-placement="right" title="" data-original-title="科学技术类">
					<a
						href="${pageContext.request.contextPath}/main/campus/${campus.id }/course/term/${term.no}/type/<%=Constants.CourseType.KEJI.getCode()%>">科</a>
				</div>
				<div type="button" class="bt-circle" data-toggle="tooltip"
					data-placement="right" title="" data-original-title="外语类">
					<a
						href="${pageContext.request.contextPath}/main/campus/${campus.id }/course/term/${term.no}/type/<%=Constants.CourseType.WAIYU.getCode()%>">外</a>
				</div>
				<div type="button" class="bt-circle" data-toggle="tooltip"
					data-placement="right" title="" data-original-title="艺术体育类">
					<a
						href="${pageContext.request.contextPath}/main/campus/${campus.id }/course/term/${term.no}/type/<%=Constants.CourseType.YISHU.getCode()%>">艺</a>
				</div>
			</div>
			<div class="container-narrow">
				<div id="pcourse-detail" class="clearfix">
					<div class="titleBox">
						<div class="cycle bt-circle">${course.courseProfiles.recommend}</div>
						<div style="width=40px;"></div>
						<div class="c-title">${course.name}</div>
						<div class="half-cycle"></div>
					</div>
					<table>
						<tr>
							<td class="itemTitle">课程号：</td>
							<td id="cNo" width="60">${course.no}</td>
							<td class="itemTitle">课程名代号：</td>
							<td width="370">[${course.nameNo}]${course.name} <span
								class="label label-primary">${course.courseProfiles.pcType.name
									}</span></td>
						</tr>

						<tr>
							<td class="itemTitle">上课节次：</td>
							<td width="60"><fmt:formatNumber type="number"
									value="${course.credits*2}" maxFractionDigits="0"></fmt:formatNumber>节/周</td>
							<td class="itemTitle">学分：</td>
							<td>${course.credits }</td>
						</tr>
					</table>
					<div class="reviews">
						<div class="reviews-title">点评:</div>
						<div class="reviews-content">${course.courseProfiles.evaluation}</div>

					</div>
					<div id="reviews-areas" class="clearfix">

						<div class="btn-group">
							<c:choose>
								<c:when test="${remarkRecord==null }">
									<button id="remark_good" type="button"
										class="btn btn-primary btn-sm ">
										<span class="glyphicon glyphicon-thumbs-up"></span> <span>${course.courseProfiles.goodCount}</span>
									</button>
									<button id="remark_bad" type="button"
										class="btn btn-info btn-sm ">
										<span class="glyphicon glyphicon-thumbs-down"></span> <span>${course.courseProfiles.badCount}</span>
									</button>
								</c:when>

								<c:otherwise>
									<button id="remark_good" type="button"
										class="btn btn-primary btn-sm disabled">
										<span class="glyphicon glyphicon-thumbs-up"></span>
										<c:if test="${remarkRecord.remarkItem==1}">已赞  </c:if>
										<span>${course.courseProfiles.goodCount}</span>
									</button>
									<button id="remark_bad" type="button"
										class="btn btn-info btn-sm disabled">
										<span class="glyphicon glyphicon-thumbs-down"></span>
										<c:if test="${remarkRecord.remarkItem==2}">已踩  </c:if>
										<span>${course.courseProfiles.badCount}</span>
									</button>
								</c:otherwise>

							</c:choose>
						</div>


					</div>
					<c:choose>
						<c:when test="${scheme!=null}">
							<button disabled id="bt-addToMyPlan" type="button"
								class="btn btn-danger pull-right">
								<span class="glyphicon glyphicon-plus"></span> 已加入选课方案
							</button>
						</c:when>
						<c:otherwise>
							<button id="bt-addToMyPlan" type="button"
								class="btn btn-danger pull-right">
								<span class="glyphicon glyphicon-plus"></span> 加入选课方案
							</button>
						</c:otherwise>
					</c:choose>

				</div>
				<div id="comment" class="well clearfix">
					<textarea name="content" class="form-control pull-left"
						id="texta-comment"></textarea>
					<button id="bt-submit" type="submit" data-loading-text="发表中..."
						class="btn btn-primary">发表</button>
					<div style="margin-top:20px;"></div>
					<c:if test="${course.commentList!=null}">
						<c:forEach var="commentItem" items="${pagination.pageDataList}"
							begin="${pagination.begin}" end="${pagination.end}">
							<div pccid="${commentItem.id}"
								id="comment-${commentItem.id}" class="comment-item clearfix">
								<div class="user-info">

									<c:choose>
										<c:when test="${commentItem.commentUser.isDIYAvater==true}">
											<img
												src="<%=Constants.Avater.AVATER_80%>${commentItem.commentUser.uid}.jpg"
												alt="${commentItem.commentUser.nickName}"
												class="img-rounded avatar">
										</c:when>
										<c:otherwise>
											<img
												src='<c:out value="${commentItem.commentUser.avatorUrl}"
												default="http://bcs.duapp.com/zhkuas/avater%2Fdefault_avt.jpg"></c:out>'
												alt="${commentItem.commentUser.nickName}"
												class="img-rounded avatar">

										</c:otherwise>
									</c:choose>
									<div uid="${commentItem.commentUser.uid}"
										class="nickName text-success">${commentItem.commentUser.nickName}</div>
								</div>
								<div class="comment-content clearfix">
									<div class="triangle-border ">
										<c:choose>
											<c:when test="${commentItem.state==2}">
												<span class="text-warning"><c:out
														value="${commentItem.content}"></c:out></span>
											</c:when>
											<c:otherwise>
												<c:out value="${commentItem.content}"></c:out>
											</c:otherwise>
										</c:choose>
										<span class="pull-right"><c:if
												test="${(commentItem.commentUser.uid==loginUser.uid||loginUser.uid==1)&& commentItem.state!=2}">
												<a href="javascript:void(0)"
													onclick="deleteComment(${commentItem.id})">删除</a> | </c:if><a
											class="bt-reply" href="javascript:void(0)">回复</a> </span>

										<c:if test="${commentItem.commentChilds!=null}">
											<c:forEach var="child_comment"
												items="${commentItem.commentChilds}">
												<div pccid="${child_comment.id}" class="comment-child ">
													<c:choose>
														<c:when
															test="${child_comment.commentUser.isDIYAvater==true}">
															<img
																src="<%=Constants.Avater.AVATER_80%>${child_comment.commentUser.uid}.jpg"
																alt="${commentItem.commentUser.nickName}"
																class="img-rounded avatar-min">
														</c:when>
														<c:otherwise>
															<img
																src='<c:out value="${child_comment.commentUser.avatorUrl}"
												default="http://bcs.duapp.com/zhkuas/avater%2Fdefault_avt.jpg"></c:out>'
																alt="${commentItem.commentUser.nickName}"
																class="img-rounded avatar-min">

														</c:otherwise>
													</c:choose>
													<div class="nickName text-success"
														uid="${child_comment.commentUser.uid}">${child_comment.commentUser.nickName}</div>
													<div class="comment-child-content">
														:回复 <span class="text-success"
															child-reply-uid="${child_comment.replyUser.uid}">${child_comment.replyUser.nickName}:
														</span>
														<c:out value="${child_comment.content}"></c:out>
														<span class="pull-right"><c:if
																test="${child_comment.commentUser.uid==loginUser.uid||loginUser.uid==1}">
																<a href="javascript:void(0)"
																	onclick="deleteComment(${child_comment.id})">删除</a> | </c:if><a
															class="bt-reply" href="javascript:void(0)">回复</a> </span>
													</div>
												</div>
											</c:forEach>

										</c:if>

									</div>
								</div>
							</div>
						</c:forEach>
						<c:if test="${pagination!=null && pagination.pageCount>1}">
							<ul class="pagination pull-right">
								<c:choose>
									<c:when test="${1==pagination.currentPage}">
										<li class="disabled"><a href="javacript:void(0)">«</a></li>
									</c:when>
									<c:otherwise>
										<li><a
											href="public_course_detail?cNo=${course.no}&currentPage=${pagination.currentPage-1}">«</a>
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
												href="public_course_detail?cNo=${course.no}&currentPage=${page}">${page}</a>
											</li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								<c:choose>
									<c:when test="${pagination.pageCount==pagination.currentPage}">
										<li class="disabled"><a href="javacript:void(0)">»</a></li>
									</c:when>
									<c:otherwise>
										<li><a
											href="public_course_detail?cNo=${course.no}&currentPage=${pagination.currentPage+1}">»</a>
										</li>
									</c:otherwise>
								</c:choose>
							</ul>

						</c:if>
					</c:if>

				</div>
			</div>
		</div>
		<div style="clear:both;"></div>
		<jsp:include page="footer.jsp"></jsp:include>
		<script src="${pageContext.request.contextPath}/static/js/courseDetail/courseDetailPage.js"></script>
	</div>
</body>
</html>
