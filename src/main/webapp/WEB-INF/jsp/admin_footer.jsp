<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!--Footer-part-->

<div class="row-fluid">
  <div id="footer" class="span12"> 2014 &copy; JACKCAN theme by Matrix Admin. Brought to you by <a href="http://themedesigner.in/">Themedesigner.in</a> </div>
</div>

<!--end-Footer-part-->
<script type="text/javascript">
<!--
	var SITE_HOST ="${pageContext.request.contextPath}";
//-->
</script>
<script src="${pageContext.request.contextPath}/static/js/admin/lib/excanvas.min.js"></script> 
<script src="${pageContext.request.contextPath}/static/js/admin/lib/jquery.min.js"></script> 
<script src="${pageContext.request.contextPath}/static/js/admin/lib/jquery.ui.custom.js"></script> 
<script src="${pageContext.request.contextPath}/static/js/admin/lib/bootstrap.min.js"></script> 
<script src="${pageContext.request.contextPath}/static/js/admin/lib/jquery.flot.min.js"></script> 
<script src="${pageContext.request.contextPath}/static/js/admin/lib/jquery.flot.resize.min.js"></script> 
<script src="${pageContext.request.contextPath}/static/js/admin/lib/jquery.peity.min.js"></script> 
<script src="${pageContext.request.contextPath}/static/js/admin/lib/fullcalendar.min.js"></script> 
<script src="${pageContext.request.contextPath}/static/js/admin/lib/jquery.gritter.min.js"></script> 
<script src="${pageContext.request.contextPath}/static/js/admin/lib/matrix.js"></script> 
<script src="${pageContext.request.contextPath}/static/js/admin/common/common.js"></script> 
<script src="${pageContext.request.contextPath}/static/js/admin/common/util.ajax.js"></script> 
<!-- <script src="${pageContext.request.contextPath}/static/js/admin/lib/matrix.dashboard.js"></script> 

<script src="${pageContext.request.contextPath}/static/js/admin/lib/matrix.interface.js"></script> 
<script src="${pageContext.request.contextPath}/static/js/admin/lib/matrix.chat.js"></script> 
<script src="${pageContext.request.contextPath}/static/js/admin/lib/jquery.validate.js"></script> 
<script src="${pageContext.request.contextPath}/static/js/admin/lib/matrix.form_validation.js"></script> 
<script src="${pageContext.request.contextPath}/static/js/admin/lib/jquery.wizard.js"></script> 
<script src="${pageContext.request.contextPath}/static/js/admin/lib/jquery.uniform.js"></script> 
<script src="${pageContext.request.contextPath}/static/js/admin/lib/select2.min.js"></script> 
<script src="${pageContext.request.contextPath}/static/js/admin/lib/matrix.popover.js"></script> 
<script src="${pageContext.request.contextPath}/static/js/admin/lib/jquery.dataTables.min.js"></script> 
<script src="${pageContext.request.contextPath}/static/js/admin/lib/matrix.tables.js"></script>  -->
 
<!-- import module hare-->
<script src="${pageContext.request.contextPath}/static/js/admin/module/menuBar/menuBar.js"></script> 
<script src="${pageContext.request.contextPath}/static/js/admin/module/progressBar/progressBar.js"></script> 
<script type="text/javascript">
	//init the sidebar
	//alert("before menubar init");
	MenuBar.init();
	//alert("after menubar init");
  // This function is called from the pop-up menus to transfer to
  // a different page. Ignore if the value returned is a null string:
	  function goPage (newURL) {
	
	      // if url is empty, skip the menu dividers and reset the menu selection to default
	      if (newURL != "") {
	      
	          // if url is "-", it is this page -- reset the menu:
	          if (newURL == "-" ) {
	              resetMenu();            
	          } 
	          // else, send page to designated URL            
	          else {  
	            document.location.href = newURL;
	          }
	      }
	  }

	// resets the menu selection upon entry to this page:
	function resetMenu() {
	   document.gomenu.selector.selectedIndex = 2;
	}
	
</script>
