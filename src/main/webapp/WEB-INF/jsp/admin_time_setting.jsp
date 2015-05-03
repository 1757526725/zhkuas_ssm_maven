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
				class="icon-home"></i> 首页</a> <a href="#" class="current">选课时间设置</a>
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
						<i class="icon-plus"></i> 添加
					</button>
					<button id="deleteTerm" class="btn btn-success">
						<i class="icon-remove"></i> 删除选中
					</button>
					<button id="editTerm" class="btn btn-success">
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
										<th>序号</th>
										<th>批次</th>
										<th>开始时间</th>
										<th>结束时间</th>
										<th>涉及班级</th>
									</tr>
								</thead>
								<tbody>
									<tr>
									<th><input type="checkbox" id="title-checkbox"
											name="title-checkbox"></th>
										<td>1</td>
										<td>第一批选课</td>
										<td>2015-6-5</td>
										<td>2015-6-7</td>
										<td>
											大四全年级和何香凝艺术学院
										</td>
									</tr>
									<tr>
									<th><input type="checkbox" id="title-checkbox"
											name="title-checkbox"></th>
										<td>2</td>
										<td>第二批选课</td>
										<td>2015-6-9</td>
										<td>2015-6-11</td>
										<td>
											大三全年级
										</td>
									</tr>
									<tr>
									<th><input type="checkbox" id="title-checkbox"
											name="title-checkbox"></th>
										<td>3</td>
										<td>第三批选课</td>
										<td>2015-6-12</td>
										<td>2015-6-14</td>
										<td>
											大二全年级
										</td>
									</tr>
									<tr>
									<th><input type="checkbox" id="title-checkbox"
											name="title-checkbox"></th>
										<td>4</td>
										<td>第四批选课</td>
										<td>2015-6-15</td>
										<td>2015-6-18</td>
										<td>
											大一全年级
											除了何香凝艺术学院和部分海珠区的大一学生
										</td>
									</tr>
								
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