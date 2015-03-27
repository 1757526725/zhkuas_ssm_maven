package com.zhku.module.fetchData.bo;

public class StudentPage {
	private String sno;
	private String sName;
	private String sSex;
	private String inSchoolDate;
	private String academy;
	private String major;
	private String className;
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public String getsName() {
		return sName;
	}
	public void setsName(String sName) {
		this.sName = sName;
	}
	public String getsSex() {
		return sSex;
	}
	public void setsSex(String sSex) {
		this.sSex = sSex;
	}
	
	public String getInSchoolDate() {
		return inSchoolDate;
	}
	public void setInSchoolDate(String inSchoolDate) {
		this.inSchoolDate = inSchoolDate;
	}
	
	public String getAcademy() {
		return academy;
	}
	public void setAcademy(String academy) {
		this.academy = academy;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	@Override
	public String toString() {

		String tableHtml="<table class=\"table table-bordered\" width=\"640\" align=\"center\" cellpadding=\"0\" cellspacing=\"1\">"+
										"<tbody><tr>"+
										 "<td width=\"92\" align=\"center\" valign=\"middle\">学&nbsp;&nbsp;&nbsp;&nbsp;号</td>"+
												"<td width=\"110\">"+getSno()+"<br>"+
												"</td>"+
												"<td width=\"110\" align=\"center\" valign=\"middle\">姓&nbsp;&nbsp;&nbsp;&nbsp;名</td>"+
												"<td colspan=\"2\">"+getsName()+" <br>"+
												"</td></tr><tr>"+
												"<td align=\"center\" valign=\"middle\">性&nbsp;&nbsp;&nbsp;&nbsp;别</td>"+
												"<td>"+getsSex()+"<br></td>"+
												"<td align=\"center\" valign=\"middle\">专业</td>"+
												"<td>"+getMajor()+"</td></tr><tr>"+
												"<td align=\"center\" valign=\"middle\">入学时间</td>"+
												"<td>"+getInSchoolDate()+" <br></td>"+
												"<td align=\"center\" valign=\"middle\">学&nbsp;&nbsp;&nbsp;&nbsp;制</td>"+
												"<td>4 <br></td></tr>"+
												"<tr><td align=\"center\" valign=\"middle\">院(系)/部</td>"+
												"<td>"+getAcademy()+"<br></td>"+
												"<td align=\"center\" valign=\"middle\">行政班级</td>"+
												"<td>"+getClassName()+"<br></td></tr></tbody></table>";
	
		return tableHtml;
	}

	
}
