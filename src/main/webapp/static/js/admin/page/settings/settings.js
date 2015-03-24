$(document).ready(function() {
	SettingsPage.main();
});
var SettingsPage =( function(){
	var main = function() {
		_initPage();
		_initEvent();
	};
	var _initPage = function() {
		//
	};
	var _initEvent= function(){
		var tips= $(".tips");
		//给表单添加提示事件
		$("form").find("input").on("focus",function(){
			var name = $(this).attr("name");
			tips.children().hide(500);
			$(".tip-"+name).show(500);
		});
		$("form").find("textarea").on("focus",function(){
			var name = $(this).attr("name");
			tips.children().hide(500);
			$(".tip-"+name).show(500);
		});
		
		
		//保存按钮添加事件
		$(".save").click(function(){
			//获取表单值 
			var url="";
			var postData= $("#setting-form").serialize();
			Util.ajax.post(url,"JSON",postData,function(jsonData){
				//服务器访问添加的学期数据.. 
				var result=jsonData.result;
				showInfo("消息",result,3000);
			});
		});
	};
	
	return {
		main:main
	};
})();