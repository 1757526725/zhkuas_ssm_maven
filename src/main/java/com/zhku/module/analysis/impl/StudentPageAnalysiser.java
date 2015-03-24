package com.zhku.module.analysis.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zhku.module.fetchData.bo.StudentPage;

public class StudentPageAnalysiser {
	private String sno;
	public StudentPage doAnalysis(String html){
		String[] htmls = html.split(">@<");
		String sno = null;
		String sName = null;
		String sSex = null;
		String sIdCard = null;
		String inSchoolDate = null;
		String studyType = null;
		String academy = null;
		String major = null;
		String className = null;
		Document doc = null;
		doc = Jsoup.parse(htmls[0]);
		// System.out.println(doc);
		Elements tds = doc.getElementsByTag("td");
		// System.out.println(tds);
		if (tds.size() < 114) {
			return null;
		}
		sno = tds.get(2).text();
		sName = tds.get(4).text();
		sSex = tds.get(11).text();
		sIdCard = tds.get(13).text();
		inSchoolDate = tds.get(79).text();
		major = tds.get(111).text();
		studyType = tds.get(83).text();
		academy = tds.get(109).text();
		className = tds.get(113).text();
		StudentPage studentPage = new StudentPage();
		studentPage.setAcademy(academy);
		studentPage.setClassName(className);
		studentPage.setInSchoolDate(inSchoolDate);
		studentPage.setMajor(major);
		studentPage.setsName(sName);
		studentPage.setSno(sno);
		studentPage.setsSex(sSex);
		
		//fix
		doc = Jsoup.parse(htmls[1]);
		// System.out.println(doc);
		Elements tables = doc.getElementsByTag("table");
		Element infoTable=tables.get(2);
		tds=infoTable.select("td");
		// System.out.println(tds);
		if (tds.size() <8) {
			return studentPage;
		}
		String m_i[]=tds.get(3).text().split("/");
		inSchoolDate = m_i[0]+"-9-1";
		major = m_i[1];
		academy = tds.get(2).text();
		className = tds.get(4).text();
		studentPage.setAcademy(academy);
		studentPage.setClassName(className);
		studentPage.setInSchoolDate(inSchoolDate);
		studentPage.setMajor(major);
		
		if(studentPage.getSno()==null||studentPage.getSno().trim().equals("")){
			studentPage.setSno(getSno());
		}
		return studentPage;
	}
	public String getSno() {
		return sno;
	}
	public StudentPageAnalysiser setSno(String sno) {
		this.sno = sno;
		return this;
	}
}
