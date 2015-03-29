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
				class="icon-home"></i> 首页</a> <a href="${pageContext.request.contextPath}/admin/school/majors" class="current">所有专业</a>
			<c:if test="${currentAcademy!=null}">
				<a href="${pageContext.request.contextPath}/admin/school/academy/${currentAcademy.no}/majors" class="current">${currentAcademy.name}</a>
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
					<button class="btn btn-success" id="btn-update">
						<i class="icon-refresh"></i> 更新数据
					</button>
					<button class="btn btn-success" id="btn-edit-campus-class">
						<i class="icon-edit"></i> 编辑(校区-班级)
					</button>
					<span class="text-danger text-large">提示:使用右上角的搜索功能可以快速查找到相应的班级！</span>
					<div class="update-message" style="display:none;">
						<div>
							<span class="icon24 icomoon-icon-arrow-up-2 green">LOG:</span>
							<span class="message">... </span>
							<div class="progress progress-success active active progress-striped ">
								<div style="width: 72%;" class="bar">72%</div>
							</div>
						</div>
					</div>
				<!-- 	<div class="btn-group pull-right">
						<div class="search">
						  <input type="text" placeholder="输入班级名称......">
						  <button type="submit" class="tip-bottom" data-original-title="Search"><i class="icon-search icon-white"></i></button>
						</div>
					</div> -->
					<!--End Action tools-->
				</div>
				<div id="edit-advance" class="span12" style="display:none;">
					<div class="span12">
						<div class="widget-box">
							<div class="widget-title">
								<span class="icon"><i class="icon-ok"></i> </span>
								<h5>检索数据</h5>
							</div>
							<div class="widget-content">
								<div class="todo">
									<ul>
										<li class="clearfix">
											<div class="span6">
											<span>年级:</span>
									  		<select name="grade" style="margin-top: 8px; width: auto;">
								            	<option value="${grade_all}" selected>全部年级</option>
								            	<c:forEach var="year" items="${admin_class_years}">
								            		<option value="${year}">${year}级</option>
								            	</c:forEach>
								            </select>
								            <span>学院:</span>
								            <select name="acadmey" style="margin-top: 8px;width: auto;">
								            	<option value="all" selected>全部学院</option>
								            	<c:forEach var="acadmey" items="${admin_class_acadmeys}">
								            		<option value="${acadmey.no}">${acadmey.name}</option>
								            	</c:forEach>
								            </select> 
								            <button class="btn btn-success" id="btn-go-search">
												<i class="icon-arrow-right"></i> 检索
											</button>
											
											</div>
											<div class="span4 pull-right" style="border: 1px #CDCDCD solid; padding:5px 5px 0;height: 35px; width: 200px; border-radius:4px">
											<lable><input type="radio" checked name="campusId" value="1" style="margin: 0">海珠校区</lable>
								            <lable><input type="radio" name="campusId" style="margin: 0" value="3" >白云校区</lable>
												<button class="btn btn-danger btn-mini" id="btn-save">
													<i class="icon-ok"></i> 提交
												</button>

											</div>
										</li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="widget-box">
					<div class="widget-content nopadding">
						<form action="#" method="post" class="form-horizontal"
							id="baseClass_form">
							<table class="table table-bordered table-striped with-check">
								<thead>
									<tr>
										<th><input type="checkbox" id="title-checkbox"
											name="title-checkbox">
										</th>
										<th>班级名称</th>
										<th>班级代号</th>
										<th>所属学院</th>
										<th>所在校区</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="clazz" items="${classes}">
										<tr>
											<td><input type="checkbox"></td>
											<td class="text-center">${clazz.name }</td>
											<td class="text-center">${clazz.no}</td>
											<td class="text-center"><c:out value="${clazz.major.academy.name}" default="${currentAcademy.name}"></c:out></td>
											<td class="text-center"><c:out value="${clazz.campus.name}" default="全校"></c:out></td>
											<td class="text-center"><a href="javascript:;">查看学生</a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</form>
					</div>
				</div>
			<%
				Object ob =request.getAttribute("classes");
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
							out.print("<li><a href=\""+pageContext.getServletContext().getContextPath()+"/admin/school/majors/page/"+(pageNum-1)+"\">Prev</a></li>");
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
								out.print("<li "+(i==pageNum?"class=\"active\"":"")+"><a href=\""+pageContext.getServletContext().getContextPath()+"/admin/school/classes/page/"+i+"\">"+i+"</a></li>");
							}
						}
						//后一页
						if(pageNum==pages){
							out.print("<li><a href=\"javascript:void(0);\">Next</a></li>");
						}else{
							out.print("<li><a href=\""+pageContext.getServletContext().getContextPath()+"/admin/school/majors/page/"+(pageNum+1)+"\">Next</a></li>");
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
<script src="${pageContext.request.contextPath}/static/js/admin/page/school/baseClass.js"></script>
</body>
</html>