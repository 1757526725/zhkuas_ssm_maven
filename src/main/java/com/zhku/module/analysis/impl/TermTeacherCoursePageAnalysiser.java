package com.zhku.module.analysis.impl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zhku.bean.Teacher;
import com.zhku.bean.Term;
import com.zhku.module.analysis.IAnalysiser;
import com.zhku.module.fetchData.bo.TermClassCoursePageBo;
import com.zhku.module.fetchData.bo.TermTeacherCoursePageBo;
import com.zhku.module.utils.HTMLUtil;

public class TermTeacherCoursePageAnalysiser implements IAnalysiser {
	private final static String headwords[] = {"课程","学分","总学时","授课方式","上课班号","课程类别","上课人数","上课班级","时间","地点"};
	private Term term;
	private Teacher teacher;
	@Override
	public List doAnalysis(String html) {
		List<TermTeacherCoursePageBo> list = new ArrayList<TermTeacherCoursePageBo>();
		if(html!=null){
			Document doc = Jsoup.parse(html);
			Element table = HTMLUtil.getTableByTableHeadWords(doc, headwords);
			//开始解析
			Elements trs = table.select("tr:gt(0)");
			TermTeacherCoursePageBo preCourse=null;
			for(Element tr:trs){
				Elements tds = tr.select("td");
				if(tds.size()<10) continue;
				String tempCourse= tds.get(1).text().trim();
				String tempCredit = tds.get(2).text().trim();
				String tempTeachingWay=tds.get(3).text().trim();
				String tempCourseType=tds.get(4).text().trim();
				String tempCourseClassNo= tds.get(5).text().trim();
				String tempClassName = tds.get(6).text().trim();
				String tempStudentNum= tds.get(7).text().trim();
				String tempTime= tds.get(8).text().trim();
				String tempClassroom= tds.get(9).text().trim();
				if(tempCourse == null ||tempCourse.equals("")){
					preCourse = list.get(list.size()-1);
					tempCourse = preCourse.getCourse();
					if(tempCredit == null ||tempCredit.equals("")){
						tempCredit = preCourse.getCredit();
					}
					if(tempTeachingWay == null ||tempTeachingWay.equals("")){
						tempTeachingWay = preCourse.getTeachingWay();
					}
					if(tempCourseClassNo == null ||tempCourseClassNo.equals("")){
						tempCourseClassNo = preCourse.getCourseClassNo();
					}
					if(tempCourseType == null ||tempCourseType.equals("")){
						tempCourseType = preCourse.getCourseType();
					}
					if(tempClassName == null ||tempClassName.equals("")){
						tempClassName = preCourse.getClassName();
					}
					if(tempStudentNum == null ||tempStudentNum.equals("")){
						tempStudentNum = preCourse.getStudentNum();
					}
					if(tempClassroom == null ||tempClassroom.equals("")){
						tempClassroom = preCourse.getClassroom();
					}
				}
				
				TermTeacherCoursePageBo termTeacherCoursePageBo= new TermTeacherCoursePageBo();
				termTeacherCoursePageBo.setClassName(tempClassName);
				termTeacherCoursePageBo.setClassroom(tempClassroom);
				termTeacherCoursePageBo.setCourseType(tempCourseType);
				termTeacherCoursePageBo.setCourseClassNo(tempCourseClassNo);
				termTeacherCoursePageBo.setCourse(tempCourse);
				termTeacherCoursePageBo.setTime(tempTime);
				termTeacherCoursePageBo.setCredit(tempCredit);
				termTeacherCoursePageBo.setTeachingWay(tempTeachingWay);
				termTeacherCoursePageBo.setStudentNum(tempStudentNum);
				termTeacherCoursePageBo.setTerm(getTerm());
				termTeacherCoursePageBo.setTeacher(getTeacher());
				list.add(termTeacherCoursePageBo);
			}
			
		}
		return list;
	}
	public Term getTerm() {
		return term;
	}
	public TermTeacherCoursePageAnalysiser setTerm(Term term) {
		this.term = term;
		return this;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public TermTeacherCoursePageAnalysiser setTeacher(Teacher teacher) {
		this.teacher = teacher;
		return this;
	}
	
}
