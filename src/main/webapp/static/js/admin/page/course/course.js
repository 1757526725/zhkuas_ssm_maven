$(function() {
	CoursePage.main();
});

var CoursePage = (function() {
	var _btn_update = $("#btn-update");
	var _progressBar = null;
	var main = function() {
		_initPage();
		_initEvent();
	};
	var _initPage = function() {
		_progressBar = $('.update-message').progressBar();
	};
	var _initEvent = function() {
		// 更新按钮
		_btn_update.click(function() {
			_showVCode();
		});
		
		$("#btn-start").click(function(){
			//检测选择
			var terms =$(":input[checked]");
			if(terms.length==0){
				showError("错误","必须选择一个学期进行抓取",2000);
				return ;
			}
			
			if($("#text-vCode").val()==null){
				showError("错误","验证码不能为空!",2000);
				return ;
			}
			showConfirm("确认", "是否进行数据抓取更新？", function() {
				_crawCourseData();
			});
		});
		
		$("#img-vcode").click(function(){
			$(this).attr("src",SITE_HOST+"/network/validateCode?t="+new Date());
		});
	};
	var _interval=null;
	var _showProgessBar = function(){
		//启动定时器去获取进度
		_progressBar.show();
		_interval = setInterval(function(){
			Util.ajax.get(SITE_HOST + "/admin/course/task/progress", "JSON", null,function(data){
				if(data.state==1){
					_progressBar.setCurrentProgress(data.result);
				}
			});
		},2000);
	};
	var _crawCourseData = function(){
		_showProgessBar();
		//step 1: 登录 第一步
		Util.ajax.put(SITE_HOST + "/admin/course/courses", "JSON", {step:1,vCode:$("#text-vCode").val()}, function(data) {
			if(data.state==1){
				showInfo("提示","登录成功，获取到教务网权限!",4000);
				//开始抓取教室信息
				//构造队列
				var termArray =[];
				var terms = $(":input[checked]");
				for ( var int = 0; int < terms.length; int++) {
					var term = terms.eq(int);
					termArray.push(term.val());
				}
				//第二步
				_progressBar.setMessage("开始课程基础信息.....");
				var jsonTermNos=JSON.stringify(termArray);
				Util.ajax.put(SITE_HOST + "/admin/course/courses", "JSON", {step:2,termNos:jsonTermNos}, function(data) {
					_progressBar.setMessage("抓取课程基础信息....."+data.result);
					//第三步
					Util.ajax.put(SITE_HOST + "/admin/course/courses", "JSON", {step:3}, function(data) {
						_progressBar.setMessage("待抓取课程数....."+data.result.all);
						Util.ajax.put(SITE_HOST + "/admin/course/courses", "JSON", {step:4}, function(data) {
							_progressBar.setMessage("完成:"+data.result.done+" 失败:"+data.result.remain);
							_progressBar.setCurrentProgress(80);
							showConfirm("确认", "完成:"+data.result.done+" 失败:"+data.result.remain+",是保存到数据库？", function() {
								_saveToDb(); //第四步 保存到数据库
							});
							clearInterval(_interval);
						},_stopProgress);
					},_stopProgress);
				},_stopProgress);
			}
		},_stopProgress);
	};
	var _saveToDb =function(){
		_progressBar.setMessage("数据保存中...");
		_showProgessBar(); //启动进度条
		Util.ajax.post(SITE_HOST + "/admin/course/courses", "JSON", null, function(data) {
			_progressBar.setMessage("新添加:"+data.result.addCount+" 已存在:"+data.result.existCount);
			clearInterval(_interval);
			_progressBar.setCurrentProgress(100);
		},_stopProgress);
	}
	var _stopProgress= function(error){
		clearInterval(_interval);
	};
	var _showVCode= function(){
		$("#img-vcode").attr("src",SITE_HOST+"/network/validateCode");
		$("#vcodeForm").show(500);
		_btn_update.hide();
	};
	var _crawClassroomData = function() {
		// 获取到所有的校区列表
		Util.ajax.get(SITE_HOST + "/data/buildings/json", "JSON", null,
				function(data) {
					// alert(academyList.shift());
					var campusList = data.result;
					var taskQueue = [];
					// 将学院列表载入队列
					for ( var i = 0; i < campusList.length; i++) {
						taskQueue.push(campusList[i]);
					}
					_progressBar.setCurrentProgress(0);
					_progressBar.setMaxSize(taskQueue.length);
					_progressBar.setMessage("教学楼列表加载完成,开始抓取教室信息.....");
					_progressBar.show();
					_crawClassroomByQueue(taskQueue, 1000);
				});

	};

	return {
		main : main
	};
})();