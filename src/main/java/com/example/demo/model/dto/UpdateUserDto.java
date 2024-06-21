package com.example.demo.model.dto;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {

	private String user_id;
	private String name;
	private String email;
	private String phone;
	private String gender;
	private String birthday;
}
