$(document).ready(function() {
	$("#bind_ensure").click(function() {
		
		var sNo = $("#bind_sNo").val();
		var password = $("#bind_password").val();
		var vCode = $("#bind_code").val();
		var isOk=true;
		var summaryNode = $("#bind-summary");
		summaryNode.hide();
		summaryNode.empty();
		var isOk = true;
		if (sNo == '') {
			dg_addErrorMassage("学号未填写", summaryNode);
			isOk = false;
		} else if (!isSno(sNo)) {
			dg_addErrorMassage("请输入正确的学号", summaryNode);
			isOk = false;
		}
		if (password == '') {
			dg_addErrorMassage("密码未填写", summaryNode);
			isOk = false;
		}
		if (vCode == '') {
			dg_addErrorMassage("验证码未填写", summaryNode);
			isOk = false;
		}

		if (isOk) {
			$("#bind_ensure").button('loading');
			var info_view = $("<div class=\"table-responsive\"></div>");
			$("#replaceNode").replaceWith(info_view);
			var messageNode=$("#basicMsg").find("small");
			bindAccount("../action/bindAccount",sNo, password,vCode, summaryNode,info_view,$(".bindForm"),messageNode,$(this)) ;// 隐藏输入界面
			
			//	$("#bind_ensure").button('reset');
			
		}
	});
	
	$("#bt-ps-save").click(function(){
		$("#bt-ps-save").button('loading');
		$.ajax({
			type : "POST",
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			url : "../action/updateUser",
			async : true,
			data : {
				"nickName" : $("#nickName").val(),
				"description" : $("#description").val()
			},
			error: function (msg) {

				addErrorMassage("服务器出错，请联系管理员", summaryNode);
				$("#bt-ps-save").button('reset');
				return ;

	        },
	        success : function(data) {
	        	if("success"==data){
					showAlert("消息","保存成功！",function(){
						location.reload();
					});
					$("#bt-ps-save").button('reset');
				}
				if(data==""){
					showAlert("消息","保存失败");
					$("#bt-ps-save").button('reset');
				}
	        
	        	}
	        }
	        );
		
	});

});

function changeCompus(_this){
	actionNode =$(_this);
	buttonNode=$("#compusName");
	if(actionNode.html()!=buttonNode.html()){
		var flag =confirm("你确定将"+actionNode.html()+"设置为你的默认校区吗？");
	
		if(flag){
			buttonNode.html(actionNode.html());
			$.get("../action/bindCompus", {
				"compusId" : actionNode.attr("compusId")
			}, function(data){
				if("success"==data){
					showAlert("消息","绑定成功！");
				}
				if(data==""){
					showAlert("消息","绑定失败");
				}
			});
		}
	}
}