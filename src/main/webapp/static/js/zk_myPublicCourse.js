$(function(){
	var bt_update=$(".bt_update");
	var ip_sNo=$("#systemSno");
	var ip_password=$("#systemPassword");
	bt_update.click(function(){
		if (ip_password.val() == "") {
			addActionResult("密码不能为空");
			return;
		}

		updateMyPublicCourse(ip_password.val());
	});
});

function updateMyPublicCourse(password){
	
	var button = $(".bt_update");
	button.button("loading");
	showLoading("正在更新数据...");
	$.ajax({
		type : "POST",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		url : "../action/getMyPublicCourseList",
		data : {
			"password":password,
			"action" :"update"
		},
		error : function(msg) {

			addActionResult("服务器出错，请重试！");
			button.button('reset');
			hideLoading();
			return;

		},
		success : function(data) {
			if (data == "") {
				addActionResult("更新失败，请重试！");
				button.button('reset');
				hideLoading();
				return;
			}
			
			if(data=="update success"){
				addActionResult("更新成功！","text-success",13);
				button.button('reset');
				showMyPublicCourse();
				hideLoading();
			}
			

		}
	});
	
}

function showMyPublicCourse(){
	var button = $(".bt_update");
	showLoading("正在获取数据...");
	$.ajax({
		type : "POST",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		url : "../action/getMyPublicCourseList",
		data : {
			"action" :"show"
		},
		error : function(msg) {
			addActionResult("获取失败，请重试！");
			button.button('reset');
			hideLoading();
			return;

		},
		success : function(data) {
			if (data == "no data") {
				addActionResult("没有数据，请先更新数据");
				button.button('reset');
				hideLoading();
				return;
			}
			addActionResult("获取成功！","text-success",14);
			$(".course_data").html(data);
			
			$("#course_form").show();
			hideLoading();
		}
	});
}