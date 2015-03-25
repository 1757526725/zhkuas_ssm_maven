$(function(){
	CourseDetailPage.main();
});

var CourseDetailPage = (function(){
	var _btn_remark_good = $("#remark_good");
	var _btn_remark_bad = $("#remark_bad");
	var remarkItem = {good : 1,bad : 2};
	var main = function(){
		_initPage();
		_initEvent();
	};
	var _initPage = function(){
		
	};
	var _initEvent = function(){

	};
	
	var _remark = function(cNo,item){
		_setRemarkBtnStatus("disabled");
		$.get(domain+"/main/user/course/remark", {
			"cNo" : cNo,
			"remarkItem" : item
		}, callBack);

		function callBack(data) {
			if (data == '') {
				_setRemarkBtnStatus("enable");
				alert("服务器错误");
				return;
			}

			var obj = eval('(' + data + ')');

			if (checkIsLogin(obj)) {

				if (obj["count"]) {
					refalshRemarkInfo(item, obj["count"]);
				}
			}
		}
	}
	function _setRemarkBtnStatus(status) {
		var bt_good = $("#remark_good");
		var bt_bad = $("#remark_bad");
		if (status == "disabled") {
			bt_good.addClass("disabled");
			bt_bad.addClass("disabled");
			return;
		}
		if (status == "enable") {
			bt_good.removeClass("disabled");
			bt_bad.removeClass("disabled");
			return;
		}
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
	return {
		main: main
	};
})();