<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:if test="${fn:length(result)!=0}">{<c:forEach var="entry" items="${result}">"${entry.key}":"${entry.value}",</c:forEach>"end":"end"}</c:if>