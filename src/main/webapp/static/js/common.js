//var redirect_uri="http://192.168.1.100:8080/Course_assistant/account/callback";
var redirect_uri = "http://zhkuas.duapp.com/account/callback";
var fun_page_reload = function() {
	location.reload();
};
var currentTermName="2013-2014第二学期";
var currentTermNo="20131";
$(function() {
	$(".messageAlert").hide();
	var remindInterval = setInterval(function() {
		getUnreadMention();
	}, 1000);
	var lastUnReadCount = "";
	$("#bt_close").click(function() {
		$(".messageAlert").hide();
		clearInterval(remindInterval);
	});
	getUnreadMention = function() {
		$.ajax({
			url : "../action/getCommentNotice",
			dataType : "html",
			success : function(data) {
				if (lastUnReadCount != data) {// 与上次不一样
					if (data == "login out time") {// session过期
						clearInterval(remindInterval);
						// showSecretAlert("登陆超时，请重新登陆！");
						// window.location.href = "index";
						return;
					}
					if (lastUnReadCount == "") {// 第一次调用后调到30秒
						clearInterval(remindInterval);
						remindInterval = setInterval(function() {
							getUnreadMention();
						}, 30000);// TODO 这里改变了时间间隔
					}
					lastUnReadCount = data;
					if (lastUnReadCount > 0) {
						$("#messageCount").html(lastUnReadCount);
						$(".messageAlert").show();
					}
				}
			},
			error : function() {
				clearInterval(remindInterval);
			}
		});
	};
});
$(document)
		.ready(
				function() {
					var bt_sina_login = $("#sina-login");
					bt_sina_login
							.click(function() {
								// alert("新浪微博登录功能未开通，敬请期待。");
								bt_sina_login.button('loading');
								window
										.open(
												'https://api.weibo.com/oauth2/authorize?client_id=3757150196&response_type=code&redirect_uri='
														+ redirect_uri, '_self');
							});

					// 回复按钮
					$(".bt-quick-reply")
							.click(
									function() {
										if ($(this).parent().siblings(
												".reply-area").length > 0) {
											removeReplyArea(this);
										} else {
											// 针对子评论
											var commentUserNode = $(this)
													.parent().parent()
													.siblings(".nickName");
											if (commentUserNode.length <= 0) { // 针对父级评论
												commentUserNode = $(this)
														.parent().parent()
														.parent().siblings(
																".user-info")
														.find(".nickName");
												if (commentUserNode.length <= 0)
													commentUserNode = $(this)
															.parent()
															.parent()
															.children(
																	".media-heading")
															.find(
																	".commentUserName");
											}
											if (isLogin == false) {
												showAlert("消息", "请登录后操作！");
												return;
											}
											showReplyInputArea(this, "回复 "
													+ commentUserNode.html()
													+ ":");
										}
									});

					
					
				});

function removeReplyArea(_this) {
	$(_this).parent().siblings(".reply-area").remove();
}

function enableBtSave(bt_save) {
	bt_save.removeClass("btn-success");
	bt_save.addClass("btn-danger");
	bt_save.removeAttr("disabled");
}
/**
 * 
 * @param cNo
 *            学号
 * @param fun
 *            成功后触发的函数
 */
function addMyScheme(cNo, fun) {
	var idval = "";
	var schoolTime = "";
	var button = $("#bt-scheme-ensure");
	var topBarInfoNode = $("#my_project").find("span");
	button.button("loading");
	try {
		var divCol = document.getElementsByTagName("input");
		for ( var i = 0; i < divCol.length; i++) {
			if (divCol[i].type == "radio" && divCol[i].checked) {
				if (idval == "") {
					idval = divCol[i].value.split("|")[2];
				}

				// 保存选课时间
				var ipTdNode = $(divCol[i]).parent();
				var ipTrNode = ipTdNode.parent();
				schoolTime += ipTdNode.prev().prev().html();
				for ( var j = 1; j < ipTdNode.attr("rowspan"); ++j) {
					var tdsOfNextTr = ipTrNode.next().children();
					schoolTime += $(tdsOfNextTr.get(5)).html();
					ipTrNode = ipTrNode.next();
				}
			}
		}

	} catch (e) {
		console.log(e);
	}
	if (schoolTime == "")
		schoolTime = "选课时间获取错误！";
	var displayValue = idval.split("@")[0];
	var formValue = idval.split("@")[1];
	var summaryNode = $("#dialog-summary");
	summaryNode.empty();
	$.ajax({
		type : "POST",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		url : "../action/addScheme",
		data : {
			"cNo" : cNo,
			"displayValue" : displayValue,
			"formValue" : formValue,
			"schoolTime" : schoolTime
		},
		error : function(msg) {

			dg_addErrorMassage("服务器出错，请联系管理员", summaryNode);
			button.button('reset');
			return;

		},
		success : function(data) {
			if (data == "") {
				dg_addErrorMassage("提交失败，请重试！", summaryNode);
				button.button('reset');
				return;
			}
			if (data == "update success") {

				$("#CourseChoose-dialog").modal("hide");
				showAlert("消息", "更新成功", fun);
				return;
			}
			if (data == "addScheme success") {
				if (topBarInfoNode.size() == 0) {
					var node = '<span class="badge pull-right">1</span>我的选课方案';
					$("#my_project").children("a").html(node);
				} else {
					topBarInfoNode.html(parseInt(topBarInfoNode.html()) + 1);
				}
				$("#CourseChoose-dialog").modal("hide");
				showAlert("消息", "添加成功", fun);
				return;
			}

		}
	});

}

