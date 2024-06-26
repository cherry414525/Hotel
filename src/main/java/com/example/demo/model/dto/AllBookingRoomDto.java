package com.example.demo.model.dto;

import java.sql.Timestamp;
import java.util.Date;

import com.example.demo.model.po.Room;
import com.example.demo.model.po.RoomType;
import com.example.demo.model.po.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllBookingRoomDto {
	
	//一般欄位資料
		private Integer bookingId;
		private Integer roomId;
		private String userName;
		private double price;	//總金額
		private String  start_date; //入住日期
		private String  end_date;	//退房日期
		private String status;	//訂單狀態
		


}
