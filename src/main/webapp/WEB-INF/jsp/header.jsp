<%@page import="com.zhku.utils.WebConfigUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.zhku.web.Constants"%>
<%@ page import="com.zhku.bean.User"%>
<%@ page import="com.zhku.bean.Student"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE HTML>
<html xmlns:wb="http://open.weibo.com/wb">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/static/assets/ico/favicon.png">

  <title>仲恺选课助手</title>

  <!-- Bootstrap core CSS -->
  <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
<%--   <link href="${pageContext.request.contextPath}/static/css/bootstrap.css" rel="stylesheet">
 --%>  <!-- Custom styles for this template -->
  
  <link type="text/css" href="${pageContext.request.contextPath}/static/css/jquery.jscrollpane.css" rel="stylesheet" media="all" />
  <link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet">
  <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!--[if lt IE 9]>
  <script src="/static/assets/js/html5shiv.js"></script>
  <script src="/static/assets/js/respond.min.js"></script>
  
  <![endif]-->
 
  <script  type="text/javascript" src="${pageContext.request.contextPath}/static/assets/js/jquery.js">
	</script>
<%-- <script src="${pageContext.request.contextPath}/js/zk_xuanke.min.js"></script> --%>
<%
 String appContext = request.getContextPath();
 String basePath = request.getScheme()+"://"+request.getServerName() + (appContext==null||appContext.equals("")?"/":appContext);
%>
<!-- <script src="../js/zk_xuanke.js"></script> -->
 <script type="text/javascript">
 	var domain = '<%=basePath%>';
 	var authoHost = '<%=WebConfigUtils.getValue("rz_host")%>'
  	var currentTermNo=<%=Constants.CURRENT_TERMNO%>;
</script>

<%
	boolean isBinded =false;
	User user = (User)(session.getAttribute(Constants.SessionKey.LOGIN_USER.toString()));
 	if(user!=null){
 		if(user.getStudent()!=null&&user.getStudent().getsNo()!=null){
 			isBinded = true;
 		}
 	}
 %>
<script type="text/javascript">
	var UserStatus = (function(){
		var _isLogin = <% if(user!=null){%>true<%}else{%>false<%}%>;
		var _isBinded= <%=isBinded%>;
		var isLogin =function(){
			return _isLogin;
		};
		var isBinded = function(){
			return _isBinded;
		};
		return {
			isLogin:isLogin,
			isBinded:isBinded
		};
	})();
</script>
<%--  <script src="${pageContext.request.contextPath}/js/lib/jquery.lazyload.js"></script> --%>
 
</head>