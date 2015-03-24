package com.zhku.service.db;

import java.util.List;

import com.zhku.bean.Academy;
import com.zhku.bean.Organization;

public interface IOrganizationService {
	public void addOrganization(Organization organization);
	public void addAcademy(Academy academy);
	public void deleteAcademy(Academy academy);
	public void updateAcademy(Academy academy);
	public Academy getAcademyById(int aId);
	public Academy getAcademyByNo(String acadmeyNo);
	public Academy getAcademyByName(String acadmeyName);
	public Organization getOrganizationByName(String organizationName);
	public List<Academy> getAcademys();
	public List<Academy> getOrganizations();
	public String getDefaultONo();
	public void updateOrganization(Organization originOrganization); 
}
