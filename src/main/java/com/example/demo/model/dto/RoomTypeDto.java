package com.example.demo.model.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomTypeDto {
	private Integer type_id;
	private String name;	//房型
	private double price;	//每晚單價
	private int capacity;	//可住人數
	private String photo;	//房型照片
	private Integer total;	
}