$(function(){
	MyPublicCoursePage.main();
});

var MyPublicCoursePage = (function(){

	var _ipt_sNo=$("#ipt-sNo");
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
			_login(_ipt_sNo.val(),_ipt_password.val(),_ipt_vCode.val(),_updateMyPublicCourse);
		});
	};
	var _hideLoading=function () {
		$(".result_loading").hide();
	};
	
	var _showLoading=function (message) {
		$(".result_loading").children("span").html(message);
		$(".result_loading").show();
	};
	var _login  = function(sNo,pwd, vCode ,successDo) {
		var button = _btn_update;
		button.button("loading");
		_showLoading("正在为您测试登录教务网...");
		$.ajax({
			type : "POST",
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			url : domain+"network/user",
			data : {
				'username' :sNo,
				'password' : pwd,
				'vCode' :vCode
			},
			error : function(msg) {
				addActionResult(null, "服务器出错,教务网繁忙,请重试！");
				button.button('reset');
				_hideLoading();
				return;
			},
			success : function(data) {
				if (data == "") {
					addActionResult(null, "参数错误，请重试！");
					button.button('reset');
					_hideLoading();
					return;
				}
				if (data.state==1) {
					addActionResult(null, "登录成功!", "text-success");
					button.button('reset');
					_hideLoading();
					successDo();
					return;
				}
				if(data.state==0){
					addActionResult(null, data.error.error_description);
					button.button('reset');
					_hideLoading();
					return;
				}
				addActionResult("未知错误，请刷新重试！");
				button.button('reset');
				_hideLoading();
			}
		});
	};
	var showMyPublicCourse =function(){
		var button = _btn_update;
		_showLoading("正在获取数据...");
		$.ajax({
			type : "GET",
			url : domain+"data/user/course/public/json",
			error : function(msg) {
				addActionResult(null,"获取失败，请重试！");
				button.button('reset');
				hideLoading();
				return;

			},
			success : function(data) {
				if(data.state==0){
					addActionResult(null, data.error.error_description);
					button.button('reset');
					_hideLoading();
					return;
				}
				if (data.state==1 && data.result.list.lenght==0) {
					addActionResult(null,"没有数据，请先更新数据");
					button.button('reset');
					hideLoading();
					return;
				}
				addActionResult(null,"获取成功！","text-success",14);
				//json to table
				$(".course_data").html(_result2Table(data.result));
				$("#course_form").show();
				_hideLoading();
			}
		});
	};
	var _result2Table = function (jsonData){
		var list = jsonData.list;
		var sizeArray = jsonData.size;
		var creditArray  = jsonData.credit;
		var countCredit = jsonData.countCredit;
		
		var tableWrapperPre = "<table id=\"oTable\" class=\"table table-bordered\" >"
								+"<tbody>"
									+"<tr>"
										+"<td>编号</td>"
										+"<td>课程名</td>"
										+"<td>选修类型</td>"
										+"<td>学分</td>"
										+"<td>成绩</td>"
										+"<td>分类学分</td>"
										+"<td>总学分</td>"
									+"</tr>";
		
		var tableWrapperSuf=	"</tbody>"
							  +"</table>"
							  
							  
		var tableWrapperContent = "";
		var count = 1;
		var index = 0;
		for(var i = 0 ;i<list.length;++i){
			var type = list[i].course.courseProfiles.pcType.name;
			tableWrapperContent +="<tr>";
			tableWrapperContent+=("<td>"+(count++)+"</td>");
			tableWrapperContent+=("<td>"+"["+list[i].course.nameNo+"]"+list[i].course.name+"</td>");
			tableWrapperContent+=("<td>"+type+"</td>");
			tableWrapperContent+=("<td>"+list[i].course.credits+"</td>");
			tableWrapperContent+=("<td>"+(list[i].score==null?"未知":list[i].score)+"</td>");
			if(i == 0||!(type==(list[i-1].course.courseProfiles.pcType.name))){
				tableWrapperContent+=("<td class=\"text-danger\" rowspan=\""+sizeArray[index]+"\" valign=\"center\" align=\"center\" style=\"font-size:25px;vertical-align:middle;\">"+creditArray[index++]+"</td>");
			}
			if(i==0){
				tableWrapperContent+=("<td class=\"text-danger\" rowspan=\""+list.length+"\" valign=\"center\" align=\"center\" style=\"font-size:25px;vertical-align:middle;\">"+countCredit+"</td>");
			}
			tableWrapperContent +="</tr>";
		}
							
		var tableWrapper = tableWrapperPre+tableWrapperContent+tableWrapperSuf;
		return tableWrapper;
	};
	
	var _updateMyPublicCourse=function(){
		var button = _btn_update;
		button.button("loading");
		_showLoading("正在更新数据...");
		$.ajax({
			type : "POST",
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			url : domain+"main/user/course/statistics",
			data : {
				"_method" :"put"
			},
			error : function(msg) {
				addActionResult(null,"服务器出错,教务网抽风了,请重试！");
				button.button('reset');
				_hideLoading();
				return;
			},
			success : function(data) {
				if (data == "") {
					addActionResult(null,"更新失败，请重试！");
					button.button('reset');
					_hideLoading();
					return;
				}
				if(data.state==0){
					addActionResult(null, data.error.error_description);
					button.button('reset');
					_hideLoading();
					return;
				}
				if(data.state==1){
					showMyPublicCourse();
					addActionResult(null,data.result);
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