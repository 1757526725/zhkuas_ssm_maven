package com.zhku.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import zhku.jackcan.webCrawler.exception.FetchTimeoutException;

import com.github.pagehelper.Page;
import com.zhku.bean.Academy;
import com.zhku.bean.Major;
import com.zhku.bean.Organization;
import com.zhku.exception.ObjectExistsException;
import com.zhku.module.fetchData.FetchHelper;
import com.zhku.service.db.IMajorService;
import com.zhku.service.db.IOrganizationService;
import com.zhku.utils.WebUtils;
import com.zhku.web.Constants;
import com.zhku.web.Constants.PageSize;

@Controller
@RequestMapping("/admin/school")
public class MajorDataController {
	private Log log = LogFactory.getLog(MajorDataController.class);
	@Autowired
	private IOrganizationService organizationService;
	@Autowired
	private IMajorService majorService;
	@RequestMapping("/majors/page/{pageNum}")
	public String showMajors(@PathVariable Integer pageNum,HttpServletRequest request) {
		List<Academy> list = organizationService.getAcademys();
		request.setAttribute("academyList", list);
		List<Major> majorList = majorService.getMajors(pageNum,PageSize.ADMIN_MAJOR.getSize(),true);
		Page page = (Page)(majorList);
		request.setAttribute("majorList", page);
		return "admin_major";
	}
	@RequestMapping("/majors")
	public String showMajors(HttpServletRequest request) {
		List<Academy> list = organizationService.getAcademys();
		request.setAttribute("academyList", list);
		List<Major> majorList = majorService.getMajors(1,PageSize.ADMIN_MAJOR.getSize(),true);
		Page page = (Page)(majorList);
		request.setAttribute("majorList", page);
		return "admin_major";
	}
	@RequestMapping("/academy/{academyNo}/majors")
	public String showMajors(@PathVariable String academyNo,HttpServletRequest request) {
		List<Academy> list = organizationService.getAcademys();
		request.setAttribute("academyList", list);
		Academy academy = organizationService.getAcademyByNo(academyNo);
		List<Major> majorList =null;
		if(academy!=null)
			majorList = academy.getMajorList();
		request.setAttribute("majorList", majorList);
		request.setAttribute("currentAcademy", academy);
		return "admin_major";
	}
	/**
	 * 提取专业信息合并数据。
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/majors",method = RequestMethod.PUT)
	public Map<String,Object> saveMajors(HttpSession session,String event){
		/**保存到数据库**/
		if(event!=null&&event.equals("save")){
			List<Major> majorList =(List<Major>) session.getAttribute(Constants.SessionKey.FETCH_MAJOR_DATA_LIST.toString());
			Map<String,String> result =  new HashMap<String,String>();
			int addCount=0,updateCount=0;
			for(Major major :majorList){
				try {
					majorService.addMajor(major);
					addCount++;
				} catch (ObjectExistsException e) {
					//如果是已经存在的，就更新数据
					majorService.updateMajor(major);
					updateCount++;
				}
			}
			result.put("addCount", addCount+"");
			result.put("updateCount", updateCount+"");
			return WebUtils.webJsonResult(result);
		}
		/**保存到数据库 END**/
		//从db 里面拿到 academyList
		List<Academy> academyList = organizationService.getAcademys();
		List<Major> majorList = new ArrayList<Major>();
		//根据 academyNo 获取到 所有的 major
		Iterator<Academy> it = academyList.iterator();
		int academySize = academyList.size();
		while(it.hasNext()){
			Academy academy=it.next();
			List<Major> list = (List<Major>) session.getAttribute(Constants.SessionKey.FETCH_MAJOR.toString()+academy.getNo());
			if(list!=null&&list.size()>0){
				majorList.addAll(list);
				session.removeAttribute(Constants.SessionKey.FETCH_MAJOR.toString()+academy.getNo()); //移除掉session里面的临时数据
				it.remove(); //提取完成后直接移除任务队列
			}
		}
		int remainSize = academyList.size();
		//将这一步的数据存到session中，待用户确认保存后再取出放数据库
		session.setAttribute(Constants.SessionKey.FETCH_MAJOR_DATA_LIST.toString(), majorList);
		Map<String,String> result =  new HashMap<String,String>();
		result.put("done",(academySize-remainSize)+"");
		result.put("all",(academySize)+"");
		return WebUtils.webJsonResult(result);
	}
	@ResponseBody
	@RequestMapping(value="/majors/{academyNo}",method = RequestMethod.PUT)
	public Map<String,Object> updateMajors(@PathVariable String academyNo,HttpSession session){
		if(academyNo == null||academyNo.equals(""))
			return WebUtils.webJsonError(Constants.Error.PARAMETER_ERROR);
		FetchHelper fetchHelper = new FetchHelper();
		Academy acadmey=new Academy();
		acadmey.setNo(academyNo);
		try {
			List<Major> majorList = fetchHelper.fetchMajorData(acadmey);
			if(majorList!=null&&majorList.size()>0){
				//暂时写入 session 以 学院 号为 key 避免 线程安全问题
				session.setAttribute(Constants.SessionKey.FETCH_MAJOR.toString()+acadmey.getNo(), majorList);
			}
		} catch (FetchTimeoutException e) {
			return WebUtils.webJsonError(Constants.Error.FETCH_TIME_OUT);
		}
		return WebUtils.webJsonResult("done");
	}
	public IOrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(IOrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	public IMajorService getMajorService() {
		return majorService;
	}

	public void setMajorService(IMajorService majorService) {
		this.majorService = majorService;
	}
}
