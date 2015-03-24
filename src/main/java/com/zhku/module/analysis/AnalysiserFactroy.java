package com.zhku.module.analysis;

import com.zhku.module.analysis.impl.BaseClassAnalysiser;
import com.zhku.module.analysis.impl.CourseBasicalInfoAnalysiser;
import com.zhku.module.analysis.impl.OrganizationAnalysiser;
import com.zhku.module.analysis.impl.SelectorValueAnalysiser;
import com.zhku.module.analysis.impl.TeacherAnalysiser;

public class AnalysiserFactroy {
	public static TeacherAnalysiser getTeacherAnalysiser(){
		return new TeacherAnalysiser();
	}

	public static OrganizationAnalysiser getOrganizationAnalysiser() {
		return new OrganizationAnalysiser();
	}

	public static MajorAnalysiser getMajorAnalysiser() {
		return new MajorAnalysiser();
	}

	public static BaseClassAnalysiser getBaseClassAnalysiser() {
		return new BaseClassAnalysiser();
	}

	public static SelectorValueAnalysiser getSelectorAnalysiser() {
		return new SelectorValueAnalysiser();
	}

	public static CourseBasicalInfoAnalysiser getCourseBasicalInfoAnalysiser() {
		// TODO Auto-generated method stub
		return new CourseBasicalInfoAnalysiser();
	}
}
