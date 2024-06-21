package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserDao;

import com.example.demo.model.po.User;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	public List<User> findAllUsers() {
		return userDao.findAllUsers();
	}

	public User getUser(Integer id) {
		return userDao.getUser(id);
	}
	
	public Integer addUser(User user) {
		/*
		user.setUser_id(5);
		user.setName("John");
		user.setBirthday(new Date()); // 假設這裡是設定生日日期的地方
		user.setGender("male");
		user.setPhone("123456789");
		user.setEmail("john@example.com");
		user.setSalt("some_salt");
		user.setPassword("some_password");
		*/
		return userDao.addUser(user);
		
	}

	public Integer updateUser(User user) {
		
		
		
		return userDao.updateUser(user.getUser_id(), user);
	}
	
	public Integer deleteUser(Integer id) {
		
		return userDao.deleteUser(id);
	}

	public Optional<User> getUserByEmail(String email) {
		return userDao.getUserByEmail(email);
	}
	
	public Integer updateUserPassword(Integer id,User user) {
		return userDao.updateUserPassword(id, user);
	}

	public List<User> getUsersByIdOrName(Integer id, String name) {
		return userDao.getUsersByIdOrName(id, name);
	}
	
}