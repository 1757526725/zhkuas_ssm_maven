package com.zhku.module.analysis.impl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zhku.bean.Course;
import com.zhku.bean.Term;
import com.zhku.module.analysis.IAnalysiser;
import com.zhku.module.fetchData.bo.TermCoursePageBo;
import com.zhku.module.utils.HTMLUtil;

public class TermCoursePageAnalysiser implements IAnalysiser<TermCoursePageBo> {
	private final static String headwords[] = {"任课教师","上课班号","上课人数","课程类别","考核方式","上课班级构成","周次","节次","地点"};
	private Term term;
	private Course course;
	@Override
	public List<TermCoursePageBo> doAnalysis(String html) {
		List<TermCoursePageBo> list = new ArrayList<TermCoursePageBo>();
		if(html!=null){
			Document doc = Jsoup.parse(html);
			Element table = HTMLUtil.getTableByTableHeadWords(doc, headwords);
			//开始解析
			Elements trs = table.select("tr:gt(0)");
			TermCoursePageBo preCourse=null;
			for(Element tr:trs){
				Elements tds = tr.select("td");
				if(tds.size()<7) continue;
				String tempTeacher= tds.get(0).text().trim();
				String tempCourseClassNo= tds.get(1).text().trim();
				String tempStudentNum= tds.get(2).text().trim();
				String tempCourseType=tds.get(3).text().trim();
				String tempClassName = tds.get(5).text().trim();
				String tempPeroids = tds.get(6).text().trim();
				String tempDayAndSections= tds.get(7).text().trim();
				String tempClassroom= tds.get(8).text().trim();
				if(tempTeacher == null ||tempTeacher.equals("")){
					preCourse = list.get(list.size()-1);
					tempTeacher = preCourse.getTeacher();
					if(tempCourseClassNo == null ||tempCourseClassNo.equals("")){
						tempCourseClassNo = preCourse.getCourseClassNo();
					}
					if(tempCourseType == null ||tempCourseType.equals("")){
						tempCourseType = preCourse.getCourseType();
					}
					if(tempClassName == null ||tempClassName.equals("")){
						tempClassName = preCourse.getClassNames();
					}
					if(tempStudentNum == null ||tempStudentNum.equals("")){
						tempStudentNum = preCourse.getStudentNum();
					}
					if(tempClassroom == null ||tempClassroom.equals("")){
						tempClassroom = preCourse.getClassRoom();
					}
				}
				
				TermCoursePageBo termCoursePageBo= new TermCoursePageBo();
				termCoursePageBo.setClassNames(tempClassName);
				termCoursePageBo.setClassRoom(tempClassroom);
				termCoursePageBo.setCourseType(tempCourseType);
				termCoursePageBo.setCourseClassNo(tempCourseClassNo);
				termCoursePageBo.setCourse(getCourse());
				termCoursePageBo.setPeriods(tempPeroids);
				termCoursePageBo.setStudentNum(tempStudentNum);
				termCoursePageBo.setTerm(getTerm());
				termCoursePageBo.setTeacher(tempTeacher);
				termCoursePageBo.setDayAndSection(tempDayAndSections);
				list.add(termCoursePageBo);
			}
			
		}
		return list;
	}
	public Term getTerm() {
		return term;
	}
	public TermCoursePageAnalysiser setTerm(Term term) {
		this.term = term;
		return this;
	}
	public Course getCourse() {
		return course;
	}
	public TermCoursePageAnalysiser setCourse(Course course) {
		this.course = course;
		return this;
	}

}
