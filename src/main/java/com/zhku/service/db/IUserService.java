package com.zhku.service.db;

import java.util.List;

import com.zhku.bean.User;
import com.zhku.exception.UserExistException;

public interface IUserService {
	public void register(User user) throws UserExistException;
	public User getUserById(int uid);
	public List<User> getUserBySno(String sno);
	public User getUserBySid(int sid);
	public User getUserByEmail(String email);
	public void deleteUser(User user);
	public void updateUser(User user);
	public List<User> getUsers();
	public List<User> getUsers(int pageNum,int pageSize,boolean needCountTotal);
	public User findUserBySinaUid(String sina_uid);
	public User getAdminUserBySinaUid(String sina_uid);
	public List<User> findUserByKeyword(String keyword);
}
