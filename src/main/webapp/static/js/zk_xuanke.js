var timerinterval = null;
var startInterval = null;


var beginTime = new Date("12/18/2013 10:30");
if(studentGrade&&studentGrade==2012){
	beginTime = new Date("12/20/2013 10:30");
}
if(studentGrade&&studentGrade==2013){
	beginTime = new Date("12/23/2013 10:30");
}
$(function() {
	$("#bt-scheme-ensure")
			.click(
					function() {
						var cNo = $("#bt-scheme-ensure")
								.attr("action-data-cNo");

						if ($("#cNo").size() > 0) {
							addMyScheme(
									cNo,
									function() {
										$("#bt-addToMyPlan").attr("disabled",
												"disabled");
										$("#bt-addToMyPlan")
												.html(
														'<span class="glyphicon glyphicon-plus"></span> 已加入选课方案');
									});
						} else {
							addMyScheme(cNo, function() {
								location.reload();
							});
						}
					});

	// 保存选课方案
	$("#bt-scan-conflict").click(function() {
		scanConflict();
	});
	$("#bt-saveAll").click(function() {
		var button = $("#bt-saveAll");
		button.button("loading");
		$.ajax({
			type : "POST",
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			url : "../action/saveScheme",
			data : $("#mySchemeForm").serialize(),
			error : function(msg) {

				showAlert("消息", "服务器出错，请联系管理员");
				button.button('reset');
				return;

			},
			success : function(data) {
				if (data == "") {
					showAlert("消息", "提交失败，请重试！");
					button.button('reset');
					return;
				}
				if (data == "save success") {
					showAlert("消息", "保存成功", function() {
						location.reload();
					});
					return;
				}

			}
		});
	});

	// 清空选课方案

	$("#bt-deleteAll").click(function() {
		if (!confirm("你确定要清空所有课程吗?")) {
			return;
		}
		var button = $("#bt-deleteAll");
		button.button("loading");
		$.ajax({
			type : "get",
			url : "../action/clearScheme",
			error : function(msg) {

				showAlert("消息", "服务器出错，请联系管理员");
				button.button('reset');
				return;

			},
			success : function(data) {
				if (data == "") {
					showAlert("消息", "清空失败，请重试！");
					button.button('reset');
					return;
				}
				if (data == "clear success") {
					location.reload();
					return;
				}

				if (data == "login out time") {
					showAlert("消息", "登录超时,请重新登录！");
					return;
				}

			}
		});
	});

	// 添加

	$("#bt-addMore")
			.click(
					function() {
						// 显示并初始化窗口
						if (!isBindStudent || !isBindCompus) {
							showAlert(
									"提示",
									'<p>1.你可能未绑定教务网账户 <a href="passport?tab=basic">去绑定</a></p><p>2.你可能未绑定上课的校区。 <a href="passport?tab=basic">去绑定校区</a></p>');
							return;
						}
						location.href = "../main/public_course?compusId="
								+ compusId;
					});

	$("#bt-oneKey").click(function() {
		$(".onekey").show();
		$(".scan-result").hide();
		timerinterval = setInterval("showTimeLeft()", 1000);
		$("#systemPassword").val("");
	});

	$(".bt_login_test").click(function() {
		if ($("#systemPassword").val() == "") {
			addActionResult("密码不能为空");
			return;
		}
		loginTest($("#systemPassword").val(), $(".bt_login_test"));
	});

	$(".bt_begin").click(function() {
		if ($("#systemPassword").val() == "") {
			addActionResult("密码不能为空");
			return;
		}

		var password = $("#systemPassword").val();
		var maxCredit = $(".maxCredit").val();
		var submitWay = $(".submitWay").val();

		 $(".bt_begin").button("loading");
		addActionResult("已经启动，时间到了，自动选课，请不要刷新","text-success","14");
		myinterval = setInterval(function(){
			var currentTime = new Date();
			var ts = beginTime - currentTime;
			if (ts < 0) {
				
				oneKeyBegin(password, submitWay);
				if (myinterval != null) {
					clearInterval(myinterval);
				}
			}
		}, 1000);
	});
	
	$(".bt_go_now").click(function(){
		if ($("#systemPassword").val() == "") {
			addActionResult("亲!你密码都没填！");
			return;
		}
		
		showMyConfirm("你确定系统提前了？点击确定选课助手会将你的提交时间提前为当前时间。",function(){
			beginTime=new Date();
			addActionResult("已经修改为当前时间,赶快点击 启动吧！");
		});
	});
});

