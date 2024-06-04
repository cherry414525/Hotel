package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.text.SimpleDateFormat;

import com.example.demo.model.po.Booking;
import com.example.demo.model.po.Room;
import com.example.demo.model.po.User;

@Repository
public class BookingDaoImpl implements BookingDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Booking> findAllBookings(Integer userId) {
		//查詢所有訂單，依照user_id
		String sql = "select booking_id, user_id, room_id, quantity,price,start_date, end_date,createdate,updatedate from booking where user_id = ?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Booking.class),userId);
	
	}

	@Override
	public Optional<Booking> getBooking(Integer id) {
		//查詢單筆訂單，依照boooking_id
		String sql = "select booking_id, user_id, room_id, quantity,price,start_date, end_date,createdate,updatedate from booking where booking_id = ?";
		try {
			
			Booking booking = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Booking.class), id);
			System.out.print(booking);
			return Optional.of(booking);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.of(null);
	}

	@Override
	public Integer addBooking(Booking booking) {
		String sql = "INSERT INTO booking (booking_id,user_id, room_id, quantity,price,start_date, end_date) VALUES (?, ?, ?, ?,?,?,?)";
        
        // 執行 SQL 新增語句
        int rowcount = jdbcTemplate.update(sql,booking.getBooking_id(),booking.getUserId(),booking.getRoomId(),booking.getQuantity(),booking.getPrice(),booking.getStart_date(),booking.getEnd_date());
        
        return rowcount;
	}

	@Override
	public Integer updateBooking(Integer id, Booking booking) {
		
		String sql = "update booking set quantity=?,price = ?,start_date= ?, end_date = ?  where booking_id = ?";
		int rowcount = jdbcTemplate.update(sql, booking.getQuantity(),booking.getPrice(),booking.getStart_date(),booking.getEnd_date(), id);
		return rowcount;
	}

	@Override
	public Integer deleteBooking(Integer id) {
		String sql = "delete from booking where booking_id = ?";
		int rowcount = jdbcTemplate.update(sql, id);
		System.out.println(rowcount);
		if(rowcount == 1) {
			System.out.println(id);
			return id;
		}
		return null;
	}
	
	
}