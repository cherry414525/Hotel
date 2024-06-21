package com.example.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.text.DateFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.dto.AllBookingRoomDto;
import com.example.demo.model.dto.BookingDto;
import com.example.demo.model.dto.BookingRoomDto;
import com.example.demo.model.dto.RoomDto;
import com.example.demo.model.dto.SearchBookingDto;
import com.example.demo.model.dto.SearchRequest;
import com.example.demo.model.po.Booking;
import com.example.demo.model.po.Room;
import com.example.demo.model.po.RoomType;
import com.example.demo.model.po.User;
import com.example.demo.service.BookingRoomService;
import com.example.demo.service.UserService;



@RestController
@RequestMapping("/api")
public class BackBookingController {
	
	@Autowired
	private BookingRoomService bookingroomService;
	
	@Autowired
	private UserService userservice;
	
	@GetMapping("/bookings")
	public List<AllBookingRoomDto>  findAll() {
		List<Booking> bookings = bookingroomService.findBookings();
		
		List<AllBookingRoomDto> bookingDtos = new ArrayList<>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		for (Booking booking : bookings) {		
			String	startDate = sdf.format(booking.getStart_date());
			String	endDate = sdf.format(booking.getEnd_date());

			
			User user = userservice.getUser(booking.getUserId());
			
			// 建立 BookingDTO 對象，將 Booking 的資料放入
			AllBookingRoomDto bookingDto = new AllBookingRoomDto();
			bookingDto.setBookingId(booking.getBooking_id());
			bookingDto.setRoomId(booking.getRoomId());
			bookingDto.setUserName(user.getName());
			bookingDto.setPrice(booking.getPrice());
			bookingDto.setStart_date(startDate);
			bookingDto.setEnd_date(endDate);
			bookingDto.setStatus(booking.getStatus());
			
			bookingDtos.add(bookingDto);
		}
		return bookingDtos;
	}
	
	@PostMapping("/searchbookings")
    public List<AllBookingRoomDto> searchRooms(@RequestBody SearchBookingDto searchBookingDto) {
		 String bookingId= searchBookingDto.getBookingId();
	     String userId = searchBookingDto.getUserId();
	     
	     List<Booking> bookings;
	     if(bookingId.equals("") && userId.equals("")) {
	    	 bookings = bookingroomService.findBookings();
	     }else if(bookingId.equals("")) {
	    	 bookings = bookingroomService.findBookingsByBookingIdOrUserId(null, Integer.parseInt(userId));
	     }else if(userId.equals("")) {
	    	 bookings = bookingroomService.findBookingsByBookingIdOrUserId(Integer.parseInt(bookingId), null);
	     }else {
	    	 bookings = bookingroomService.findBookingsByBookingIdOrUserId(Integer.parseInt(bookingId), Integer.parseInt(userId));
	     }
	     
	     
		
		
		List<AllBookingRoomDto> bookingDtos = new ArrayList<>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		for (Booking booking : bookings) {		
			String	startDate = sdf.format(booking.getStart_date());
			String	endDate = sdf.format(booking.getEnd_date());

			
			User user = userservice.getUser(booking.getUserId());
			
			// 建立 BookingDTO 對象，將 Booking 的資料放入
			AllBookingRoomDto bookingDto = new AllBookingRoomDto();
			bookingDto.setBookingId(booking.getBooking_id());
			bookingDto.setRoomId(booking.getRoomId());
			bookingDto.setUserName(user.getName());
			bookingDto.setPrice(booking.getPrice());
			bookingDto.setStart_date(startDate);
			bookingDto.setEnd_date(endDate);
			bookingDto.setStatus(booking.getStatus());
			
			bookingDtos.add(bookingDto);
		}
		return bookingDtos;
        
    }
	
	@DeleteMapping("/deleteBooking/{bookingId}")
    public String deleteRoomType(@PathVariable("bookingId") Integer bookingId) {
        try {
        	
            // 呼叫服務層的方法來刪除訂單
			bookingroomService.deleteBookingByBookingId(bookingId);
            return "訂單成功刪除";
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "刪除訂單失敗: " + e.getMessage(), e);
        }
    }
}