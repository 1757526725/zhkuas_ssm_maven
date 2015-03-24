$(function(){
	MyPublicCoursePage.main();
});

var MyPublicCoursePage = (function(){

	var _ipt_password=$("#academy_pwd");
	var _ipt_vCode=$("#academy_code");
	var _btn_update=$(".bt_update");
	var main = function(){
		_initPage();
		_initEvent();
	};

	var _initPage = function(){
		
	};

	var _initEvent = function(){
		_btn_update.click(function(){
			if (_ipt_password.val() == "") {
				addActionResult(null,"密码不能为空");
				return;
			}
			if (_ipt_vCode.val() == "") {
				addActionResult(null,"验证码不能为空");
				return;
			}
			_updateMyPublicCourse(_ipt_password.val(),_ipt_vCode.val());
		});
	};
	var _hideLoading=function () {
		$(".result_loading").hide();
	};
	
	var _showLoading=function (message) {
		$(".result_loading").children("span").html(message);
		$(".result_loading").show();
	};
	var showMyPublicCourse =function(){
		var button = _btn_update;
		_showLoading("正在获取数据...");
		$.ajax({
			type : "POST",
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			url : "../action/getMyPublicCourseList",
			data : {
				"action" :"show"
			},
			error : function(msg) {
				addActionResult(null,"获取失败，请重试！");
				button.button('reset');
				hideLoading();
				return;

			},
			success : function(data) {
				if (data == "no data") {
					addActionResult(null,"没有数据，请先更新数据");
					button.button('reset');
					hideLoading();
					return;
				}
				addActionResult(null,"获取成功！","text-success",14);
				$(".course_data").html(data);
				
				$("#course_form").show();
				_hideLoading();
			}
		});
	};
	var _updateMyPublicCourse=function(pwd ,vCode){
		var button = _btn_update;
		button.button("loading");
		_showLoading("正在更新数据...");
		$.ajax({
			type : "POST",
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			url : "../action/getMyPublicCourseList",
			data : {
				"academy_code":vCode,
				"academy_pwd":pwd,
				"action" :"update"
			},
			error : function(msg) {

				addActionResult(null,"服务器出错,教务网抽风了,请重试！");
				button.button('reset');
				_hideLoading();
				return;

			},
			success : function(data) {
				
				var result="未知错误";
				if (data == "") {
					addActionResult(null,"更新失败，请重试！");
					button.button('reset');
					_hideLoading();
					return;
				}
				try {
					result=eval('('+data+')');
				} catch (e) {
					console.log(e);
				}
				
				if(result['error']){
					addActionResult(null,result['error_description']);
					button.button('reset');
					_hideLoading();
					return;
				}
			
				if(result['info']){
					
					if(result['info_code']=='10002'){
						showMyPublicCourse();
					}
					addActionResult(null,result['info_description']);
					button.button('reset');
					_hideLoading();
					return;
				}
	

			}
		});
	};
	return {
		main: main,
		showMyPublicCourse:showMyPublicCourse
	};
})();