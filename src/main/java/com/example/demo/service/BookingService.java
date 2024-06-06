package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.BookingDao;
import com.example.demo.dao.RoomDao;
import com.example.demo.dao.UserDao;
import com.example.demo.model.po.Booking;
import com.example.demo.model.po.Room;
import com.example.demo.model.po.User;

@Service
public class BookingService {
	
	@Autowired
	private BookingDao bookingDao;
	
	public List<Booking> findAllBookingsByUserId(Integer userId) {
		return bookingDao.findAllBookingsByUserId(userId);
	}

	public Optional<Booking> getBooking(Integer id) {
		return bookingDao.getBooking(id);
	}
	
	public Integer addBooking(Booking booking) {
		
		/*booking.setBooking_id(2003);
		booking.setRoomId(203);
		booking.setUserId(1);
		booking.setQuantity(1);
		booking.setPrice(5000.0);
		booking.setStart_date(new Date());
		booking.setEnd_date(new Date());*/
		return bookingDao.addBooking(booking);
		
	}

	public Integer updateBooking(Booking booking) {
		
		
		booking.setBooking_id(2001);
		booking.setQuantity(1);
		booking.setPrice(5000.0);
		booking.setStart_date(new Date());
		booking.setEnd_date(new Date());
		return bookingDao.updateBooking(booking.getBooking_id(),booking);
	}
	
	public Integer deleteBooking(Integer id) {
		
		return bookingDao.deleteBooking(id);
	}

	
	
}