package com.zhku.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import zhku.jackcan.webCrawler.exception.FetchTimeoutException;

import com.zhku.bean.Academy;
import com.zhku.bean.BaseClass;
import com.zhku.bean.CampusAreaClassRS;
import com.zhku.bean.Major;
import com.zhku.bean.Term;
import com.zhku.exception.ObjectExistsException;
import com.zhku.module.fetchData.FetchHelper;
import com.zhku.service.db.IBaseClassService;
import com.zhku.service.db.ICampusAreaClassRSService;
import com.zhku.service.db.IMajorService;
import com.zhku.service.db.IOrganizationService;
import com.zhku.service.db.ITermService;
import com.zhku.utils.WebUtils;
import com.zhku.web.Constants.Error;
import com.zhku.web.Constants.PageSize;
import com.zhku.web.Constants.SessionKey;

@Controller
@RequestMapping("/admin/school")
public class BaseClassDataController {
	@Autowired
	private IBaseClassService baseClassService;
	@Autowired
	private IOrganizationService organizationService;
	@Autowired
	private ITermService termService;
	@Autowired
	private ICampusAreaClassRSService campusAreaClassRSService;
	@Autowired
	private IMajorService majorService;

	@ResponseBody
	@RequestMapping(value = "/campusAndclass/rs", method = RequestMethod.POST)
	public Map<String, Object> saveCampusAreaClassRS(HttpSession session, String classNos, Integer campusId) {
		if (classNos == null || campusId == null) {
			return WebUtils.webJsonError(Error.PARAMETER_ERROR);
		}
		int count = 0;
		String[] classNoArray = classNos.split(",");
		for (String classNo : classNoArray) {
			CampusAreaClassRS campusAreaClassRS = new CampusAreaClassRS();
			campusAreaClassRS.setCampusId(campusId);
			campusAreaClassRS.setClassNo(classNo);
			CampusAreaClassRS dbItem = campusAreaClassRSService.getCampusAreaClassRSByClassNo(classNo);
			if (dbItem == null) {
				BaseClass dbClass = baseClassService.getBaseClassByClassNo(classNo);
				if (dbClass != null) {
					campusAreaClassRSService.addCampusAreaClassRS(campusAreaClassRS);
					count++;
				}
			} else {
				if (!dbItem.getCampusId().equals(campusId)) {
					dbItem.setCampusId(campusId);
					campusAreaClassRSService.updateCampusAreaClassRS(dbItem);
				}
				count++;
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", count);
		result.put("fail", classNoArray.length - count);
		return WebUtils.webJsonResult(result);
	}

	/**
	 * 更新数据 触发的粒度是 专业，会使用数据库的专业信息，故需要提前更新专业信息。
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/classes", method = RequestMethod.PUT)
	public Map<String, Object> update(HttpSession session, String majorNo, Integer step) {
		if (1 == step) {
			Set<String> grades = (Set<String>) session.getAttribute("grades");
			if (grades == null) { // 如果在内存中找不到年级的数据，就到数据库中取
				grades = termService.getSchoolGrades();
				session.setAttribute("grades", grades);
			}
			Major major = new Major();
			major.setNo(majorNo);
			FetchHelper fetchHelper = new FetchHelper();
			List<BaseClass> list = new ArrayList<BaseClass>();
			for (String grade : grades) {
				try {
					// 成功的task
					List<BaseClass> baseClassList = fetchHelper.fetchClassData(major, grade);
					if (baseClassList.size() > 0)
						list.addAll(baseClassList);
				} catch (FetchTimeoutException e) {
					// 失败的Task
				}
			}
			session.setAttribute(SessionKey.FETCH_MAJOR_BASECLASS.toString() + majorNo, list);
			return WebUtils.webJsonResult(list.size()+"个班级");
		}else if(2==step){ //整理数据
			List<Major> majors = majorService.getMajors();
			List<BaseClass> list = new ArrayList<BaseClass>();
			for (Major mj : majors) {
				//
				List<BaseClass> tempList = (List<BaseClass>) session.getAttribute(SessionKey.FETCH_MAJOR_BASECLASS.toString() + mj.getNo());
				if (tempList != null && tempList.size() > 0) {
					list.addAll(tempList);
					// 将缓存数据清除
					session.removeAttribute(SessionKey.FETCH_MAJOR_BASECLASS.toString() + mj.getNo());
				}
			}
			session.setAttribute(SessionKey.FETCH_MAJOR_BASECLASS_DATA_LIST.toString(), list);
			Map<String,Integer> result = new HashMap<String,Integer>();
			result.put("size",list.size());
			return WebUtils.webJsonResult(result);
		}else{
			return WebUtils.webJsonError("参数错误");
		}
		
		
	}

	/**
	 * 最后一步，保存数据
	 * 
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/classes",method =RequestMethod.POST)
	public Map<String, Object> save(HttpSession session) {
		List<BaseClass> list = (List<BaseClass>) session.getAttribute(SessionKey.FETCH_MAJOR_BASECLASS_DATA_LIST.toString());
		int addCount = 0;
		int existCount = 0;
		// 写入数据库
		for (BaseClass bc : list) {
			try {
				baseClassService.addBaseClass(bc);
				addCount++;
			} catch (ObjectExistsException e) {
				existCount++;
			}
		}
		Map<String, Integer> result = new HashMap<String, Integer>();
		result.put("addCount", addCount);
		result.put("existCount", existCount);
		return WebUtils.webJsonResult(result);
	}

	@RequestMapping(value = "/classes")
	public String show(HttpSession session, HttpServletRequest request) {
		List<BaseClass> list = baseClassService.getBaseClassesWithMajorAndCampus(1, PageSize.ADMIN_CLASS.getSize(), true);
		request.setAttribute("classes", list);
		if (session.getAttribute("admin_class_acadmeys") == null) {
			List<Academy> acadmeylist = organizationService.getAcademys();
			session.setAttribute("admin_class_acadmeys", acadmeylist);
		}
		// 填充当学年信息
		fillYearsData(session);
		return "admin_class";
	}
	
	
	@RequestMapping(value = "/classes/page/{pageNum}")
	public String showPage(@PathVariable Integer pageNum, HttpSession session, HttpServletRequest request) {
		List<BaseClass> list = baseClassService.getBaseClassesWithMajorAndCampus(pageNum, PageSize.ADMIN_CLASS.getSize(), true);
		request.setAttribute("classes", list);
		if (session.getAttribute("admin_class_acadmeys") == null) {
			List<Academy> acadmeylist = organizationService.getAcademys();
			session.setAttribute("admin_class_acadmeys", acadmeylist);
		}
		fillYearsData(session);
		return "admin_class";
	}
	
	@RequestMapping(value = "/major/{majorNo}/classes")
	public String show(@PathVariable String majorNo, HttpSession session, HttpServletRequest request){
		List<BaseClass> list = baseClassService.getBaseClassesWithMajorAndCampusByMajorNo(majorNo,1, PageSize.ADMIN_CLASS.getSize(), true);
		request.setAttribute("classes", list);
		if (session.getAttribute("admin_class_acadmeys") == null) {
			List<Academy> acadmeylist = organizationService.getAcademys();
			session.setAttribute("admin_class_acadmeys", acadmeylist);
		}
		fillYearsData(session);
		return "admin_class";
	}
	@RequestMapping(value = "/major/{majorNo}/classes/page/{pageNum}")
	public String show(@PathVariable String majorNo,@PathVariable Integer pageNum, HttpSession session, HttpServletRequest request){
		List<BaseClass> list = baseClassService.getBaseClassesWithMajorAndCampusByMajorNo(majorNo,pageNum, PageSize.ADMIN_CLASS.getSize(), true);
		request.setAttribute("classes", list);
		if (session.getAttribute("admin_class_acadmeys") == null) {
			List<Academy> acadmeylist = organizationService.getAcademys();
			session.setAttribute("admin_class_acadmeys", acadmeylist);
		}
		fillYearsData(session);
		return "admin_class";
	}
	/**
	 * 填充年份列表，给校区编辑
	 * @param session
	 */
	private void fillYearsData(HttpSession session) {
		if (session.getAttribute("admin_class_years") == null) {
			Term term = termService.getCurrentTerm();
			int year = Integer.parseInt(term.getNo().substring(0, 4));
			List<Integer> years = new ArrayList<Integer>();
			StringBuffer sb = new StringBuffer();
			for (int i = year - 4; i <= year + 1; i++) {
				years.add(i);
				sb.append(i).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			session.setAttribute("grade_all", sb.toString());
			session.setAttribute("admin_class_years", years);
		}
	}


	public IBaseClassService getBaseClassService() {
		return baseClassService;
	}

	public void setBaseClassService(IBaseClassService baseClassService) {
		this.baseClassService = baseClassService;
	}

	public IOrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(IOrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	public ITermService getTermService() {
		return termService;
	}

	public void setTermService(ITermService termService) {
		this.termService = termService;
	}

	public ICampusAreaClassRSService getCampusAreaClassRSService() {
		return campusAreaClassRSService;
	}

	public void setCampusAreaClassRSService(ICampusAreaClassRSService campusAreaClassRSService) {
		this.campusAreaClassRSService = campusAreaClassRSService;
	}

	public IMajorService getMajorService() {
		return majorService;
	}

	public void setMajorService(IMajorService majorService) {
		this.majorService = majorService;
	}

}
