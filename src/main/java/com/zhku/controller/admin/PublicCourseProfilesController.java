package com.zhku.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhku.bean.CampusArea;
import com.zhku.bean.Course;
import com.zhku.bean.PublicCourse;
import com.zhku.bean.PublicCourseType;
import com.zhku.bean.Term;
import com.zhku.service.db.ICampusAreaService;
import com.zhku.service.db.IPublicCourseProfilesService;
import com.zhku.service.db.IPublicCourseService;
import com.zhku.service.db.IPublicCourseTypeService;
import com.zhku.service.db.ITermService;

/**
 * 课程属性 设置 入口
 * 
 * @author JackCan
 * 
 */
@Controller
@RequestMapping("/admin/course/profiles")
public class PublicCourseProfilesController {
	@Autowired
	private ICampusAreaService campusAreaService;
	@Autowired
	private IPublicCourseService publicCourseService;
	@Autowired
	private IPublicCourseProfilesService publicCourseProfilesService;
	@Autowired
	private ITermService termService;
	@Autowired
	private IPublicCourseTypeService publicCourseTypeService;

	@RequestMapping("")
	public String show(HttpServletRequest request) {
		// 获取当前学年学期
		List<Term> terms = termService.getAvailabelTerms();
		Term currentTerm = null;
		for (Term term : terms) {
			if (term.isCurrent()) {
				currentTerm = term;
				request.setAttribute("currentTerm", currentTerm);
				break;
			}
		}
		request.setAttribute("terms", terms);
		// 获取当年学年学期的公选课列表，如果两个校区的课程号不一样，将在课程名的定位上加上校区
		List<CampusArea> campuses = campusAreaService.getCampusAreas();
		// 记录课程的校区
		Map<String, PublicCourse> noMap = new HashMap<String, PublicCourse>();
		Map<String, PublicCourse> nameMap = new HashMap<String, PublicCourse>();
		List<PublicCourse> destPublicCoureList = new ArrayList<PublicCourse>();
		for (CampusArea campus : campuses) {
			List<PublicCourse> publicCourses = publicCourseService.getPublicCoursesByTermNoAndCampusId(currentTerm.getNo(), campus.getId());
			for (int i = 0; i < publicCourses.size(); i++) {
				
				PublicCourse findCourse = noMap.get(publicCourses.get(i).getTermCourse().getCourse().getNo());
				if (findCourse != null) {
					continue;
				} else {
					findCourse = nameMap.get(publicCourses.get(i).getTermCourse().getCourse().getName());
					if(findCourse!=null){
						if (!publicCourses.get(i).getCampusId().equals(findCourse.getCampusId())) {
							findCourse.getTermCourse().getCourse().setName(findCourse.getTermCourse().getCourse().getName() + "[" + (findCourse.getCampusId() == 1 ? "海珠" : "白云") + "]");
							publicCourses.get(i).getTermCourse().getCourse().setName(publicCourses.get(i).getTermCourse().getCourse().getName() + "[" + (publicCourses.get(i).getCampusId() == 1 ? "海珠" : "白云") + "]");
							destPublicCoureList.add(publicCourses.get(i));
							continue;
						}
					}else{
						nameMap.put(publicCourses.get(i).getTermCourse().getCourse().getName(), publicCourses.get(i));
					}
					destPublicCoureList.add(publicCourses.get(i));
					noMap.put(publicCourses.get(i).getTermCourse().getCourse().getNo(), publicCourses.get(i));
				}

			}
		}
		request.setAttribute("publicCourses", destPublicCoureList);
		// 获取选修课类别列表
		List<PublicCourseType> courseTypes = publicCourseTypeService.getPublicCourseTypes();
		request.setAttribute("courseTypes", courseTypes);
		return "admin_course_profiles";
	}

	public ITermService getTermService() {
		return termService;
	}

	public void setTermService(ITermService termService) {
		this.termService = termService;
	}

	public IPublicCourseProfilesService getPublicCourseProfilesService() {
		return publicCourseProfilesService;
	}

	public void setPublicCourseProfilesService(IPublicCourseProfilesService publicCourseProfilesService) {
		this.publicCourseProfilesService = publicCourseProfilesService;
	}

	public IPublicCourseService getPublicCourseService() {
		return publicCourseService;
	}

	public void setPublicCourseService(IPublicCourseService publicCourseService) {
		this.publicCourseService = publicCourseService;
	}

	public ICampusAreaService getCampusAreaService() {
		return campusAreaService;
	}

	public void setCampusAreaService(ICampusAreaService campusAreaService) {
		this.campusAreaService = campusAreaService;
	}

	public IPublicCourseTypeService getPublicCourseTypeService() {
		return publicCourseTypeService;
	}

	public void setPublicCourseTypeService(IPublicCourseTypeService publicCourseTypeService) {
		this.publicCourseTypeService = publicCourseTypeService;
	}
}
