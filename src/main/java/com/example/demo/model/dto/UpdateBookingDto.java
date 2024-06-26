package com.example.demo.model.dto;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookingDto {

	private String bookingId;
	private String roomId;
	private String userName;
	private String price;
	private String start_date;
	private String end_date;
	private String status;
}
