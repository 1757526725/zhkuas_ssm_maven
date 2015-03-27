<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="admin_header.jsp"></jsp:include>
<jsp:include page="admin_sideBar.jsp"></jsp:include>


<!--main-container-part-->
<div id="content">
<!--breadcrumbs-->
  <div id="content-header">
    <div id="breadcrumb"> <a href="index" title="Go to Home" class="tip-bottom"><i class="icon-home"></i>Home</a></div>
  </div>
<!--End-breadcrumbs-->

<!--Action boxes-->
  <div class="container-fluid">
    <div class="quick-actions_homepage">
      <ul class="quick-actions">
        <li class="bg_lo span3"> <a href="form-common.html"><i class="icon-info-sign"></i>仲恺农业工程学院</a> </li>
        <li class="bg_lb"> <a href="#"> <i class="icon-calendar"></i> 2014-2015第一学期</a> </li>
        <li class="bg_ly"> <a href="#"> <i class="icon-inbox"></i><span class="label label-success">101</span> 新增加课程 </a> </li>
        <li class="bg_lo"> <a href="#"> <i class="icon-th"></i> <span class="label label-success">101</span>海珠公选课</a> </li>
        <li class="bg_lr"> <a href="#"> <i class="icon-th"></i> <span class="label label-success">120</span>白云公选课</a> </li>
        <li class="bg_ls"> <a href="#"> <i class="icon-user"></i><span class="label label-success">10</span> 新增用户</a> </li>
        
        <li class="bg_ls"> <a href="#"> <i class="icon-tint"></i> 学院</a> </li>
        <li class="bg_lb"> <a href="#"> <i class="icon-pencil"></i>专业</a> </li>
        <li class="bg_lg"> <a href="#"> <i class="icon-calendar"></i> 班级</a> </li>
        <li class="bg_lr"> <a href="#"> <i class="icon-info-sign"></i>错误日志</a> </li>

      </ul>
    </div>
<!--End-Action boxes-->    


    </div>
  </div>


<!--end-main-container-part-->
<jsp:include page="admin_footer.jsp"></jsp:include>
</body>
</html>
