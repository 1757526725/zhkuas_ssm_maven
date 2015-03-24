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

// 检测是不是已经登录了
function checkIsLogin(obj) {
	if (obj["error"]) {
		if (obj['error'] == 'unlogined') {
			setRemarkAction("enable");
			showAlert("消息", "请登录后操作！");
		}

		return false;
	}

	return true;
}
