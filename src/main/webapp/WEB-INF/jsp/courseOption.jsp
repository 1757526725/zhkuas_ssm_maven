<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${option==null }">
state:nothing
</c:if>
<c:if test="${option.optionHtml!=null}">
<div class="data-wrapper">
	${option.optionHtml}
	<c:if test="${myCourseScheme!=null}">
		<div id="initActionData" hadChoose="${myCourseScheme.formValue}"></div>
	</c:if>
</div>
</c:if>

