package com.example.demo.model.po;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private Integer user_id;
	private String name;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd") // 日期格式
	private Date birthday;
	private String gender;
	private String phone;
	private String email;
	private String salt;
	private String password;
	
	
	
}
