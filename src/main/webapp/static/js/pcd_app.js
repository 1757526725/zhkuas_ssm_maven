var remarkItem = {
	good : 1,
	bad : 2
};

$(document).ready(
		function() {
			var cNo = $("#cNo").html();
			$("#remark_good").click(function() {
				remark(cNo, remarkItem["good"]);
			});

			$("#remark_bad").click(function() {
				remark(cNo, remarkItem["bad"]);
			});
			$("#bt-submit").click(
					function() {
						// alert($("#texta-comment").val());
						if (isLogin == false) {
							showAlert("消息", "请登录后操作！");
							return;
						}
						if ($("#texta-comment").val() == "") {
							showAlert("警告", "评论内容不能为空");
							return;
						}
						createComment("creatRootComment", cNo,$("#texta-comment")
								.val(), null, null);
						//
					});
			//加入选课方案按钮
			
			$("#bt-addToMyPlan").click(function(){
				if (isLogin == false) {
					showAlert("消息", "请登录后操作！");
					return;
				}
				//显示并初始化窗口
				if(!isBindStudent||!isBindCompus){
					showAlert("提示",'<p>1.你可能未绑定教务网账户 <a href="passport?tab=basic">去绑定</a></p><p>2.你可能未绑定上课的校区。 <a href="passport?tab=basic">去绑定校区</a></p>');
					return;
				}
				
				showChooseCourseTimeDialog($("#cNo").html());
				
			});
			// 回复按钮
			$(".bt-reply").click(
					function() {
						if ($(this).parent().siblings(".reply-area").length > 0) {
							removeReplyArea(this);
						} else {
							// 针对子评论
							var commentUserNode = $(this).parent().parent().siblings(
									".nickName");
							if (commentUserNode.length > 0) {

							} else { // 针对父级评论
								commentUserNode = $(this).parent().parent().parent()
										.siblings(".user-info").find(
												".nickName");
							}
							if (isLogin == false) {
								showAlert("消息", "请登录后操作！");
								return;
							}
							showReplyInputArea(this, "回复 " + commentUserNode.html()
									+ ":");
						}
					});

		});


function setRemarkAction(status) {
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
/* 点赞，点踩 */
function remark(cNo, item) {

	setRemarkAction("disabled");
	$.get("../action/remark", {
		"cNo" : cNo,
		"remarkItem" : item
	}, callBack);

	function callBack(data) {
		if (data == '') {
			setRemarkAction("enable");
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
