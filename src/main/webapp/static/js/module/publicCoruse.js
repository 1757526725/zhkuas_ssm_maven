var PublicCourse=(function(){
	var _initMsgPanel=$("#initMsgPanel");
	var _noticePanel=$("#noticePanel");
	var _optionList=$("#optionList");
	var _CourseChooseDialog=$("#CourseChoose-dialog");
	var _bt_scheme_ensure=$("#bt-scheme-ensure");
	var _updateInfo={};
	var _cNo,_endTodo;
	//注册btn事件
	_bt_scheme_ensure.click(function(){
		_addMyScheme(_cNo,_endTodo);
	});
	var showOptionDialog=function(cNo,campusId,termNo,endTodo){
		
		//界面初始
		_initMsgPanel.show();
		_noticePanel.hide();
		_optionList.empty();
		_CourseChooseDialog.modal("show");
		_bt_scheme_ensure.attr("disabled", "disabled");
		_bt_scheme_ensure.attr("action-data-cNo", cNo);
		
		//接收数据
		_getOptionData(cNo,campusId,termNo);
		_cNo=cNo;
		_endTodo=endTodo;
	
	};
	var _initOptionData=function(dataNode) {
		_initMsgPanel.hide();
		$("#noticePanel").show();
		dataNode.children("#dataListDiv").children("table").find("table").addClass(
				"table table-bordered");

	};
	var _getOptionData=function(cNo,campusId,termNo){
		var noneDataMsg = $('<h4 class="text-center text-danger" > 没有选课信息，可能是教务网没有该选课信息!</h4>');
		var unBindMsg =$('<p>1.你可能未绑定教务网账户 <a href="passport?tab=basic">去绑定</a></p><p>2.你可能未绑定上课的校区。 <a href="passport?tab=basic">去绑定校区</a></p>');
		$.get(domain+"main/course/public/option/"+cNo+"/campus/"+campusId+"/term/"+termNo+"?time=" + new Date().getTime(), {
			"cNo" : cNo
		}, function(data) {
			if (data.indexOf("state:unBind")>=0) {
				_optionList.empty();
				_optionList.append(unBindMsg);
				_initMsgPanel.hide();
				return;
			}
			
			if(data.indexOf("data-wrapper")<0){
				//请求超时..
				_optionList.empty();
				_optionList.append(noneDataMsg);
				_initMsgPanel.hide();
				return;
			}
			var dataNode = $(data);
			_initOptionData(dataNode);
			try {
				_optionList.append(dataNode);
			} catch (e) {

			}

			// 已经选过了的显示初始化
			var idval = "";
			var actiondataNode = dataNode.find("#initActionData");
			if (actiondataNode) {
				try {
					var divCol = dataNode.find("input");
					for ( var i = 0; i < divCol.length; i++) {
						if (divCol[i].type == "radio") {
							idval = divCol[i].value.split("|")[2].split("@")[1];
							if (actiondataNode.attr("hadChoose") == idval) {
								selradio(divCol[i], 1);
								return;
							}
						}
					}

				} catch (e) {
					console.log(e);
				}
			}
		});
	};
	var getUpdateInfo=function(){
		return _updateInfo;
	};
	var _addMyScheme=function(cNo, endTodo){
		var idval = "";
		var schoolTime = "";
		var button = $("#bt-scheme-ensure");
		var topBarInfoNode = $("#my_project").find("span");
		button.button("loading");
		try {
			var divCol = document.getElementsByTagName("input");
			for ( var i = 0; i < divCol.length; i++) {
				if (divCol[i].type == "radio" && divCol[i].checked) {
					if (idval == "") {
						idval = divCol[i].value.split("|")[2];
					}

					// 保存选课时间
					var ipTdNode = $(divCol[i]).parent();
					var ipTrNode = ipTdNode.parent();
					schoolTime += ipTdNode.prev().prev().html();
					for ( var j = 1; j < ipTdNode.attr("rowspan"); ++j) {
						var tdsOfNextTr = ipTrNode.next().children();
						schoolTime += $(tdsOfNextTr.get(5)).html();
						ipTrNode = ipTrNode.next();
					}
				}
			}

		} catch (e) {
			console.log(e);
		}
		if (schoolTime == "")
			schoolTime = "选课时间获取错误！";
		var displayValue = idval.split("@")[0];
		var formValue = idval.split("@")[1];
		var summaryNode = $("#dialog-summary");
		summaryNode.empty();
		$.ajax({
			type : "POST",
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			url :domain+"main/user/curriculum/scheme",
			data : {
				"cNo" : cNo,
				"displayValue" : displayValue,
				"formValue" : formValue,
				"schoolTime" : schoolTime
			},
			error : function(msg) {
				addErrorMessage("服务器出错，请联系管理员", summaryNode);
				button.button('reset');
				return;

			},
			success : function(data) {
				
				if (data == "") {
					addErrorMessage("提交失败，请重试！", summaryNode);
					button.button('reset');
					return;
				}
				if(data.state==0){
					addErrorMessage(data.error.error_description, summaryNode);
					button.button('reset');
					return;
				}
				if (data.state==1 && data.result == "update success") {
					//保存页面数据。
					_updateInfo['schoolTime']=schoolTime;
					_updateInfo['displayValue']=displayValue;
					
					$("#CourseChoose-dialog").modal("hide");
					showAlert("消息", "更新成功", endTodo);
					return;
				}
				if (data.state==1  && data.result == "add success") {
					if (topBarInfoNode.size() == 0) {
						var node = '<span class="badge pull-right">1</span>我的选课方案';
						$("#my_project").children("a").html(node);
					} else {
						topBarInfoNode.html(parseInt(topBarInfoNode.html()) + 1);
					}
					$("#CourseChoose-dialog").modal("hide");
					showAlert("消息", "添加成功", endTodo);
					return;
				}

			}
		});

	};
	
	return {
		'showOptionDialog':showOptionDialog,
		'getUpdateInfo':getUpdateInfo
	};
})();

