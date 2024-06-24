package com.example.demo.service;

import java.util.List;
import java.util.Optional;

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
	
	public Booking getBooking(Integer booking) {
		
		return bookingRoomDao.getBooking(booking);
	}
	
	public Integer updateBooking(Integer id, Booking booking) {
		
		return bookingRoomDao.updateBooking(id,booking);
	}
	
	public Integer deleteBookingByUserId(Integer id) {
		return bookingRoomDao.deleteBookingByUserId(id);
	}
	
	public List<Booking> findBookings() {
		return bookingRoomDao.findBookings();
	}
	
	public List<Booking> findBookingsByBookingIdOrUserId(Integer id,Integer userId,String checkin,String status) {
		return bookingRoomDao.findBookingsByBookingIdOrUserId(id,userId,checkin,status);
	}
	
	public Integer deleteBookingByBookingId(Integer id) {
		return bookingRoomDao.deleteBookingByBooking(id);
	}
	
	public Booking findBooking(Integer id) {
		return bookingRoomDao.findBooking(id);
	}
	
	public Integer updateBookingByBookingId(Integer id,Booking booking) {
		return bookingRoomDao.updateBookingAllColumn(id,booking);
	}
}