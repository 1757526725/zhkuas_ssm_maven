package com.zhku.dao;

import com.zhku.bean.Admin;


public interface AdminMapper {
	public void addAdmin(Admin admin);
	public void deleteAdmin(Admin admin);
	public void updateAdmin(Admin admin);
	public Admin findAdmin(Admin admin);
}
