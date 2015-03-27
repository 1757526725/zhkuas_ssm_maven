$(function(){
	CourseDetailPage.main();
});

var CourseDetailPage = (function(){
	var _btn_remark_good = $("#remark_good");
	var _btn_remark_bad = $("#remark_bad");
	var remarkItem = {good : 1,bad : 2};
	var _campusId = $("input[name='campusId']").val();
	var _termNo = $("input[name='termNo']").val();
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
		//加入选课方案按钮
		$("#bt-addToMyPlan").click(function(){
			if (!UserStatus.isLogin()) {
				showAlert("消息", "请登录后操作！");
				return;
			}
			//显示并初始化窗口
			if(!UserStatus.isBinded()){
				showAlert("提示",'<p>1.你可能未绑定教务网账户 <a href="passport?tab=basic">去绑定</a></p><p>2.你可能未绑定上课的校区。 <a href="passport?tab=basic">去绑定校区</a></p>');
				return;
			}
			PublicCourse.showOptionDialog(cNo,_campusId,_termNo,function(){
				location.reload();
			});
		});
		// 回复按钮
		$(".bt-reply").click(
				function() {
					if ($(this).parent().siblings(".reply-area").length > 0) {
						Comment.removeReplyArea(this);
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
						Comment.showReplyInputArea(this, "回复 " + commentUserNode.html()
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
	return {
		main: main
	};
})();