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
				class="icon-home"></i> 首页</a> <a href="#" class="current">校区</a>
		</div>
	</div>
	<!--End-breadcrumbs-->

	<!--container-fluid-->
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="btn-tools">
					<!--Action tools-->
					<button id="updateCampus" class="btn btn-success">
						<i class="icon-refresh"></i> 更新
					</button>
					<button id="deleteCampus" class="btn btn-success">
						<i class="icon-remove"></i> 删除选中
					</button>
					<button id="editCampus" class="btn btn-success">
						<i class="icon-edit"></i> 编辑
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
										<th>校区名称</th>
										<th>校区代号</th>
										<th>教学楼</th>
										<th>班级</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="campus" items="${campuses}">
										<tr  campusId="${campus.id }">
											<td><input type="checkbox"></td>
											<td class="text-center">${campus.name}</td>
											<td class="text-center">${campus.id }</td>
											<td class="text-center">${fn:length(campus.buildingList)}</td>
											<td class="text-center">${fn:length(campus.classList)}</td>
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
</body>
</html>