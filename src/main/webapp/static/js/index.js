$(function(){
	$(".card").click(function(){
		if(!UserStatus.isLogin()){
			showAlert("消息", "请登录后操作");
			return;
		}
		window.open("main/"+$(this).attr("data-name"));
	});
}	
);