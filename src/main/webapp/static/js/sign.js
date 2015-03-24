var signupDiv = $("#signup");
var signinDiv = $("#signin");

$(document).ready(

		function() {
			$("[href='#signin']").click(function() {
				showSignInDiv();
			});

			$("[href='#signup']").click(function() {
				showSignUpDiv();
			});

			// 添加注册表达提交事件

			var signupFm = $("#sign-form-1");

			signupFm.submit(function(evt) {
				if (evt) {
					evt.preventDefault();
				} else {
					evt.returnValue = false;
				}

				// 验证表单
				var nickNameNode = $("[name='nickName']");
				var summaryNode = $("#sign-form-1 > #summary");
				var emailIpuNode = signupFm.find("[name='email']");
				var pwdIpuNode = signupFm.find("[name='password']");
				var cfmIpuNode = signupFm.find("[name='confirm']");
				var isOk = true;
				summaryNode.empty();
				summaryNode.hide();
				if (nickNameNode.val() == '') {
					addErrorMassage("昵称不能为空", summaryNode);
					isOk = false;
				}
				if (emailIpuNode.val() == '') {
					addErrorMassage("邮箱未填写", summaryNode);
					isOk = false;
				} else if (!isEmail(emailIpuNode.val())) {
					addErrorMassage("请输入正确的邮箱", summaryNode);
					isOk = false;
				}
				if (pwdIpuNode.val() == '') {
					addErrorMassage("密码未填写", summaryNode);
					isOk = false;
				} else if (!checkPassword(pwdIpuNode.val())) {
					addErrorMassage("密码要求6-16位", summaryNode);
					isOk = false;
				}
				if (cfmIpuNode.val() == '') {
					addErrorMassage("确认密码未填写", summaryNode);
					isOk = false;
				} else if (cfmIpuNode.val() != pwdIpuNode.val()) {
					addErrorMassage("两次输入的密码不一致", summaryNode);
					isOk = false;
				}

				if (isOk) {
					register(nickNameNode.val(), emailIpuNode.val(), pwdIpuNode
							.val(), cfmIpuNode.val());
				}

			});
			// showBindView();
		});
function showSignUpDiv() {
	signinDiv.hide();
	signupDiv.show();
}
function showSignInDiv() {
	signupDiv.hide();
	signinDiv.show();

	var siginForm = $('.view-signin >.zu-side-login-box');
	siginForm.submit(function(evt) {
		if (evt) {
			evt.preventDefault();
		} else {
			evt.returnValue = false;
		}
	});
	var siginEmail = siginForm.find('[name=email]');
	var siginPassword = siginForm.find('[name=password]');
	var summaryNode = $('#sigin_summary');

	$('.sign-button').click(function() {
		summaryNode.hide();
		summaryNode.empty();

		var isOk = true;
		if (siginEmail.val() == '') {
			addErrorMassage("邮箱不能为空", summaryNode);
			isOk = false;
		} else if (!isEmail(siginEmail.val())) {
			addErrorMassage("请输入正确的邮箱", summaryNode);
			isOk = false;
		}

		if (siginPassword.val() == '') {
			isOk = false;
			addErrorMassage("密码不能为空", summaryNode);
		}
		if (isOk) {
			
			$(this).html("登录中...");
			
			$.ajax({
				type : "POST",
				url : "./login",
				async : false,
				data : siginForm.serialize(),
				success : function(data) {
					if (data == '') {
						addErrorMassage("服务器出错，请联系管理员", summaryNode);
						return;
					}
					var obj = eval('(' + data + ')');
					if (obj["error"]) {
						if (obj['error'] == '00005') {
							addErrorMassage("邮箱或密码不正确", summaryNode);
							$(this).html("登录");
							return;
						}
					}
					
					if(obj["success"]){
						location.href ="./main/index";
					}
					
				},
				error:function(msg){
					$(this).html("登录");
				}
			});
		}
	});
}
function register(nickName, email, password, comfirm) {

	$.ajax({
		type : "POST",
		url : "./register/account",
		async : false,
		data : {
			"action" : "register",

			"nickName" : nickName,
			"email" : email,
			"password" : password,
			"comfirm" : comfirm
		},
		success : regCallBack
	});
	/*
	 * $.post("./register/account", { "realName" : realName, "email" : email,
	 * "password" : password, "comfirm" : comfirm }, regCallBack);
	 */
}

function regCallBack(data) {
	var obj = eval('(' + data + ')');
	var summaryNode = $("#sign-form-1 > #summary");
	if (obj["error"]) {
		// 邮箱已经被注册了，显示提示信息
		var errorMassageLi = $('<li><i class="icon-sign icon-sign-error"></i>该邮箱已经注册,</li>');
		var quickSignLink = $('<a href="javascript:;" class="zg-link-litblue-normal js-signin">直接登录</a>');
		// 添加事件
		quickSignLink.click(function() {
			var signUpEmailnode = $("#sign-form-1").find("[name='email']");
			var signinEmailnode = $(".zu-side-login-box")
					.find("[name='email']");
			signinEmailnode.val(signUpEmailnode.val());
			showSignInDiv();
		});
		quickSignLink.appendTo(errorMassageLi);
		errorMassageLi.appendTo(summaryNode);
		summaryNode.show();
	} else if (obj["success"]) {
		// 如果注册成功
		showBindView();
	}
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

function addErrorMassage(massage, summaryNode) {

	var errorMassageLi = $('<li><i class="icon-sign icon-sign-error"></i>'
			+ massage + '</li>');
	errorMassageLi.appendTo(summaryNode);
	summaryNode.show();
}

// 显示绑定面板

function showBindView() {
	// 隐藏掉注册登录面板
	var signupView = $("#signup");
	var signinView = $("#signin");
	signupView.hide();
	signinView.hide();

	// 修改body 样式
	$("body").addClass("no-auth regflow bg-signup");

	// 显示绑定界面

	var bindView = $(".view-info");
	bindView.show();

	// 隐藏footer
	$("#footer").hide();

	// 配置表单
	var bindForm = $("#bind-form");
	bindForm.submit(function(evt) {
		if (evt) {
			evt.preventDefault();
		} else {
			evt.returnValue = false;
		}
	});
	// 绑定账户按钮 TODO
	$("#bt-bindAccount").click(function() {
		// 验证表单
		var ipu_sno_node = $("[name='sNo']");
		var ipu_sno_pwd = $("[name='b_password']");
		var summaryNode = $("#bind-summary");
		summaryNode.hide();
		summaryNode.empty();
		var isOk = true;
		if (ipu_sno_node.val() == '') {
			addErrorMassage("学号未填写", summaryNode);
			isOk = false;
		} else if (!isSno(ipu_sno_node.val())) {
			addErrorMassage("请输入正确的学号", summaryNode);
			isOk = false;
		}
		if (ipu_sno_pwd.val() == '') {
			addErrorMassage("密码未填写", summaryNode);
			isOk = false;
		}

		if (isOk) {
			var info_view = $("#jiaowu-Info");
			bindAccount("./register/account",ipu_sno_node.val(), ipu_sno_pwd.val(), summaryNode,info_view,$("#jiaowuAccount"),null,$(this));
			//隐藏输入界面
		}
	});

	// 完成注册按钮 TODO

	$("#bt-reg-end").click(function() {
		location.href ="./main/index";
	});
}


