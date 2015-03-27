<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.github.pagehelper.Page"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="admin_header.jsp"></jsp:include>
<jsp:include page="admin_sideBar.jsp"></jsp:include>


<!--main-container-part-->
<div id="content">
	<!--breadcrumbs-->
	<div id="content-header">
		<div id="breadcrumb">
			<a href="index.html" title="Go to Home" class="tip-bottom"><i
				class="icon-home"></i> 首页</a> <a href="${pageContext.request.contextPath}/admin/school/classrooms" class="current">所有教学楼</a>
			<c:if test="${currentBuilding!=null}">
				<a href="${pageContext.request.contextPath}/admin/school/building/${currentBuilding.no}/classrooms" class="current">${currentBuilding.name}</a>
			</c:if>
		</div>
	</div>
	<!--End-breadcrumbs-->

	<!--container-fluid-->
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="btn-tools">
					<!--Action tools-->
					<div class="btn-group">
						<button data-toggle="dropdown"
							class="btn btn-success dropdown-toggle"><c:choose><c:when test="${currentBuilding!=null}">${currentBuilding.name}</c:when><c:otherwise>&nbsp;&nbsp;所有教学楼&nbsp;&nbsp;</c:otherwise></c:choose>
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" id="campusList">
						<c:if test="${currentBuilding!=null}">
							<li class="text-center"><a href="${pageContext.request.contextPath}/admin/school/classrooms">所有教学楼</a></li>
							<li class="divider"></li>
						</c:if>
						<c:forEach var="building" items="${buildings}">
							<li><a href="${pageContext.request.contextPath}/admin/school/building/${building.no}/classroom">${building.name}</a></li>
						</c:forEach>
						</ul>
					</div>
					<button class="btn btn-success" id="btn-update">
						<i class="icon-refresh"></i> 更新数据
					</button>
					<div class="update-message" style="display:none;">
						<div>
							<span class="icon24 icomoon-icon-arrow-up-2 green">LOG:</span>
							<span class="message">... </span>
							<div class="progress progress-success active active progress-striped ">
								<div style="width: 72%;" class="bar">72%</div>
							</div>
						</div>
					</div>

					<!--End Action tools-->
				</div>
				<div class="widget-box">
					<div class="widget-content nopadding">
						<form action="#" method="post" class="form-horizontal"
							id="academy_form">
							<table class="table table-bordered table-striped with-check">
								<thead>
									<tr>
										<th><input type="checkbox" id="title-checkbox"
											name="title-checkbox">
										</th>
										<th>教室</th>
										<th>代号</th>
										<th>所属教学楼</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="classroom" items="${classrooms}">
										<tr>
											<td><input type="checkbox"></td>
											<td class="text-center">${classroom.name }</td>
											<td class="text-center">${classroom.no}</td>
											<td class="text-center"><c:out value="${classroom.schoolBuilding.name}"default="${currentBuilding.name}"></c:out></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</form>
					</div>
				</div>
			<%
					Object ob =request.getAttribute("classrooms");
				if(ob!=null&& ob instanceof  Page){
					Page pageList = (Page) ob;
					//大于一页的时候
					int pages = pageList.getPages();
					int pageNum = pageList.getPageNum();
					if(pages>1){
					
						//前一页
						out.print("<div class=\"pagination\">");
						out.print("<ul>");
						if(pageNum==1){
							out.print("<li><a href=\"javascript:void(0);\">Prev</a></li>");
						}else{
							out.print("<li><a href=\""+pageContext.getServletContext().getContextPath()+"/admin/school/classrooms/page/"+(pageNum-1)+"\">Prev</a></li>");
						}
						//页码 class="active"
						int index = pageNum<8?1:(pageNum-4);
						for(int i = index;i<=pages;++i){
							if(i-index>=8){
								if(i-index==9)
								out.print("<li><a href=\"javascript:void(0);\">...</a></li>");
								else
								continue;
							}else{
								out.print("<li "+(i==pageNum?"class=\"active\"":"")+"><a href=\""+pageContext.getServletContext().getContextPath()+"/admin/school/classrooms/page/"+i+"\">"+i+"</a></li>");
							}
						}
						
						//后一页
						if(pageNum==pages){
							out.print("<li><a href=\"javascript:void(0);\">Next</a></li>");
						}else{
							out.print("<li><a href=\""+pageContext.getServletContext().getContextPath()+"/admin/school/classrooms/page/"+(pageNum+1)+"\">Next</a></li>");
						}
						out.print("</ul>");
						out.print("</div>");
					}
				}
			 %>
			</div>
		</div>
	</div>
	<!--End container-fluid-->
</div>


<!--end-main-container-part-->
<jsp:include page="admin_footer.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/static/js/admin/page/school/classroom.js"></script>
</body>
</html>