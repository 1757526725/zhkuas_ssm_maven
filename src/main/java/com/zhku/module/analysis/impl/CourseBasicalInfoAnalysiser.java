package com.zhku.module.analysis.impl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zhku.bean.Course;
import com.zhku.bean.Teacher;
import com.zhku.module.analysis.IAnalysiser;
import com.zhku.module.utils.HTMLUtil;
import com.zhku.web.Constants;


public class CourseBasicalInfoAnalysiser implements IAnalysiser{

	@Override
	public List doAnalysis(String html) {
		List<Course> list = new ArrayList<Course>();
		Element element = null;
		Elements options = null;
		element=HTMLUtil.getSelectorByName(html, Constants.HTML_ELEMENT_NAME.SELECT_NAME_COURSE.getValue());
		if (element != null) {
			options = element.children();
			Course course = null;
			for (Element e : options) {
				if (e.attr("value").equals(""))
					continue;
				course = new Course();
				course.setName(HTMLUtil.cutName(e.text()));
				course.setNo(e.attr("value"));
				course.setNameNo(HTMLUtil.cutNameNo(e.text()));
				list.add(course);
			}
		}
		return list;
	}

}
