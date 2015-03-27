package com.zhku.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import zhku.jackcan.webCrawler.exception.FetchTimeoutException;

import com.zhku.bean.Academy;
import com.zhku.bean.Organization;
import com.zhku.module.fetchData.FetchHelper;
import com.zhku.service.db.IOrganizationService;
import com.zhku.utils.WebUtils;
import com.zhku.web.Constants;

@Controller
@RequestMapping("/admin/school")
public class AcademyDataController {
	private Log log = LogFactory.getLog(AcademyDataController.class);
	@Autowired
	private IOrganizationService organizationService;
	@RequestMapping(value={"","/academys"},method=RequestMethod.GET)
	public String show(HttpServletRequest request) {
		List<Academy> list = organizationService.getOrganizations();
		request.setAttribute("organizationList", list);
		return "admin_academy";
	}
	@ResponseBody
	@RequestMapping("/academys/json")
	public Map<String,Object> getAcademysList(){
		Map<String, Object> retval = new HashMap<String, Object>();
		List<Academy> academyList = organizationService.getAcademys();
		if(academyList==null){
			retval.put("state", 0);
			retval.put("error", Constants.Error.ACADEMY_LIST_IS_EMPTY);
			return retval;
		}
		return WebUtils.webJsonResult(academyList);
	}
	
	/**
	 * 学校机构 抓取 更新 控制器
	 * 
	 * @param step
	 *            1 代表抓取数据 ,2 代表存入数据库
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/academys", method = RequestMethod.PUT)
	public Map<String, Object> updateAcademys(Integer step, HttpSession session) {
		Map<String, Object> retval = new HashMap<String, Object>();
		List<Organization> organizationList = null;
		if (step != null) {
			try {
				switch ((int) step) {
				case 1: // 抓取数据
					FetchHelper fetchHelper = new FetchHelper();
					organizationList = fetchHelper.fetchOrganizationData();
					session.setAttribute(Constants.SessionKey.FETCH_ORGANIZATION_DATA.toString(), organizationList);
					retval.put("state", 1);
					retval.put("result", organizationList);
					return retval;
				case 2: // 保存数据
					// 先从session里面那到第一步的数据
					organizationList = (List<Organization>) session.getAttribute(Constants.SessionKey.FETCH_ORGANIZATION_DATA.toString());
					if (organizationList == null || organizationList.size() == 0) {
						retval.put("state", 0);
						retval.put("error", Constants.Error.FETCH_ORGANIZATION_EMPTY.toMap());
						return retval;
					}
					// 获取到数据库的原数据，进行对比，判断有多少是更新的。
					List<Academy> originList = organizationService.getOrganizations();
					// 转换成map
					Map<String, Organization> originMap = new HashMap<String, Organization>();

					for (Organization originOrganization : originList) {
						if(originOrganization!=null)
						originMap.put(originOrganization.getNo(), originOrganization);
					}
					int updateCount = 0,
					addCount = 0;
					for (Organization newOrganization : organizationList) {
						Organization originOrganization = originMap.get(newOrganization.getNo());
						if (originOrganization == null) {
							addCount++;
							organizationService.addOrganization(newOrganization);
						} else if (!newOrganization.getName().equals(originOrganization.getName())) {
							originOrganization.setName(newOrganization.getName());
							organizationService.updateOrganization(originOrganization);
							updateCount++;
						}
					}
					retval.put("state", 1);
					retval.put("result", "存盘成功,更新了" + updateCount + "条,添加了" + addCount + "条！");
					return retval;
				default:
					break;
				}

			} catch (FetchTimeoutException e) {
				log.fatal("机构信息抓取失败!");
				retval.put("state", 0);
				retval.put("error", Constants.Error.FETCH_ORGANIZATION_FAIL.toMap());
				return retval;
				// e.printStackTrace();
			}
		}
		retval.put("state", 0);
		retval.put("error", Constants.Error.FETCH_ORGANIZATION_PARAM_WRONG.toMap());
		return retval;
	}

	public IOrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(IOrganizationService organizationService) {
		this.organizationService = organizationService;
	}

}
