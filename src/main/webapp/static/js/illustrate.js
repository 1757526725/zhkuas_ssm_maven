$(function(){
	$(".card").click(function(){
		if(!isLogin){
			showAlert("消息", "请登录后操作");
			return;
		}
		if($(this).attr("data-name")=='myPublicCourse'){
			showAlert("消息", "开发中，敬请期待！");
			return;
		}
		window.open($(this).attr("data-name"));
	});
	$("#loginForm").submit(function(evt) {
		if($("#systemSno").val()==""){
			showAlert("提示","学号不能为空");
			if (evt) {
				evt.preventDefault();
			} else {
				evt.returnValue = false;
			}
			return;
		}
		if($("#systemPassword").val()==""){
			showAlert("提示","密码不能为空");
			if (evt) {
				evt.preventDefault();
			} else {
				evt.returnValue = false;
			}
			return;
		}
	});
	$(".bt_login_academic").click(function(){
		
		
		$("#loginForm").submit();

	});
}
	
);