package com.zhku.module.analysis.impl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zhku.bean.CampusArea;
import com.zhku.bean.Term;
import com.zhku.module.analysis.IAnalysiser;
import com.zhku.module.fetchData.bo.PublicCoursePageBo;
import com.zhku.module.utils.HTMLUtil;

public class PublicCoursePageAnalysiser implements IAnalysiser<PublicCoursePageBo> {
	private final static String headwords[] = {"课程","学分","任课教师","上课班号","上课人数","周次","时间","上课地点"};
	private Term term;
	private CampusArea campus;
	@Override
	public List<PublicCoursePageBo> doAnalysis(String html) {
		
		List<PublicCoursePageBo> list = new ArrayList<PublicCoursePageBo>();
		if(html!=null){
			Document doc = Jsoup.parse(html);
			Element table = HTMLUtil.getTableByTableHeadWords(doc, headwords);
			//开始解析
			Elements trs = table.select("tr:gt(0)");
			PublicCoursePageBo preCourse=null;
			for(Element tr:trs){
				Elements tds = tr.select("td");
				if(tds.size()<7) continue;
				String tempCourse= tds.get(1).text().trim();
				String tempCredit = tds.get(2).text().trim();
				String tempTeacher=tds.get(3).text().trim();
				String tempCourseClassNo= tds.get(4).text().trim();
				String tempStudentNum= tds.get(5).text().trim();
				String tempPeroids= tds.get(6).text().trim();
				String tempDayAndSections= tds.get(7).text().trim();
				String tempClassroom= tds.get(8).text().trim();
				if((tempCourse == null ||tempCourse.equals(""))&&list.size()>0){
					preCourse = list.get(list.size()-1);
					tempCourse = preCourse.getCourse();
					if(tempTeacher == null ||tempTeacher.equals("")){
						tempTeacher = preCourse.getTeacher();
					}
					if(tempCourseClassNo == null ||tempCourseClassNo.equals("")){
						tempCourseClassNo = preCourse.getCourseClassNo();
					}
					if(tempStudentNum == null ||tempStudentNum.equals("")){
						tempStudentNum = preCourse.getStudentNum();
					}
					if(tempClassroom == null ||tempClassroom.equals("")){
						tempClassroom = preCourse.getClassRoom();
					}
					if(tempPeroids == null ||tempPeroids.equals("")){
						tempPeroids = preCourse.getPeriods();
					}
					if(tempDayAndSections == null ||tempDayAndSections.equals("")){
						tempDayAndSections = preCourse.getDayAndSection();
					}
				}
				
				PublicCoursePageBo publicCoursePageBo= new PublicCoursePageBo();
				publicCoursePageBo.setClassRoom(tempClassroom);
				publicCoursePageBo.setCourseClassNo(tempCourseClassNo);
				publicCoursePageBo.setCourse(tempCourse);
				publicCoursePageBo.setPeriods(tempPeroids);
				publicCoursePageBo.setStudentNum(tempStudentNum);
				publicCoursePageBo.setTerm(getTerm());
				publicCoursePageBo.setTeacher(tempTeacher);
				publicCoursePageBo.setDayAndSection(tempDayAndSections);
				publicCoursePageBo.setCampusArea(getCampus());
				list.add(publicCoursePageBo);
			}
			
		}
		return list;
	}
	public Term getTerm() {
		return term;
	}
	public PublicCoursePageAnalysiser setTerm(Term term) {
		this.term = term;
		return this;
	}
	public CampusArea getCampus() {
		return campus;
	}
	public PublicCoursePageAnalysiser setCampus(CampusArea campus) {
		this.campus = campus;
		return this;
	}

}
