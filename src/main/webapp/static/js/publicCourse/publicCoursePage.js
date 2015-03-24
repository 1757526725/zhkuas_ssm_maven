$(function() {
	PublicCoursePage.main();
});

var PublicCoursePage = (function() {
	var _dataNode = $("#js_initData");
	var _campusId = $("input[name='campusId']").val();
	var _termNo = $("input[name='termNo']").val();
	var _isLogin = false;
	var main = function() {
		_initPage();
		_initEvent();
	};
	var _isCurrentTermCourseList = function() {
		var params= getRequest();
		return params['termNo']==null||params['termNo'] == currentTermNo;
	};
	var _getHasAddData = function() {

		return _dataNode.text().trim().split(",");
	};
	var _initPage = function() {
		
		if($("#sina-login").size()>0){
			_isLogin =false;
		}else{
			_isLogin =true;
		}
		if (_isCurrentTermCourseList()) {
			var hasAddCnos = _getHasAddData();
			var opera_bts = $(".opera_bt");
			for ( var i = 0; i < opera_bts.size(); ++i) {
				var curr_bt = $(opera_bts[i]);
				if (hasAddCnos.indexOf(curr_bt.parent().attr("cNo")) >= 0) {
					curr_bt.removeClass("glyphicon-plus-sign text-primary");
					curr_bt.addClass("glyphicon-edit text-danger");
					var orderNode = curr_bt.parent().parent().parent()
							.children().first();
					orderNode.html("<span class='badge badge-success'>"
							+ orderNode.html() + "</span>");
				}
			}
			// 更新个数
			var badgeNumNode = $("#my_project").find(".badge");
			if (badgeNumNode.size() > 0) {
				badgeNumNode.text(hasAddCnos.length - 1);
			}
			;
		}
	};
	var _updatePage = function(cNo) {

		// 修改jsInitData
		var data = _getHasAddData();
		data.push(cNo);
		_dataNode.text(data.toString());
		// 更新 序号标志
		var curr_bt = $("a[cno='" + cNo + "']").find(".opera_bt");
		var orderNode = curr_bt.parent().parent().parent().children().first();
		// 如果是添加方案的话
		if (orderNode.find(".badge").size() <= 0) {
			orderNode.html("<span class='badge badge-success'>"
					+ orderNode.html() + "</span>");

			// 更新操作按钮
			curr_bt.removeClass("glyphicon-plus-sign text-primary");
			curr_bt.addClass("glyphicon-edit text-danger");

			// 更新选课方案数量；
			var badgeNumNode = $("#my_project").find(".badge");
			badgeNumNode.text(data.length - 1);
		}
	};
	var _initEvent = function() {
		$("a[cno]").click(function() {
			if(!UserStatus.isLogin()){
				showAlert("消息","请登录后操作！");
				return;
			}
			if(_isCurrentTermCourseList()){
				var cNo=$(this).attr("cno");
				PublicCourse.showOptionDialog(cNo,_campusId,_termNo,function(){
					_updatePage(cNo);
				});
			}else{
				//提示跳转到最新课表
				showConfirm("<h4 class=\"text-center text-danger\" > 你打开的不是这学期的课程,是否切换到当前学期课程.</h4>", function(){
					window.location.href='../main/public_course';
				});
			}
		});

	};

	return {
		main : main
	};
})();