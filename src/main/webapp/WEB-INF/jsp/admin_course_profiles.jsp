<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
				class="icon-home"></i> 首页</a> <a href="#" class="current">课程详细</a>
		</div>
	</div>
	<!--End-breadcrumbs-->

	<!--container-fluid-->
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<!-- widget-box -->
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon"> <i class="icon-align-justify"></i> </span>
						<h5>课程详情编辑</h5>
					</div>
					<div class="widget-content nopadding">
						<form action="#" method="post" class="form-horizontal" id="course_profiles_form">
							<div class="control-group">
								<label class="control-label">学年学期</label>
								<div class="controls">
									<select style="display: none;" class= "span8">
										<option selected="selected" value="${currentTerm.no}">${currentTerm.name}</option>
										<c:forEach var="term" items="${terms}">
											<c:if test="${term.no!= currentTerm.no}">
												<option value="${term.no}">${term.name}</option>
											</c:if>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">课程名</label>
								<div class="controls">
									<select style="display: none;" class= "span8">
									<c:forEach var="publicCourse" items="${publicCourses}">
										<option value="${publicCourse.termCourse.course.no}">[${publicCourse.termCourse.course.nameNo}]${publicCourse.termCourse.course.name}</option>
									</c:forEach>										
									</select>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">课程号</label>
								<div class="controls">
									<input type="text" placeholder="000062" disabled="" class="span8">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">课程标签</label>
								<div class="controls">
									<input type="text" class="span8" placeholder="多个标签，请用英文逗号隔开">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">推荐等级</label>
								<div class="controls rank">
									<label><input type="radio" name="radios" style="opacity:0;">A</label> 
									<label><input type="radio" name="radios" style="opacity: 0;">B</label> 
									<label><input type="radio" name="radios"style="opacity: 0;">C</label>
									<label><input type="radio" name="radios"style="opacity: 0;">D</label>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">选修类别</label>
								<div class="controls">
									<select style="display: none;" class= "span8">
										<option>外语类</option>
										<option>科学技术类</option>
										<option>人文社科类</option>
										<option>艺术体育类</option>
									</select>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">课程点评</label>
								<div class="controls">
			            	 	 <textarea class="textarea_editor span8" rows="5" placeholder="Enter text ..."></textarea>
			           		 	</div>
							</div>
							
							<div class="form-actions">
								<button class="btn btn-danger">保存</button>
							</div>
						</form>
					</div>
				</div>
				<!-- end widget-box -->

			</div>
		</div>
	</div>
	<!--End container-fluid-->
</div>
<!--end-main-container-part-->
<jsp:include page="admin_footer.jsp"></jsp:include>
<script
	src="${pageContext.request.contextPath}/static/js/admin/lib/jquery.uniform.js"></script>
<script
	src="${pageContext.request.contextPath}/static/js/admin/lib/select2.min.js"></script>
<script
	src="${pageContext.request.contextPath}/static/js/admin/lib/matrix.form_common.js"></script>
</body>
</html>