package com.zhku.controller.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhku.bean.Course;
import com.zhku.bean.CourseTypeRecord;
import com.zhku.bean.MyPublicCourse;
import com.zhku.bean.PublicCourseProfiles;
import com.zhku.bean.PublicCourseType;
import com.zhku.bean.User;
import com.zhku.controller.util.SecurityUtil;
import com.zhku.service.db.ICourseTypeRecordService;
import com.zhku.service.db.IMyPublicCourseService;
import com.zhku.service.db.IPublicCourseProfilesService;
import com.zhku.utils.WebUtils;
import com.zhku.web.Constants.Error;

@Controller
@RequestMapping(value="/data")
public class UserDataController {
	@Autowired
	private ICourseTypeRecordService courseTypeRecordService;
	@Autowired
	private IMyPublicCourseService myPublicCourseSerivce;
	@Autowired
	private IPublicCourseProfilesService publicCourseProfilesService;
	@ResponseBody
	@RequestMapping("/user/course/public/json")
	public Map<String,Object> getMyPublicCourseJsonList(HttpSession session){
		User  user = SecurityUtil.getUser(session);
		if(user==null) return WebUtils.webJsonError(Error.PERMISSIONS_DO_NOT_ALLOW);
		List<MyPublicCourse> myPublicCourses = myPublicCourseSerivce.getMyPublicCoursesByUid(user.getUid());
		int size[] = new int[4];
		double credit[] = new double[4];
		double countCredit = 0;
		size[0] = 0;
		int index = 0;
		if (myPublicCourses != null) {
			for (int i = 0; i < myPublicCourses.size(); ++i) {
				Course prev = myPublicCourses.get(i).getCourse();
				Course after = null;
				if (prev.getCourseProfiles() == null
						|| prev.getCourseProfiles().getPcType() == null) {
					CourseTypeRecord record = courseTypeRecordService.getCourseTypeRecordByCName(prev.getName());
					if (record != null) {
						PublicCourseProfiles temp = new PublicCourseProfiles();
						PublicCourseType type = new PublicCourseType();
						type.setId(record.getPctid());
						temp.setcNo(prev.getNo());
						temp.setPcType(type);
						publicCourseProfilesService.addPublicCourseProfiles(temp);
						prev.setCourseProfiles(temp);
					}

				}
				if (i != 0) {
					after = myPublicCourses.get(i - 1).getCourse();
					if (after.getCourseProfiles() == null
							|| after.getCourseProfiles().getPcType() == null) {
						if (!after.getNo().equals(prev.getNo())) {
							CourseTypeRecord record = courseTypeRecordService.getCourseTypeRecordByCName(after.getName());
							if (record != null) {
								PublicCourseProfiles temp = new PublicCourseProfiles();
								PublicCourseType type = new PublicCourseType();
								type.setId(record.getPctid());
								temp.setcNo(after.getNo());
								temp.setPcType(type);
								publicCourseProfilesService.addPublicCourseProfiles(temp);
							}
						} else {
							after.setCourseProfiles(prev
									.getCourseProfiles());
						}

					}
				}
				if (i != 0
						&& prev.getCourseProfiles().getPcType().getId() != after
								.getCourseProfiles().getPcType().getId()) {
					size[index] = i;
					for (int j = index - 1; j >= 0; j--) {
						size[index] -= (index == 0 ? 0 : size[j]);
					}
					index++;
					if (myPublicCourses.get(i).getScore() != null) {
						credit[index] = myPublicCourses.get(i).getCourse()
								.getCredits();
						countCredit += credit[index];
					}
				} else {
					if (myPublicCourses.get(i).getScore() != null) {
						credit[index] += myPublicCourses.get(i).getCourse()
								.getCredits();
						countCredit += myPublicCourses.get(i).getCourse()
								.getCredits();
					}
				}

				size[index] = 5;
			}
		}
		Map<Object,Object> resultMap = new HashMap<Object,Object>();
		resultMap.put("countCredit", countCredit);
		resultMap.put("credit", credit);
		resultMap.put("size", size);
		resultMap.put("list", myPublicCourses);
		return WebUtils.webJsonResult(resultMap);
	}
	public ICourseTypeRecordService getCourseTypeRecordService() {
		return courseTypeRecordService;
	}
	public void setCourseTypeRecordService(ICourseTypeRecordService courseTypeRecordService) {
		this.courseTypeRecordService = courseTypeRecordService;
	}
	public IPublicCourseProfilesService getPublicCourseProfilesService() {
		return publicCourseProfilesService;
	}
	public void setPublicCourseProfilesService(IPublicCourseProfilesService publicCourseProfilesService) {
		this.publicCourseProfilesService = publicCourseProfilesService;
	}
	public IMyPublicCourseService getMyPublicCourseSerivce() {
		return myPublicCourseSerivce;
	}
	public void setMyPublicCourseSerivce(IMyPublicCourseService myPublicCourseSerivce) {
		this.myPublicCourseSerivce = myPublicCourseSerivce;
	}
	
}
