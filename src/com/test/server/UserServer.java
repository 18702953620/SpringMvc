package com.test.server;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.test.mapper.UserDao;
import com.test.model.UserModel;

@Service
public class UserServer {
	@Resource
	UserDao userdao;

	public List<UserModel> getAllUsers() {
		return userdao.getAllUsers();
	}

	public UserModel getBookById(String id) {
		return userdao.getUserById(id);
	}

	public void add(UserModel entity) {
		userdao.add(entity);
	}

	public void delete(String id) {
		userdao.delete(id);
	}

	public void update(UserModel entity) {
		userdao.update(entity);
	}

}
