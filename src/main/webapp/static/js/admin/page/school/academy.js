$(function(){
	AcadmeyPage.main();
});

var AcadmeyPage = (function(){
	var _btn_delete =$("#btn-delete");
	var _btn_update =$("#btn-update");
	var _btn_saveDB =$("#btn-toDb");
	var main = function(){
		_initPage();
		_initEvent();
	};
	var _initPage= function(){
		
	};
	var _initEvent = function(){
		//监听 删除按钮的状态
		var chkbox=$("#academy_form").find("input[type='checkbox']");
		$("#academy_form").on("click","input[type='checkbox']",function(){
			var state = false;
			for(var i=0;i<chkbox.size();i++){
				if(chkbox.eq(i).prop("checked")){
					state =true;
					break;
				}
			}
			
			if(state){
				_btn_delete.removeAttr("disabled");
			}else{
				_btn_delete.attr("disabled","disabled");
			}
		});
		
		//保存按钮
		_btn_saveDB.click(function(){
			showConfirm("确认","是否现在就将数据合并并保存到数据库中!",_saveToDB);
		});
		//更新按钮
		_btn_update.click(function(){
			//获取到当前页面的学院数 
			var size = _btn_update.attr("originSize");
			showConfirm("确认","你确定要更新学院信息,当前的学院个数为 "+size+"个！",function(){
				_crawAcademyData();
			});
		});
	};
	var _saveToDB = function(){
		Util.ajax.put("academys","JSON",{step:2},function(jsonData){
			var result=jsonData.result;
			showInfo("保存完毕！",result,3000);
		});
	};
	var _crawAcademyData=function (){
		Util.ajax.put("academys","JSON",{step:1},function(jsonData){
			//服务器返回的数据,json
			var academysList=jsonData.result;
			if(academysList!=null){
				//将抓取的学院机构列表加载到页面
				var trHtml ="";
				for(var i = 0;i<academysList.length;i++){
					trHtml+="<tr>"+
							"<td><input type=\"checkbox\"></td>"+
							"<td class=\"text-center\">"+academysList[i].name+"</td>"+
							"<td class=\"text-center\">"+academysList[i].no+"</td>"+
							"<td class=\"text-center\">"+(academysList[i].major==null?"未更新":academysList[i].major.length)+"</td>"+
							"<td class=\"text-center\"><a href=\"javascript:;\">查看专业</a></td>"+
							"</tr>";
				}
				
				//清空原来的列表
				var tableNode=$("#academy_form").find("table");
				var tbodyNode=tableNode.find("tbody");
				tbodyNode.hide();
				tbodyNode.html(trHtml);
				tbodyNode.show(1000);
				showInfo("结果","抓取完毕！",3000);
				showConfirm("确认","是否现在就将数据合并并保存到数据库中!",_saveToDB);
			}else{
				showInfo("错误","更新失败！",3000);
			}
		});
	};
	return {
		main :main
	};
})();