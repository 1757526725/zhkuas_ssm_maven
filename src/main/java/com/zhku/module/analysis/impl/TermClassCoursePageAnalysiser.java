package com.zhku.module.analysis.impl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zhku.bean.BaseClass;
import com.zhku.bean.Term;
import com.zhku.module.analysis.IAnalysiser;
import com.zhku.module.fetchData.bo.TermClassCoursePageBo;
import com.zhku.module.utils.HTMLUtil;

public class TermClassCoursePageAnalysiser implements IAnalysiser {
	private final static String headwords[] = {"课程","学分","总学时","考核方式","教师","上课班号","上课人数","周次","节次","地点"};
	private Term term;
	private BaseClass baseClass;
	@Override
	public List doAnalysis(String html) {
		List<TermClassCoursePageBo> list = new ArrayList<TermClassCoursePageBo>();
		if(html!=null){
			Document doc = Jsoup.parse(html);
			Element table = HTMLUtil.getTableByTableHeadWords(doc, headwords);
			//开始解析
			Elements trs = table.select("tr:gt(0)");
			TermClassCoursePageBo preCourse = null;
			for(Element tr:trs){
				Elements tds = tr.select("td");
				if(tds.size()<10) continue;
				String tempCourse= tds.get(0).text().trim();
				String tempCredit = tds.get(1).text().trim();
				String tempExamType= tds.get(3).text().trim();
				String tempTeacher= tds.get(4).text().trim();
				String tempCourseClassNo= tds.get(5).text().trim();
				String tempStudentNum= tds.get(6).text().trim();
				String tempPeriods= tds.get(7).text().trim();
				String tempDayAndsection= tds.get(8).text().trim();
				String tempClassroom= tds.get(9).text().trim();
				if(list.size()>0){
					if(tempCourse == null ||tempCourse.trim().equals("")){
						preCourse = list.get(list.size()-1);
						tempCourse = preCourse.getCourse();
						
						if(tempCredit == null ||tempCredit.trim().equals("")){
							tempCredit = preCourse.getCredit();
						}
						if(tempExamType == null ||tempExamType.trim().equals("")){
							tempExamType = preCourse.getExamType();
						}
						if(tempTeacher == null ||tempTeacher.trim().equals("")){
							tempTeacher = preCourse.getTeacher();
						}
						if(tempCourseClassNo == null ||tempCourseClassNo.trim().equals("")){
							tempCourseClassNo = preCourse.getCourseClassNo();
						}
						if(tempStudentNum == null ||tempStudentNum.trim().equals("")){
							tempStudentNum = preCourse.getStudentNum();
						}
						if(tempPeriods == null ||tempPeriods.trim().equals("")){
							tempPeriods = preCourse.getPeriods();
						}
						if(tempDayAndsection == null ||tempDayAndsection.trim().equals("")){
							tempDayAndsection = preCourse.getDayAndsection();
						}
						if(tempClassroom == null ||tempClassroom.trim().equals("")){
							tempClassroom = preCourse.getClassroom();
						}
					}
					
				}
				
				TermClassCoursePageBo termTclassCoursePageBo= new TermClassCoursePageBo();
				termTclassCoursePageBo.setBaseClass(getBaseClass());
				termTclassCoursePageBo.setTeacher(tempTeacher);
				termTclassCoursePageBo.setClassroom(tempClassroom);
				termTclassCoursePageBo.setCourse(tempCourse);
				termTclassCoursePageBo.setCourseClassNo(tempCourseClassNo);
				termTclassCoursePageBo.setCredit(tempCredit);
				termTclassCoursePageBo.setDayAndsection(tempDayAndsection);
				termTclassCoursePageBo.setExamType(tempExamType);
				termTclassCoursePageBo.setPeriods(tempPeriods);
				termTclassCoursePageBo.setStudentNum(tempStudentNum);
				termTclassCoursePageBo.setTerm(getTerm());
				list.add(termTclassCoursePageBo);
			}
			
		}
		return list;
	}
	public BaseClass getBaseClass() {
		return baseClass;
	}
	public TermClassCoursePageAnalysiser setBaseClass(BaseClass baseClass) {
		this.baseClass = baseClass;
		return this;
	}
	public Term getTerm() {
		return term;
	}
	public TermClassCoursePageAnalysiser setTerm(Term term) {
		this.term = term;
		return this;
	}
	
}
