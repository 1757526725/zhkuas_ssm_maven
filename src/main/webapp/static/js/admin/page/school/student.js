$(function() {
	StundetPage.main();
});

var StundetPage = (function() {
	var _ipt_keyword =$("#search > input");
	var _btn_search = $("#search > button");
	var main = function(){
		_initPage();
		_initEvent();
	};
	var _initPage = function(){

	};
	var _initEvent =function(){
		_btn_search.on("click",_findAccount);
		
	};
	var _findAccount =function(){
		if(_ipt_keyword.val()!=null){
			Util.ajax.post(SITE_HOST + "/admin/school/students/json", "JSON", {keyword:_ipt_keyword.val()}, function(data){
				if (data.state==1) {
					_showData(data.result);					
				}
			}
			);
		}
	};
	
	var _showData =function(dataJson){
		var html_trs ="";
		var tbodyNode = $("#student_form >table > tbody");
		tbodyNode.empty();
		if (dataJson.length==0) {
			showInfo("结果","没有找到结果!",3000);
			return ;
		};
		for (var i = 0; i < dataJson.length; i++) {
			html_trs+=("<tr><td><input type=\"checkbox\"></td>"+
							"<td class=\"text-center\">"+dataJson[i].id+"</td>"+
											"<td class=\"text-center\">"+dataJson[i].name+"</td>"+
											"<td class=\"text-center\">"+dataJson[i].sNo+"</td>"+
											"<td class=\"text-center\">"+dataJson[i].classBelongTo.name+"</td>"+
											"<td class=\"text-center\"><a href=\"javascript:void(0)\">查看学生详情</a></td>"+
										"</tr>");
		};
		tbodyNode.html(html_trs);
		$(".pagination").remove();
	}
	return {
		main : main
	};
})();