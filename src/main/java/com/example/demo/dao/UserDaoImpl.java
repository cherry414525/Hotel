package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.text.SimpleDateFormat;


import com.example.demo.model.po.User;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<User> findAllUsers() {
		String sql = "select user_id, name,birthday,gender,phone,email from user";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
	}

	@Override
	public Optional<User> getUser(Integer id) {
		String sql = "select user_id, name,birthday,gender,phone,email from user where user_id=?";
		try {
			User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
			return Optional.of(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.of(null);
	}

	@Override
	public Integer addUser(User user) {
        String sql = "INSERT INTO user (user_id, name, birthday, gender, phone, email, salt, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        // 格式化生日日期為字串
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedBirthday = dateFormat.format(user.getBirthday());
        
        // 執行 SQL 新增語句
        int rowcount = jdbcTemplate.update(sql, user.getUser_id(), user.getName(), formattedBirthday, user.getGender(), user.getPhone(), user.getEmail(), user.getSalt(), user.getPassword());
        
        return rowcount;
    }

	@Override
	public Integer updateUser(Integer id, User user) {
			System.out.println(user.toString());
			String sql = "update user set name = ?, birthday = ?, gender = ?, phone = ?, email = ?  where user_id = ?";
			int rowcount = jdbcTemplate.update(sql, user.getName(), user.getBirthday(), user.getGender(), user.getPhone(), user.getEmail(), id);
			return rowcount;
	
	}

	
	@Override
	public Integer deleteUser(Integer id) {
		String sql = "delete from user where user_id = ?";
		int rowcount = jdbcTemplate.update(sql, id);
		System.out.println(rowcount);
		if(rowcount == 1) {
			System.out.println(id);
			return id;
		}
		return null;
	}
	
}