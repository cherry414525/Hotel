package com.example.demo.model.dto;

import java.sql.Timestamp;
import java.util.Date;

import com.example.demo.model.po.Room;
import com.example.demo.model.po.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRoomDto {
	
	//一般欄位資料
		private Integer bookingId;
		private Integer roomId;
		private Integer userId;
		private Integer quantity;	//訂房數量
		private double price;	//總金額
		private Date  start_date; //入住日期
		private Date  end_date;	//退房日期
		private Timestamp createDate;
		
		
		//關聯欄位(多對一)
		private Room room;
		

}
