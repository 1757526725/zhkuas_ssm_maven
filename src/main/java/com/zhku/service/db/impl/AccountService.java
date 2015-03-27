package com.zhku.service.db.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhku.bean.Account;
import com.zhku.dao.AccountMapper;
import com.zhku.service.db.IAccountService;
@Service("accountService")
public class AccountService implements IAccountService {
	@Autowired
	private AccountMapper accountMapper ;
	@Override
	public void addAccount(Account account) {
		accountMapper.addAccount(account);
	}

	@Override
	public void deleteAccount(Account account) {
		accountMapper.deleteAccount(account);
	}

	@Override
	public void updateAccount(Account account) {
		accountMapper.updateAccount(account);

	}

	@Override
	public List<Account> getAccountsByCampusId(int campusId) {
		return accountMapper.getAccountsByCampusId(campusId);
	}

	public AccountMapper getAccountMapper() {
		return accountMapper;
	}

	public void setAccountMapper(AccountMapper accountMapper) {
		this.accountMapper = accountMapper;
	}

}
