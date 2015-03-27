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
			<a href="index" title="Go to Home" class="tip-bottom"><i
				class="icon-home"></i> 首页</a> <a href="settings" class="current">系统设置</a>
		</div>
	</div>
	<!--End-breadcrumbs-->

	<div class="container-fluid">
  <div class="row-fluid">
    <div class="span6 br">
      <div class="nopadding">
        <form action="" method="PUT" class="form-horizontal" id="setting-form">
          <label class="control-label">系统名称:</label>
          <div class="controls">
            <input type="text" class="span11" name="siteName" value="${config.siteName }">
          </div>
          <label class="control-label">系统域名:</label>
          <div class="controls">
            <input type="text" class="span11" name="domain" value="${config.domain }">
          </div>
          <label class="control-label">系统关键字:</label>
          <div class="controls">
            <input type="text" class="span11" name="keywords" value="${config.keyword }">
          </div>
          <label class="control-label">系统介绍:</label>
          <div class="controls">
            <textarea class="span11" name="description" value="<c:out value='${config.description }'></c:out>"><c:out value='${config.description }'></c:out></textarea>
          </div>
          <label class="control-label">当前学期:</label>
          <div class="controls">
            <select name="term" class="span8">
            <c:forEach var="term" items="${termList}">
            	<option value="${term.no }" <c:if test="${term.no==config.currentTerm.no}">selected='selected'</c:if> >${term.name }</option>
            </c:forEach>
            </select>
          </div>
          <div class="controls">
            <button type="button" class="save btn btn-danger span3">保存</button>
          </div>
        </form>
      </div>
    </div>
    <div class="span6 tips">
      <div class="span9  tip-siteName">
        <h4 class="alert-heading" >请输入一个系统名称，作为系统的首页 Title和系统的关键字等</h4>
      </div>
      <div class="span9  tip-domain" style="display:none;">
        <h4 class="alert-heading" >请输入一个域名，作为系统的访问网址</h4>
      </div>
      <div class="span9  tip-keywords" style="display:none;">
        <h4 class="alert-heading" >请输入一些系统的关键字，作为seo的关键字</h4>
      </div>
      <div class="span9  tip-description" style="display:none;">
        <h4 class="alert-heading" >请输入一些文字来描述系统的功能和简介</h4>
      </div>
    </div>
  </div>
</div>
  <!--End container-fluid-->
</div>
<!--end-main-container-part-->
<jsp:include page="admin_footer.jsp"></jsp:include>
<script src="../static/js/admin/lib/bootstrap-datepicker.js"></script>
<script src="../static/js/admin/page/settings/settings.js"></script>
</body>
</html>