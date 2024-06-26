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
public class PasswordDto {
	private Integer user_id;
	private String newPassword;
	private String confirmPassword;
	
	
}