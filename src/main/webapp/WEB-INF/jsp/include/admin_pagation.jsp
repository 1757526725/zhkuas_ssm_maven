<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.github.pagehelper.Page"%> 
<%
	Object ob = request.getAttribute("dataList");
	if (ob != null && ob instanceof Page) {
		Page pageList = (Page) ob;
		//大于一页的时候
		int pages = pageList.getPages();
		int pageNum = pageList.getPageNum();
		String url =(String) request.getAttribute("pagationUrl") ;
		if (pages > 1) {

			//前一页
			out.print("<div class=\"pagination\">");
			out.print("<ul>");
			if (pageNum == 1) {
				out.print("<li><a href=\"javascript:void(0);\">Prev</a></li>");
			} else {
				out.print("<li><a href=\"" + pageContext.getServletContext().getContextPath() + url+ (pageNum - 1) + "\">Prev</a></li>");
			}
			//页码 class="active"
			int index = pageNum < 8 ? 1 : (pageNum - 4);
			for (int i = index; i <= pages; ++i) {
				if (i - index >= 8) {
					if (i - index == 9)
						out.print("<li><a href=\"javascript:void(0);\">...</a></li>");
					else
						continue;
				} else {
					out.print("<li " + (i == pageNum ? "class=\"active\"" : "") + "><a href=\"" + pageContext.getServletContext().getContextPath()
							+ url + i + "\">" + i + "</a></li>");
				}
			}

			//后一页
			if (pageNum == pages) {
				out.print("<li><a href=\"javascript:void(0);\">Next</a></li>");
			} else {
				out.print("<li><a href=\"" + pageContext.getServletContext().getContextPath() + url + (pageNum + 1) + "\">Next</a></li>");
			}
			out.print("</ul>");
			out.print("</div>");
		}
	}
%>
