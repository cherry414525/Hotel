package com.example.demo.model.po;

import java.sql.Timestamp;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking{
	private Integer booking_id;	
	private Integer roomId;
	private Integer userId;
	private Integer quantity;	//訂房數量
	private double price;	//總金額
	private Date  start_date; //入住日期
	private Date  end_date;	//退房日期
	private Timestamp createDate;
	private Timestamp updateDate;
}