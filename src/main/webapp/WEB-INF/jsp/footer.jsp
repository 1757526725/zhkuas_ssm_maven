<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="footer">
	<div class="container">
		<p class="text-muted credit">
			@copyright 2013 <a href="http://zhkuas.duapp.com">仲恺选课助手</a> All
			rights reserved <a href="http://weibo.com/jackcliao">建议</a> <a href="http://weibo.com/jackcliao">JackCan</a> <a href="${pageContext.request.contextPath}/admin/index">后台管理</a>
			.
		<script type="text/javascript">
		var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
		document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F2cc20475fe9d26094bcbccc5c05f1cad' type='text/javascript'%3E%3C/script%3E"));
		</script>
</p>
	</div>
</div>
<div class="alert messageAlert" style="display:none;">
<button class="close" id="bt_close">×</button>
<span class="glyphicon glyphicon-envelope text-success"></span> 
<a href="${pageContext.request.contextPath}/main/comment?tab=replyMe" onclick="" class="text-danger">你有<span id="messageCount">3</span>条回复信息</a>

</div>

<!-- Bootstrap core JavaScript
    ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->

<!-- <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script> -->
<script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/common/common.js"></script>
<script src="${pageContext.request.contextPath}/static/js/common/utils.verify.js"></script>

<script type="text/javascript">
	$('.bt-circle').tooltip();
	$('#topBar .nav > li').removeClass("active");

	$('#${nav_actived}').addClass("active");
<%-- <%if (session.getAttribute("error") != null) {
				out.print("showAlert(\"错误\",\"" + session.getAttribute("error")
						+ "\")");
				session.removeAttribute("error");
			} else if (request.getAttribute("message") != null) {
				out.print("showAlert(\"消 息\",\""
						+ request.getAttribute("error") + "\")");
				request.removeAttribute("message");
			}%> --%>
	
</script>
