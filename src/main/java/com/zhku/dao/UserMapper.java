package com.zhku.dao;

import java.util.List;

import com.zhku.bean.User;

public interface UserMapper {
	public void addUser(User user);
	public void deleteUser(User user);
	public void updateUser(User user);
	public User getUserById(int uid);
	public User getUserBySid(int sid);
	public List<User> getUsersBySno(String sno);
	public User getUserByEmail(String email);
	public List<User> getUsers();
	public User findUserBySinaUid(String sina_uid);
	public User getAdminUserBySinaUid(String sina_uid);
	public List<User> findUserByKeyword(String keyword);
}
