$(document).ready(function(){
	registerOnclick();
	var keyWordIpu=$("#keyWord");
	var bt_search=$("#bt-search");
	bt_search.click(updateClassList);
	keyWordIpu.keyup(function(event){
		var myEvent = event || window.event;
		var kCode=myEvent.keyCode;
		if(kCode==13){
			updateClassList();
		}
	});
	
	
	function updateClassList(){
		$.ajax({
			type : "POST",
			url : "../action/get_List",
			async:false,
			data : {
				"action":"classList",
				"keyword":keyWordIpu.val()
			},
			success : function(data){
				//清空列表
				var sid_ul=$("#sidlerbar").find(".bs-sidenav");
				sid_ul.html(data);
				//注册事件
				registerOnclick();
			}
		});
	}
	

});


function registerOnclick(){
	//注册搜索框的事件
	$(".nav >li a").click(function(){
		var parentNode=$(this).parent();		
		$(this).next().show();
		parentNode.addClass("active");
		parentNode.siblings().removeClass("active");
		parentNode.siblings().children("ul").hide();
	});
	//注册班级按键的事件
	var termAnode=$(".nav >li a").next().find("a");
	
	termAnode.click(function(){
		var termNo=$(this).parent().val();
		var classNo=$(this).parent().parent().parent().val();
		
		$.ajax({
			type : "POST",
			url : "../action/get_table",
			async:false,
			data : {
				"action":"classCourseTable",
				"termNo":termNo,
				"classNo":classNo
			},
			success : function(data){
				$(".courseTable").html(data);
			}
		});
		
	});
	
}

//评价模块

