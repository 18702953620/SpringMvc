package com.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.test.model.UserModel;

@Mapper
public interface UserDao {

	public List<UserModel> getUser(@Param("userId") String articleId);

	public int delete(String id);

	public int add(UserModel entity);

	public int update(UserModel entity);

	public UserModel getUserByName(@Param("username") String username);

	public UserModel getUserByNameAndPassword(@Param("username") String username, @Param("password") String password);
}
