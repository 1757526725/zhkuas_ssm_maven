<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="admin_header.jsp"></jsp:include>
<jsp:include page="admin_sideBar.jsp"></jsp:include>


<!--main-container-part-->
<div id="content">
	<!--breadcrumbs-->
	<div id="content-header">
		<div id="breadcrumb">
			<a href="index.html" title="Go to Home" class="tip-bottom"><i
				class="icon-home"></i> 首页</a> <a href="#" class="current">学期信息</a>
		</div>
	</div>
	<!--End-breadcrumbs-->

	<!--container-fluid-->
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="btn-tools">
					<!--Action tools-->
					<button id="addTerm" class="btn btn-success">
						<i class="icon-plus"></i> 添加一个学期
					</button>
					<button id="deleteTerm" class="btn btn-success">
						<i class="icon-remove"></i> 删除选中
					</button>
					<button id="editTerm" class="btn btn-success">
						<i class="icon-edit"></i> 编辑
					</button>
					<button id="setDefault" class="btn btn-success">
						<i class="icon-time"></i> 设为当前学期
					</button>
					<!--End Action tools-->
				</div>
				<div class="widget-box">
					<div class="widget-content nopadding">
						<form action="#" method="post" class="form-horizontal" id="term_form">
							<table class="table table-bordered table-striped with-check">
								<thead>
									<tr>
										<th><input type="checkbox" id="title-checkbox"
											name="title-checkbox"></th>
										<th>学期名称</th>
										<th>学期代号</th>
										<th>开始时间</th>
										<th>结束时间</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="term" items="${terms}">
										<tr termNo="${term.no }" termId="${term.id }">
											<td><input type="checkbox"></td>
											<td>${term.name}<c:if test="${term.current}">
													<span class="pull-right badge badge-important">默认</span>
												</c:if>
											</td>
											<td class="text-center">${term.no }</td>
											<td><fmt:formatDate value="${term.beginDate }"
													pattern="yyyy-MM-dd " />
											</td>
											<td><fmt:formatDate value="${term.endDate }"
													pattern="yyyy-MM-dd " />
											</td>
										</tr>

									</c:forEach>
								</tbody>
							</table>
						</form>
					</div>
				</div>

			</div>
		</div>
	</div>
	<!--End container-fluid-->
</div>
<!-- templete -->
<div id="tpl" style="display:none">
	<table id="tpl_form_term_ipt">
		<tr>
			<td><input type="checkbox"></td>
			<td><input name="termName" type="text" value="@{beginDate}-@{endDate}年度第@{termOrder}学期"/></td>
			<td class="text-center"><input name="termNo" type="text" value="@{year}@{orderNum}"/></td>
			<td><input type="text" data-date-format="yyyy-mm-dd" data-date="@{beginDate}-9-1" value="@{beginDate}-9-1"
				class="beginDate datepicker span5">
			</td>
			<td><input type="text"  data-date-format="yyyy-mm-dd" data-date="@{beginDate}-9-1" value="@{beginDate}-9-1"
				class="endDate datepicker span5">
				<div class="pull-right opt">
					<a href="javascript:void(0);" class="tip tip-top opt-ok" data-original-title="保存"> <i class="icon-ok"></i> </a>
					<a href="javascript:void(0);" class="tip tip-top opt-cancel" data-original-title="取消"> <i class="icon-remove"></i> </a>
				</div>
			</td>
		</tr>
	</table>
	
</div>
<!-- end templete -->




<!--end-main-container-part-->
<jsp:include page="admin_footer.jsp"></jsp:include>
<script src="../static/js/admin/lib/bootstrap-datepicker.js"></script>
<script src="../static/js/admin/page/term/term.js"></script>
</body>
</html>