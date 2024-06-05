package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.BookingDao;
import com.example.demo.dao.BookingRoomDao;
import com.example.demo.model.dto.BookingRoomDto;
import com.example.demo.model.po.Booking;

@Service
public class BookingRoomService {

	@Autowired
	private BookingRoomDao bookingRoomDao;
	
	public List<BookingRoomDto> findAllBookings(){
		return bookingRoomDao.findAllBookings();
	}
	
	public List<BookingRoomDto> findAllBookingsByUserId(Integer userId) {
		return bookingRoomDao.findAllBookingsByUserId(userId);
	}

	public Integer deleteBooking(Integer id) {
		
		return bookingRoomDao.deleteBooking(id);
	}
}
