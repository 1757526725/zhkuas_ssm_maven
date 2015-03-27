package com.zhku.module.analysis.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zhku.bean.CampusArea;
import com.zhku.bean.CourseListPage;
import com.zhku.bean.PublicCourseOption;

public class ZXCoursePageAnalysiser {
	private String majorNo;
	public CourseListPage doAnalysis(String html){
		if (html == null)
			return null;
		CourseListPage courseListPage = new CourseListPage();
		Document doc = null;
		doc = Jsoup.parse(html);
		Elements tables = doc.select("table");
		if (tables.size() < 2)
			return null;
		Element table = tables.get(1);
		table.select("script").remove();
		courseListPage.setMajorNo(getMajorNo());
		table.select("input[type=checkbox]").removeAttr("disabled")
				.removeAttr("checked");
		table.select("input[type=text]").attr("value", "");

		courseListPage.setPageContent(table.children().html()
				.replaceAll("查看", "选择"));
		return courseListPage;
	}

	public String getMajorNo() {
		return majorNo;
	}
	public ZXCoursePageAnalysiser setMajorNo(String majorNo) {
		this.majorNo = majorNo;
		return this;
	}
}
