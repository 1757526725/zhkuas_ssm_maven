var Comment = (function(){
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
		showReplyInputArea:_showReplyInputArea,
		removeReplyArea:_removeReplyArea
	}
})();