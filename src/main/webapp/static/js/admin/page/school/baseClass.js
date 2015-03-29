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