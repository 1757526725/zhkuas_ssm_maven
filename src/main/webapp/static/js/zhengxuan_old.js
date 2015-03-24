$(function(){
	
	var bt_login_academic =$(".bt_login_academic");
	var bt_switch_account=$(".bt_switch_account");
	var ip_sNo=$("#systemSno");
	var ip_password=$("#systemPassword");
	var ip_v_code=$("#academy_code");
	bt_switch_account.click(function(){
		showLoginPanel();
		if(ip_sNo.attr("disabled")){
			ip_sNo.removeAttr("disabled");
			bt_switch_account.html("我的账号");
		}
		else{
			ip_sNo.val(bt_switch_account.attr("osno"));
			ip_sNo.attr("disabled","disabled");
			bt_switch_account.html("其他账号");
		}
	});
	bt_login_academic.click(function(){
		if($("#systemPassword").val()==""){
			addActionResult("密码不能为空");
			return;
		}
		if(ip_v_code.val()==""){
			addActionResult("验证码不能为空");
			return;
		}
		login(ip_sNo.val(),ip_password.val(),ip_v_code.val(),bt_login_academic);
	});
	var bt_search=$(".bt-search");
	bt_search.click(function(){
		var sel_lx =$("#sel_lx").val();
		var majorNo =$("[name='SelSpeciality']").val();
		var campusId =$("[name='campus']").val();
		getCourseList(bt_search,sel_lx,majorNo,campusId);
	});
	
	var bt_submit_course =$(".bt_submit_course");
	bt_submit_course.click(function(){
		submitCourse();
	});
});
function submitCourse(){
	//检测表单
/*	var divCol = document.getElementsByTagName("input");
	for (var i=0;i<divCol.length;i++){
		if( divCol[i].type == "radio" && !divCol[i].disabled){
			
		}
	}*/

	var button=$(".bt_submit_course");

	button.button("loading");
	if(!CheckForm()){

		button.button("reset");
		return;
	}
	showLoading("正在为您提交到教务网...");
	$.ajax({
		type : "POST",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		url : domain+"main/user/course/public/zx",
		data : $("#course_form").serialize(),
		error : function(msg) {
			addActionResult("服务器出错，请重试！");
			button.button('reset');
			hideLoading();
			return;
		},
		success : function(data) {
			if(data.state == 0 && data.error.error_code=='-10006'){
				addActionResult("教务网登录过期，请重新登录教务网！");
				button.button('reset');
				showLoginPanel();
				return;
			}else if(data.state == 0){
				addActionResult(data.error.error_description);
				button.button('reset');
				return ;
			}
			addActionResult(data.result,"text-success","12");
			button.button('reset');
			hideLoading();
		}
	});
	
}
function getCourseList(button,sel_lx,majorNo,campusId){
	button.button("loading");
	showLoading("正在为您检索数据...");
	$("#course_form").hide();
	$(".bt_submit_course").attr("disabled","disabled");
	$.ajax({
		type : "POST",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		url : domain+"main/user/course/public/zx/list",
		data : {
			"sel_lx":sel_lx,
			"SelSpeciality":majorNo,
			"campus":campusId
		},
		error : function(msg) {
			addActionResult("服务器出错，请重试！");
			button.button('reset');
			hideLoading();
			return;
		},
		success : function(data) {
			if(data=="system login out time"){
				addActionResult("教务网登录过期，请重新登录教务网！");
				button.button('reset');
				showLoginPanel();
				return;
			}
			if(data=="unlogin"){
				addActionResult("登录超时，请刷新页面，并用微博登录！");
				button.button('reset');
				hideLoading();
				return;
			}
			if (data == "") {
				addActionResult("参数错误，请重试！");
				button.button('reset');
				hideLoading();
				return;
			}
			if (data == "unBind") {
				addActionResult('1.你可能未绑定教务网账户 <a href="passport?tab=basic">去绑定</a></p><p>2.你可能未绑定上课的校区。 <a href="passport?tab=basic">去绑定校区</a>');
				button.button('reset');
				hideLoading();
				return;
			}
			if(data=="noData"){
				addActionResult("没有数据！可能是未到选课时间！");
				button.button('reset');
				hideLoading();
				return;
			}
			$("#oTable").html(data);
			$("#course_form").show();
			addActionResult("检索完成","text-success");
			button.button('reset');
			$(".bt_submit_course").button("reset");
			hideLoading();
		}
	});
}
function addActionResult(message,text_class,fontSize){
	var action_result=$(".result_text");
	action_result.empty();
	if(text_class==null) text_class ="text-danger";
	if(fontSize!=null){
		action_result.append("<h3 style=\"font-size:"+fontSize+"px;\"class='"+text_class+"'>"+message+"</h3>");
		return;
	}
	action_result.append("<h3 class='"+text_class+"'>"+message+"</h3>");
}
function login(sNo,password,vCode,button){
	button.button("loading");
	showLoading("正在为您登录教务网...");
	$.ajax({
		type : "POST",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		url : domain+"network/user",
		data : {
			"username":sNo,
			"password":password,
			"vCode":vCode
		},
		error : function(msg) {
			addActionResult("服务器出错，请重试！");
			button.button('reset');
			hideLoading();
			return;

		},
		success : function(data) {
			
			if(data.state==0&& data.error.error_code=="-20001"){
				addActionResult("登录超时，请刷新页面，并用微博登录！");
				button.button('reset');
				hideLoading();
			}else if(data.state==0){
				addActionResult(data.error.error_description);
				button.button('reset');
				hideLoading();
				if(data.error.error_code="-10002"){
					changeValidateCode($("#vCode")[0]);
				}
				return;
			}
			if (data == null) {
				addActionResult("参数错误，请重试！");
				button.button('reset');
				hideLoading();
				return;
			}
			/*if (data == "unbind") {
				addActionResult('1.你可能未绑定教务网账户 <a href="passport?tab=basic">去绑定</a></p><p>2.你可能未绑定上课的校区。 <a href="passport?tab=basic">去绑定校区</a>');
				button.button('reset');
				hideLoading();
				return;
			}*/
			if (data.state==1) {
				addActionResult("登录成功","text-success");
				button.button('reset');
				showLoginSuccessState();
				return;
			}
			addActionResult("未知错误，请刷新重试！");
			button.button('reset');
			hideLoading();
		}
	});
}
function showLoginPanel(){
	$(".bt_login_academic").show();
	$(".login_state").hide();
	hideSelectOption();
	hideLoading();
}
function showLoginSuccessState(){
	$(".bt_login_academic").hide();
	$(".login_state").show();
	showSelectOption();
	hideLoading();
}

