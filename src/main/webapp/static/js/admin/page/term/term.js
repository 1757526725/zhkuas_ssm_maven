$(document).ready(function() {
	TermPage.main();
});
var TermPage = (function() {
	var _ckb_selectAll = $("#title-checkbox");
	var _btn_addTerm = $("#addTerm");
	var _btn_deleteTerm = $("#deleteTerm");
	var _btn_editTerm = $("#editTerm");
	var _btn_setDefault = $("#setDefault");
	var _tpl_form_tr = $("#tpl_form_term_ipt").find("tr").eq(0);
	var _tbody_of_term_form = $("#term_form").find("tbody");
	var _form_beginDate;
	var _form_endData;
	var main = function() {
		_initPage();
		_initEvent();
	};
	var _initPage = function() {
		$("#deleteTerm").attr("disabled","disabled");
		$("#editTerm").attr("disabled","disabled");
		$("#setDefault").attr("disabled","disabled");
	};
	var _setOperatorBtnDiasabed=function(){
		$(".btn-tools>.btn").attr("disabled","disabled");
	};
	
	var _setOperatorBtnEnabled = function(){
		$(".btn-tools>.btn").removeAttr("disabled");
	};
	
	var _initEvent = function() {

		_ckb_selectAll.click(_selectAll);
		_btn_addTerm.click(_showAddTermForm);
		
		//给起始时间的改变添加事件。
		$("#term_form").on("changeDate",".beginDate",function(ev){
			var beginDate = ev.date;
			var endDateDp=$("#term_form").find(".endDate").first();
			var beginDateDp=$("#term_form").find(".beginDate").first();
			var year = beginDate.getFullYear();
			var month = beginDate.getMonth();
			var date = beginDate.getDate();
			var dateVal = year+"-"+(month+1)+"-"+date;
			$(this).datepicker('setValue', dateVal);
			_form_beginDate=dateVal;
			//beginDateDp.val(dateVal);
			if(beginDate>new Date(endDateDp.val())){
				endDateDp.datepicker('setValue', dateVal);
				_form_endDate=dateVal;

			}
		/*	//学期代号的智能变动。
			var termNoStr=year+(month>=8?"0":"1");
			$("#term_form").find("input[name='termNo']").val(termNoStr);*/
			//学期名称的智能变动。
			/*var termNameStr=(month>=8?year:(year-1))+"-"+endDateDp.val().substr(0,4)+"年度第"+(month>=8?"一":"二")+"学期";
			$("#term_form").find("input[name='termName']").val(termNameStr);*/
		});
		$("#term_form").on("changeDate",".endDate",function(ev){
			var beginDate = ev.date;
			var endDateDp=$("#term_form").find(".endDate").first();
			var beginDateDp=$("#term_form").find(".beginDate").first();
			var year = beginDate.getFullYear();
			var month = beginDate.getMonth();
			var date = beginDate.getDate();
			var dateVal = year+"-"+(month+1)+"-"+date;
			$(this).datepicker('setValue', dateVal);
			_form_endDate=dateVal;
			//beginDateDp.val(dateVal);
			if(beginDate>new Date(endDateDp.val())){
				beginDateDp.datepicker('setValue', dateVal);
				_form_beginDate=dateVal;

			}
		});
		//取消按钮
		$("#term_form").on("click",".opt-cancel",function(){
			_removeAddTermForm();
		});
		//保存按钮
		$("#term_form").on("click",".opt-ok",function(){
			if($(this).parent().parent().parent().attr("termno")!=null){
				_ensureUpdate();
			}else{
				_ensureAdd();
			}
		});
		//选择按钮
		$("#term_form").on("click","input[type=checkbox]",function(){
			
			var ckbox=$("#term_form").find(":input[type=checkbox]");
			var selectCount=0;
			for(var i=0;i<ckbox.size();i++){
				if(ckbox.eq(i).prop("checked")){
					selectCount++;
				}
			}
			if(selectCount==1){
				_setOperatorBtnEnabled();
			}else if(selectCount>1){
				_setOperatorBtnDiasabed();
				_btn_addTerm.removeAttr("disabled");
				_btn_deleteTerm.removeAttr("disabled");
			}else{
				_setOperatorBtnDiasabed();
				_btn_addTerm.removeAttr("disabled");
			}
		});
		//删除按钮
		_btn_deleteTerm.click(function(){
			var termNos="";
			//找到选中的checkbox
			var ips_selected = $(":checkbox[checked]");
			//迭代上值,拼凑一个termNo的数组
			for(var i=0;i<ips_selected.size();i++){
				var termNo =ips_selected.eq(i).parent().parent().attr("termno");
				//将多个no值 做 合并用 -连接;
				if(termNo==null||termNo=='undefined') continue;
				if(termNos==""){
					termNos+=termNo;
				}else{
					termNos=termNos+"-"+termNo;
				}
			}
			_deleteTerms(termNos);
		});
		
		//编辑按钮
		_btn_editTerm.click(function(){
			//获取选中的 tr 
			var ckbox = $(":input[checked=checked]").last();
			var termTr=null;
			if(ckbox!=null){
				termTr =ckbox.parent().parent();
				var termId=$.trim(termTr.attr("termId"));
				var termNo=$.trim(termTr.attr("termno"));
				var termName=$.trim(termTr.children("td").eq(1).text().replace('默认',''));
				var termBeginTime=$.trim(termTr.children("td").eq(3).text());
				var termEndTime=$.trim(termTr.children("td").eq(4).text());
				var tp_tr=_getDefaultTemplet(termId,termNo,termName,termBeginTime,termEndTime);
				termTr.remove();
				_tbody_of_term_form.append(tp_tr);
				_setOperatorBtnDiasabed();
			}
		});
		
		
	};
	
	var _removeAddTermForm=function(){
		$("#term_form").find(".opt").parent().parent().remove();
		//回复按钮组的状态
		_setOperatorBtnEnabled();
	};
	var _getDefaultTemplet = function(id,termNo,termName,beginDate,endDate) {
		var tpl = _tpl_form_tr.clone(false);
		var currentDate = new Date();
		//如果是添加学期
		if(id==null||id == 'undefined'){
			var year = currentDate.getFullYear();
			var month = currentDate.getMonth();
			var date = currentDate.getDate();
			// 初始化模板
			tpl = $(tpl[0].outerHTML.replace(/@{beginDate}/g, year).replace(/@{endDate}/g, year + 1).replace(/@{termOrder}/g, "month<9" ? "二" : "一").replace(/@{year}/g,year).replace(/@{orderNum}/g, month<9? "1" : "0")); // 故意调转过来。
		}else{
			//如果是编辑当前学期
			tpl = $(tpl[0].outerHTML.replace("@{beginDate}-@{endDate}年度第@{termOrder}学期", termName).replace("@{year}@{orderNum}",termNo).replace(/@{beginDate}-9-1/g,beginDate).replace(/@{endDate}-2-1/g,endDate)); // 故意调转过来。
			tpl.attr("termno",termNo);
			tpl.attr("termId",id);
			
		}
		
		// 注册事件
		tpl.find(".beginDate").datepicker();
		tpl.find(".endDate").datepicker();
		tpl.find("a.tip").tooltip();
		return tpl;
	};
	var _showAddTermForm = function() {
		// 显示添加的一行
		_tbody_of_term_form.append(_getDefaultTemplet());
		// 设置btn为disable
		_setOperatorBtnDiasabed();
	};
	//
	var _selectAll = function() {
		$(":input[type='checkbox']").prop('checked',
			_ckb_selectAll.prop("checked"));
	};
	//确定添加
	var _ensureAdd=function(){
		//构造ajax -->data method=post
		var postData={};
		postData.termName=$("#term_form").find("input[name='termName']").val();
		postData.termNo=$("#term_form").find("input[name='termNo']").val();
		postData.beginDate=$("#term_form").find("input.beginDate").val();
		postData.endDate=$("#term_form").find("input.endDate").val();
//		showInfo("测试",postData.termName+"  "+postData.termNo+"  "+postData.beginDate+"  "+postData.endDate,2000);
		var url="term";
		Util.ajax.post(url,"JSON",postData,function(jsonData){
			//服务器访问添加的学期数据.. 
			showInfo("消息","添加成功！",3000);
			var term=jsonData.result;
			_insertARow(term);
			//去除添加行.
			_removeAddTermForm();
		});
		
	};
	//确定更新
	var _ensureUpdate=function(){
		//构造ajax -->data method=post(put)
		var postData={};
		postData.termId=$("#term_form").find("input[name='termName']").parent().parent().attr("termid");
		postData.termName=$.trim($("#term_form").find("input[name='termName']").val());
		postData.termNo=$.trim($("#term_form").find("input[name='termNo']").val());
		postData.beginDate=_form_beginDate||$("#term_form").find("input.beginDate").val();
		postData.endDate=_form_endDate||$("#term_form").find("input.endDate").val();
		
		$("#term_form").find("input.beginDate").val(postData.beginDate);
		$("#term_form").find("input.endDate").val(postData.endDate);
//		showInfo("测试",postData.termName+"  "+postData.termNo+"  "+postData.beginDate+"  "+postData.endDate);
		var url="term";
		Util.ajax.put(url,"JSON",postData,function(jsonData){
			//服务器访问添加的学期数据.. 
			showInfo("消息","更新成功！",3000);
			var term=jsonData.result;
			_insertARow(term);
			//去除添加行.
			_removeAddTermForm();
		});
		
	};
	//删除选中学期
	var _deleteTerms=function(termNos){
		if(termNos!=null){
			var url ="term/"+termNos+"/";
			Util.ajax.del(url,"JSON","",function(jsondata){
				try{
					showInfo("消息",jsondata.result,5000);
					if(jsondata.state==1){
						var nos=termNos.split("-");
						for ( var no in nos) {
							$("tr[termno="+nos[no]+"]").remove();
						}
					}
				}catch(e){
					showError("错误","服务器消息回传处理失败",4000);
				};
				
				
			});
		};
	};
	var _removeArow=function(termNos){
		
	};
	var _insertARow=function(term){
		var row="<tr termNo=\""+term.no+"\">"+
			"<td><input type=\"checkbox\"></td>"+
			"<td>"+term.name+"</td>"+
			"<td class=\"text-center\">"+term.no+"</td>"+
			"<td>"+new Date(term.beginDate).Format("yyyy-MM-dd")+"</td>"+
			"<td>"+new Date(term.endDate).Format("yyyy-MM-dd")+"</td>"+
			"</tr>";
		_tbody_of_term_form.append(row);
	};
	return {
		main : main
	};
})();