function showAlert(title, message,fun,model) {
	$("#alert-dialog").find("#myModalLabel").html(title);
	$("#alert-dialog").find(".modal-body").html(message);
	$("#alert-dialog").modal("show");
	if (typeof (fun) == "function") {

		$("#alert-dialog").find("button").one("click", fun);
	}
	if(model=="undefined"||model==null){
		setTimeout(function() {
			$("#alert-dialog").modal("hide");
			if (typeof (fun) == "function") {
				fun();
			}
		}, 4000);
	}
}
function refalshPageInfo() {
	var cNo = $("#cNo").html();
	$.get("../action/remark", {
		"cNo" : cNo,
		"action" : "getRemark"
	}, function(data) {
		if (data == '') {
			return;
		}
		var obj = eval('(' + data + ')');

		if (obj['remarkItem'] == 1) {
			refalshRemarkInfo(1, $("#remark_good").children().last().html());
		} else if (obj['remarkItem'] == 2) {
			refalshRemarkInfo(2, $("#remark_bad").children().last().html());
		}
	});
}

function refalshRemarkInfo(item, count) {

	var bt_good = $("#remark_good");
	var bt_bad = $("#remark_bad");
	if (item == 1) {
		// 获取赞的按钮
		bt_good.children().last().html("已赞 " + count);
		bt_good.addClass("disabled");
		bt_bad.addClass("disabled");
	}

	if (item == 2) {
		// 获取赞的按钮
		bt_bad.children().last().html("已踩 " + count);
		bt_bad.addClass("disabled");
		bt_good.addClass("disabled");
	}
}
/**
 * 
 * @param email
 *            邮箱
 * @param sno
 *            学号
 * @param password
 *            教务网密码
 * @param summaryNode
 *            错误信息显示节点
 * @param nodeToappend
 *            教务信息显示节点
 */
function bindAccount(url, sno, password,vCode, summaryNode, nodeToappend, nodeToHide,
		messageNode, button) {
	// 请求服务器，获取绑定信息

	$.ajax({
		type : "POST",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		url : url,
		async : true,
		data : {
			"action" : "bind",
			"password" : password,
			"sno" : sno,
			"vCode":vCode
		},
		error : function(msg) {

			dg_addErrorMassage("服务器出错，请联系管理员", summaryNode);
			button.button('reset');
			return;

		},
		success : function(data) {
			if (data == '') {
				dg_addErrorMassage("服务器出错，请联系管理员", summaryNode);
				button.button('reset');
				return;
			}

			var obj = eval('(' + decodeURIComponent(data) + ')');
			if (obj['error'] == '00003') {
				dg_addErrorMassage("学号或密码不正确", summaryNode);
				button.button('reset');
				return;
			}
			if (obj['error'] == '00002') {
				dg_addErrorMassage("教务网阻塞，暂时无法通信", summaryNode);
				button.button('reset');
				return;
			}
			if (obj['error'] == '-00001') {
				dg_addErrorMassage("未知错误，绑定失败", summaryNode);
				button.button('reset');
				return;
			}
			if (obj['error'] == '-00002') {
				dg_addErrorMassage("验证码错误", summaryNode);
				button.button('reset');
				return;
			}
			if (obj['success']) {
				// 绑定成功获取数据
				var tableNode = $(obj['myInfo']);
				nodeToappend.empty();
				tableNode.appendTo(nodeToappend);
				nodeToappend.show();
				nodeToHide.hide();
				if (messageNode) {
					messageNode.html("(已绑定)");
				}
				location.reload();
				return;
			}
		}
	});
}
function dg_addErrorMassage(massage, summaryNode) {
	var errorMassageLi = $('<p>' + massage + '</p>');
	errorMassageLi.appendTo(summaryNode);
	summaryNode.show();
}

function isChinese(obj) {
	var reg = /[^\u4e00-\u9fa5]/;
	if (reg.test(obj))
		return false;
	return true;
}
function isEmail(obj) {
	reg = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
	if (reg.test(obj))
		return true;
	return false;
}
function checkPassword(obj) {

	if (obj.length >= 6 && obj.length <= 16)
		return true;
	return false;
}

