$(function(){
	// 个人课表
	// 保存按钮的激活
	$("#mySchueduleForm").find("[name='cNo']").click(
			function() {

				var bt_save = $("#bt-saveSchedule");
				enableBtSave(bt_save);
				$("#mySchueduleForm").find(".panel-title").html("个人基础课程表--未保存");
			});
	$("#bt-saveSchedule").click(
			function() {
				var chkNodes = $("#mySchueduleForm").find(
						"input[name='cNo']");
				var flag = false;
				for ( var i = 0; i < chkNodes.size(); ++i) {
					if (chkNodes[i].checked) {
						flag = true;
						break;
					}
				}
				if(!flag){
					showAlert("错误","你未选中任何课程！");
					return;
				}
				
				showMyConfirm("你确定保存您的基础课程吗？",function(){
					
					var button = $("#bt-saveSchedule");
					button.button("loading");
					$.ajax({
								type : "POST",
								contentType : "application/x-www-form-urlencoded; charset=UTF-8",
								url : "../action/saveSchedule",
								data : $("#mySchueduleForm")
										.serialize(),
								error : function(msg) {

									showAlert("消息",
											"服务器出错，请联系管理员");
									button.button('reset');
									return;

								},
								success : function(data) {
									if(data=="login out time"){
										showAlert("错误","登录超时,请重新登录！");
										button.button('reset');
										return;
									}
									if (data == "") {
										showAlert("消息","提交失败，请重试！");
										button.button('reset');
										return;
									}
									if (data == "unBind") {
										showAlert(
												"提示",
												'<p>1.你可能未绑定教务网账户 <a href="passport?tab=basic">去绑定</a></p><p>2.你可能未绑定上课的校区。 <a href="passport?tab=basic">去绑定校区</a></p>');
										button.button('reset');
										return;
									}
									if (data == "save success") {
										showAlert("消息","保存成功",
												function() {
													location.reload();
												});
										return;
									}

								}
							});
					
				});
			});
	// 全选
	$("#bt-selectAll").click(
			function() {
				var chkNodes = $("#mySchueduleForm").find(
						"input[name='cNo']");
				var flag = false;
				for ( var i = 0; i < chkNodes.size(); ++i) {
					if (!$(chkNodes[i]).attr("disabled")
							&& !chkNodes[i].checked) {
						chkNodes[i].checked = true;
						flag = true;
					}
				}
				if (flag) {

					var bt_save = $("#bt-saveSchedule");
					enableBtSave(bt_save);
					$("#mySchueduleForm").find(".panel-title")
							.html("个人基础课程表--未保存");
				}

			});

	// 反选
	$("#bt-inverse")
			.click(
					function() {
						var chkNodes = $("#mySchueduleForm")
								.find("input[name='cNo']");
						var flag = false;
						for ( var i = 0; i < chkNodes.size(); ++i) {
							if (!$(chkNodes[i])
									.attr("disabled")) {

								chkNodes[i].checked = !chkNodes[i].checked;
								flag = true;
							}
						}
						if (flag) {

							var bt_save = $("#bt-saveSchedule");
							enableBtSave(bt_save);
							$("#mySchueduleForm").find(
									".panel-title").html(
									"个人基础课程表--未保存");
						}
					});
	
	
	//bt-edit
	
	$("#bt-edit").click(function(){
		var isCancel =false;
		var btNameNode=$("#bt-edit").find(".bt-name");
		if(btNameNode.html().indexOf("取消")>=0){
			isCancel=true;
			btNameNode.html(" 修改");
		}else{
			btNameNode.html(" 取消");
		}
		var chkNodes = $("#mySchueduleForm").find("input[name='cNo']");
		
		for ( var i = 0; i < chkNodes.size(); ++i){
			if(isCancel){
				$(chkNodes[i]).attr("disabled","disabled");
			}else{
				$(chkNodes[i]).removeAttr("disabled");
			}
		}
		
	});
});