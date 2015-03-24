package com.zhku.module.analysis.impl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zhku.bean.Academy;
import com.zhku.bean.Organization;
import com.zhku.module.analysis.IAnalysiser;
import com.zhku.module.utils.HTMLUtil;
import com.zhku.web.Constants;
/**
 * 学院信息解析类
 * @author JackCan
 *
 */
public class OrganizationAnalysiser extends AnalysiserBase implements IAnalysiser {

	@Override
	public List<Organization> doAnalysis(String html) {
			List<Organization> list = new ArrayList<Organization>();
			Element element = null;
			Elements options = null;
			element=HTMLUtil.getSelectorByName(html, Constants.HTML_ELEMENT_NAME.SELECT_NAME_ORGANIZATION.getValue());
			if (element != null) {
				options = element.children();
				Organization organization = null;
				for (Element e : options) {
					if (e.attr("value").equals(""))
						continue;
					organization = new Organization();
					organization.setName(HTMLUtil.cutName(e.text()));
					organization.setNo(e.attr("value"));
					if(organization.getName()!=null&&(organization.getName().indexOf("学院")<0&&organization.getName().indexOf("系")<0)){
						organization.setTypeId(Organization.TYPE_ORGANIZATION);
					}else{
						organization.setTypeId(Academy.TYPE_ACADMEY);
					}
					list.add(organization);
				}
			}
			return list;
	}

}
