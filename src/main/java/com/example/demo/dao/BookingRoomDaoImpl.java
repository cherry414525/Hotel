package com.example.demo.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;


import com.example.demo.model.dto.BookingRoomDto;
import com.example.demo.model.po.Booking;
import com.example.demo.model.po.Room;
import com.example.demo.model.po.RoomType;
import com.example.demo.model.po.User;

@Repository
public class BookingRoomDaoImpl implements BookingRoomDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<BookingRoomDto> findAllBookings() {
		
		String sql = "select "
	            + "b.booking_id, b.user_id, b.room_id, b.status, b.price, b.start_date, b.end_date, b.createdate, b.updatedate, " 
	            + "r.room_id,r.type_id, t.name, t.capacity, t.price,t.photo "
	            + "from booking b "
	            + "left join room r on b.room_id = r.room_id "
	            + "left join roomtype t on r.type_id=t.type_id ";
		
		// 自定義對應邏輯規則
				RowMapper<BookingRoomDto> mapper = new RowMapper<>() {
					
					@Override
					public BookingRoomDto mapRow(ResultSet rs, int rowNum) throws SQLException {
						// 逐筆逐項將每一個欄位資料抓出
						Integer bookingId = rs.getInt("booking_id");
						Integer roomId = rs.getInt("b.room_id");
						Integer userId = rs.getInt("b.user_id");
						Date start_date = rs.getDate("start_date");
						Date end_date = rs.getDate("end_date");
						
						Double price = rs.getDouble("price");
						Timestamp createDate = rs.getTimestamp("createDate");
						
						Integer typeId = rs.getInt("r.type_id");
						String name = rs.getString("t.name");
						Integer capacity = rs.getInt("t.capacity");
						Double rprice = rs.getDouble("t.price");
						String photo = rs.getString("t.photo");
						
						
						// 注入資料
						Room Room = new Room(roomId, typeId);
						RoomType RoomType = new RoomType(typeId, name, rprice, capacity, photo);
						
						
						// DTO
						BookingRoomDto dto = new BookingRoomDto();
						dto.setBookingId(bookingId);
						dto.setRoomId(roomId);
						dto.setUserId(userId);
						dto.setStart_date(start_date);
						dto.setEnd_date(end_date);
						dto.setPrice(price);
						dto.setCreateDate(createDate);
						dto.setRoom(Room);
						dto.setRoomType(RoomType);
						System.out.print(dto);
						return dto;
					}
				};
				
				return jdbcTemplate.query(sql, mapper);
	}

	@Override
	public List<BookingRoomDto> findAllBookingsByUserId(Integer userId) {
		//查詢所有訂單，依照user_id
		/*
		 * select 
			b.booking_id, b.user_id, b.room_id, b.quantity, b.price, b.start_date, b.end_date, b.createdate, b.updatedate, 
			r.room_id,r.type_id, t.name, t.capacity, t.price 
			from booking b 
			left join room r on b.room_id = r.room_id
			left join roomtype t on r.type_id=t.type_id;
		 
		*/
		String sql = "select "
	            + "b.booking_id, b.user_id, b.room_id, b.status, b.price, b.start_date, b.end_date, b.createdate, b.updatedate, " 
	            + "r.room_id,r.type_id, t.name, t.capacity, t.price,t.photo "
	            + "from booking b "
	            + "left join room r on b.room_id = r.room_id "
	            + "left join roomtype t on r.type_id=t.type_id "
	            + "where user_id = ? and status <> '已取消' ";
		
		// 自定義對應邏輯規則
				RowMapper<BookingRoomDto> mapper = new RowMapper<>() {
					

					@Override
					public BookingRoomDto mapRow(ResultSet rs, int rowNum) throws SQLException {
						// 逐筆逐項將每一個欄位資料抓出
						Integer bookingId = rs.getInt("booking_id");
						Integer roomId = rs.getInt("b.room_id");
						Integer userId = rs.getInt("b.user_id");
						Date start_date = rs.getDate("start_date");
						Date end_date = rs.getDate("end_date");					
						Double price = rs.getDouble("price");
						Timestamp createDate = rs.getTimestamp("createDate");
						
						Integer typeId = rs.getInt("r.type_id");
						String name = rs.getString("t.name");
						Integer capacity = rs.getInt("t.capacity");
						Double rprice = rs.getDouble("t.price");
						String photo = rs.getString("t.photo");
						
						
						// 注入資料
						Room Room = new Room(roomId, typeId);
						RoomType RoomType = new RoomType(typeId, name, rprice, capacity, photo);
						
						
						// DTO
						BookingRoomDto dto = new BookingRoomDto();
						dto.setBookingId(bookingId);
						dto.setRoomId(roomId);
						dto.setUserId(userId);
						dto.setStart_date(start_date);
						dto.setEnd_date(end_date);						
						dto.setPrice(price);
						dto.setCreateDate(createDate);
						dto.setRoom(Room);
						dto.setRoomType(RoomType);
						System.out.print(dto);
						return dto;
					}
				};
				return jdbcTemplate.query(sql, mapper, userId);
		
	
	}

	@Override
	public Booking getBooking(Integer id) {
		//查詢單筆訂單，依照boooking_id
		String sql = "select booking_id, user_id, room_id, status,price,start_date, end_date,createdate,updatedate from booking where booking_id = ?";
		try {
			
			Booking booking = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Booking.class), id);
			System.out.print(booking);
			return booking;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer addBooking(Booking booking) {
		String sql = "INSERT INTO booking (booking_id,user_id, room_id,price,start_date, end_date,status) VALUES (?, ?, ?, ?,?,?,?)";
        
        // 執行 SQL 新增語句
        int rowcount = jdbcTemplate.update(sql,booking.getBooking_id(),booking.getUserId(),booking.getRoomId(),booking.getPrice(),booking.getStart_date(),booking.getEnd_date(),booking.getStatus());
        
        return rowcount;
	}

	@Override
	public Integer updateBooking(Integer id, Booking booking) {
		
		String sql = "update booking set price = ?,start_date= ?, end_date = ?  ,status = ? where booking_id = ?";
		int rowcount = jdbcTemplate.update(sql,booking.getPrice(),booking.getStart_date(),booking.getEnd_date(),booking.getStatus(), id);
		return rowcount;
	}

	@Override
	public Integer deleteBooking(Integer id) {
		String sql = "update booking set status =? where booking_id = ?";
		int rowcount = jdbcTemplate.update(sql, "已取消",id);
		System.out.println(rowcount);
		if(rowcount == 1) {
			System.out.println(id);
			return id;
		}
		return null;
	}
	
	
}