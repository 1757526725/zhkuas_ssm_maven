$(function() {
	AccountPage.main();
});

var AccountPage = (function() {
	var _ipt_keyword =$("#search > input");
	var _btn_search = $("#search > button");
	var _btn_rmBind = $("#btn-removeBind");
	var main = function(){
		_initPage();
		_initEvent();
	};
	var _initPage = function(){

	};
	var _initEvent =function(){
		_btn_search.on("click",_findAccount);
		$("#academy_form >table > tbody").on("click","input[type=checkbox]",function(){
			if($(this).prop("checked")){
				_btn_rmBind.removeAttr("disabled");
			}else{
				var chks=$("#academy_form >table > tbody").find("input[type=checkbox]");
				for (var i = 0; i < chks.length; i++) {
					if(chks.eq(i).prop("checked")){
						_btn_rmBind.removeAttr("disabled");
						break;
					}else{
						_btn_rmBind.attr("disabled","disabled");
					}
				};
			}
		});
		$("#title-checkbox").click(function(){
			var chks=$("#academy_form >table > tbody").find("input[type=checkbox]");
			chks.prop("checked",$(this).prop("checked"));
			for (var i = 0; i < chks.length; i++) {
				if(chks.eq(i).prop("checked")){
					_btn_rmBind.removeAttr("disabled");
					break;
				}else{
					_btn_rmBind.attr("disabled","disabled");
				}
			};
		});
		_btn_rmBind.on("click",function(){
			_rmBind();
		});
	};
	var _findAccount =function(){
		if(_ipt_keyword.val()!=null){
			Util.ajax.post(SITE_HOST + "/admin/account/users", "JSON", {keyword:_ipt_keyword.val()}, function(data){
				if (data.state==1) {
					_showAccount(data.result);					
				}
			}
			);
		}
	};
	var _rmBind =function(){
		var ckOks = $("#academy_form >table > tbody").find(":input[checked]");
		if (ckOks.length>0) {
			var str ="你确定将 ";
			var data=[];
			for (var i = 0; i < ckOks.length-1; i++) {
				var tds=ckOks.eq(i).parent().parent().children();
				str+=(tds.eq(2).text()+",");
				data.push(tds.eq(1).text());
			};
			str+=ckOks.eq(ckOks.length-1).parent().parent().children().eq(2).text();
			data.push(ckOks.eq(ckOks.length-1).parent().parent().children().eq(1).text());
			showConfirm("确认",str+" 解除绑定吗？",function(){

			Util.ajax.put(SITE_HOST + "/admin/account/users","json",{uid:data},function(data){
				if(data.state==1){
					showInfo("结果","解绑成功!",3000);
					var map=[];
					for (var i = 0; i < data.result.length; i++) {
						map[data.result[i].uid]=data.result[i];
					};
					var trs=$("#academy_form >table > tbody").find("tr");
					for (var i = 0; i < trs.length; i++) {
						var usrJson = map[parseInt(ckOks.eq(i).parent().parent().children().eq(1).text())];
						if(usrJson!=null){
							var html_trs=("<td><input type=\"checkbox\"></td><td class=\"text-center\">"+usrJson.uid+"</td>"
							+"<td class=\"text-center\">"+((usrJson.nickName==''||usrJson.nickName=='undefined')?usrJson.student.name:usrJson.nickName)+"</td>"
							+"<td class=\"text-center\">"+(usrJson.student==null?"":usrJson.student.name)+"</td>"
							+"<td class=\"text-center\">"+(usrJson.student==null?"":usrJson.student.sNo)+"</td>"
							+"<td class=\"text-center\"><a href=\"javascript:void(0)\">查看学生详情</a></td>"
							);
							trs.eq(i).html(html_trs);
						}
					};
				}
			});
			});
		};
	};
	var _showAccount =function(accountJson){
		var html_trs ="";
		var tbodyNode = $("#academy_form >table > tbody");
		tbodyNode.empty();
		if (accountJson.length==0) {
			showInfo("结果","没有找到结果!",3000);
			return ;
		};
		for (var i = 0; i < accountJson.length; i++) {
			html_trs+=("<tr><td><input type=\"checkbox\"></td><td class=\"text-center\">"+accountJson[i].uid+"</td>"
						+"<td class=\"text-center\">"+((accountJson[i].nickName==''||accountJson[i].nickName=='undefined')?accountJson[i].student.name:accountJson[i].nickName)+"</td>"
						+"<td class=\"text-center\">"+(accountJson[i].student==null?"":accountJson[i].student.name)+"</td>"
						+"<td class=\"text-center\">"+(accountJson[i].student==null?"":accountJson[i].student.sNo)+"</td>"
						+"<td class=\"text-center\"><a href=\"javascript:void(0)\">查看学生详情</a></td>"
						+"</tr>");
		};
		tbodyNode.html(html_trs);
		$(".pagination").remove();
	}
	return {
		main : main
	};
})();