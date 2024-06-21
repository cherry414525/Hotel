package com.example.demo.dao;

import java.util.List;
import java.util.Optional;


import com.example.demo.model.po.User;

public interface UserDao {
	List<User> findAllUsers();
	User getUser(Integer id);
	Optional<User> getUserByEmail(String email);
	Integer addUser(User user);
	Integer updateUser(Integer id,User user);
	Integer updateUserPassword(Integer id,User user);
	Integer deleteUser(Integer id);
	
	List<User> getUsersByIdOrName(Integer id, String name);
}