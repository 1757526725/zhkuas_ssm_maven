$(function() {
	MySchedulePage.main();
});

var MySchedulePage = (function() {
	var btn_save = $("#bt-saveSchedule");
	var btn_selectAll = $("#bt-selectAll");
	var btn_inverse = $("#bt-inverse");
	var btn_edit = $("#bt-edit");
	var frm_mySchuedule = $("#mySchueduleForm");
	var main = function() {
		_initPage();
		_initEvent();
	};

	var _initPage = function() {

	};

	var _initEvent = function() {
		btn_save.click(function() {
			_saveSchedule();
		});
		btn_selectAll.click(function() {
			var chkNodes = frm_mySchuedule.find("input[name='cNo']");
			var flag = false;
			for ( var i = 0; i < chkNodes.size(); ++i) {
				if (!$(chkNodes[i]).attr("disabled") && !chkNodes[i].checked) {
					chkNodes[i].checked = true;
					flag = true;
				}
			}
			if (flag) {
				_enableBtSave();
				frm_mySchuedule.find(".panel-title").html("个人基础课程表--未保存");
			}

		});
		btn_inverse.click(function() {
			var chkNodes = frm_mySchuedule.find("input[name='cNo']");
			var flag = false;
			for ( var i = 0; i < chkNodes.size(); ++i) {
				if (!$(chkNodes[i]).attr("disabled")) {
					chkNodes[i].checked = !chkNodes[i].checked;
					flag = true;
				}
			}
			if (flag) {
				_enableBtSave();
				frm_mySchuedule.find(".panel-title").html("个人基础课程表--未保存");
			}
		});
		btn_edit.click(function() {
			var isCancel = false;
			var btNameNode = btn_edit.find(".bt-name");
			if (btNameNode.html().indexOf("取消") >= 0) {
				isCancel = true;
				btNameNode.html(" 修改");
			} else {
				btNameNode.html(" 取消");
			}
			var chkNodes = frm_mySchuedule.find("input[name='cNo']");

			for ( var i = 0; i < chkNodes.size(); ++i) {
				if (isCancel) {
					$(chkNodes[i]).attr("disabled", "disabled");
				} else {
					$(chkNodes[i]).removeAttr("disabled");
				}
			}
		});

		// 保存按钮的激活

		frm_mySchuedule.on('click', "[name='cNo']", function() {
			_enableBtSave();
			frm_mySchuedule.find(".panel-title").html("个人基础课程表--未保存");
		});
	};
	var _enableBtSave = function() {
		btn_save.removeClass("btn-success");
		btn_save.addClass("btn-danger");
		btn_save.removeAttr("disabled");
	};
	
	var _updatePage =function(){
		//保存按钮不可用
		btn_save.removeClass("btn-danger");
		btn_save.addClass("btn-success");
		btn_save.addAttr("disabled");
		//选择控件不可用
		
	};
	var _saveSchedule = function() {
		var chkNodes = frm_mySchuedule.find("input[name='cNo']");
		var flag = false;
		for ( var i = 0; i < chkNodes.size(); ++i) {
			if (chkNodes[i].checked) {
				flag = true;
				break;
			}
		}
		if (!flag) {
			showAlert("错误", "你未选中任何课程！");
			return;
		}

		showConfirm(
				"你确定保存您的基础课程吗？",
				function() {
					var button = btn_save;
					button.button("loading");
					$.ajax({
								type : "POST",
								contentType : "application/x-www-form-urlencoded; charset=UTF-8",
								url : domain+"main/user/curriculum/schedule",
								data : $("#mySchueduleForm").serialize(),
								error : function(msg) {
									showAlert("消息", "服务器出错，请联系管理员");
									button.button('reset');
									return;

								},
								success : function(data) {
									if (data.state==0&&data.error.code!="-10007") {
										showAlert("错误", "登录超时,请重新登录！");
										button.button('reset');
										return;
									}else if (data.state==0&&data.error.code=="-10007") {
										showAlert(
												"提示",
												'<p>1.你可能未绑定教务网账户 <a href="passport?tab=basic">去绑定</a></p><p>2.你可能未绑定上课的校区。 <a href="passport?tab=basic">去绑定校区</a></p>');
										button.button('reset');
										return;
									}
									if (data.state==1) {
										showAlert("消息", "保存成功", function() {
											location.reload();
										});
										return;
									}

								}
							});

				});
	};
	return {
		main : main
	};
})();