function isSno(obj) {
	var reg = /^([0-9]{12})$/;
	if (reg.test(obj))
		return true;
	return false;
}


/*******************************************************************************
 * comfirm
 * 
 * @param tip
 * @param confirmCallback
 * @returns
 */
function showMyConfirm(tip, confirmCallback) {

	var confirmStr = "<div class=\"modal fade\" id=\"my-confirm\" tabindex=\"-1\" role=\"dialog\"";
	confirmStr += "aria-labelledby=\"myModalLabel\" aria-hidden=\"true\" data-backdrop=\"static\">";
	confirmStr += "	<div class=\"modal-dialog\">";
	confirmStr += "<div class=\"modal-content\">";
	confirmStr += "<div class=\"modal-header\">";
	confirmStr += "<button type=\"button\" class=\"close\" data-dismiss=\"modal\"";
	confirmStr += "aria-hidden=\"true\">&times;</button>";
	confirmStr += "	<h4 class=\"modal-title\" id=\"myModalLabel\">确认</h4>";
	confirmStr += "	</div>";
	confirmStr += "<div class=\"modal-body\">" + tip + "</div>";
	confirmStr += "	<div class=\"modal-footer\">";
	confirmStr += "	<button type=\"button\" class=\"btn btn-primary ensure\">确定</button>";
	confirmStr += "	<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">取消</button>";
	confirmStr += "	</div>";
	confirmStr += "	</div>";
	confirmStr += "	</div>";
	confirmStr += "	</div>";

	var confirm = $(confirmStr);
	confirm.on("click", ".ensure", function(e) {
		$("#my-confirm").modal("hide");
		confirmCallback();
	});
	confirm.on('hidden.bs.modal', function() {
		confirm.remove();
	});
	$("body").append(confirm);
	$("#my-confirm").modal("show");
	return confirm;
}

function deleteComment(pccid) {
	showMyConfirm("你确定删除此条评论吗?", function() {
		$.get("../action/deleteComment", {
			"pccid" : pccid
		}, function(data) {
			if (data == "") {
				showAlert("错误", "删除失败，请刷新页面重试！");
				return;
			}

			if (data == "comment delete success") {
				showAlert("消息", "删除成功", fun_page_reload);
				return;
			}
			if (data == "less permission") {
				showAlert("消息", "没有权限");
				return;
			}
		});

	});
}

//
function showReplyInputArea(_this, replyUserName) {
	var replyAreaNode = $('<div class="reply-area clearfix"><textarea placeholder="'
			+ replyUserName
			+ '" name="reply-content" class="texta-reply form-control"></textarea><button type="submit" data-loading-text="发表中..." class="btn btn-warning btn-sm pull-right">评论</button></div>');

	$(_this).parent().after(replyAreaNode);

	// 评论按钮注册事件

	var bt_comment = replyAreaNode.find(".btn");
	var textarea = replyAreaNode.find("textarea");
	bt_comment.click(function() {
		var content = textarea.val();
		if (content == "") {
			showAlert("错误", "回复内容不能为空");
			return;
		}
		var ppcid = replyAreaNode.parent().parent().attr("pccid")
				|| replyAreaNode.parent().parent().parent().attr("pccid");
		var parent_pccid = replyAreaNode.parent().parent().parent().parent()
				.parent().attr("pccid")
				|| replyAreaNode.parent().parent().parent().attr("pccid")
				|| replyAreaNode.parent().parent().attr("parent_pccid");
		var reply_uid = replyAreaNode.parent().siblings(".nickName")
				.attr("uid")
				|| replyAreaNode.parent().parent().siblings(".user-info").find(
						".nickName").attr("uid")
				|| replyAreaNode.parent().find(".commentUserName").attr("uid");
		var cNo = $("#cNo").html();
		var actionItem = "";
		if (cNo == undefined) {
			cNo = replyAreaNode.parent().parent().attr("cNo");
			actionItem = "directReply";
		} else {
			actionItem = "replyComment";
		}
		createComment(actionItem, cNo, content, parent_pccid, reply_uid);
	});
}

function createComment(actionItem, cNo, content, parent_pccid, reply_uid) {

	$.ajax({
		type : "POST",
		url : "../action/comment",
		async : false,
		data : {
			"action" : actionItem,
			"cNo" : cNo,
			"content" : content,
			"parent_pccid" : parent_pccid,
			"reply_uid" : reply_uid
		},
		success : function(data) {
			if (data == '') {
				showAlert("消息", "服务器出错");
				return;
			}
			var obj = eval('(' + data + ')');

			if (checkIsLogin(obj)) {
				if (obj['success']) {
					if (actionItem == "directReply") {
						location.href = "comment?tab=myComment";
						return;
					}
					location.reload();
				}
			}
		}
	});

}



