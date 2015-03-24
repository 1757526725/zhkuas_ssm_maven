/**
 * @Author JackCan
 * 针对 当前管理系统的ajax 相关 方法进行封装
 * 需要common.js jquery.js boostrap-gritter.js
 * 基于Jquery 
 * 基于bootstrap-gritter --->做提示信息用
 * 2014-8-20
 */
window.Util = window.Util || {};
Util.ajax={
	ajax:function(url,type,dataType,data,success,error){
		data =data||{};
		if(type=='PUT'){
			type='POST';
			data._method='put';
		}else if(type=='DELETE'){
			type='POST';
			data._method='delete';
		}
		var settings={'url':url,'type':type,'dataType':dataType,'data':data};
		if(error==null){
			settings.error=function(msg){
				//这里应该对错误进行解析
				showError("错误",msg.status+"："+msg.statusText,3000);
			};
		}else{
			settings.error=error;
		}
		
		settings.success=function(data){
			//先解析服务器回传状态 data的结构。正确结果:{state:1,result:""},错误:{state:0,error:{'code':'',error:'',description:""}};
			
			var jsonData ={};
			if(typeof data == 'object'){
				jsonData=data;
			}else{
				try{
					jsonData = eval("'"+data+"'");
				}catch(e){
					showError("服务器回传的数据处理失败",data,5000);
					return;
				}
			}
			
			if(jsonData.state==0){
				showError(jsonData.error.error,"错误代码:"+jsonData.error.error_code+"<br/>"+jsonData.error.error_description,3000);
				return;
			};
			success(jsonData);
		};
		$.ajax(settings);
	},
	get:function(url,dataType,data,success,error){Util.ajax.ajax(url,"GET",dataType,data,success,error);},
	post:function(url,dataType,data,success,error){Util.ajax.ajax(url,"POST",dataType,data,success,error);},
	put:function(url,dataType,data,success,error){Util.ajax.ajax(url,"PUT",dataType,data,success,error);},
	del:function(url,dataType,data,success,error){Util.ajax.ajax(url,"DELETE",dataType,data,success,error);}
};