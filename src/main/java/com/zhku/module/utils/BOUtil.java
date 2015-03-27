package com.zhku.module.utils;

import java.util.ArrayList;
import java.util.List;

import com.zhku.bean.Academy;
import com.zhku.bean.Course;
import com.zhku.bean.Organization;
import com.zhku.module.fetchData.bo.CoursePageBo;

public class BOUtil {
	/**
	 * 过滤学校的非院校的机构
	 * @param organizations
	 * @return
	 */
	public static List<Academy> academyfilter(List<Organization> organizations){
		List<Academy> academies = new ArrayList<Academy>();
		for(Organization o : organizations){
			Academy academy =null;
			if(o.getTypeId()==Academy.TYPE_ACADMEY){
				academy= new Academy();
				academy.setId(o.getId());
				academy.setName(o.getName());
				academy.setNo(o.getNo());
				academy.setTypeId(Academy.TYPE_ACADMEY);
				academies.add(academy);
			}
		}
		return academies;
	}
}
