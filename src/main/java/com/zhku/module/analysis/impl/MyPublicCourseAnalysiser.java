package com.zhku.module.analysis.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zhku.bean.Course;
import com.zhku.bean.MyPublicCourse;
import com.zhku.bean.Term;
import com.zhku.module.analysis.IAnalysiser;
import com.zhku.module.utils.HTMLUtil;

public class MyPublicCourseAnalysiser implements IAnalysiser {
	private Integer uid;
	private List<Term> termList ;
	@Override
	public List doAnalysis(String html) {
		List<MyPublicCourse> list = new ArrayList<MyPublicCourse>();
		if(html!=null){
			String [] htmlScorseSplit = html.split("~~score~~");
			String [] courseHtmls = htmlScorseSplit[0].split("~~course~~");
			Document doc = null;
			/**解析课表**/
			for(int j =0;j< courseHtmls.length;j++){
				String courseHtml = courseHtmls[j];
				if(courseHtml!=null&&!"".equals(courseHtml)){
					doc  = Jsoup.parse(courseHtml);
					Elements tables = doc.select("table");
					Element mainTable = tables.get(1);
					Elements trs = mainTable.select("tr");
					for (int i = 2; i < trs.size(); ++i) {
						Elements tds = trs.get(i).children();
						String cName = null;
						Elements tagAs = tds.get(1).select("a");
						if (tagAs.size() <= 0)
							continue;
						if (tds.get(6).html().indexOf("公共课/任选课") < 0)
							continue;
						cName = tagAs.first().html().toString();
						String cNameNo = HTMLUtil.cutNameNo(cName);
						MyPublicCourse myPublicCourse = new MyPublicCourse();
						myPublicCourse.setcNameNo(cNameNo);
						Course course = new Course();
						course.setNameNo(cNameNo);
						course.setName(HTMLUtil.cutName(cName));
						myPublicCourse.setCourse(course);
						if(getTermList()!=null&&getTermList().size()>j){
							String termNo = getTermList().get(j).getNo();
							Term term = new Term ();
							term.setNo(termNo);
							myPublicCourse.setTerm(term);
						}
							
						myPublicCourse.setUid(uid);
						list.add(myPublicCourse);
					}
				}
			}
			/**END**/
			/**解析成绩**/
			if(list.size()>0){
				doc = Jsoup.parse(htmlScorseSplit[1]);
					Map<String,String> scoreMap = new HashMap<String,String>();
//					Element table = tables.first();
					Elements trs = doc.select("tr:gt(0)");
					for(Element tr:trs){
						Elements tds = tr.select("td");
						if(tds.size()<12)continue;
						String nameNo = HTMLUtil.cutNameNo(tds.get(1).text());
						String score = tds.get(11).text();
						scoreMap.put(nameNo, score);
					}
					for(MyPublicCourse publicCourse:list){
						publicCourse.setScore(scoreMap.get(publicCourse.getcNameNo()));
					}
			}
			/**END**/
		}
		return list;
	}
	public Integer getUid() {
		return uid;
	}
	public MyPublicCourseAnalysiser setUid(Integer uid) {
		this.uid = uid;
		return this;
	}
	public List<Term> getTermList() {
		return termList;
	}
	public MyPublicCourseAnalysiser setTermList(List<Term> termList) {
		this.termList = termList;
		return this;
	}
}
