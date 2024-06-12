package com.example.demo.model.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private Integer user_id;
	private String name;
	
	private String birthday;
	private String gender;
	private String phone;
	private String email;
	private String salt;
	private String password;
	
	
	
}