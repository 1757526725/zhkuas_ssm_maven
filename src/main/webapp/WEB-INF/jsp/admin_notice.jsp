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
				class="icon-home"></i> 首页</a> <a href="academys" class="current">公告设置</a>
		</div>
	</div>
	<!--End-breadcrumbs-->

	<!--container-fluid-->
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="btn-tools">
					<!--Action tools-->
					<button id="btn-save" class="btn btn-danger" disabled="disabled">
						<i class="icon-save"></i> 保存
					</button>
					<span class="text-primary text-large pull-right" >提示:这里设置的内容将在首页中显示,一般是选课通知的内容！</span>
					<!--End Action tools-->
				</div>
				<div class="widget-box">
			      <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
			        <h5>公告编辑</h5>
			      </div>
			      <div class="widget-content">
			        <div class="control-group">
			          <form>
			            <div class="controls">
			              <textarea class="textarea_editor span12" rows="17" placeholder="Enter text ...">${notice.content}</textarea>
			            </div>
			          </form>
			        </div>
			      </div>
			    </div>
			</div>
		</div>
	</div>
	<!--End container-fluid-->
</div>
<!--end-main-container-part-->
<jsp:include page="admin_footer.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/static/js/admin/lib/wysihtml5-0.3.0.js"></script> 
<script src="${pageContext.request.contextPath}/static/js/admin/lib/bootstrap-wysihtml5.js"></script> 
<!-- <script src="../../static/js/admin/page/school/academy.js"></script> -->
<script>
	$('.textarea_editor').wysihtml5();
</script>
</body>
</html>