function showLoading(message){
	$(".result_loading").children("span").html(message);
	$(".result_loading").show();
}
function hideLoading(){
	$(".result_loading").hide();
}
function hideSelectOption(){
	$(".use_notice").show();
	$(".zx_option").hide();
}
function showSelectOption(){
	$(".use_notice").hide();
	$(".zx_option").show();
}

function showListWSXK(_this){
	if($(_this).val()=="0"){
		$("[name='SelSpeciality']").parent().parent().show();
		$("[name='campus']").parent().parent().hide();
		return;
	}
	if($(_this).val()=="2"){
		$("[name='SelSpeciality']").parent().parent().hide();
		$("[name='campus']").parent().parent().show();
		return;
	}
}

function openWinDialog(theID,N){
	var winDiaLogStr="<div class=\"modal fade in\" id=\"option-WinDialog\" tabindex=\"-2\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"false\" data-backdrop=\"static\">";
	winDiaLogStr+="<div class=\"modal-dialog\">";
	winDiaLogStr+="<div class=\"modal-content\">";
	winDiaLogStr+="<div class=\"modal-header\">";
	winDiaLogStr+="<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">×</button>";
	winDiaLogStr+="<h4 class=\"modal-title\" id=\"myModalLabel\">";
	winDiaLogStr+="请选择上课时间 &nbsp;<span class=\"text-warning\">("+$("input[name='term']").attr('termName')+")</span>";
	winDiaLogStr+="</h4>";
	winDiaLogStr+="</div>";
	winDiaLogStr+="<div class=\"modal-body\">";
	winDiaLogStr+="<div id=\"initMsgPanel\">";
	winDiaLogStr+="<div class=\"useLocalData\"></div>";
	winDiaLogStr+="<span>正在查询上课信息,请勿刷新或关闭窗口...</span>";
	winDiaLogStr+="<div class=\"text-center\"><img src=\""+domain+"static/images/result_loading.gif\"></div>";
	winDiaLogStr+="</div>";
	winDiaLogStr+="<div id=\"noticePanel\" style=\"display: none;\">";
	winDiaLogStr+="<p class=\"text-danger\">注意:人数一栏不是即时更新的,每个选课时间段更新一次，所以同学们可以大胆加入选课方案！</p>";
	winDiaLogStr+="</div>";
	winDiaLogStr+="<div id=\"optionList\"><div class=\"data-wrapper\">";
	winDiaLogStr+="</div>";
	winDiaLogStr+="<div id=\"dialog-summary\" class=\"text-danger\"></div>";
	winDiaLogStr+="</div>";
	winDiaLogStr+="<div class=\"modal-footer\">";
	winDiaLogStr+="<button id=\"bt-option-ensure\" action-data-cno=\"040227\" type=\"button\" data-loading-text=\"提交中...\" class=\"btn btn-primary ensure\">确定</button>";
	winDiaLogStr+="<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">取消</button>";
	winDiaLogStr+="</div>";
	winDiaLogStr+="</div>";
	winDiaLogStr+="</div>";
	winDiaLogStr+="</div>";
	var winDiaLog=$(winDiaLogStr);
	winDiaLog.on("click", ".ensure", function(e) {
		//处理数据
		$(this).button("loading");
		var ReturnStr=enSureSelect();
		try
		{
			jsskbj_str=ReturnStr.split('@')[0];
			jsskbj_val=ReturnStr.split('@')[1];
			if(ReturnStr.split('@')[0]=="undefined"){jsskbj_str="";}
			if(ReturnStr.split('@')[1]=="undefined"){jsskbj_val="";}
			document.getElementById("chkSKBJstr"+N).value = jsskbj_str;
			document.getElementById("chkSKBJ"+N).value = jsskbj_val;
			
		}catch(e){}
	});
	winDiaLog.on('hidden.bs.modal', function() {
		winDiaLog.remove();
	});
	$("body").append(winDiaLog);
	winDiaLog.modal("show");
	var skbjval=document.getElementById("chkSKBJ"+N).value;
	var value=theID.getAttribute("value");
	var campusId = $("select[name='campus']").val();
	//获取数据
	zx_getOptionData(value,skbjval,"online",campusId);
}
function enSureSelect(){
		var idval="";
		try{
			var divCol = document.getElementsByTagName("input");
			for (var i=0;i<divCol.length;i++){
				if( divCol[i].type == "radio" && divCol[i].checked ){
					if(idval == ""){
						idval = divCol[i].value.split("|")[2];
					}
				}
			}
			$("#option-WinDialog").modal("hide");
		
		}catch(e){}
		return idval;
}
function reLogin(){
	$("#option-WinDialog").model("hide");
	//login($("#systemSno"),$("#systemPassword").val(),bt_login_academic);
	alert("手动刷新吧。。。");
}
function zx_getOptionData(value,skbjval,type,campusId){
	var winDiaLog=$("#option-WinDialog");
	var userLocalData = $('<h4 class="text-danger" > 你可以跳过检索数据，直接使用本地数据.<a href="javascript:void(0)" onclick="zx_getOptionData(\''+value+'\',\''+skbjval+'\',\'local\')"'+'>点击使用！</a></h4>');
	$(".useLocalData").html(userLocalData);
	var noneDataMsg = $('<h4 class="text-center text-danger" > 没有选课信息，可能是教务网没有该选课信息，也可能是网络错误.<a href="javascript:void(0)" onclick="zx_getOptionData(\''+value+'\',\''+skbjval+'\',\'online\')"'+'>点击刷新！</a></h4>');
	var outTimeMsg = $('<h4 class="text-center text-danger" > 选课助手忙不过来了，数据没请求到，请点击刷新.<a href="javascript:void(0)" onclick="zx_getOptionData(\''+value+'\',\''+skbjval+'\',\'online\')"'+'>点击刷新！</a></h4>');
	var loginOutTimeMsg = $('<h4 class="text-center text-danger" > 登录超时，请重新新登录教务网.<a href="javascript:void(0)" onclick="reLogin()">点击刷新！</a></h4>');
	var initMsgPanel=winDiaLog.find("#initMsgPanel");
	var optionListNode=winDiaLog.find("#optionList");
	var noticePanel=winDiaLog.find("#noticePanel");
	initMsgPanel.show();
	$.get(domain+"main/course/public/option?time="+new Date().getTime(), {
		"value":value,
		"skbjval" : skbjval,
		"type":type,
		"campus":campusId
	}, function(data) {
		if (data == "state:nothing") {
			optionListNode.empty();
			optionListNode.append(noneDataMsg);
			initMsgPanel.hide();
			return;
		}
		if(data=="system login out time"){
			optionListNode.empty();
			optionListNode.append(loginOutTimeMsg);
			initMsgPanel.hide();
			return;
		}
		if(data.indexOf("Request process time exceed th max limit")>=0){
			//请求超时..
			optionListNode.empty();
			optionListNode.append(outTimeMsg);
			initMsgPanel.hide();
			return;
		}
		var dataNode = "<span>出错啦！！</span>";
		try{
			dataNode = $(data);
		}catch (e) {
			dataNode = "<span>出错啦！！</span>";
		}
		initOptionData(dataNode);
		try {
			optionListNode.append(dataNode);
		} catch (e) {

		}
		initMsgPanel.hide();
		if(type=='local'){
			noticePanel.show();
		}
	});
}
function initOptionData(dataNode) {
	dataNode.children("#dataListDiv").children("table").find("table").addClass(
			"table table-bordered");

}
function CheckForm()
{
	var mFlag=true;
	var mb=false;
	var mcount;
	var m;
	mcount=FormAdd.mcount.value;
	if(mcount>0) {
		for(i=0;i<mcount;i++){
			if(eval("FormAdd.chkKC"+i+".checked==true")&&eval("FormAdd.chkKC"+i+".disabled==false"))
			{
				mb=true;
				if(mb==true)
				{
					if(eval("window.document.all.chkSKBJ"+i+".value==''"))
					{mFlag=false;}
				}
			}
		}
		if(mb==false){
			addActionResult("需选定一门课程！");
			return false;		
		}
		if(mFlag==false){
			addActionResult("需选定上课班号→任课教师！");
			return false;		
		}
		else{
			return ChkValue();
			return false;
		}
	}
}

function ChkValue()
{
	var strurl;
	var strid="TTT",s=false;
	var N;
	var jsskbj_str="";
	var jsskbj_array;
	var xyjc;
	N=FormAdd.mcount.value;
	for (i=1;i<=N;i++){
		if(eval("FormAdd.chkKC"+(i-1)+".checked==true")&&eval("FormAdd.chkKC"+(i-1)+".disabled==false")){
			eval("jsskbj_str=FormAdd.chkSKBJ"+(i-1)+".value;");
			if(jsskbj_str!=""){
				var jsskbj_array=jsskbj_str.split(";");
				for(j=0;j<jsskbj_array.length;j++){
					strid+=","+jsskbj_array[j]+"#"+eval("FormAdd.chkKC"+(i-1)+".value");
				}
			}
			s=true;
		}
	}
	if (!s)return false;
	if (!confirm('是否提交记录？'))return false;
	FormAdd.id.value=strid;
	return true;
}  
