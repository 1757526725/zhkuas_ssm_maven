$(function (){
	var bt_drop_course=$(".bt_drop_course");
	var bt_login_academic=$(".bt_login_academic");
	var bt_switch_account=$(".bt_switch_account");
	var ip_sNo=$("#systemSno");
	var ip_password=$("#systemPassword");
	bt_switch_account.click(function(){
		if(ip_sNo.attr("disabled")){
			ip_sNo.removeAttr("disabled");
			bt_switch_account.html("我的账号");
		}
		else{
			ip_sNo.val(bt_switch_account.attr("osno"));
			ip_sNo.attr("disabled","disabled");
			bt_switch_account.html("其他账号");
		}
	});
	
	bt_login_academic.click(function(){
		if(!ip_sNo.attr("disabled")&& ip_sNo.val()==""){
			addActionResult("学号不能为空");
			return;
		}
		if (ip_password.val() == "") {
			addActionResult("密码不能为空");
			return;
		}

		tx_login(ip_sNo.val(),ip_password.val(),bt_login_academic,function(){
			hadLogin(ip_sNo.val(),ip_password.val());
		});
	});
	bt_drop_course.click(function(){
		if(ChkVal()){
			dropCourse();
		}
	});
});
function dropCourse(){
	var button= $(".bt_drop_course");
	$.ajax({
		type : "POST",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		url : "../action/dropCourse",
		data :$("form1").serialize(),
		error : function(msg) {

			addActionResult("服务器出错，请重试！");
			button.button('reset');
			hideLoading();
			return;

		},
		success : function(data) {
			if(data=="system login out time"){
				addActionResult("教务网登录过期，请重新登录教务网！");
				button.button('reset');
				return;
			}
		
			if(data.indexOf("Request process time exceed th max limit")>=0){
				//请求超时..
				addActionResult("请求超时,请刷新重试!",null,"13");
				hideLoading();
				return;
			}
			
			if(data=="获取不到退选结果"){
				addActionResult("获取不到退选结果,请刷新重试!",null,"13");
				hideLoading();
				return;
			}
			addActionResult(data,"text-success","12");
			button.button('reset');
			hideLoading();
			$(".bt_drop_course").button("reset");
		}
	});
}
function ChkVal()
{
	try{
		var strid="TTT";
		var s = false;
		
		var ip_chks = $("#oTable").find("input[type='checkbox']");
		var skbj_str;
		for (var i=0;i<ip_chks.size();++i){
			if( ip_chks[i].checked &&ip_chks[i].disabled == false ){
				
				skbj_str = ip_chks[i].value;
				if(skbj_str!="")
				{
					var val_array=skbj_str.split(";");
					for(j=0;j < val_array.length;j++)
					{
						strid+=","+val_array[j];
					}
				}
				s=true;
			}
		}
		$("#deleteValue").val(strid);
		if (!s){
			addActionResult("需选定一门课程！");
			return false;
		}
		if (!confirm('是否退选记录？'))return false;
	}catch(e){}
}
function getCourseSelected(sNo,password){
	showLoading("正在为您获取数据...");
	$.ajax({
		type : "POST",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		url : "../action/getMyCourseSelected",
		data : {
			"sNo":sNo,
			"password" : password
		},
		error : function(msg) {

			addActionResult("服务器出错，请重试！");
			hideLoading();
			return;

		},
		success : function(data) {
			if (data == "login out time") {
				addActionResult("登录超时，请刷新页面，并用微博登录！");
				hideLoading();
				return;
			}
			if (data == "passwordIncorrect") {
				addActionResult("密码错误");
				hideLoading();
				return;
			}
			if (data == "") {
				addActionResult("参数错误，请重试！");
				hideLoading();
				return;
			}
			if(data=="教务网禁止操作！(原因：未开通)"){
				addActionResult("教务网禁止操作！(原因：未开通)",null,"13");
				hideLoading();
				return;
			}
			if(data=="获取不到已选结果"){
				addActionResult("获取不到已选结果,请刷新重试!");
				hideLoading();
				return;
			}
			if(data.indexOf("Request process time exceed th max limit")>0){
				//请求超时..
				addActionResult("请求超时,请刷新重试!");
				hideLoading();
				return;
			}
			
			$("#oTable").html(data);
			$("#course_form").show();
			addActionResult("学号<span class='text-info'>"+sNo+"</span>已选课程获取成功！","text-success","13");
			$(".bt_drop_course").button("reset");
			hideLoading();
		}
	});
}
function tx_login(sNo,password, button ,func_success) {
	button.button("loading");
	showLoading("正在为您登录教务网...");
	$.ajax({
		type : "POST",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		url : "../action/loginSystem",
		data : {
			"sNo":sNo,
			"password" : password
		},
		error : function(msg) {

			addActionResult("服务器出错，请重试！");
			button.button('reset');
			hideLoading();
			return;

		},
		success : function(data) {
			if (data == "login out time") {
				addActionResult("登录超时，请刷新页面，并用微博登录！");
				button.button('reset');
				hideLoading();
				return;
			}
			if (data == "passwordIncorrect") {
				addActionResult("密码错误");
				button.button('reset');
				hideLoading();
				return;
			}
			if (data == "connect Error") {
				addActionResult("教务网登录超时，请重试!");
				button.button('reset');
				hideLoading();
				return;
			}
			if (data == "") {
				addActionResult("参数错误，请重试！");
				button.button('reset');
				hideLoading();
				return;
			}
			if (data == "login success") {
				addActionResult("学号<span class='text-info'>"+sNo+"</span>登录成功", "text-success","12");
				button.button('reset');
				hideLoading();
				func_success();
				return;
			}
			addActionResult("未知错误，请刷新重试！");
			button.button('reset');
			hideLoading();
		}
	});
}

function hadLogin(sNo,password){

	var bt_login_academic=$(".bt_login_academic");
	var afterLoginNode=$(".afterlogin");
	afterLoginNode.show();
	$(".use_notice").hide();
	bt_login_academic.html("重新登录");
	var ip_sNo=$("#systemSno");
	var ip_password=$("#systemPassword");
	
	getCourseSelected(sNo,password);
}