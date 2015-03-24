package com.zhku.module.analysis.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zhku.bean.CampusArea;
import com.zhku.bean.PublicCourseOption;

public class PublicCourseOptiontAnalysiser {
	private String termNo;
	private CampusArea campusArea;
	private String cNo;
	public PublicCourseOption doAnalysis(String html){
		if (html == null)
			return null;
		PublicCourseOption option = new PublicCourseOption();
		Document doc = null;
		doc = Jsoup.parse(html);
		Elements elements = doc.select("#dataListDiv");
		Element dataListDiv = elements.first();
		if(dataListDiv==null) {
			System.out.println(cNo);
			return null;
		}
		/*
		 * 去掉 上课班级的那一列
		 */
		//
		// 去掉title
		Elements titleNodes = dataListDiv.select(".T");

		if (titleNodes.size() == 0) {
			return null;
		}
		for (int i = 0; i < titleNodes.size(); i = i + 2) {
			titleNodes.get(i).childNode(3).remove();
		}
		// 去掉class =B 的 第四个节点
		Elements classBNodes = dataListDiv.select(".B");
		Elements classHNodes = dataListDiv.select(".H");

		for (Element classBNode : classBNodes) {
			classBNode.childNode(3).remove();
		}

		for (Element classHNode : classHNodes) {
			classHNode.childNode(2).remove();
		}

		// System.out.println(dataListDiv);
		option.setcNo(getcNo());
		option.setCampus(getCampusArea());
		option.setTermNo(getTermNo());
		String optionHtml = dataListDiv.toString().replaceAll(
				"<=\\\"\\\" d=\\\"\\\"", "").replaceAll("disabled", " ");
		option.setOptionHtml(optionHtml);
		return option;
	}
	public String getTermNo() {
		return termNo;
	}
	public PublicCourseOptiontAnalysiser setTermNo(String termNo) {
		this.termNo = termNo;
		return this;
	}
	public CampusArea getCampusArea() {
		return campusArea;
	}
	public PublicCourseOptiontAnalysiser setCampusArea(CampusArea campusArea) {
		this.campusArea = campusArea;
		return this;
	}
	public String getcNo() {
		return cNo;
	}
	public PublicCourseOptiontAnalysiser setcNo(String cNo) {
		this.cNo = cNo;
		return this;

	}
}
