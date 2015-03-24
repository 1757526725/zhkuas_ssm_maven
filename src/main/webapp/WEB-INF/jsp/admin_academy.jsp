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
				class="icon-home"></i> 首页</a> <a href="academys" class="current">学院信息</a>
		</div>
	</div>
	<!--End-breadcrumbs-->

	<!--container-fluid-->
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="btn-tools">
					<!--Action tools-->
					<button id="btn-delete" class="btn btn-success" disabled="disabled">
						<i class="icon-remove"></i> 删除选中
					</button>
					<button id="btn-update"  class="btn btn-success" originSize="${fn:length(organizationList)} ">
						<i class="icon-refresh"></i> 更新数据
					</button>
					<button id="btn-toDb"  class="btn btn-danger" style="display:none;">
						<i class="icon-save"></i> 合并到数据库
					</button>
					<div class="update-message" style="display:none;">
						<div>
							<span class="icon24 icomoon-icon-arrow-up-2 green">LOG:</span>
							<span class="message">计算科学学院 </span>
							<div class="progress progress-success active active progress-striped ">
								<div style="width: 72%;" class="bar">72%</div>
							</div>
						</div>
					</div>

					<!--End Action tools-->
				</div>
				<div class="widget-box">
					<div class="widget-content nopadding">
						<form action="#" method="post" class="form-horizontal"
							id="academy_form">
							<table class="table table-bordered table-striped with-check">
								<thead>
									<tr>
										<th><input type="checkbox" id="title-checkbox"
											name="title-checkbox">
										</th>
										<th>学院名称</th>
										<th>学院代号</th>
										<th>类型</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="o" items="${organizationList}">
									<tr>
										<td><input type="checkbox">
										</td>
										<td class="text-center">${o.name }</td>
										<td class="text-center">${o.no}</td>
										<td class="text-center"><c:choose><c:when test="${o.typeId==1}"><span class="label label-success">学院</span></c:when><c:otherwise><span class="label label-important">机构</span></c:otherwise></c:choose></td>
										<td class="text-center"><c:choose><c:when test="${o.typeId==1}"><a href="javascript:;">查看专业</a></c:when><c:otherwise></c:otherwise></c:choose>
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
			<td><input type="checkbox">
			</td>
			<td><input name="termName" type="text"
				value="@{beginDate}-@{endDate}年度第@{termOrder}学期" />
			</td>
			<td class="text-center"><input name="termNo" type="text"
				value="@{year}@{orderNum}" />
			</td>
			<td><input type="text" data-date="2014-9-1"
				data-date-format="yyyy-mm-dd" value="2014-9-1"
				class="beginDate datepicker span5"></td>
			<td><input type="text" data-date="2015-2-1"
				data-date-format="yyyy-mm-dd" value="2015-2-1"
				class="endDate datepicker span5">
				<div class="pull-right opt">
					<a href="javascript:void(0);" class="tip tip-top opt-ok"
						data-original-title="保存"> <i class="icon-ok"></i> </a> <a
						href="javascript:void(0);" class="tip tip-top opt-cancel"
						data-original-title="取消"> <i class="icon-remove"></i> </a>
				</div></td>
		</tr>
	</table>

</div>
<!-- end templete -->




<!--end-main-container-part-->
<jsp:include page="admin_footer.jsp"></jsp:include>
<script src="../../static/js/admin/page/school/academy.js"></script>
</body>
</html>