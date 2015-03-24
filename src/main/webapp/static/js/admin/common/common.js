
// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function(fmt) { // author: meizz
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};
// 获取模版
function getTemplate(id) {
	var new_tpl = $("#tpl_" + id).clone(false);
	new_tpl.attr("id", id);
	return new_tpl;
}
// 显示消息对话框
function showAlert(title, message, time) {
	var tpl = getTemplate("myAlert");
	var dist_alert = $(tpl.get(0).outerHTML.replace("@{title}", title).replace(
			"@{message}", message));
	$("body").append(dist_alert);
	dist_alert.modal("show");
	dist_alert.on('hidden.bs.modal', function(e) {
		dist_alert.remove();
	});
	if (time != null) {
		setTimeOut(function() {
			dist_alert.modal("hide");
		}, time);
	}
}
// 显示确定对话框
function showConfirm(title, message, callback) {
	var tpl = getTemplate("myConfirm");
	var dist_confirm = $(tpl.get(0).outerHTML.replace("@{title}", title)
			.replace("@{message}", message));
	$("body").append(dist_confirm);
	dist_confirm.modal("show");
	dist_confirm.on('hidden.bs.modal', function(e) {
		dist_confirm.remove();
	});
	if (typeof callback === "function") {
		dist_confirm.on("click", ".ensure", callback);
	}
}
// 消息推送封装
function addGritter(title, text, image, time) {

	var imageUrl = SITE_HOST+"/static/img/gritter/" + image + ".png";
	$.gritter.add({
		title : title,
		text : text,
		image : imageUrl,
		time : time == null ? 400000 : time
	});
}
// 推送后台消息
function showMessage(title, text, time) {
	addGritter(title, text, "message", time);
}
// 推送操作提示信息
function showInfo(title, text, time) {
	addGritter(title, text, "info", time);
}
// 推送操作错误信息
function showError(title, text, time) {
	addGritter(title, text, "error", time);
}
// 推送操作警告信息
function showWarring(title, text, time) {
	addGritter(title, text, "warring", time);
}
