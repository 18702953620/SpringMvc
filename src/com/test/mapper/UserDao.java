package com.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.test.model.UserModel;

public interface UserDao {
	/**
	 * 获取所有用户
	 * 
	 * @return
	 */
	public List<UserModel> getAllUsers();

	public UserModel getUserById(@Param("id") String id);

	public int delete(String id);

	public int add(UserModel entity);

	public int update(UserModel entity);

	public UserModel getUserByName(@Param("username") String username);

	public UserModel getUserByNameAndPassword(@Param("username") String username, @Param("password") String password);
}
