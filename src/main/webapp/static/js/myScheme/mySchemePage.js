$(function() {
	MySchemePage.main();
});

var MySchemePage = (function() {
	var btn_save = $("#bt-saveAll");
	var btn_scan = $("#bt-scan-conflict");
	var btn_empty = $("#bt-deleteAll");
	var btn_add = $("#bt-addMore");
	var btn_oneKey = $("#bt-oneKey");
	var main = function() {
		_initPage();
		_initEvent();
	};

	var _initPage = function() {
		
	};

	var _initEvent = function() {
		btn_add.click(function() {
			location.href = domain+"/main/campus/course";
		});
		btn_empty.click(function() {
			_clearScheme();
		});
		btn_save.click(function() {
			_saveScheme();
		});
		btn_scan.click(function() {
			_scanConflict();
		});
		btn_oneKey.click(function() {
			OneKey.show();
			btn_oneKey.attr('disabled','true');
		});
		$("#mySchemeForm").on('click', 'a[actionCommand]', function() {
			var actioncommand = $(this).attr("actioncommand");
			if (actioncommand == 'moveUp') {
				_moveSchemeUp($(this));
				return;
			}

			if (actioncommand == 'editScheme') {
				_editScheme($(this));
				return;
			}
			if (actioncommand == 'deleteScheme') {
				_deleteScheme($(this));
				return;
			}

		});
	};
	var _saveScheme = function() {
		var button = btn_save;
		button.button("loading");
		$("#mySchemeForm").find("input[name='_method']").val("PUT");
		$.ajax({
			type : "POST",
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			url : domain+"main/user/curriculum/scheme",
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
				if(data.state==0){
					showAlert(data.error.error_description, "提交失败，请重试！");
					button.button('reset');
					return;
				}
				if (data.result == "success") {
					showAlert("消息", "保存成功", function() {
						location.reload();
					});
					return;
				}

			}
		});
	};
	var _clearScheme = function() {

		showConfirm("你确定要清空选课方案吗?", function() {

			btn_empty.button("loading");
			var data = {};
			data._method='delete';
			$.ajax({
				type : "POST",
				url : domain+"main/user/schemes",
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				data:data,
				error : function(msg) {
					showAlert("消息", "服务器出错，请联系管理员");
					btn_empty.button('reset');
					return;
				},
				success : function(data) {
					if (data == "") {
						showAlert("消息", "清空失败，请重试！");
						btn_empty.button('reset');
						return;
					}
					if (data == "clear success") {
						location.reload();
						return;
					}

					if (data == "login out time") {
						showAlert("消息", "登录超时,请重新登录！");
						btn_empty.button('reset');
						return;
					}

				}
			});

		});

	};

	var _deleteScheme = function(btn) {
		var schemeNode = btn.parent().parent();
		showConfirm("你确定要移除这门课程吗?", function() {
			var data = {};
			data._method='delete';
			$.ajax({
				type : "POST",
				url : domain+"main/user/curriculum/scheme/"+schemeNode.attr("msid"),
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				data:data,
				error : function(msg) {
					showAlert("消息", "服务器出错，请联系管理员");
					return;
				},
				success : function(data) {
					if (data == "") {
						showAlert("消息", "删除失败！请重试");
						return;
					}
					if (data.state == 0) {
						showAlert(data.error.error_description, "删除失败！请重试");
					}
					if (data.result == "success") {
						location.reload();
					}
				}
			});
		});

	};
	// 待修改 。。。。。 //TODO
	var _editScheme = function(btn) {
		var schemeNode = btn.parent().parent();
		var cNo = schemeNode.attr("cno");
		var campusId = $("input[name='campusId']").val();
		var termNo = $("input[name='termNo']").val();
		PublicCourse.showOptionDialog(cNo,campusId,termNo, function() {
			var newData = PublicCourse.getUpdateInfo();
			var timeTdNode = schemeNode.find("td").get(3);
			var teacherTdNode = schemeNode.find("td").get(4);
			timeTdNode.innerHTML = newData['schoolTime'];
			teacherTdNode.innerHTML = newData['displayValue'];
			_scanConflict();
		});
	};
	var _moveSchemeUp = function(btn) {
		var src_tr = btn.parent().parent();
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

		btn_save.removeClass("btn-success");
		btn_save.addClass("btn-danger");
		btn_save.removeAttr("disabled");
	};
	var _scanConflict = function() {
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
		$.ajax({
					type : "get",
					url : domain+"main/user/curriculum/scheme/check",
					async : true,
					success : function(data) {
						if (data.state == 0 &&( data.error.error_code == '-10007'||data.error.error_code == '-50010')) {
							var result = '<h4 class=\"text-info\">你未确认你的个人课表.无法进行冲突性检测 。<a href=\"schedule\" class=\"text-danger pull-right\">点击去确认</a></h4>';
							addErrorMessage(result, summaryNode);
							button.button("reset");
							_hideScanResultLoading();
							return;
						}else if (data.state == 0) {
							showAlert("消息", data.error.error_description);
							button.button("reset");
							$(".scan-result").hide();
							return;
						}
						if (data == '') {
							var result = "<span class=\"text-danger\">服务器错误，检测未完成</span>";
							addErrorMessage(result, summaryNode);
							button.button("reset");
							$(".scan-result").hide();
							return;
						}
						if (data.result == 'OK') {
							var result = "<span class=\"text-success\">没有冲突课程</span>";
							addErrorMessage(result, summaryNode);
							button.button("reset");
							_hideScanResultLoading();
							return;
						}
					
						for (index_cNo in data.result) {
							var message = '<span class="label label-danger pull-right flag-label">冲突</span>';
							var courseNodeTd = $($("[cNo='" + index_cNo + "']")
									.children().get(1));
							courseNodeTd.append(message);
							var result = "<span class=\"text-danger\">"
									+ courseNodeTd.children("a").html()
									+ "</span> 与"
									+ "<span class=\"text-info\">"
									+ obj[index_cNo] + "</span>冲突";
							addErrorMessage(result, summaryNode);
						}
						button.button("reset");
						_hideScanResultLoading();

					},
					error : function(msg) {
						addErrorMessage("服务器出错，请联系管理员", summaryNode);
						button.button('reset');
						return;
					}
				});
	};
	
	var _hideScanResultLoading=function() {
		var scan_resultNode = $(".scan-result");
		scan_resultNode.find(".initialText").hide();
		scan_resultNode.find(".loading").hide();
	};
	return {
		main : main
	};
})();