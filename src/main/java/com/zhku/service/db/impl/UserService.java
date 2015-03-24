package com.zhku.service.db.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.zhku.bean.User;
import com.zhku.dao.UserMapper;
import com.zhku.exception.UserExistException;
import com.zhku.service.db.IUserService;

@Service("userService")
public class UserService extends ServiceSupport implements IUserService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public void register(User user){
		// 查找是不是已经存在
		userMapper.addUser(user);
	}

	@Override
	public User getUserById(int uid) {
		return userMapper.getUserById(uid);
	}
	/**
	 * 获取学号绑定的多个用户。
	 */
	@Override
	public List<User> getUserBySno(String sno) {
		return userMapper.getUsersBySno(sno);

	}

	@Override
	public User getUserBySid(int sid) {
		return userMapper.getUserBySid(sid);
	}

	@Override
	public User getUserByEmail(String email) {
		return userMapper.getUserByEmail(email);
	}

	@Override
	public void deleteUser(User user) {
		userMapper.deleteUser(user);
	}

	@Override
	public void updateUser(User user) {
		userMapper.updateUser(user);
	}

	@Override
	public List<User> getUsers() {
		return userMapper.getUsers();
	}

	@Override
	public User findUserBySinaUid(String sina_uid) {
		return userMapper.findUserBySinaUid(sina_uid);
	}

	@Override
	public User getAdminUserBySinaUid(String sina_uid) {
		return userMapper.getAdminUserBySinaUid(sina_uid);
	}

	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	@Override
	public List<User> getUsers(int pageNum, int pageSize, boolean needCountTotal) {
		PageHelper.startPage(pageNum, pageSize,needCountTotal);
		return userMapper.getUsers();
	}

	@Override
	public List<User> findUserByKeyword(String keyword) {
		return userMapper.findUserByKeyword(keyword);
	}

}
