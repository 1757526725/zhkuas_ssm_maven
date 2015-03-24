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
			<a href="${pageContext.request.contextPath}/index" title="Go to Home" class="tip-bottom"><i
				class="icon-home"></i> 首页</a><a href="${pageContext.request.contextPath}/admin/school/classes">班级列表</a><a href="${pageContext.request.contextPath}/class/114" class="current">信计114</a>  
		</div>
	</div>
	<!--End-breadcrumbs-->

	<!--container-fluid-->
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="btn-tools">
					<!--Action tools-->
					<div id ="optionBox" class="buttonBox">
						<c:forEach var="term" items="${terms}" varStatus="status">
							<span class="termChK"><input type="checkbox" value="${term.no }" name="term">${term.name }</span>
							<c:if test="${status.count%4==0}"><br/></c:if>
						</c:forEach>
					</div>
					<button class="btn btn-success pull-right" id="btn-update">
						<i class="icon-refresh"></i> 更新数据
					</button>
					<div id="vcodeForm" class="pull-right" style="display:none;">
						<img alt="验证码" src="" id="img-vcode" style="width:20%;height:20%;"><input id="text-vCode" type="text" placeholder="输入验证码" style="width:100px; margin:0 5px;"/>
						<button class="btn btn-danger" id="btn-start">
						<i class="icon-play"></i> 开始
					</button>
					</div>
					<div class="update-message update-message-left" style="display:none;">
						<div>
							<div class="progress progress-success active active progress-striped ">
								<div style="width:0%;" class="bar"></div>
							</div>
							<span class="icon24 icomoon-icon-arrow-up-2 green">LOG:</span>
							<span class="message">... </span>
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
										<th>课程名</th>
										<th>课程号</th>
										<th>学分</th>
										<th>班号</th>
										<th>上课时间</th>
										<th>上课地点</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="course" items="${courses}">
										<tr>
											<td><input type="checkbox"></td>
											<td class="text-center">${course.name }</td>
											<td class="text-center">${course.no}</td>
											<td class="text-center">${course.credits}</td>
											<td class="text-center"><a href="javascript:void(0)">查看详细</a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</form>
					</div>
				</div>
			<%
				Object ob =request.getAttribute("courses");
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
							out.print("<li><a href=\""+pageContext.getServletContext().getContextPath()+"/admin/course/courses/page/"+(pageNum-1)+"\">Prev</a></li>");
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
								out.print("<li "+(i==pageNum?"class=\"active\"":"")+"><a href=\""+pageContext.getServletContext().getContextPath()+"/admin/course/courses/page/"+i+"\">"+i+"</a></li>");
							}
						}
						
						//后一页
						if(pageNum==pages){
							out.print("<li><a href=\"javascript:void(0);\">Next</a></li>");
						}else{
							out.print("<li><a href=\""+pageContext.getServletContext().getContextPath()+"/admin/course/courses/page/"+(pageNum+1)+"\">Next</a></li>");
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
<script src="${pageContext.request.contextPath}/static/js/admin/page/course/classCourse.js"></script>
</body>
</html>