package com.zhku.service.db.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhku.bean.Admin;
import com.zhku.dao.AdminMapper;
import com.zhku.service.db.IAdminService;
@Service("adminService")
public class AdminService implements IAdminService{
	@Autowired
	private AdminMapper adminMapper;
	@Override
	public void addAdmin(Admin admin) {
		adminMapper.addAdmin(admin);
	}

	@Override
	public void deleteAdmin(Admin admin) {
		adminMapper.deleteAdmin(admin);
	}

	@Override
	public void updateAdmin(Admin admin) {
		adminMapper.updateAdmin(admin);
	}

	@Override
	public Admin findAdmin(Admin admin) {
		return adminMapper.findAdmin(admin);
	}

	public AdminMapper getAdminMapper() {
		return adminMapper;
	}

	public void setAdminMapper(AdminMapper adminMapper) {
		this.adminMapper = adminMapper;
	}

}
