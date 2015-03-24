package com.zhku.service.db;

import java.util.List;

import com.zhku.bean.Account;

public interface IAccountService {
	public void addAccount(Account account);
	public void deleteAccount(Account account);
	public void updateAccount(Account account);
	public List<Account> getAccountsByCampusId(int campusId);
}
