package com.zhku.module.analysis.impl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zhku.bean.Teacher;
import com.zhku.module.analysis.IAnalysiser;
import com.zhku.module.utils.HTMLUtil;
import com.zhku.web.Constants;

public class TeacherAnalysiser extends AnalysiserBase implements IAnalysiser<Teacher>{
	
	@Override
	public List<Teacher> doAnalysis(String html) {
		List<Teacher> list = new ArrayList<Teacher>();
		Element element = null;
		Elements options = null;
		element=HTMLUtil.getSelectorByName(html, Constants.HTML_ELEMENT_NAME.SELECT_NAME_TEACHER.getValue());
		if (element != null) {
			options = element.children();
			Teacher teacher = null;
			for (Element e : options) {
				if (e.attr("value").equals(""))
					continue;
				teacher = new Teacher();
				teacher.setName(HTMLUtil.cutName(e.text()).trim());
				teacher.setNo(e.attr("value").trim());
//				teacher.setNameNo(HTMLUtil.cutNameNo(e.text()));
				list.add(teacher);
			}
		}

		return list;
	}
	

}
