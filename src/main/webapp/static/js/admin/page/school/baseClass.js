$(function() {
	BaseClassPage.main();
});

var BaseClassPage = (function() {
	var _ipt_keyword =$("#search > input");
	var _btn_search = $("#search > button");
	var _btn_update = $("#btn-update");
	var _btn_edit= $("#btn-edit-campus-class");
	var _btn_save= $("#btn-save");
	var _btn_go_search= $("#btn-go-search");
	var _sel_grade = $("select[name=grade]");
	var _sel_acadmey = $("select[name=acadmey]");
	var _array_class_nos = [];
	var _progressBar = null;
	var main = function() {
		_initPage();
		_initEvent();
	};
	var _initPage = function() {
		_progressBar = $('.update-message').progressBar();
		_progressBar.setCurrentProgress(0);
	};
	var _initEvent = function() {
		_btn_search.on("click",_quick_search);
		// 更新按钮
		_btn_update.click(function() {
			showConfirm("确认", "是否进行数据抓取更新？", function() {
				_crawBaseClassData();
			});
		});
		//显示编辑界面
		_btn_edit.click(function() {
			$("#edit-advance").toggle();
		});
		
		_btn_go_search.click(function(){
			_retrieve_class_data(_sel_grade.val(),_sel_acadmey.val());
		});
		
		$("#edit-advance").on("click","a[data-original-title]",function(){
			_remove_data(this);
		});
		
		_btn_save.click(function(){
			_save_campus_class_rs();
		});
		
		$("#baseClass_form").on("click","a",function(){
			//获取班级号
			var classNo=$(this).parent().parent().children().eq(2).text();
			window.location.href=SITE_HOST+"/admin/school/students/class/"+classNo;
		});
	};
	
	var _crawBaseClassData =function(){
		//先获取专业列表
		Util.ajax.get(SITE_HOST + "/data/majors/json", "JSON", null,
				function(data) {
					// alert(academyList.shift());
					var majorList = data.result;
					var taskQueue = [];
					// 将学院列表载入队列
					for ( var i = 0; i < majorList.length; i++) {
						taskQueue.push(majorList[i]);
					}
					_progressBar.setCurrentProgress(0);
					_progressBar.setMaxSize(taskQueue.length);
					_progressBar.setMessage("专业列表加载完成,开始抓取班级信息.....");
					_progressBar.show();
					_crawBaseClassByQueue(taskQueue, 1000);
				});

	};
	var _saveToDb = function(){
		Util.ajax.post(SITE_HOST+ "/admin/school/classes","JSON",{"step" : 3},
				function(data) {
					if (data.state == 1) {
						var addCount = data.result.addCount;
						var existCount = data.result.existCount;
						showInfo("结果","添加了"+ addCount+ "个班级，"+ existCount+ "间教室已存在！",3000);
					}
		});
	}
	// 专业队列抓取 班级信息
	var _crawBaseClassByQueue = function(queue, interval) {
		if (queue == null)
			return;
		if (interval < 0)
			interval = 0;
		if (queue.constructor == Array) {
			// 如果是队列任务
			if (queue.length == 0) {
				// 队列任务结束
				// 请求服务器 整理 数据
				Util.ajax.put(SITE_HOST + "/admin/school/classes","JSON",{"step" : 2},
					function(data) {
						if (data.state == 1) {
							var done = data.result.size;
							showConfirm("确认","抓取完成" + done + "间教室,是否保存数据？",
								function() {
									_saveToDb();
								});
					}
				});

				return;
			}
			var task = queue.shift();
			// 发动请求
			Util.ajax.put(SITE_HOST + "/admin/school/classes", "JSON", {"step": 1,"majorNo":task.no}, function(data) {
				// 请求成功
				if (data.state == 1) {
					_progressBar.setCurrentProgress(((_progressBar
							.getCurrentProgress() / 100)
							* _progressBar.getMaxSize() + 1)
							* 100 / _progressBar.getMaxSize());
					_progressBar.setMessage(task.no + "：" + task.name
							+ "......" + data.result);
				} else {
					_progressBar.setMessage(task.no + "：" + task.name
							+ "......出错，加入未完成列表!");
					_progressBar.addRemainTask(task);
				}
				setTimeout(function() {
					_crawBaseClassByQueue(queue, interval);
				}, interval);
			}, function(error) {
				// 请求失败，将任务添加到未完成任务中
				_progressBar.addRemainTask(task);
				queue.push(task);
			});
		} else {
			throw new Error("参数queue需要一个队列！");
		}
	};
	
	//快速搜索功能
	var _quick_search = function (){
		if(_ipt_keyword.val()!=null){
			Util.ajax.post(SITE_HOST + "/data/classes/json", "JSON", {keyword:_ipt_keyword.val()}, function(data){
				if (data.state==1) {
					_showData(data.result);					
				}
			}
			);
		}
		
	};
	
	var _showData = function (jsonList){
		var html_trs ="";
		var tbodyNode = $("#baseClass_form >table > tbody");
		tbodyNode.empty();
		if (jsonList.length==0) {
			showInfo("结果","没有找到结果!",3000);
			return ;
		};
		for (var i = 0; i < jsonList.length; i++) {
			html_trs+=("<tr>"+
						"<td><input type=\"checkbox\"></td>"+
						"<td class=\"text-center\">"+jsonList[i].name+"</td>"+
						"<td class=\"text-center\">"+jsonList[i].no+"</td>"+
						"<td class=\"text-center\">"+jsonList[i].major.academy.name+"</td>"+
						"<td class=\"text-center\">"+(jsonList[i].campus==null?"未知":jsonList[i].campus.name)+"</td>"+
						"<td class=\"text-center\"><a href=\"javascript:;\">查看学生</a></td>"+
						"</tr>");
		};
		tbodyNode.html(html_trs);
		$(".pagination").remove();
	};
	var _save_campus_class_rs = function(){
		//构造表单
		var formatClassNos = "";
		var campusId = $("input:checked[name='campusId']").val();
		for ( var clazzIndex in _array_class_nos) {
			var classes = _array_class_nos[clazzIndex];
			for ( var int = 0; int < classes.length; int++) {
				formatClassNos+=(classes[int].no+",");
			}
		}
		formatClassNos = formatClassNos.substring(0,formatClassNos.length-1);
		Util.ajax.post(SITE_HOST + "/admin/school/campusAndclass/rs", "JSON", {
			"classNos":formatClassNos,
			"campusId":campusId
		},
		function(data) {
			if(data.state == 1){
				showAlert("结果","成功修改 "+data.result.success+" 个班级,"+data.result.fail+" 个班级操作失败。");
				//恢复初始view
				var lis = $("#edit-advance").find("div.todo > ul li");
				for(var i = 1;i<lis.length;++i){
					lis[i].remove();
				}
				//清空 数据列表 
				_array_class_nos = [];
			}
		});
	}
	
	var _remove_data = function (_this){
		var currentRoot=$(_this).parent().parent();
		var index = currentRoot.find("div[dataindex]").attr("dataindex");
		//先移除 array 列表
		delete _array_class_nos[index];
		//移除view
		currentRoot.remove();
	}
	var _retrieve_class_data= function(grades,acadmeyNo){
		// 获取到所有的校区列表
		Util.ajax.post(SITE_HOST + "/data/classes/full/json", "JSON", {
			"grade":grades,
			"acadmeyNo":acadmeyNo
		},
		function(data) {
			if(data.state == 1){
				var classes = data.result;
				if(classes == null ){
					showAlert("结果","没有检索到班级信息");
				}
				var grade = _sel_grade.find("option:selected").text();
				var acadmey = _sel_acadmey.find("option:selected").text();
				var index = grade+"_"+acadmey;
				_array_class_nos[index]=classes;
				// 年级 
				//构造HTML节点
				var htmlNode  = "<li class=\"clearfix\">"
											+"<div class=\"txt\" dataIndex=\""+(grade+"_"+acadmey)+"\">"+grade+"_"+acadmey+"<span class=\"date badge badge-warning\">"+classes.length+"个班级</span>"
											+"</div>"
											+"<div class=\"pull-right\">"
												+"<a class=\"tip\" href=\"#\" data-original-title=\"Delete\"> <i class=\"icon-remove\"></i>"
												+"</a>"
											+"</div>"
										+"</li>";
				// 载入显示区
				var destUl = $("#edit-advance").find("div.todo > ul");
				destUl.append(htmlNode);
			}
		});
	}
	return {
		main : main
	};
})();