package com.zhku.module.analysis;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zhku.bean.Academy;
import com.zhku.bean.Major;
import com.zhku.module.utils.HTMLUtil;
import com.zhku.web.Constants;

public class MajorAnalysiser implements IAnalysiser {
	private String academyNo;
	@Override
	public List doAnalysis(String html) {
		List<Major> list = new ArrayList<Major>();
		Element element = null;
		Elements options = null;
		element=HTMLUtil.getSelectorByName(html, Constants.HTML_ELEMENT_NAME.SELECT_NAME_MAJOR.getValue());
		if (element != null) {
			options = element.children();
			Major major = null;
			for (Element e : options) {
				if (e.val().equals("")||e.text()==null||e.text().trim().equals("")||e.val().equals("Nothing"))
					continue;
				major = new Major();
				major.setName(HTMLUtil.cutName(e.text()));
				major.setNo(e.attr("value"));
				Academy academy=new Academy();
				academy.setNo(academyNo);
				major.setAcademy(academy);
				list.add(major);
			}
		}
		return list;
	}

	public IAnalysiser setAcademyNo(String no) {
		this.academyNo=no;
		return this;
	}

	public String getAcademyNo() {
		return academyNo;
	}

}
