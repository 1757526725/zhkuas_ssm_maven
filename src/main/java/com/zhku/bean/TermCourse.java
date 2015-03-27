package com.zhku.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 课程（学期），主要保存的是课程的信息，
 * 
 * @author JackCan_Liao
 * 
 */
public class TermCourse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1428112185148942919L;
	private Integer id;
	private Term term;
	private int courseTeacherRSId;
	private String courseClassNo;
	private Course course;
	private Teacher teacher;
	private int studentNum;
	private String periods;
	private int classWeek;
	private String classSection;
	private Classroom classroom;
	private List<BaseClass> classList;
	private static String weeks[] = { " ", "一", "二", "三", "四", "五", "六", "日" };

	public String getCourseClassNo() {
		return courseClassNo;
	}

	public void setCourseClassNo(String courseClassNo) {
		this.courseClassNo = courseClassNo;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public int getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(int studentNum) {
		this.studentNum = studentNum;
	}

	public String getPeriods() {
		return periods;
	}

	public void setPeriods(String periods) {
		this.periods = periods;
	}

	public String getClassSection() {
		return classSection;
	}

	public void setClassSection(String classSection) {
		this.classSection = classSection;
	}

	public int getClassWeek() {
		return classWeek;
	}

	public void setClassWeek(int classWeek) {
		this.classWeek = classWeek;
	}

	public List<BaseClass> getClassList() {
		return classList;
	}

	public void setClassList(List<BaseClass> classList) {
		this.classList = classList;
	}

	public String getWeekOfChinese() {
		return weeks[this.classWeek];
	}

	public List<Integer> getPeriodList() {
		List<Integer> periodList = new ArrayList<Integer>();
		int isBoth = 1;
		if (this.getPeriods().contains("单")) {
			isBoth = 0;
		} else if (this.getPeriods().contains("双")) {
			isBoth = 2;
		}
		String portions[] = this.getPeriods().split(",");
		try {
			for (String portion : portions) {
				String period[] = portion.split("-");
				if (period.length >= 2) {
					for (int i = Integer.valueOf(period[0]); i <= Integer.valueOf(period[1]); ++i) {
						if ((isBoth == 0 && i % 2 == 0) || (isBoth == 2 && i % 2 != 0)) {
							continue;
						}
						periodList.add(i);
					}
				} else {
					periodList.add(Integer.valueOf(period[0]));
				}
			}
		} catch (Exception e) {
			System.out.println(this.getPeriods());
		}
		return periodList;
	}

	public List<Integer> getSectionList() {
		List<Integer> sectionList = new ArrayList<Integer>();
		String[] sections = this.getClassSection().split("-");
		if(sections.length==1){
			sectionList.add(Integer.valueOf(sections[0]));
			return sectionList;
		}
		for (int i = Integer.valueOf(sections[0]); i <= Integer.valueOf(sections[1]); ++i) {
			sectionList.add(i);
		}
		return sectionList;
	}

	public Classroom getClassroom() {
		return classroom;
	}

	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}

	public int getCourseTeacherRSId() {
		return courseTeacherRSId;
	}

	public void setCourseTeacherRSId(int courseTeacherRSId) {
		this.courseTeacherRSId = courseTeacherRSId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	// 周期过滤
	public static List<Integer> periodFilter(String period) {
		List<Integer> pList = new ArrayList<Integer>();
		String[] periodArr = period.split(period.contains(",") ? "," : " ");
		for (int i = 0; i < periodArr.length; i++) {
			String p = periodArr[i].trim();
			if (p.contains("-")) {
				// 部分周期（含单双）
				int start = Integer.parseInt(p.split("-")[0]);
				int ad = 1;
				if (p.contains("单")) {
					ad = 2;
					if (start % 2 == 0) {
						start++;
					}
					p = p.replace("单", "");
				} else if (p.contains("双")) {
					ad = 2;
					if (start % 2 == 1) {
						start++;
					}
					p = p.replace("双", "");
				}
				int end = Integer.parseInt(p.split("-")[1].trim());

				for (int j = start; j <= end; j += ad) {
					if (start <= j && j <= end) {
						pList.add(j);
					}
				}
			} else {
				pList.add(Integer.valueOf(p.replaceAll("[^\\d]+", "")));
			}
		}
		return pList;
	}
}
