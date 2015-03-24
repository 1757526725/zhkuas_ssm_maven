var MenuBar = (function(){
	var init = function(){
		_initPage();
		_initEvent();
	};
	
	var _initPage = function(){
		// MenuBar Page
		//get url
		var current_url = window.location.href;
		//迭代menu .找到当前 url 对应的 li标签
		var sidebar =$("#sidebar");
		var lis =sidebar.children("ul").children("li");
		var resultLi = null;
		for(var i=0;i<lis.length;i++){
			var li=lis.eq(i);
			var isFind =false;
			if(li.hasClass("submenu")){
				//如果是有二级菜单 进入搜索子菜单
				var subLis=li.find("li");
				for(var j=0;j<subLis.length;j++){
					var url=subLis.eq(j).children("a").first().attr("href");
					var menuUrlLast=url.substring(url.lastIndexOf("/")+1);

					//新算法 2014-12-28
					//将url分割,逐级匹配

					var urlLast= current_url.substring(current_url.lastIndexOf("/")+1);
					if(current_url.indexOf(url)>=0||menuUrlLast == urlLast){
						resultLi=subLis.eq(j);
						isFind =true;
						break;
					}
				}
			}else{
				var url=li.children("a").first().attr("href");
				if(current_url.indexOf(url)>=0){
					resultLi=li;
					isFind =true;
					break;
				}
			}
			
			if(isFind){
				break;
			}
			
		}
		
		//找到节点后 做样式的处理
		if(resultLi!=null){
			//先移除 默认的 active
			sidebar.find(".active").removeClass("active");
			if(resultLi.parent().parent().hasClass("submenu")){
				resultLi.parent().slideDown(1000);
				resultLi.parent().parent().addClass("open");
			}
			resultLi.addClass("active");
		}
	};
	
	var _initEvent = function(){
		// MenuBar Events
	};
	
	return{
		init:init
	};
})();