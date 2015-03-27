<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="header.jsp"></jsp:include>

<body>

	<!-- Wrap all page content here -->
	<div id="wrap">

		<jsp:include page="topBar.jsp"></jsp:include>

		<!-- Begin page content -->
		<div id="pageContent">
			<div class="container-narrow clearfix">
				<ul class="nav nav-tabs">
					<li class="active"><a href="passport?tab=basic">基本信息</a></li>
					<li><a href="passport?tab=avater">修改头像</a></li>

				</ul>

				<div class="tab-content">
					<div class="tab-pane active" id="basicMsg">


						<c:choose>
							<c:when test="${loginUser.student==null}">
								<div class="page-header">
									<h3>
										教务网信息 <small>(未绑定)</small>
									</h3>
								</div>
								<div class="bindForm">
									<div class="form-horizontal" role="form">
										<div class="form-group">
											<label for="academy_sno" class="col-sm-2 control-label">学号</label>
											<div class="col-sm-10">
												<input type="text" class="form-control" id="academy_sno"
													placeholder="学号">
											</div>
										</div>
										<div class="form-group">
											<label for="academy_pwd" class="col-sm-2 control-label">密码</label>
											<div class="col-sm-10">
												<input type="password" class="form-control"
													id="academy_pwd" placeholder="Password">
											</div>
										</div>
										<div class="form-group">
											<label for="academy_code" class="col-sm-2 control-label">验证码</label>
											<div class="col-sm-2">
												<input type="text" class="form-control"
													id="academy_code" placeholder="">
											</div>
											<div class="col-sm-2">
												<img id="vCode" style="width:70px;" onclick="changeValidateCode(this)" alt="验证码" src="${pageContext.request.contextPath}/network/validateCode">
											</div>
										</div>
										<div id="bind-summary" class="alert alert-danger text-left"
											style="display:none;"></div>

										<div class="form-group">
											<div class="col-sm-offset-2 col-sm-10">
												<button id="bind_ensure" data-loading-text="绑定中..."
													type="submit" class="btn btn-danger">绑定</button>
												<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#bindInfo">手动绑定教务网信息</button>
											</div>
										</div>
									</div>

								</div>
							</c:when>

							<c:otherwise>
								<div class="page-header">
									<h3>
										教务网信息 <small>(已绑定)</small>
									</h3>
									
								</div>
								<div class="table-responsive">
									<table class="table table-bordered" width="640" align="center"
										cellpadding="0" cellspacing="1">
										<tbody>

											<tr>
												<td width="92" align="center" valign="middle">学&nbsp;&nbsp;&nbsp;&nbsp;号</td>
												<td width="110">${loginUser.student.sNo}<br>
												</td>
												<td width="110" align="center" valign="middle">姓&nbsp;&nbsp;&nbsp;&nbsp;名</td>
												<td colspan="2">${loginUser.student.name} <br>
												</td>
											</tr>
											<tr>
												<td align="center" valign="middle">性&nbsp;&nbsp;&nbsp;&nbsp;别</td>
												<td>${loginUser.student.sex}<br>
												</td>
												<td align="center" valign="middle">专业</td>
												<td>${loginUser.student.classBelongTo.major.name}</td>

											</tr>
											<tr>
												<td align="center" valign="middle">入学时间</td>
												<td><br>
												</td>
												<td align="center" valign="middle">学&nbsp;&nbsp;&nbsp;&nbsp;制</td>
												<td>4 <br>
												</td>
											</tr>
											<tr>
												<td align="center" valign="middle">院(系)/部</td>
												<td>${loginUser.student.classBelongTo.major.academy.name}<br>
												</td>
												<td align="center" valign="middle">行政班级</td>
												<td>${loginUser.student.classBelongTo.name}<br>
												</td>

											</tr>
										</tbody>
									</table>
								</div>
							</c:otherwise>
						</c:choose>
						<div id="replaceNode"></div>

						<div class="page-header">
							<h3>系统资料</h3>
						</div>
						<div class="passportForm">
							<div class="form-horizontal" role="form">
								<div class="form-group">
									<label for="nickName" class="col-sm-2 control-label">昵称:</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="nickName"
											 value="${loginUser.nickName }" placeholder="昵称">
									</div>
								</div>
								<div class="form-group">
									<label for="description" class="col-sm-2 control-label">简介:</label>
									<div class="col-sm-10">
										<textarea type="text" rows="3" class="form-control"
											id="description"  placeholder="简介">${loginUser.description}</textarea>
									</div>
									<div class="form-group">
										<div class="col-sm-offset-2 col-sm-10">
											<button id="bt-ps-save" type="submit"
												data-loading-text="保存中..." class="btn btn-primary">保存</button>
										</div>
									</div>
								</div>
							</div>

						</div>

					</div>
				</div>
			</div>


		</div>
		<div style="clear:both;"></div>
	</div>
	<div class="modal fade" id="bindInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
            <!-- .modal-lg无法调成大尺寸弹出框？ -->
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">
                            <span aria-hidden="true">×</span>
                            <span class="sr-only">关闭</span>
                        </button>
                        <h4 class="modal-title" id="myModalLabel">请完善教务信息</h4>
                    </div>
                    <div class="modal-body clearfix">
                        <form role="form-horizontal" id= "bindInfoForm"class="col-md-12" action="#" method="GET">
                            <div class="form-group col-md-5">
                                <label class="col-md-3 control-label" for="userName">姓名</label>
                                <div class="col-md-9">
                                    <input type="text" class="form-control" id="userName" name="studentName" placeholder="请输入您的姓名">
                                </div>
                            </div>
                            <div class="form-group col-md-5 col-md-offset-1">
                                <label class="col-md-3 control-label">性别</label>
                                <div class="col-md-9">
                                    <select class="gender form-control" name="sex">
                                        <option value="男">男</option>
                                        <option value="女">女</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group col-md-5">
                                <label class="col-md-3 control-label" for="studentNo">学号</label>
                                <div class="col-md-9">
                                    <input type="text" class="form-control" id="studentNo" name ="sNo" placeholder="请输入您的学号">
                                </div>
                            </div>
                            <div class="form-group col-md-5 col-md-offset-1">
                                <label class="col-md-3 control-label">院系</label>
                                <div class="col-md-9">
                                    <select class="organization form-control"><option value="">请选择院系</option><option value="计算科学学院">计算科学学院</option><option value="轻工食品学院">轻工食品学院</option><option value="机电工程学院">机电工程学院</option><option value="管理学院">管理学院</option><option value="外国语学院">外国语学院</option><option value="化学化工学院">化学化工学院</option><option value="艺术设计学院">艺术设计学院</option><option value="人文社科系">人文社科系</option><option value="农学院">农学院</option><option value="城市建设学院">城市建设学院</option><option value="生命科学学院">生命科学学院</option><option value="环境科学与工程学院">环境科学与工程学院</option><option value="信息学院">信息学院</option><option value="经贸学院">经贸学院</option><option value="园艺园林学院">园艺园林学院</option><option value="自动化学院">自动化学院</option><option value="信息科学与技术学院">信息科学与技术学院</option><option value="园艺园林系">园艺园林系</option><option value="资源环境系">资源环境系</option><option value="计算机与电子工程学院">计算机与电子工程学院</option><option value="计算机科学与工程学院">计算机科学与工程学院</option></select>
                                </div>
                            </div>
                            <div class="form-group col-md-5">
                                <label class="col-md-3 control-label">专业</label>
                                <div class="col-md-9">
                                    <select class="major form-control">
                                        <option>请选择专业</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group col-md-5 col-md-offset-1">
                                <label class="col-md-3 control-label">班级</label>
                                <div class="col-md-9">
                                    <select class="clazz form-control" name="classNo">
                                        <option>请选择班级</option>
                                    </select>
                                </div>
                            </div>
                        </form>
                        <span class="error-message text-danger text-center" ></span>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" id="btn-save" class="btn btn-primary">保存</button>
                    </div>
                </div>
            </div>
        </div>
        <script>
            $.ajax({
                type: "GET",
                url: domain+"data/acamdeys/json",
                dataType: "json",
                success: function(data){
                    // 全部专业
                    var majors = [];
                    // 获取代表院系的下拉框
                    var organization = $(".organization");
                    // organization的长度
                    var length = data.result.length;
                    for(var i=0; i<length; i++){
                        var organizationName = data.result[i].name;
                        var organizationNo = data.result[i].no;
                        // 创建第二维数组保存同一个院系的全部专业
                        majors[i] = new Array();
                        // 添加name属性，院系的名字
                        majors[i].name = organizationName;
                        majors[i].no = organizationNo;
                        // 遍历每个学院的专业
                        for(var j=0; j<data.result[i].majorList.length; j++){
                            // 把每个专业放入对应的数组
                            majors[i].push(data.result[i].majorList[j]);
                        }
                    }

                    // 把所有院系添加到.organization
                    var dom = "<option value=''>请选择院系</option>";
                    for(var i=0; i<majors.length; i++){
                        dom += "<option value='" + majors[i].no + "'>" + majors[i].name + "</option>";
                    }
                    organization.html(dom);

                    // .organization下拉联动
                    organization.on("change", function(){
                        var organizationNo = $(this).val();
                        var major = $(".major");
                        var dom = "";
                        for(var i=0; i<majors.length; i++){
                            if(majors[i].no == organizationNo){
                                for(var j=0; j<majors[i].length; j++){
                                    dom += "<option value='" + majors[i][j].no+ "'>" + majors[i][j].name+ "</option>";
                                }
                            }
                        }
                        if(dom == ""){
                            dom = "<option value=''>请选择专业</option>";
                        }
                        major.html(dom);
                        major.change();
                    });
                     $(".major").on("change", function(){
                        var no = $(this).val();
                        var clazz = $(".clazz");
                        var dom = "";
                        if(no==""){
                        	clazz.html("<option value=''>请选择班级</option>");
                        	return;
                        }
                        $.ajax({
                        	url:domain+"data/major/"+no+"/class/json",
                        	type:"get",
                        	dataType:"json",
                        	success:function(data){
                        		var classes = data.result;
                        		 for(var i=0; i<classes.length; i++){
                        		 	 dom += "<option value='" + classes[i].no+ "'>" + classes[i].name+ "</option>";
                        		 
			                    }
		                        if(dom == ""){
		                            dom = "<option value=''>请选择班级</option>";
		                        }
			                    clazz.html(dom);
			               }
			                        	
	                     });
                       
                    });
                    
                    $("#btn-save").on("click",function(){
                    	$.ajax({
                    		url:domain+"main/user/bind/local",
                        	type:"post",
                        	data:$("#bindInfoForm").serialize(),
                        	dataType:"json",
                        	success:function(data){
                        		if(data.state==0){
                        			$(".error-message").html(data.error.error_description);
                        			return ;
                        		}
                        		if(data.state==1){
                        			location.reload();
                        		}
                        	},
                        	error:function(error){
                        		$(".error-message").html("保存失败请重试!");
                        	}
                    	});
                    });
                }
            });
            
        </script>
	<jsp:include page="footer.jsp"></jsp:include>
	<script src="${pageContext.request.contextPath}/static/js/passport.js"></script>
</body>
</html>