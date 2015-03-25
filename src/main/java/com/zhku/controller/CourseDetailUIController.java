package com.zhku.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhku.bean.CampusArea;
import com.zhku.bean.Course;
import com.zhku.bean.MyCourseScheme;
import com.zhku.bean.Pagination;
import com.zhku.bean.PublicCourseComment;
import com.zhku.bean.RemarkRecord;
import com.zhku.bean.Term;
import com.zhku.bean.User;
import com.zhku.controller.util.SecurityUtil;
import com.zhku.service.db.ICourseService;
import com.zhku.service.db.IMyCourseSchemeService;
import com.zhku.service.db.IRemarkRecordService;
import com.zhku.service.db.ITermService;
import com.zhku.utils.RegExpValidator;
import com.zhku.web.Constants;
import com.zhku.web.Constants.Campus;
/**
 * 课程详细信息页
 * @author JackCan
 *
 */
@Controller
@RequestMapping("/main/course/detail")
public class CourseDetailUIController {
	@Autowired
	private ICourseService courseService;
	@Autowired
	private IRemarkRecordService remarkRecordService;
	@Autowired
	private ITermService termService;
	@Autowired
	private IMyCourseSchemeService myCourseSchemeService;
	public ICourseService getCourseService() {
		return courseService;
	}
	public void setCourseService(ICourseService courseService) {
		this.courseService = courseService;
	}
	
	
	@RequestMapping("{cNo}")
	public String showDetail(@PathVariable String cNo,HttpServletRequest request,HttpSession session,Integer currentPage){
		Course course = courseService.getCourseWitchDetailByCno(cNo);
		request.setAttribute("course", course);
		User  user = SecurityUtil.getUser(session);
		CampusArea campus = null;
		RemarkRecord remarkRecord=null;
		Term currentTerm = (Term) session.getAttribute("currentTerm");
		if(currentTerm == null){
			currentTerm = termService.getCurrentTerm();
			session.setAttribute("currentTerm", currentTerm);
		}
		if(user!=null){
			if(user.getStudent()!=null){
				campus=user.getStudent().getClassBelongTo().getCampus();
				MyCourseScheme scheme =new MyCourseScheme();
				scheme.setcNo(cNo);
				scheme.setTerm(currentTerm);
				scheme.setUid(user.getUid());
				scheme=myCourseSchemeService.findMyCourseScheme(scheme);
				request.setAttribute("scheme", scheme);
			}
			remarkRecord = remarkRecordService.getRemarkRecordByCnoAndUid(cNo, user.getUid());
			request.setAttribute("remarkRecord", remarkRecord);
		}
		if(campus==null){
			campus = new CampusArea();
			campus.setId(Campus.HAIZHU.getId());
			campus.setName("海珠校区");
		}
		request.setAttribute("campus", campus);
		
		
		/**评论**/
		Pagination<PublicCourseComment> pagination = new Pagination<PublicCourseComment>();
		pagination.setPageSize(Constants.PageSize.MY_COMMENT.getSize());
		if(currentPage==null){
			pagination.setCurrentPage(1);
		}else{
			pagination.setCurrentPage(currentPage);
		}
		List<PublicCourseComment> comments=course.getCommentList();
		pagination.setPageDataList(comments);
		request.setAttribute("pagination", pagination);
		/**END**/
		return "public_course_detail";
	}
	public ITermService getTermService() {
		return termService;
	}
	public void setTermService(ITermService termService) {
		this.termService = termService;
	}
	public IRemarkRecordService getRemarkRecordService() {
		return remarkRecordService;
	}
	public void setRemarkRecordService(IRemarkRecordService remarkRecordService) {
		this.remarkRecordService = remarkRecordService;
	}
	public IMyCourseSchemeService getMyCourseSchemeService() {
		return myCourseSchemeService;
	}
	public void setMyCourseSchemeService(IMyCourseSchemeService myCourseSchemeService) {
		this.myCourseSchemeService = myCourseSchemeService;
	}
}
