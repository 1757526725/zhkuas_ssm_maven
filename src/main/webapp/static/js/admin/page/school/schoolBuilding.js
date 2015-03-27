$(function(){
	SchoolBuildingPage.main();
});

var SchoolBuildingPage = (function(){
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
			showConfirm("确认","是否进行数据抓取更新？",function(){
				_crawBuildingData();
			});
		});
	};
	//校区队列抓取 教学楼
	var _crawBuildingByQueue= function(queue,interval){
		if(queue==null) return;
		if(interval<0) interval=0;
		if(queue.constructor==Array){
			//如果是队列任务
			if(queue.length==0){
				//队列任务结束
				//请求服务器 整理 数据 
				Util.ajax.post(SITE_HOST+"/admin/school/buildings","JSON",{"step":1},function(data){
					if(data.state==1){
						var done = data.result.done;
						showConfirm("确认","抓取完成"+done+"坐教学楼,是否保存数据？",function(){
							Util.ajax.post(SITE_HOST+"/admin/school/buildings","JSON",{"step":2},function(data){
								if(data.state==1){
									var addCount = data.result.addCount;
									var updateCount = data.result.updateCount;
									showInfo("结果","添加了"+addCount+"坐，更新了"+updateCount+"座教学楼！",3000);
								}
							});
						});
					}
				});
				
				return ;
			}
			var task = queue.shift();
			//发动请求
			Util.ajax.put(SITE_HOST+"/admin/school/campus/"+task.id+"/buildings","JSON",null,function(data){
				//请求成功
					if(data.state==1){
						_progressBar.setCurrentProgress(((_progressBar.getCurrentProgress()/100)*_progressBar.getMaxSize()+1)*100/_progressBar.getMaxSize());
						_progressBar.setMessage(task.id+"："+task.name+"......"+data.result);
					}else{
						_progressBar.setMessage(task.id+"："+task.name+"......出错，加入未完成列表!");
						_progressBar.addRemainTask(task);
					}
					setTimeout(function(){
						_crawBuildingByQueue(queue,interval);
					},interval);
				},function(error){
				//请求失败，将任务添加到未完成任务中
					_progressBar.addRemainTask(task);
					setTimeout(function(){
						_crawBuildingByQueue(queue,interval);
					},interval);
			});
		}else{
			throw new Error("参数queue需要一个队列！");
		}
	};
	var _crawBuildingData=function (){
			//获取到所有的校区列表
			Util.ajax.get(SITE_HOST+"/data/campuses/json","JSON",null,function(data){
				//alert(academyList.shift());
				var campusList = data.result;
				var taskQueue =[];
				//将学院列表载入队列
				for(var i=0;i<campusList.length;i++){
					taskQueue.push(campusList[i]);
				}
				_progressBar.setCurrentProgress(0);
				_progressBar.setMaxSize(taskQueue.length);
				_progressBar.setMessage("校区列表加载完成,开始抓取教学楼信息.....");
				_progressBar.show();
				_crawBuildingByQueue(taskQueue,1000);
			});
	
	};
	return {
		main:main
	};
})();