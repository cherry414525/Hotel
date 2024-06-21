package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.dto.BookingRoomDto;
import com.example.demo.model.po.Booking;
import com.example.demo.model.po.Room;
import com.example.demo.model.po.User;

public interface BookingRoomDao {

	List<BookingRoomDto> findAllBookings();	//查所有訂單
	List<BookingRoomDto> findAllBookingsByUserId(Integer userId);	//查詢所有訂單，依照user_id
	Booking getBooking(Integer id);	//查詢單筆訂單，依照boooking_id
	Integer addBooking(Booking booking);		//新增訂單
	Integer updateBooking(Integer id,Booking booking);	//修改訂單
	Integer deleteBooking(Integer id);		//刪除訂單
	Integer deleteBookingByUserId(Integer id);
	List<Booking> findBookings();
	List<Booking> findBookingsByBookingIdOrUserId(Integer id, Integer userId);
}