function showTimeLeft() {
	var currentTime = new Date();
	var ts = beginTime - currentTime;
	if (ts < 0) {
		$(".time").html("已经开始");
		if (timerinterval != null) {
			clearInterval(timerinterval);
		}
	} else {
		var dd = parseInt(ts / 1000 / 60 / 60 / 24, 10);// 计算剩余的天数
		var hh = parseInt(ts / 1000 / 60 / 60 % 24, 10);// 计算剩余的小时数
		var mm = parseInt(ts / 1000 / 60 % 60, 10);// 计算剩余的分钟数
		var ss = parseInt(ts / 1000 % 60, 10);// 计算剩余的秒数
		dd = checkTime(dd);
		hh = checkTime(hh);
		mm = checkTime(mm);
		ss = checkTime(ss);
		$(".time").html(dd + "天" + hh + "时" + mm + "分" + ss + "秒");
	}

}

function checkTime(i) {
	if (i < 10) {
		i = "0" + i;
	}
	return i;
}
/** *****选课相关********* */
function openWin(theID) {
	var theURL, w, h, Tform;
	w = 450, h = 380;
	eval("Tform='width=" + w + ",height=" + h + "'");
	theURL = 'http://jw.zhku.edu.cn/jwmis/JXZY/INFO_Teacher.aspx?id='
			+ theID.getAttribute("value");
	pop = window.open(theURL, 'winKPT', Tform);
}

function selradio(theID, N) {
	var object_id;
	var object_value;
	var object_temp;
	var skbz_id;
	var divColl;
	object_value = theID.value;
	object_temp = object_value.split("|");
	skbz_id = object_temp[0];
	try {
		var divCol = document.getElementsByTagName("input");
		for (i = 0; i < divCol.length; i++) {
			if (divCol[i].type == "radio")
				divCol[i].checked = false;
		}
		if (object_temp[0] != "") {
			try {
				eval("window.document.all.J" + object_temp[0]
						+ ".checked=true;");
			} catch (e) {
			}
			try {
				eval("window.document.all.S" + object_temp[0]
						+ ".checked=true;");
			} catch (e) {
			}
			try {
				eval("window.document.all.E" + object_temp[0]
						+ ".checked=true;");
			} catch (e) {
			}
			try {
				eval("window.document.all.D" + object_temp[0]
						+ ".checked=true;");
			} catch (e) {
			}
			try {
				eval("window.document.all.M" + object_temp[0]
						+ ".checked=true;");
			} catch (e) {
			}
			try {
				eval("window.document.all.N" + object_temp[0]
						+ ".checked=true;");
			} catch (e) {
			}
			try {
				eval("window.document.all.P" + object_temp[0]
						+ ".checked=true;");
			} catch (e) {
			}
			try {
				eval("window.document.all.Q" + object_temp[0]
						+ ".checked=true;");
			} catch (e) {
			}
		} else {
			theID.checked = true;
		}
	} catch (e) {
	}
	$("[name=jsskbj_str]").val(object_temp[2]);
	$("#bt-scheme-ensure").button("reset");
	// window.document.all.jsskbj_str.value=object_temp[2];
}

function deleteScheme(_this) {
	var schemeNode = $(_this).parent().parent();

	if (confirm("你确定要移除这门课程吗?")) {
		$.ajax({
			type : "GET",
			url : "../action/deleteScheme",
			data : {
				"msid" : schemeNode.attr("msid")
			},
			error : function(msg) {

				showAlert("消息", "服务器出错，请联系管理员");
				return;

			},
			success : function(data) {
				if (data == "") {
					showAlert("消息", "删除失败！请重试");
					return;
				}

				if (data == "remove success") {
					location.reload();
				}
			}
		});
	}
}

function editScheme(_this) {
	var schemeNode = $(_this).parent().parent();
	showChooseCourseTimeDialog(schemeNode.attr("cNo"));

}

function moveUp(_this) {

	var src_tr = $(_this).parent().parent();
	var des_tr = src_tr.prev();
	if (des_tr.size() == 0)
		return;
	var src_orderNode = src_tr.children(".order_num");
	var des_orderNode = des_tr.children(".order_num");

	var temp = src_orderNode.html();
	src_orderNode.html(des_orderNode.html());
	des_orderNode.html(temp);
	src_tr.addClass("scheme-seleted");
	src_tr.parent().children().removeClass("scheme-seleted");
	src_tr.addClass("scheme-seleted");
	var tempNode = src_tr.clone();
	src_tr.remove();
	des_tr.before(tempNode);

	var bt_save = $("#bt-saveAll");
	bt_save.removeClass("btn-success");
	bt_save.addClass("btn-danger");

	bt_save.removeAttr("disabled");
}

function quickAddToMyscheme(cNo) {
	if (isLogin == false) {
		showAlert("消息", "请登录后操作！");
		return;
	}
	if (!isBindStudent || !isBindCompus) {
		showAlert(
				"提示",
				'<p>1.你可能未绑定教务网账户 <a href="passport?tab=basic">去绑定</a></p><p>2.你可能未绑定上课的校区。 <a href="passport?tab=basic">去绑定校区</a></p>');
		return;
	}
	showChooseCourseTimeDialog(cNo);
}

