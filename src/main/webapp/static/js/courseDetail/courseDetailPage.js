$(function(){
	CourseDetailPage.main();
});

var CourseDetailPage = (function(){
	var _btn_remark_good = $("#remark_good");
	var _btn_remark_bad = $("#remark_bad");
	var remarkItem = {good : 1,bad : 2};
	var cNo = $("#cNo").html();
	var main = function(){
		_initPage();
		_initEvent();
	};
	var _initPage = function(){
		
	};
	var _initEvent = function(){
		_btn_remark_good.click(function(){
			_remark(cNo, remarkItem["good"]);
		});
		_btn_remark_bad.click(function(){
			_remark(cNo, remarkItem["bad"]);
		});
		//发表评论
		$("#bt-submit").click(
				function() {
					if (!UserStatus.isLogin()) {
						showAlert("消息", "请登录后操作！");
						return;
					}
					if ($("#texta-comment").val() == "") {
						showAlert("警告", "评论内容不能为空");
						return;
					}
					createComment("creatRootComment", cNo,$("#texta-comment").val(), null, null);
					//
		});
		
		// 回复按钮
		$(".bt-reply").click(
				function() {
					if ($(this).parent().siblings(".reply-area").length > 0) {
						_removeReplyArea(this);
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
						if (!UserStatus.isLogin()) {
							showAlert("消息", "请登录后操作！");
							return;
						}
						_showReplyInputArea(this, "回复 " + commentUserNode.html()
								+ ":");
					}
		});
	};
	
	var _remark = function(cNo,item){
		if(!UserStatus.isLogin()){
			showAlert("错误","你需要登录后才能点评");
			return ;
		}
		_setRemarkBtnStatus("disabled");
		$.post(domain+"main/ws/user/course/remark", {
			"cNo" : cNo,
			"remarkItem" : item
		},callBack);

		function callBack(data) {
			if (data == '') {
				_setRemarkBtnStatus("enable");
				showAlert("服务器错误");
				return;
			}
			if(data.state==0){
				showAlert(data.error.error_description);
				button.button('reset');
				_hideLoading();
				return;
			}
			
			if(data.state==1){
				_refalshRemarkInfo(item, data.result);
			}
		}
	}
	var  _setRemarkBtnStatus = function(status) {
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
	 var _refalshRemarkInfo = function(item, result) {
		var bt_good = $("#remark_good");
		var bt_bad = $("#remark_bad");
		if (item == 1) {
			// 获取赞的按钮
			bt_good.children().last().html("已赞 " + result.good);
			bt_good.addClass("disabled");
			bt_bad.addClass("disabled");
		}

		if (item == 2) {
			// 获取赞的按钮
			bt_bad.children().last().html("已踩 " + result.bad);
			bt_bad.addClass("disabled");
			bt_good.addClass("disabled");
		}
	}
	 
	 var _showReplyInputArea =function(_this, replyUserName){
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
	 	var _removeReplyArea =function(_this) {
			$(_this).parent().siblings(".reply-area").remove();
		};
	return {
		main: main
	};
})();