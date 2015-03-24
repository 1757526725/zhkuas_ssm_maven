<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:forEach var="clazz" items="${classes}">
	<li value="${clazz.classNo}"><a href="javascript:void(0);">${clazz.className}</a>
		<ul class="nav" style="display: none; ">
			<li value="20130"><a href="javascript:void(0);">2013-2014第一学期</a>
			</li>
			<li value="20131"><a href="javascript:void(0);">2013-2014第二学期</a>
			</li>
			<li value="20140"><a href="javascript:void(0);">2014-2015第一学期</a>
			</li>
		</ul>
	</li>
</c:forEach>