function scanConflict() {
	$(".scan-result").remove();
	$(".flag-label").remove();
	var resultPanel = "<div class=\"panel panel-primary ctrl-panel clearfix scan-result\">";
	resultPanel += "<span class=\"text-danger\">检测结果:</span><span class=\"initialText\">正在检测...</span><div class=\"loading pull-right\"></div><div class=\"summaryNode\"></div>";
	resultPanel += "</div>";
	var button = $("#bt-scan-conflict");
	button.button("loading");
	$("#mySchemeForm").after(resultPanel);
	var summaryNode = $(".summaryNode");
	summaryNode.empty();
	$
			.ajax({
				type : "get",
				url : "../action/scanConflict",
				async : true,
				success : function(data) {
					if (data == 'login time out') {
						showAlert("消息", "登录超时，请重新登录！");
						button.button("reset");
						$(".scan-result").hide();
						return;
					}
					if (data == '') {
						var result = "<span class=\"text-danger\">服务器错误，检测未完成</span>";
						dg_addErrorMassage(result, summaryNode);
						button.button("reset");
						$(".scan-result").hide();
						return;
					}
					if (data == 'no course conflict') {
						var result = "<span class=\"text-success\">没有冲突课程</span>";
						dg_addErrorMassage(result, summaryNode);
						button.button("reset");
						hideScanResultLoading();
						return;
					}
					if (data == 'unBind') {
						var result = '<h4 class=\"text-info\">你未确认你的个人课表.无法进行冲突性检测 。<a href=\"mySchedule\" class=\"text-danger pull-right\">点击去确认</a></h4>';
						dg_addErrorMassage(result, summaryNode);
						button.button("reset");
						hideScanResultLoading();
						return;
					}
					var obj = eval('(' + data + ')');

					for (index_cNo in obj) {
						if (index_cNo == 'end')
							break;
						var message = '<span class="label label-danger pull-right flag-label">冲突</span>';
						var courseNodeTd = $($("[cNo='" + index_cNo + "']")
								.children().get(1));
						courseNodeTd.append(message);
						var result = "<span class=\"text-danger\">"
								+ courseNodeTd.children("a").html()
								+ "</span> 与" + "<span class=\"text-info\">"
								+ obj[index_cNo] + "</span>冲突";
						dg_addErrorMassage(result, summaryNode);
					}

					button.button("reset");
					hideScanResultLoading();

				},
				error : function(msg) {
					dg_addErrorMassage("服务器出错，请联系管理员", summaryNode);
					button.button('reset');
					return;

				}
			});
}

function hideScanResultLoading() {
	var scan_resultNode = $(".scan-result");
	scan_resultNode.find(".initialText").hide();
	scan_resultNode.find(".loading").hide();
}

function showChooseCourseTimeDialog(cNo) {
	$("#initMsgPanel").show();
	$("#noticePanel").hide();
	$("#optionList").empty();
	$("#CourseChoose-dialog").modal("show");
	$("#bt-scheme-ensure").attr("disabled", "disabled");
	$("#bt-scheme-ensure").attr("action-data-cNo", cNo);
	// 请求数据
	getOptionData(cNo);
}

function getOptionData(cNo) {
	var noneDataMsg = $('<h4 class="text-center text-danger" > 没有选课信息，可能是教务网没有该选课信息，也可能是网络错误.<a href="javascript:void(0)" onclick="getOptionData('
			+ cNo + ')">点击刷新！</a></h4>');
	var switchMsg = $('<h4 class="text-center text-danger" > 你打开的不是这学期的课程,是否切换到当前学期课程.<a href="../main/public_course?termNo='+currentTermNo+'">点击切换！</a></h4>');
	if($(".panel-heading").size()>0&& $(".panel-heading").find("span").html()!=currentTermName){
		$("#optionList").empty();
		$("#optionList").append(switchMsg);
		$("#initMsgPanel").hide();
		return;
	}
	$("#initMsgPanel").show();
	$.get("../action/courseOption?time=" + new Date().getTime(), {
		"cNo" : cNo
	}, function(data) {
		if (data == "state:nothing") {
			$("#optionList").empty();
			$("#optionList").append(noneDataMsg);
			$("#initMsgPanel").hide();
			return;
		}
		if(data.indexOf("Request process time exceed th max limit")>=0){
			//请求超时..
			$("#optionList").empty();
			$("#optionList").append(noneDataMsg);
			$("#initMsgPanel").hide();
			return;
		}
		var dataNode = $(data);
		initOptionData(dataNode);
		try {
			$("#optionList").append(dataNode);
		} catch (e) {

		}

		// 已经选过了的显示初始化
		var idval = "";
		var actiondataNode = dataNode.find("#initActionData");
		if (actiondataNode) {
			try {
				var divCol = dataNode.find("input");
				for ( var i = 0; i < divCol.length; i++) {
					if (divCol[i].type == "radio") {
						idval = divCol[i].value.split("|")[2].split("@")[1];
						if (actiondataNode.attr("hadChoose") == idval) {
							selradio(divCol[i], 1);
							return;
						}
					}
				}

			} catch (e) {
				console.log(e);
			}
		}
	});
}

