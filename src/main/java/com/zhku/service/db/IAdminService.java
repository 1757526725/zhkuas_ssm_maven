package com.zhku.service.db;

import com.zhku.bean.Admin;


public interface IAdminService {
	public void addAdmin(Admin admin);
	public void deleteAdmin(Admin admin);
	public void updateAdmin(Admin admin);
	public Admin findAdmin(Admin admin);
}
