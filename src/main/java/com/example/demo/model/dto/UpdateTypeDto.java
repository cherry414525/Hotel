package com.example.demo.model.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTypeDto {
	private String roomTypeId;
	private String roomTypeName;	//房型
	private String roomTypePrice;	//每晚單價
	private String roomTypeCapacity;	//可住人數
	private String roomTypeImage;	//房型照片
	
}