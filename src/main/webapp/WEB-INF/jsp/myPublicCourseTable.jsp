<%@page import="com.zhku.bean.PublicCourseType"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.zhku.bean.MyPublicCourse"%>
<%@ page language="java" import="com.zhku.bean.Course"%>
<%@ page language="java" import="com.zhku.bean.CourseTypeRecord"%>
<%@ page language="java" import="com.zhku.bean.PublicCourseProfiles"%>
<%
	List<MyPublicCourse> list = (List<MyPublicCourse>) request
			.getAttribute("list");
	int size[] =(int [])request.getAttribute("size");
	double credit[] =(double [])request.getAttribute("credit");
	double countCredit=(Double)request.getAttribute("countCredit");
	int count = 1;
	int index = 0;
%>
<%
	if (list != null && list.size() > 0) {
%>

<table id="oTable" class="table table-bordered" >
	<tbody>
		<tr>
			<td>编号</td>
			<td>课程名</td>
			<td>选修类型</td>
			<td>学分</td>
			<td>成绩</td>
			<td>分类学分</td>
			<td>总学分</td>
		</tr>
		<%
			for (int i = 0; i < list.size(); ++i) {
					String type = list.get(i).getCourse().getCourseProfiles()
							.getPcType().getName();
		%>
		<tr>
			<td><%=count++%></td>
			<td>[<%=list.get(i).getCourse().getNameNo()%>]<%=list.get(i).getCourse().getName()%></td>
			<td><%=type%></td>
			<td><%=list.get(i).getCourse().getCredits()%></td>
			<td><%=list.get(i).getScore()==null?"未知":list.get(i).getScore()%></td>
			<%
				if (i == 0
								|| !type.equals(list.get(i - 1).getCourse()
										.getCourseProfiles().getPcType()
										.getName())) {
			%>
			<td class="text-danger" rowspan="<%=size[index]%>" valign="center" align="center" style="font-size:25px;vertical-align:middle;"><%=credit[index++]%></td>
			<%
				}
						if (i == 0) {
			%>
			<td class="text-danger" rowspan="<%=list.size()%>" valign="center" align="center" style="font-size:25px;vertical-align:middle;"><%=countCredit%></td>
			<%
				}
			%>

		</tr>
		<%
			}
		%>
		<%
			}
		%>
	</tbody>
</table>
