package com.zhku.dao;

import java.util.List;

import com.zhku.bean.Academy;
import com.zhku.bean.Organization;

public interface OrganizationMapper {
	public void addOrganization(Organization organization);
	public void addAcademy(Academy academy);
	public void deleteAcademy(Academy academy);
	public void updateAcademy(Academy academy);
	public Academy getAcademyById(int oId);
	public Academy getOrganizationByNo(String no);
	public Academy getAcademyByNo(String acadmeyNo);
	public Academy getAcademyByName(String acadmeyName);
	public List<Academy> getAcademys();
	public List<Academy> getOrganizations();
	public String getDefaultONo();
	public void updateOrganization(Organization organization);
	public Organization getOrganizationByName(String organizationName); 
}
