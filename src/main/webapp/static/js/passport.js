$(function() {
	PassportPage.main();
});

var PassportPage = (function() {
	var _iptSno = $("#academy_sno");
	var _iptPassword = $("#academy_pwd");
	var _iptVcode = $("#academy_code");
	var _btnBind = $("#bind_ensure");
	var main = function() {
		_initPage();
		_initEvent();
	};

	var _initPage = function() {

	};

	var _initEvent = function() {
		$("#bind_ensure").click(function() {
			var sNo = _iptSno.val();
			var password = _iptPassword.val();
			var vCode = _iptVcode.val();
			var isOk = true;
			var summaryNode = $("#bind-summary");
			summaryNode.hide();
			summaryNode.empty();
			var isOk = true;
			if (sNo == '') {
				addErrorMessage("学号未填写", summaryNode);
				isOk = false;
			} else if (!isSno(sNo)) {
				addErrorMessage("请输入正确的学号", summaryNode);
				isOk = false;
			}
			if (password == '') {
				addErrorMessage("密码未填写", summaryNode);
				isOk = false;
			}
			if (vCode == '') {
				addErrorMessage("验证码未填写", summaryNode);
				isOk = false;
			}

			if (isOk) {
				_bindAccount();
			}
		});
		
		$(".compus").click(function(){
			_changeCompus(this);
		});
		
		$("#bt-ps-save").click(function(){
			_saveUserInfo();
		});
	};

	var _bindAccount = function() {
		var _url = domain+"main/user/bind";
		var _data = {
			"password" : _iptPassword.val(),
			"sno" : _iptSno.val(),
			"vCode" : _iptVcode.val()
		};
		var _loading = function() {
			_btnBind.button("loading");
		};
		var _reset = function() {
			_btnBind.button("reset");
		};
		var _summaryNode = $("#bind-summary");
		var _success = function(data) {
			if (data == '') {
				addErrorMessage("服务器出错，请联系管理员", _summaryNode);
				_reset();
				return;
			}	
			if (data.state==1) {
				// 绑定成功获取数据
				location.reload();
				return;
			}
		};
		HTTP.post(_url, _data, _loading, _reset, _success, _summaryNode);
	};
	
	var _saveUserInfo = function() {
		
		var url=domain+"main/user";
		var data={
				"nickName" : $("#nickName").val(),
				"description" : $("#description").val()
			};
		var loading=function(){
			$("#bt-ps-save").button('loading');
		};
		var reset=function(){
			$("#bt-ps-save").button('reset');
		};
		var success=function(data) {
        	if("success"==data.result){
				showAlert("消息","保存成功！",function(){
					location.reload();
				});
				
			}
			if(data==""){
				showAlert("消息","保存失败");
			}
			reset();
        };
		HTTP.post(url, data, loading, reset, success, null);
	};

	return {
		main : main
	}
})();