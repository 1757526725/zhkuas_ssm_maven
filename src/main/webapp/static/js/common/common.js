var html_alert_dialog = "<div class=\"modal fade\" id=\"alert-dialog\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">"
		+ "<div class=\"modal-dialog\">"
		+ "<div class=\"modal-content\">"
		+ "<div class=\"modal-header\">"
		+ "<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">×</button>"
		+ "<h4 class=\"modal-title\" id=\"myModalLabel\">{消息}</h4>"
		+ "</div>"
		+ "<div class=\"modal-body\">评论内容不能为空</div>"
		+ "<div class=\"modal-footer\">"
		+ "<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">Close</button>"
		+ "</div>" + "</div>" + "</div>" + "</div>";
var html_confirm_dialog = "<div class=\"modal fade\" id=\"my-confirm\" tabindex=\"-1\" role=\"dialog\""
		+ "aria-labelledby=\"myModalLabel\" aria-hidden=\"true\" data-backdrop=\"static\">"
		+ "	<div class=\"modal-dialog\">"
		+ "<div class=\"modal-content\">"
		+ "<div class=\"modal-header\">"
		+ "<button type=\"button\" class=\"close\" data-dismiss=\"modal\""
		+ "aria-hidden=\"true\">&times;</button>"
		+ "	<h4 class=\"modal-title\" id=\"myModalLabel\">确认</h4>"
		+ "	</div>"
		+ "<div class=\"modal-body\">{tip}</div>"
		+ "	<div class=\"modal-footer\">"
		+ "	<button type=\"button\" class=\"btn btn-primary ensure\">确定</button>"
		+ "	<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">取消</button>"
		+ "	</div>" + "	</div>" + "	</div>" + "	</div>";
$(document).ready(function() {
					var bt_sina_login = $("#sina-login");
					bt_sina_login
							.click(function() {
								// alert("新浪微博登录功能未开通，敬请期待。");
								bt_sina_login.button('loading');
								window.open(authoHost+'/weibo/login?service='+domain, '_self');
							});
});
function deleteComment(pccid) {
	showConfirm("你确定删除此条评论吗?", function() {
		$.post(domain+"main/ws/user/course/comment/"+pccid,{"_method":"delete"}, function(data) {
			if (data == "") {
				showAlert("错误", "删除失败，请刷新页面重试！");
				return;
			}
			if(data.state==1){
				showAlert("消息", "删除成功", function(){
					location.reload();
				});
				return;
			}
			if(data.state==0){
				showAlert(data.error.error_description);
				return;
			}
		});

	});
}
function createComment(mode, cNo, content, parent_pccid, reply_uid) {
	$.ajax({
		type : "POST",
		url : domain+"main/ws/user/course/comment",
		async : false,
		data : {
			"mode" : mode,
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
			if(data.state==0){
				showAlert(data.error.error_description);
				return;
			}
			if(data.state==1){
				if (mode == "directReply") {
					location.href = domain+"/main/user/comment/receive";
					return;
				}
				location.reload();
			}
		}
	});

}
function showAlert(title, message, endCall, model) {

	var alert_dialog = $("#alert-dialog");
	var isCalled = false;
	if (alert_dialog.size() <= 0) {
		alert_dialog = $(html_alert_dialog);
		$("body").append(alert_dialog);
	}
	$("#alert-dialog").find("#myModalLabel").html(title);
	$("#alert-dialog").find(".modal-body").html(message);
	$("#alert-dialog").modal("show");
	if (typeof (endCall) == "function") {
		$("#alert-dialog").find("button").one("click", function() {
			isCalled = true;
			endCall();
		});
	}
	if (model == "undefined" || model == null) {
		setTimeout(function() {
			if (!isCalled) {
				$("#alert-dialog").modal("hide");
				if (typeof (endCall) == "function") {
					endCall();
				}
			}
		}, 4000);
	}
}

function showConfirm(tip, callBack) {
	var confirm = $(html_confirm_dialog.replace("\{tip\}", tip));
	confirm.on("click", ".ensure", function(e) {
		$("#my-confirm").modal("hide");
		callBack();
	});
	confirm.on('hidden.bs.modal', function() {
		confirm.remove();
	});
	$("body").append(confirm);
	$("#my-confirm").modal("show");
	return confirm;
}

function addErrorMessage(message, summary_node) {
	var errorMassage = $('<p>' + message + '</p>');
	errorMassage.appendTo(summary_node);
	summary_node.show();
}
function changeValidateCode(_this) {
	_this.src = domain+"/network/validateCode?t=" + (new Date()).getTime();
}
var HTTP = {
	del : function(url, data, loading, reset, success, summaryNode) {
			data._method='delete';
			HTTP.post(url, data, loading, reset, success, summaryNode);
	},
	put : function(url, data, loading, reset, success, summaryNode) {
			data._method='put';
			HTTP.post(url, data, loading, reset, success, summaryNode);
	},
	post : function(url, data, loading, reset, success, summaryNode) {
		loading();
		$.ajax({
			'type' : "POST",
			'contentType' : "application/x-www-form-urlencoded; charset=UTF-8",
			'url' : url,
			'async' : true,
			'data' : data,
			error : function(msg) {
				if (summaryNode != null) {
					addErrorMessage("服务器出错，教务网抽风了，连接不上", summaryNode);
				} else {
					showAlert("错误", "服务器出错，教务网抽风了，连接不上", function() {
					}, 'static');
				}

				reset();
				return;
			},
			success : function(data) {
				try{
					if(data.state == 0){
						addErrorMessage(obj.error.error_description, summaryNode);
						reset();
						return ;
					}
				}catch(e){
					addErrorMessage("服务器返回的数据不合法，请重试!", summaryNode);
				}
				success(data);
				reset();
			}
		});

	},

	get : function(url, loading, reset, success, summaryNode) {
		loading();
		$.ajax({
			'type' : "GET",
			'url' : url,
			error : function(msg) {
				if (summaryNode != null) {
					addErrorMessage("服务器出错，教务网抽风了，连接不上", summaryNode);
				} else {
					showAlert("错误", "服务器出错，教务网抽风了，连接不上", function() {
					}, 'static');
				}
				reset();
				return;
			},
			success : function(data) {
				try{
					if(data.state == 0){
						addErrorMessage(obj.error.error_description, summaryNode);
						reset();
						return ;
					}
				}catch(e){
					addErrorMessage("服务器返回的数据不合法，请重试!", summaryNode);
				}
				success(data);
				reset();
			}
		});
	}
};
/**
 * 去除空格
 * 
 * @returns
 */
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
/*******************************************************************************
 * 获取url参数值
 * 
 * @returns {Object}
 */
function getRequest() {
	var url = location.search; // 获取url中"?"符后的字串
	var params = new Object();
	if (url.indexOf("?") != -1) {
		var str = url.substr(1);
		strs = str.split("&");
		for ( var i = 0; i < strs.length; i++) {
			params[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
		}
	}
	return params;
}

/**
 * 分解参数
 * 
 * @param params
 * @returns {Array}
 */
function getActionData(params) {
	var map = new Array(); // 定义一数组
	var first = new Array(); // 定义一数组
	first = params.split(";"); // 字符分割
	var second = new Array();
	for ( var i = 0; i < first.length; ++i) {
		second = first[i].split(":");
		map[second[0]] = second[1];
	}
	return map;
}

function addActionResult(resultNode, message, text_class, fontSize) {
	var action_result = resultNode || $(".result_text");
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


