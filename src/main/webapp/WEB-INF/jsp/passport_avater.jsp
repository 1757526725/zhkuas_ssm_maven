<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="header.jsp"></jsp:include>

<body>

	<!-- Wrap all page content here -->
	<div id="wrap">

		<jsp:include page="topBar.jsp"></jsp:include>

		<!-- Begin page content -->
		<div id="pageContent">
			<div class="container-narrow">
				<ul class="nav nav-tabs">
					<li><a href="passport?tab=basic">基本信息</a>
					</li>
					<li class="active"><a href="passport?tab=avater">修改头像</a>
					</li>
				</ul>

				<div class="tab-content">
					<div class="tab-pane active" id="avaterMsg">
						<div id="altContent"></div>
						<script type="text/javascript"
							src="http://images1.jyimg.com/w4/register/j/swfobject.js"></script>
						<script type="text/javascript">
				function uploadevent(status){
				     status += '';
					 switch(status){
						case '1':
							showAlert("信息","修改成功!");
							window.location.href = window.location.href;
						break;
						case '-1008':
							showAlert("错误","登陆超时，请重新登陆！");
							showLoginDailog();
							break;
						case '2':
							if(confirm('js call upload')){
								return 1;
							}else{
								return 0;
							}
						break;
						case '-1':
							//showSecretAlert("取消!");
							window.location.href = "?tab=avater";
						break;
						case '-2':
							showAlert("错误",'上传失败!');
							window.location.href = "?tab=avater";
						break;
	
						default:
							showAlert("未知错误",typeof(status) + ' ' + status);
					} 
				}
	
				var flashvars = {
				  "jsfunc":"uploadevent",
				  "pid":"75642723",
				  "uploadSrc":false,
				  "showBrow":true,
				  "showCame":true,
				  "uploadUrl":"../avater",
				  "pSize":"300|300|180|180|80|80|30|30",
				};
	
				var params = {
					menu: "false",
					scale: "noScale",
					allowFullscreen: "false",
					allowScriptAccess: "always",
					wmode:"transparent",
					bgcolor: "#FFFFFF"
				};
	
				var attributes = {
					id:"FaustCplus"
				};
	
				swfobject.embedSWF("http://zhkuas.cdn.duapp.com/FaustCplus-master/FaustCplus.swf", "altContent", "680", "450", "9.0.0", "expressInstall.swf", flashvars, params, attributes);

			</script>

					</div>
				</div>
			</div>


		</div>
		<div style="clear:both;"></div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
	
</body>
</html>