//将进度条的操作封装成一个组件
$(function(){
	var ProgressBar = function(){
		var _this;
		var _message_span;
		var _progressBar;
		var _currentProgress=0;
		var _maxSize=100;
		var _remainTask=[];
		var init  = function(__this){
			_this = $(__this);
			_message_span = _this.find("span.message");
			_progressBar = _this.find("div.bar");
		};
		var show =function(){
			_this.show();
		};
		var setCurrentProgress  =function(progress){
			_currentProgress = progress;
			temp =_currentProgress.toFixed(2);
			_progressBar.css("width",temp+"%");
			_progressBar.text(temp+"%");
		};
		var getCurrentProgress =function(){
			return _currentProgress;
		};
		var setMessage = function(message){
			_message_span.html(message);
		};
		var setMaxSize = function(maxSize){
			_maxSize = maxSize;
		};
		var getMaxSize = function(){
			return _maxSize;
		};
		var getRemainTask=function(){
			return _remainTask;
		};
		var addRemainTask=function(task){
			_remainTask.push(task);
		};
		return {
			init:init,
			setCurrentProgress:setCurrentProgress,
			getCurrentProgress:getCurrentProgress,
			setMessage:setMessage,
			show:show,
			setMaxSize:setMaxSize,
			getMaxSize:getMaxSize,
			getRemainTask:getRemainTask,
			addRemainTask:addRemainTask
		};
	};
	
	$.fn.extend({
		progressBar:function(){
			var _this=$(this);
			var progressBar =new ProgressBar();
			progressBar.init(_this);
			//ProgressBar.init(_this);
			return progressBar;
		}
	});
});
