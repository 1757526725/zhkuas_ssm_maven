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
				class="icon-home"></i> 首页</a> <a href="${pageContext.request.contextPath}/admin/school/users" class="current">所有用户</a>
		</div>
	</div>
	<!--End-breadcrumbs-->

	<!--container-fluid-->
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="btn-tools">
					<!--Action tools-->
					<button class="btn btn-success" id="btn-removeBind" disabled>
						<i class="icon-refresh"></i> 解除绑定
					</button>
					<span class="text-primary text-large" >提示:使用右上角的搜索功能可以快速查找到相应的用户！</span>
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
										<th>UID</th>
										<th>微博名</th>
										<th>姓名</th>
										<th>学号</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="user" items="${users}">
										<tr>
											<td><input type="checkbox"></td>
											<td class="text-center">${user.uid }</td>
											<td class="text-center">${user.nickName}</td>
											<td class="text-center">${user.student.name}</td>
											<td class="text-center">${user.student.sNo}</td>
											<td class="text-center"><a href="javascript:void(0)">查看学生详情</a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</form>
					</div>
				</div>
			<%
				Object ob =request.getAttribute("users");
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
							out.print("<li><a href=\""+pageContext.getServletContext().getContextPath()+"/admin/school/users/page/"+(pageNum-1)+"\">Prev</a></li>");
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
								out.print("<li "+(i==pageNum?"class=\"active\"":"")+"><a href=\""+pageContext.getServletContext().getContextPath()+"/admin/school/users/page/"+i+"\">"+i+"</a></li>");
							}
						}
						
						//后一页
						if(pageNum==pages){
							out.print("<li><a href=\"javascript:void(0);\">Next</a></li>");
						}else{
							out.print("<li><a href=\""+pageContext.getServletContext().getContextPath()+"/admin/school/users/page/"+(pageNum+1)+"\">Next</a></li>");
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
<script src="${pageContext.request.contextPath}/static/js/admin/page/account/account.js"></script>
</body>
</html>