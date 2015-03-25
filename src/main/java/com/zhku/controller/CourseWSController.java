package com.zhku.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhku.bean.PublicCourseProfiles;
import com.zhku.bean.RemarkRecord;
import com.zhku.bean.User;
import com.zhku.controller.util.SecurityUtil;
import com.zhku.service.db.IPublicCourseProfilesService;
import com.zhku.service.db.IRemarkRecordService;
import com.zhku.utils.WebUtils;
import com.zhku.web.Constants.Error;

/**
 * 课程相关的webService 服务的入口
 * @author JackCan
 *
 */
@Controller
@RequestMapping("/main/ws/")
public class CourseWSController {
	
	@Autowired
	private IRemarkRecordService remarkRecordService;
	@Autowired
	private IPublicCourseProfilesService publicCourseProfilesService;
	@ResponseBody
	@RequestMapping(value="user/course/remark",method=RequestMethod.POST)
	public Map<String,Object> doCourseRemark(HttpSession session, String cNo,String remarkItem){
		User user = SecurityUtil.getUser(session);
		if(user==null){
			return WebUtils.webJsonError(Error.HAVE_NOT_LOGIN_SYSTEM);
		}
		RemarkRecord record = remarkRecordService.getRemarkRecordByCnoAndUid(cNo, user.getUid());
		PublicCourseProfiles courseProfile = publicCourseProfilesService.getPublicCourseProfilesByCno(cNo);
		if (record == null) {
			// 创建一个 记录
			RemarkRecord remarkRecord = new RemarkRecord(cNo, user.getUid());
			remarkRecord.setRemarkItem(Integer.valueOf(remarkItem));
			remarkRecordService.addRemarkRecord(remarkRecord);
			if(courseProfile==null){
				courseProfile=new PublicCourseProfiles();
				courseProfile.setcNo(cNo);
				courseProfile.setRecommend("-");
				if(remarkItem.equals("1")){
					courseProfile.setGoodCount(1);
				}else if(remarkItem.equals("2")){
					courseProfile.setBadCount(1);
				}
				publicCourseProfilesService.addPublicCourseProfiles(courseProfile);
			}else{
				if(remarkItem.equals("1")){
					courseProfile.setGoodCount(courseProfile.getGoodCount()+1);
				}else if(remarkItem.equals("2")){
					courseProfile.setBadCount(courseProfile.getBadCount()+1);
				}
				publicCourseProfilesService.updatePublicCourseProfiles(courseProfile);
			}
			int count=0;
			if(remarkItem.equals("1")){
				count=courseProfile.getGoodCount();
			}else if(remarkItem.equals("2")){
				count=courseProfile.getBadCount();
			}
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap.put("count", count);
			return WebUtils.webJsonResult(resultMap);
		}else{
			return WebUtils.webJsonError("你已经点过了!!!");
		}
	}
	@ResponseBody
	@RequestMapping("user/course/{cNo}/remark")
	public Map<String,Object> getUserRemarkInfo(HttpSession session,@PathVariable String cNo){
		User user = SecurityUtil.getUser(session);
		if(user==null){
			return WebUtils.webJsonError(Error.HAVE_NOT_LOGIN_SYSTEM);
		}
		RemarkRecord record = remarkRecordService.getRemarkRecordByCnoAndUid(cNo, user.getUid());
		return null;
	}
	public IRemarkRecordService getRemarkRecordService() {
		return remarkRecordService;
	}
	public void setRemarkRecordService(IRemarkRecordService remarkRecordService) {
		this.remarkRecordService = remarkRecordService;
	}
	public IPublicCourseProfilesService getPublicCourseProfilesService() {
		return publicCourseProfilesService;
	}
	public void setPublicCourseProfilesService(IPublicCourseProfilesService publicCourseProfilesService) {
		this.publicCourseProfilesService = publicCourseProfilesService;
	}
}
