package com.zhku.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhku.bean.Course;
import com.zhku.bean.PublicCourse;
import com.zhku.bean.PublicCourseComment;
import com.zhku.bean.PublicCourseProfiles;
import com.zhku.bean.PublicCourseType;
import com.zhku.bean.RemarkRecord;
import com.zhku.bean.User;
import com.zhku.controller.util.SecurityUtil;
import com.zhku.service.db.ICourseService;
import com.zhku.service.db.IPublicCourseCommentService;
import com.zhku.service.db.IPublicCourseProfilesService;
import com.zhku.service.db.IPublicCourseService;
import com.zhku.service.db.IPublicCourseTypeService;
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
	private ICourseService courseService;
	@Autowired
	private IRemarkRecordService remarkRecordService;
	@Autowired
	private IPublicCourseProfilesService publicCourseProfilesService;
	@Autowired
	private IPublicCourseCommentService publicCourseCommentService;
	@Autowired
	private IPublicCourseTypeService publicCourseTypeService;
	@Autowired
	private IPublicCourseService publicCourseService;
	
	/**
	 * 获取公选课类型列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("course/public/types")
	public Map<String ,Object> getPublicCourseTypeList(){
		List<PublicCourseType> list = publicCourseTypeService.getPublicCourseTypes();
		return WebUtils.webJsonResult(list);
	}
	
	
	/**
	 * 按校区、学期、类型获取到公选列表
	 * @param termNo
	 * @param campusId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("course/public/campus/{campusId}/term/{termNo}/type/{typeId}")
	public Map<String,Object> getPublicCourseList(@PathVariable String termNo,@PathVariable Integer campusId,@PathVariable Integer typeId){
		List<PublicCourse> publicCourses = publicCourseService.getPublicCoursesByTermNoAndCampusIdAndTypeId(termNo, campusId, typeId);
		return WebUtils.webJsonResult(publicCourses);
	}
	/**
	 * 按校区、学期获取到公选列表
	 * @param termNo
	 * @param campusId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("course/public/campus/{campusId}/term/{termNo}")
	public Map<String,Object> getPublicCourseList(@PathVariable String termNo,@PathVariable Integer campusId){
		List<PublicCourse> publicCourses = publicCourseService.getPublicCoursesByTermNoAndCampusId(termNo, campusId);
		return WebUtils.webJsonResult(publicCourses);
	}
	
	/**
	 * 获取课程详情
	 * @return
	 */
	@ResponseBody
	@RequestMapping("course/{cNo}/details")
	public Map<String,Object> getCourseDetailData(@PathVariable String cNo){
		Course course = courseService.getCourseWitchDetailByCno(cNo);
		return WebUtils.webJsonResult(course);
	}
	
	/**
	 * 删除评论 
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="user/course/comment/{pccid}",method=RequestMethod.DELETE)
	public Map<String,Object> removeComment(HttpSession session,@PathVariable Integer pccid){
		User user = SecurityUtil.getUser(session);
		if(user==null){
			return WebUtils.webJsonError(Error.PERMISSIONS_DO_NOT_ALLOW);
		}
		PublicCourseComment publicCourseComment = null;
		publicCourseComment = publicCourseCommentService.getPublicCourseCommentById(pccid);
		if (publicCourseComment != null && (user.getUid() == 1 ||publicCourseComment.getCommentUser().getUid()== user
				.getUid())) {
			publicCourseCommentService.deletePublicCourseComment(publicCourseComment);
			return WebUtils.webJsonResult("删除成功!");
		}else{
			return WebUtils.webJsonError("删除失败，请刷新重试!");
		}
	
	}
	
	/**
	 * POST 课程评论与回复
	 * @param session
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value="user/course/comment",method=RequestMethod.POST)
	public Map<String,Object> makeCommentOrReply(HttpSession session ,String cNo,String mode,String content,Integer parent_pccid,Integer reply_uid){
		User user = SecurityUtil.getUser(session);
		if(user==null){
			return WebUtils.webJsonError(Error.HAVE_NOT_LOGIN_SYSTEM);
		}
		//判断是不是合法的表单
		if(content==null||"".equals(content.trim())){
			return WebUtils.webJsonError("内容不能为空!");
		}
		PublicCourseComment comment=new PublicCourseComment();
		comment.setcNo(cNo);
		comment.setCommentUser(user);
		comment.setContent(content);
		if ("creatRootComment".equals(mode)) {
			
		}else if("replyComment".equals(mode)||"directReply".equals(mode)){	
			comment.setParentId(parent_pccid);
			comment.setReplyUser(new User(reply_uid));
			
		}
		publicCourseCommentService.addPublicCourseComment(comment);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("parentId", comment.getId());
		return WebUtils.webJsonResult(result);
	}
	
	/**
	 * POST 用户 点赞 或者点 踩
	 * @param cNo
	 * @param remarkItem
	 * @return
	 */
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
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap.put("good", courseProfile.getGoodCount());
			resultMap.put("bad", courseProfile.getBadCount());
			return WebUtils.webJsonResult(resultMap);
		}else{
			return WebUtils.webJsonError("你已经点过了!!!");
		}
	}
	/**
	 * 查询当前课程的点赞和点踩得人数
	 * @param session
	 * @param cNo
	 * @return json{
	 * 
	 * 			}
	 */
	@ResponseBody
	@RequestMapping("user/course/{cNo}/remark")
	public Map<String,Object> getUserRemarkInfo(HttpSession session,@PathVariable String cNo){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Integer good = 0;
		Integer bad =  0;
		PublicCourseProfiles courseProfile = publicCourseProfilesService.getPublicCourseProfilesByCno(cNo);
		if(courseProfile!=null){
			good =courseProfile.getGoodCount();
			bad = courseProfile.getBadCount();
		}
		User user = SecurityUtil.getUser(session);
		if(user!=null){
			RemarkRecord record = remarkRecordService.getRemarkRecordByCnoAndUid(cNo, user.getUid());
			if(record!=null){
				Map<String,Object>  subResult = new HashMap<String,Object>();
				subResult.put("remarkItem", record.getRemarkItem());
				subResult.put("remarkItemName", record.getRemarkItem()==1?"赞":"踩");
				resultMap.put("userRecord", subResult);
			}
		}
		resultMap.put("good", good);
		resultMap.put("bad", bad);
		return resultMap;
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

	public IPublicCourseCommentService getPublicCourseCommentService() {
		return publicCourseCommentService;
	}

	public void setPublicCourseCommentService(IPublicCourseCommentService publicCourseCommentService) {
		this.publicCourseCommentService = publicCourseCommentService;
	}

	public ICourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(ICourseService courseService) {
		this.courseService = courseService;
	}

	public IPublicCourseTypeService getPublicCourseTypeService() {
		return publicCourseTypeService;
	}

	public void setPublicCourseTypeService(IPublicCourseTypeService publicCourseTypeService) {
		this.publicCourseTypeService = publicCourseTypeService;
	}

	public IPublicCourseService getPublicCourseService() {
		return publicCourseService;
	}

	public void setPublicCourseService(IPublicCourseService publicCourseService) {
		this.publicCourseService = publicCourseService;
	}
}
