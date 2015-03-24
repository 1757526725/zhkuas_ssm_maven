<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="tpl_myAlert" class="modal hide in" aria-hidden="false"
	style="display: none;">
	<div class="modal-header">
		<button data-dismiss="modal" class="close" type="button">×</button>
		<h3>@{title}</h3>
	</div>
	<div class="modal-body">
		<p>@{message}</p>
	</div>
	<div class="modal-footer">
		<a data-dismiss="modal" class="btn" href="#">确定</a> 
	</div>
</div>
<div id="tpl_myConfirm" class="modal hide in" aria-hidden="false"
	style="display: none;">
	<div class="modal-header">
		<button data-dismiss="modal" class="close" type="button">×</button>
		<h3>@{title}</h3>
	</div>
	<div class="modal-body">
		<p>@{message}</p>
	</div>
	<div class="modal-footer">
		<a data-dismiss="modal" class="btn btn-success ensure" href="#">确定</a> 
		<a data-dismiss="modal" class="btn" href="#">取消</a>
	</div>
</div>