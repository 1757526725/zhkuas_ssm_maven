$(function() {
	OneKey.main();
});
var OneKey = (function() {

	var Config = {
		'11' : {
			'begin' : new Date("6/9/2014 10:30"),
			'end' : new Date("6/9/2014 24:00")
		},
		'12' : {
			'begin' : new Date("6/10/2014 10:30"),
			'end' : new Date("6/11/2014 24:00")
		},
		'13' : {
			'begin' : new Date("6/12/2014 10:30"),
			'end' : new Date("6/14/2014 24:00")
		}
	};
	var _isBind = false;
	var _academy_had_logined = false;
	var _onekeyPanel = $(".onekey");
	var _scanResultNode = $(".scan-result");

	var _btn_login_test = $(".bt_login_test");
	var _btn_begin = $(".bt_begin");
	var _btn_go_now = $(".bt_go_now");
	var _ipt_pwd = $("#academy_pwd");
	var _ipt_code = $("#academy_code");
	var _v_academy_sno = $("#v_academy_sno").text();
	var _stu_grade = _v_academy_sno.substring(2, 4);
	var _timerinterval = null;
	var _bg_timerinterval = null;
	var _isBegin = false;
	var _showTimeLeft = function() {
		var currentTime = new Date();
		var beginTime = Config[_stu_grade]['begin'];
		var ts = beginTime - currentTime;

		if (ts < 0) {
			_isBegin = true;
			$(".time").html("已经开始");
			if (_timerinterval != null) {
				clearInterval(_timerinterval);
			}
		} else {
			var dd = parseInt(ts / 1000 / 60 / 60 / 24, 10);// 计算剩余的天数
			var hh = parseInt(ts / 1000 / 60 / 60 % 24, 10);// 计算剩余的小时数
			var mm = parseInt(ts / 1000 / 60 % 60, 10);// 计算剩余的分钟数
			var ss = parseInt(ts / 1000 % 60, 10);// 计算剩余的秒数
			dd = _checkTime(dd);
			hh = _checkTime(hh);
			mm = _checkTime(mm);
			ss = _checkTime(ss);
			$(".time").html(dd + "天" + hh + "时" + mm + "分" + ss + "秒");
		}
	};
	var _checkTime = function(i) {
		if (i < 10) {
			i = "0" + i;
		}
		return i;
	};
	var show = function() {

		if (_isBind) {
			_timerinterval = setInterval(function() {
				_showTimeLeft();
			}, 1000);
			_onekeyPanel.show();
			_scanResultNode.hide();
			if (!_academy_had_logined)
				changeValidateCode($("#vCode")[0]);
		} else
			showAlert(
					"提示",
					'<p>1.你可能未绑定教务网账户 <a href="passport?tab=basic">去绑定</a></p><p>2.你可能未绑定上课的校区。 <a href="passport?tab=basic">去绑定校区</a></p>');

	};
	var main = function() {
		_initData();
		_initEvent();
	};
	var _initData = function() {
		if (_v_academy_sno == "") {
			_isBind = false;
		} else {
			_isBind = true;
		}

		if ($("input[name='loginState']").val()=='true') {
			_academy_had_logined = true;
		} else {
			_academy_had_logined = false;
		}

		if (_academy_had_logined) {
			_showAcademyHadLoginState();
		}
	};
	var _showAcademyHadLoginState = function() {
		_btn_login_test.hide();
		_ipt_pwd.attr("disabled", "disabled");
		_ipt_code.attr("disabled", "disabled");
		$(".login_state").show();
	};
	var _showAcademyLoginState = function() {
		_btn_login_test.show();
		_ipt_pwd.removeAttr("disabled");
		_ipt_code.removeAttr("disabled");
		_academy_had_logined = false;
		$(".login_state").hide();
	};
	var _initEvent = function() {
		_btn_login_test.click(function() {
			if (_checkForm()) {
				_loginTest($("#v_academy_sno").text(),_ipt_pwd.val(), _ipt_code.val());
			}
		});
		_btn_begin.click(function() {
			if(!_academy_had_logined&&!($("input[name='loginState']").val()=='true')){
				addActionResult(null, "请先登录测试!");
				return ;
			}
			if (_checkForm()) {
				_oneKeyBegin(_ipt_pwd.val(), _ipt_code.val());
			}
		});
		_btn_go_now.click(function() {
			if(!_academy_had_logined&&!($("input[name='loginState']").val()=='true')){
				addActionResult(null, "请先登录测试!");
				return;
			}
			if (_checkForm()) {
				_oneKeyGoNow(_ipt_pwd.val(), _ipt_code.val());
			}
		});

	};

	var _oneKeyBegin = function(pwd, vCode) {
		_bg_timerinterval = setInterval(function() {
			if (_isBegin) {
				_oneKeyGoNow(pwd, vCode);
				if (_bg_timerinterval != null) {
					clearInterval(_bg_timerinterval);
				}
			}
		}, 1000);
	};
	var _oneKeyGoNow = function(pwd, vCode) {
		$(".bt_begin").button("reset");
		$(".submit-items").empty();
		var submitWay = $(".submitWay").val();
		var isQueue = true;
		if (submitWay == "true")
			isQueue = false;
		var i = 0;
		var trs = $("tbody").find("tr");
		for ( var i = 0; i < trs.size(); ++i) {
			var tr = $(trs.get(i));
			var formValue = tr.attr("formvalue");
			var cNo = tr.attr("cno");
			var tName = $(tr.children()[4]).html();
			var credit = $(tr.children()[2]).html();
			var courseName = $(tr.children()[1]).children("a").html();

			_submitTaskQueue.addTask(pwd, cNo, vCode, tName, courseName,
					formValue, credit);
		}

		_submitTaskQueue.start(isQueue);
	};

	var resubmitCourse = function(password, cNo, vCode, tName, courseName,
			formValue, credit) {
		_submitTaskQueue.addTask(password, cNo, vCode, tName, courseName,
				formValue, credit);
	};

	var _submitTaskQueue = (function() {
		var taskList = [];
		var isRun = false;
		var isQueue = true;
		var addTask = function(password, cNo, vCode, tName, courseName,
				formValue, credit) {
			var course = new Array();
			course['password'] = password;
			course['cNo'] = cNo;
			course['tName'] = tName;
			course['courseName'] = courseName;
			course['formValue'] = formValue;
			course['credit'] = credit;
			course['vCode'] = vCode;
			taskList.push(course);
		};

		var start = function(_isQueue) {
			isQueue = _isQueue;
			setInterval(function() {
				if (taskList.length > 0 && !isRun) {
					isRun = isQueue;
					this.submitOnKeyCourse(taskList.shift());
				}
			}, 100);
		};

		this.submitOnKeyCourse = function(course) {
			var button = $(".bt_begin");
			var cNo = course['cNo'];
			var password = course['password'];
			var tName = course['tName'];
			var courseName = course['courseName'];
			var formValue = course['formValue'];
			var credit = course['credit'];
			var vCode = course['vCode'];

			var itemNode = "<p id=\"item_"
					+ cNo
					+ "\"><span class=\"text-info\">"
					+ courseName
					+ " </span><span><img src=\""+domain+"static/images/loading_min.gif\"></span><span class=\"text-danger message\"> 提交中...</span></p>";

			if ($("#item_" + cNo).size() > 0) {
				$("#item_" + cNo).replaceWith(itemNode);
			} else {
				$(".submit-items").append(itemNode);
			}

			var retry = " <a class=\"text-info\" href=\"javascript:void(0)\" onclick=\"OneKey.resubmitCourse('"
					+ password
					+ "','"
					+ cNo
					+ "','"
					+ vCode
					+ "','"
					+ tName
					+ "','"
					+ courseName
					+ "','"
					+ formValue
					+ "','"
					+ credit
					+ "')\">重新提交</a>";
			$("#item_" + cNo).find(".message").html("提交中...");
			$.ajax({
						type : "POST",
						contentType : "application/x-www-form-urlencoded; charset=UTF-8",
						url : domain+"network/course/public/oneKey",
						data : {
							"academy_pwd" : course['password'],
							"cNo" : course['cNo'],
							"tName" : course['tName'],
							"courseName" : course['courseName'],
							"formValue" : course['formValue'],
							"credit" : course['credit'],
							"academy_code" : course['vCode']

						},

						error : function(msg) {
							$("#item_" + cNo).find(".message").html(
									"提交失败..." + retry);
							isRun = false;
							return;
						},
						success : function(data) {
							if (data.state==0 && data.error.error_description=='-10006') {
								addActionResult(null, "教务网登录超时，请重新登录");
								_showAcademyLoginState();
								return;
							}
//							if (data.indexOf("Request process time exceed th max limit") >= 0) {
//								// 请求超时..
//								$("#item_" + cNo).find(".message").html(
//										"提交请求超时..." + retry);
//								return;
//							}
							if (data.state==0 && data.error.error_description == "-50013") {
								$("#item_" + cNo).find(".message").html(
										"获取不到选课结果..." + retry);
								return;
							}else if (data.state==0) {
									addActionResult(null,data.error.error_description);
									return;
							}
							$("#item_" + cNo).find(".message").html(
									data.result + retry);
							isRun = false;
						}
					});

		};

		return {
			addTask : addTask,
			start : start
		};
	})();

	var _hideLoading = function() {
		$(".result_loading").hide();
	};
	var _checkForm = function() {
		if (_academy_had_logined)
			return true;
		if (_ipt_pwd.val() == "") {
			addActionResult(null, "密码不能为空！");
			return false;
		}
		if (_ipt_code.val() == "") {
			addActionResult(null, "验证码不能为空！");
			return false;
		}

		return true;

	};
	var _showLoading = function(message) {
		$(".result_loading").children("span").html(message);
		$(".result_loading").show();
	};
	var _loginTest = function(sNo,pwd, vCode) {
		var button = _btn_login_test;
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
					addActionResult(null, "测试通过", "text-success");
					button.button('reset');
					_hideLoading();
					_academy_had_logined = true;
					_showAcademyHadLoginState();
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
	return {
		main : main,
		show : show,
		resubmitCourse : resubmitCourse
	};
})();