package com.zhku.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.zhku.utils.ServiceUtils;

public class MyCourseScheme implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4221922186050787945L;
	private Integer id;
	private Term term;
	private String cNo;
	private String displayValue;
	private String formValue;
	private Integer uid;
	private Course course;
	private String orderNum;
	private String schoolTime;

	public String getcNo() {
		return cNo;
	}
	public void setcNo(String cNo) {
		this.cNo = cNo;
	}
	public String getDisplayValue() {
		return displayValue;
	}
	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}
	public String getFormValue() {
		return formValue;
	}
	public void setFormValue(String formValue) {
		this.formValue = formValue;
	}
	/**
	 * @return the course
	 */
	public Course getCourse() {
		return course;
	}
	/**
	 * @param course the course to set
	 */
	public void setCourse(Course course) {
		this.course = course;
	}
	/**
	 * @return the orderNum
	 */
	public String getOrderNum() {
		return orderNum;
	}
	/**
	 * @param orderNum the orderNum to set
	 */
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getSchoolTime() {
		return schoolTime;
	}
	
	
	public void setSchoolTime(String schoolTime) {
		this.schoolTime = schoolTime;
	}
	public List<TimeWapper> getCourseTimeList() {
		String timeStr =this.getSchoolTime().replaceAll("&nbsp;", "");
		String timeItems[]=timeStr.split("<br>");
		List<TimeWapper> timeList =new ArrayList<TimeWapper>();
		for(int i=0;i<timeItems.length;++i){
			if(timeItems[i]==null||timeItems[i].equals("")) continue;
			List<Integer> periodList =new ArrayList<Integer>();
			List<Integer> sectionList =new ArrayList<Integer>();
			int indexWeek=timeItems[i].indexOf("周");
			int week=ServiceUtils.getNumOfWeek((timeItems[i].substring(indexWeek+1,indexWeek+2)));
			String periodStr =timeItems[i].substring(0,indexWeek);
			String portionsOfPeriod[] =periodStr.split(",");
			//周
			for(String portion:portionsOfPeriod){
				String period[] =portion.split("-");
				if(period.length>=2){
				for(int j=Integer.valueOf(period[0]);j<=Integer.valueOf(period[1]);++j){
					periodList.add(j);
				}
				}else{
					periodList.add(Integer.valueOf(period[0]));
				}
			}
			//节
			String sectionStr =timeItems[i].substring(indexWeek+3,timeItems[i].indexOf("节"));
			String sections[] =sectionStr.split("-");
			for(int k=Integer.valueOf(sections[0]);k<=Integer.valueOf(sections[1]);++k){
				sectionList.add(k);
			}
			
			TimeWapper timeWipper =new TimeWapper(periodList, sectionList, week);
			timeList.add(timeWipper);
		}
		return timeList;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Term getTerm() {
		return term;
	}
	public void setTerm(Term term) {
		this.term = term;
	}
	
	
}
