$(function(){
	MajorPage.main();
});

var MajorPage = (function(){
	var _btn_update =$("#btn-update");
	var _progressBar =null;
	var main = function(){
		_initPage();
		_initEvent();
	};
	var _initPage= function(){
		_progressBar=$('.update-message').progressBar();
	};
	var _initEvent = function(){
		//更新按钮
		_btn_update.click(function(){
			//获取到当前页面的学院数 
			var academyNo = $("#iptAcademyNo").val();
			var academyName = $("#iptAcademyName").val();
			showConfirm("确认","你当前选择的是<span style='color:red;'>"+(academyNo==null||academyNo==""?"全部学院":academyName)+"</span>,是否进行数据抓取更新？",function(){
				if(academyNo==null||academyNo=="") {
					_crawMajorData(null);
				}else{
					_crawMajorData([{'name':academyName,'no':academyNo}]);
				}
			});
		});
		
		$("#major_form").on("click","a",function(){
			//获取班级号
			var majorNo=$(this).parent().parent().children().eq(2).text();
			window.location.href=SITE_HOST+"/admin/school/major/"+majorNo+"/classes";
		});
	};
	var _crawMajorsByQueue= function(queue,interval){
		if(queue==null) return;
		if(interval<0) interval=0;
		if(queue.constructor==Array){
			//如果是队列任务
			if(queue.length==0){
				//队列任务结束
				//请求服务器 整理 数据 
				Util.ajax.put(SITE_HOST+"/admin/school/majors","JSON",null,function(data){
					if(data.state==1){
						var done = data.result.done;
						var all = data.result.all;
						showConfirm("确认","一共"+all+"个学院,"+done+"个抓取完成,是否保存数据？",function(){
							Util.ajax.put(SITE_HOST+"/admin/school/majors","JSON",{"event":"save"},function(data){
								if(data.state==1){
									var addCount = data.result.addCount;
									var updateCount = data.result.updateCount;
									showInfo("结果","添加了"+addCount+"个，更新了"+updateCount+"个专业！",3000);
								}
							});
						});
					}
				});
				return ;
			}
			var task = queue.shift();
			//发动请求
			Util.ajax.put(SITE_HOST+"/admin/school/majors/"+task.no,"JSON",null,function(data){
				//请求成功
					if(data.state==1){
						_progressBar.setCurrentProgress(((_progressBar.getCurrentProgress()/100)*_progressBar.getMaxSize()+1)*100/_progressBar.getMaxSize());
						_progressBar.setMessage(task.no+"："+task.name+"......"+data.result);
					}else{
						_progressBar.setMessage(task.no+"："+task.name+"......出错，加入未完成列表!");
						_progressBar.addRemainTask(task);
					}
					setTimeout(function(){
						_crawMajorsByQueue(queue,interval);
					},interval);
				},function(error){
				//请求失败，将任务添加到未完成任务中
					_progressBar.addRemainTask(task);
					setTimeout(function(){
						_crawMajorsByQueue(queue,interval);
					},interval);
			});
		}else{
			throw new Error("参数queue需要一个队列！");
		}
	};
	var _crawMajorData=function (academyQueue){
		//如果 academyNo == null 这进行全部学院的更新
		if(academyQueue==null){
			//获取到所有的学院列表
			Util.ajax.get(SITE_HOST+"/admin/school/academys/json","JSON",null,function(data){
				//alert(academyList.shift());
				var academyList = data.result;
				var taskQueue =[];
				//将学院列表载入队列
				for(var i=0;i<academyList.length;i++){
					taskQueue.push(academyList[i]);
				}
				_progressBar.setCurrentProgress(0);
				_progressBar.setMaxSize(taskQueue.length);
				_progressBar.setMessage("学院列表加载完成,开始抓取专业信息.....");
				_progressBar.show();
				_crawMajorsByQueue(taskQueue,1000);
			});
		}else{
			_crawMajorsByQueue(academyQueue,1000);
		}
	};
	return {
		main:main
	};
})();