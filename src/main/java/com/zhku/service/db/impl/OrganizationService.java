package com.zhku.service.db.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhku.bean.Academy;
import com.zhku.bean.Organization;
import com.zhku.dao.OrganizationMapper;
import com.zhku.service.db.IOrganizationService;
@Service("organizationService")
public class OrganizationService implements
		IOrganizationService {
	@Autowired
	private OrganizationMapper organizationMapper ;
	@Override
	public String getDefaultONo() {
         return  ""+(Integer.parseInt(organizationMapper.getDefaultONo())+1);
	}
	@Override
	public void addOrganization(Organization organization) {
		organizationMapper.addOrganization(organization);
	}
	@Override
	public void addAcademy(Academy academy) {
		organizationMapper.addAcademy(academy);
	}

	@Override
	public void deleteAcademy(Academy academy) {
		organizationMapper.deleteAcademy(academy);
	}

	@Override
	public void updateAcademy(Academy academy) {
		organizationMapper.updateAcademy(academy);
	}

	@Override
	public Academy getAcademyById(int aId) {
		return organizationMapper.getAcademyById(aId);
	}

	@Override
	public Academy getAcademyByNo(String acadmeyNo) {
		return organizationMapper.getAcademyByNo(acadmeyNo);
	}

	@Override
	public Academy getAcademyByName(String acadmeyName) {
		return organizationMapper.getAcademyByName(acadmeyName);
	}

	@Override
	public List<Academy> getAcademys() {
		return organizationMapper.getAcademys();
	}
	public OrganizationMapper getOrganizationMapper() {
		return organizationMapper;
	}

	public void setOrganizationMapper(OrganizationMapper organizationMapper) {
		this.organizationMapper = organizationMapper;
	}
	@Override
	public List<Academy> getOrganizations() {
		return this.organizationMapper.getOrganizations();
	}
	@Override
	public void updateOrganization(Organization organization) {
		this.organizationMapper.updateOrganization(organization);
	}
	@Override
	public Organization getOrganizationByName(String organizationName) {
		return this.organizationMapper.getOrganizationByName(organizationName);
	}


}
