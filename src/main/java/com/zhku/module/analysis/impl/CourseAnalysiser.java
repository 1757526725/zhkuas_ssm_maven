package com.zhku.module.analysis.impl;

import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zhku.module.analysis.IAnalysiser;
import com.zhku.module.fetchData.bo.CoursePageBo;

public class CourseAnalysiser implements IAnalysiser{
	private String cNo;
	@Override
	public List doAnalysis(String html) {
		System.out.println(html);
		Document doc = null;
		doc = Jsoup.parse(html);
		String cNameNo;
		String cName;
		String cEnglishName;
		String credits;
		String qualityHours;
		String teachingHours;
		String experimentalHours;
		String computerClassHours;
		String otherClassHours;
		String teachingProgram;
		String organization;
		Elements elements = doc.select("table tbody tr td table tbody");
		Element element = elements.last();
		Elements childs = element.children().select("td");
		cNameNo = childs.get(2).text();
		if (cNameNo.trim().equals(""))
			return null;
		organization = childs.get(4).text();
		cName = childs.get(6).text();
		cEnglishName = childs.get(8).text();
		credits = childs.get(10).text();
		qualityHours = childs.get(12).text();
		teachingHours = childs.get(14).text();
		experimentalHours = childs.get(16).text();
		computerClassHours = childs.get(18).text();
		otherClassHours = childs.get(20).text();
		teachingProgram = childs.get(26).select("div").text();
		CoursePageBo coursePageBo = new CoursePageBo();
		coursePageBo.setNameNo(cNameNo);
		coursePageBo.setName(cName);
		coursePageBo.setComputerClassHours(computerClassHours);
		coursePageBo.setCredits(credits);
		coursePageBo.setExperimentalHours(experimentalHours);
		coursePageBo.setOrganization(organization);
		coursePageBo.setOtherClassHours(otherClassHours);
		coursePageBo.setQualityHours(qualityHours);
		coursePageBo.setTeachingHours(teachingHours);
		coursePageBo.setTeachingProgram(teachingProgram);
		coursePageBo.setEnglishName(cEnglishName);
		coursePageBo.setNo(getcNo());
		List list = new LinkedList();
		list.add(coursePageBo);
		return list;
	}
	public String getcNo() {
		return cNo;
	}
	public CourseAnalysiser setcNo(String cNo) {
		this.cNo = cNo;
		return this;
	}

}