function initOptionData(dataNode) {
	$("#initMsgPanel").hide();
	$("#noticePanel").show();

	dataNode.children("#dataListDiv").children("table").find("table").addClass(
			"table table-bordered");

}

function loginTest(password, button) {
	button.button("loading");
	showLoading("正在为您测试登录教务网...");
	$.ajax({
		type : "POST",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		url : "../action/loginSystem",
		data : {
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
				addActionResult("测试通过", "text-success");
				button.button('reset');
				hideLoading();
				return;
			}
			addActionResult("未知错误，请刷新重试！");
			button.button('reset');
			hideLoading();
		}
	});
}
function addActionResult(message, text_class, fontSize) {
	var action_result = $(".result_text");
	action_result.empty();
	if (text_class == null)
		text_class = "text-danger";
	if (fontSize != null) {
		action_result.append("<h3 style=\"font-size:" + fontSize
				+ "px;\"class='" + text_class + "'>" + message + "</h3>");
		return;
	}
	action_result.append("<h3 class='" + text_class + "'>" + message + "</h3>");
}

function showLoading(message) {
	$(".result_loading").children("span").html(message);
	$(".result_loading").show();
}
function hideLoading() {
	$(".result_loading").hide();
}

function oneKeyBegin(password, submitWay) {
	$(".bt_begin").button("reset");
	$(".submit-items").empty();
	var i=0;
	// 获取每个tr
	var trs = $("tbody").find("tr");
	var password=$("#systemPassword").val();
	if (submitWay == "true") {
		for ( var i = 0; i < trs.size(); ++i) {
			var tr = $(trs.get(i));
			var formValue = tr.attr("formvalue");
			var cNo = tr.attr("cno");
			var tName = $(tr.children()[4]).html();
			var credit = $(tr.children()[2]).html();
			var courseName = $(tr.children()[1]).children("a").html();

			var itemNode = "<p id=\"item_"
					+ cNo
					+ "\"><span class=\"text-info\">"
					+ courseName
					+ " </span><span><img src=\"../images/loading_min.gif\"></span><span class=\"text-danger message\"> 提交中...</span></p>";
			$(".submit-items").append(itemNode);

			submitOnKeyCourse(password,cNo, tName, courseName, formValue,
					credit);

		}
	}else{
		startInterval=setInterval(function(){
			if(i>=trs.size()){
				if (startInterval != null) {
					clearInterval(startInterval);
					return;
				}
			}
			var tr = $(trs.get(i));
			var formValue = tr.attr("formvalue");
			var cNo = tr.attr("cno");
			var tName = $(tr.children()[4]).html();
			var credit = $(tr.children()[2]).html();
			var courseName = $(tr.children()[1]).children("a").html();

			var itemNode = "<p id=\"item_"
					+ cNo
					+ "\"><span class=\"text-info\">"
					+ courseName
					+ " </span><span><img src=\"../images/loading_min.gif\"></span><span class=\"text-danger message\"> 提交中...</span></p>";
			$(".submit-items").append(itemNode);
			i++;
			submitOnKeyCourse(password,cNo, tName, courseName, formValue,
					credit);
		}, 2000);
	}
}
function submitOnKeyCourse(password,cNo, tName, courseName, formValue, credit) {
	var button = $(".bt_begin");
	var retry=" <a class=\"text-info\" href=\"javascript:void(0)\" onclick=\"submitOnKeyCourse('"+password+"','"+cNo+"','"+tName+"','"+courseName+"','"+formValue+"','"+credit+"')\">重新提交</a>";
	$("#item_"+cNo).find(".message").html("提交中...");
	$.ajax({
		type : "POST",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		url : "../action/submitOneKeyCourse",
		data : {
			"password":password,
			"cNo" : cNo,
			"tName" : tName,
			"courseName" : courseName,
			"formValue" : formValue,
			"credit" : credit
		},
		error : function(msg) {

			addActionResult("服务器出错，请重试！"+retry);
			button.button('reset');
			return;

		},
		success : function(data) {
			if(data.indexOf("Request process time exceed th max limit")>=0){
				//请求超时..
				$("#item_"+cNo).find(".message").html("提交请求超时..."+retry);
				return;
			}
			if(data=="获取不到选课结果"){
				$("#item_"+cNo).find(".message").html("获取不到选课结果..."+retry);
				return;
			}
			$("#item_"+cNo).find(".message").html(data+retry);
		}
	});
}