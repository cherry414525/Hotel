package com.example.demo.model.dto;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchBookingDto{
	 private String bookingId;
	    private String userId;
	    private String checkIn;
	    private String status;
	    
	   
}