/** *****选课相关********* */
function openWin(theID) {
	var theURL, w, h, Tform;
	w = 450, h = 380;
	eval("Tform='width=" + w + ",height=" + h + "'");
	theURL = 'http://jw.zhku.edu.cn/jwmis/JXZY/INFO_Teacher.aspx?id='
			+ theID.getAttribute("value");
	pop = window.open(theURL, 'winKPT', Tform);
}

function selradio(theID, N) {
	var object_id;
	var object_value;
	var object_temp;
	var skbz_id;
	var divColl;
	object_value = theID.value;
	object_temp = object_value.split("|");
	skbz_id = object_temp[0];
	try {
		var divCol = document.getElementsByTagName("input");
		for (i = 0; i < divCol.length; i++) {
			if (divCol[i].type == "radio")
				divCol[i].checked = false;
		}
		if (object_temp[0] != "") {
			try {
				eval("window.document.all.J" + object_temp[0]
						+ ".checked=true;");
			} catch (e) {
			}
			try {
				eval("window.document.all.S" + object_temp[0]
						+ ".checked=true;");
			} catch (e) {
			}
			try {
				eval("window.document.all.E" + object_temp[0]
						+ ".checked=true;");
			} catch (e) {
			}
			try {
				eval("window.document.all.D" + object_temp[0]
						+ ".checked=true;");
			} catch (e) {
			}
			try {
				eval("window.document.all.M" + object_temp[0]
						+ ".checked=true;");
			} catch (e) {
			}
			try {
				eval("window.document.all.N" + object_temp[0]
						+ ".checked=true;");
			} catch (e) {
			}
			try {
				eval("window.document.all.P" + object_temp[0]
						+ ".checked=true;");
			} catch (e) {
			}
			try {
				eval("window.document.all.Q" + object_temp[0]
						+ ".checked=true;");
			} catch (e) {
			}
		} else {
			theID.checked = true;
		}
	} catch (e) {
	}
	$("[name=jsskbj_str]").val(object_temp[2]);
	$("#bt-scheme-ensure").button("reset");
	// window.document.all.jsskbj_str.value=object_temp[2];
}