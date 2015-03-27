<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!--sidebar-menu-->
<div id="sidebar"><a href="#" class="visible-phone"><i class="icon icon-home"></i> 更多菜单</a>
  <ul>
    <li><a href="${pageContext.request.contextPath}/admin/index"><i class="icon icon-home"></i> <span>系统信息</span></a> </li>
    <li><a href="${pageContext.request.contextPath}/admin/term"><i class="icon icon-th"></i> <span>学期信息</span></a></li>
     <li class="submenu"> <a href="#"><i class="icon icon-info-sign"></i> <span>学校信息管理</span> <span class="label label-important">3</span></a>
      <ul>
        <li><a href="${pageContext.request.contextPath}/admin/school/academys">学院信息管理</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/school/majors">专业信息管理</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/school/classes">班级信息管理</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/school/campuses">校区信息管理</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/school/buildings">教学楼信息管理</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/school/classrooms">教室信息管理</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/school/teachers">老师信息管理</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/school/students">学生信息管理</a></li>
      </ul>
    </li>
    <li class="submenu"> <a href="#"><i class="icon icon-file"></i> <span>用户管理</span> <span class="label label-important">5</span></a>
      <ul>
        <li><a href="#">微博绑定管理</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/account/users">教务网绑定管理</a></li>
      </ul>
    </li>
    <li class="submenu"> <a href="#"><i class="icon icon-info-sign"></i> <span>课程管理</span> <span class="label label-important">3</span></a>
      <ul>
        <li><a href="${pageContext.request.contextPath}/admin/course">课程信息管理</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/course/termcourse">学期课程管理</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/course/class/courses">班级课程管理</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/course/publiccourse">公选课程管理</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/course/publiccourse/type">公选课分类管理</a></li>
      </ul>
    </li>
     <li class="submenu"> <a href="#"><i class="icon icon-info-sign"></i> <span>评价管理</span> <span class="label label-important">3</span></a>
      <ul>
        <li><a href="${pageContext.request.contextPath}/admin/comment/publiccourse">公选课点评信息管理</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/comment/config">评价过滤设置</a></li>
      </ul>
    </li>
    

    <li class="submenu"> <a href="#"><i class="icon icon-info-sign"></i> <span>缓存管理</span> <span class="label label-important">4</span></a>
      <ul>
        <li><a href="${pageContext.request.contextPath}/admin/cache/user">用户表缓存</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/cache/classcourse">班级课程缓存</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/cache/publiccourse">公选课程缓存</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/cache/publiccourse/option">公选课选项页面缓存</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/cache/validatecode">验证码缓存</a></li>
      </ul>
    </li>
  	<li><a href="${pageContext.request.contextPath}/admin/fetcher/settings"><i class="icon icon-tint"></i> <span>抓取配置</span></a></li>
    <li><a href="${pageContext.request.contextPath}/admin/time"><i class="icon icon-tint"></i> <span>选课时间设置</span></a></li>
  </ul>
</div>
<!--sidebar-menu-->
