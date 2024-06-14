package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.swing.text.DateFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.po.Booking;
import com.example.demo.model.dto.BookingDto;
import com.example.demo.model.po.Room;
import com.example.demo.model.po.User;
import com.example.demo.service.BookingService;
import com.example.demo.service.RoomService;
import com.example.demo.service.RoomTypeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/booking")
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	@Autowired
	private RoomTypeService roomtypeService;
	@Autowired
	private RoomService roomService;
	
	@GetMapping
	public String findAll(@ModelAttribute("roomtype") String roomtype,
            @RequestParam("totalPrice") double totalPrice,
            @RequestParam("start_date") String startDate,
            @RequestParam("end_date") String endDate,Model model, HttpServletRequest request) {
		
		// 從 HttpServletRequest 中獲取 Session
	    HttpSession session = request.getSession(false);
	    // 檢查 Session 中是否存在登入狀態和使用者資訊
	    if (session != null && session.getAttribute("loginStatus") != null && (Boolean) session.getAttribute("loginStatus")) {
	        // 使用者已登入
	        // 獲取登入的使用者資訊
	        User loggedInUser = (User) session.getAttribute("loggedInUser");
	        model.addAttribute("loggedInUser", loggedInUser);
		 model.addAttribute("roomtype", roomtype);
		  model.addAttribute("totalPrice", totalPrice);
		  model.addAttribute("start_date", startDate);
		  model.addAttribute("end_date", endDate);
		  System.out.println(roomtype);
		  System.out.println(totalPrice);
		  System.out.println(startDate);
		  System.out.println(endDate);
		return "booking";
		
	    }else {
	        // 使用者未登入，導向登入頁面或執行其他操作
	        return "redirect:/login";
	    }
	}
	
	@PostMapping("/addbooking")
	public String addUser(@RequestParam("roomType") String roomType,
			@RequestParam("start_date") String startDateStr,
		    @RequestParam("end_date") String endDateStr,
		    @RequestParam("price") double price, Model model, HttpServletRequest request) {
		
		// 從 HttpServletRequest 中獲取 Session
	    HttpSession session = request.getSession(false);
	    User loggedInUser = (User) session.getAttribute("loggedInUser");
		//System.out.println(bookingDto);
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        Date startDate = formatter.parse(startDateStr);
	        Date endDate = formatter.parse(endDateStr);
	        
	        
	        Integer roomid = roomService.findRoomsBydateAndType(startDateStr, endDateStr, roomtypeService.findRoomtypebyid(roomType));
	        System.out.println(roomtypeService.findRoomtypebyid(roomType));
			 Booking booking = new Booking();
			 booking.setUserId(loggedInUser.getUser_id());
			 booking.setRoomId(roomid);
			 booking.setStart_date(startDate);
			 booking.setEnd_date(endDate);
			 booking.setPrice(price);
			 booking.setStatus("已訂房");
			 
			 System.out.println(booking);
			Integer rowcount = bookingService.addBooking(booking);
			String message = "新增" + ((rowcount == 1)?"成功":"失敗");
			System.out.print(rowcount);
			model.addAttribute("message", message);
			return "redirect:/hotel";
		}catch (Exception e) {
			System.out.print(e);
			String message = "新增錯誤: ";
			// "unique_roomId_and_bookingDate" 是在建立資料表時的表單約束條件
			if(e.getMessage().contains("Duplicate")) {
				message += "房間id已重複"; 
			} else {
				message += e.getMessage();
			}
			
			model.addAttribute("message", message);
		}
		
		return "booking";
	}
	
	/*
	@GetMapping
	public String findAll(@RequestParam("userId") Integer userId,Model model) {
		List<Booking> bookingDtos = bookingService.findAllBookingsByUserId(userId);
		model.addAttribute("bookingDtos", bookingDtos); // 給列表用
		return "booking";
	}
	
	
	@GetMapping("/findbooking")
	public String getUser(@RequestParam("bookingId") Integer bookingId, Model model) {
		
		
		 model.addAttribute("bookingDtos",bookingService.getBooking(bookingId));
		return "result";
	}
	
	@PostMapping("/addbooking")
	public String addUser(@ModelAttribute Booking booking, Model model) {
		
		try {
			
			Integer rowcount = bookingService.addBooking(booking);
			String message = "新增" + ((rowcount == 1)?"成功":"失敗");
			System.out.print(rowcount);
			model.addAttribute("message", message);
		}catch (Exception e) {
			System.out.print(e);
			String message = "新增錯誤: ";
			// "unique_roomId_and_bookingDate" 是在建立資料表時的表單約束條件
			if(e.getMessage().contains("Duplicate")) {
				message += "房間id已重複"; 
			} else {
				message += e.getMessage();
			}
			
			model.addAttribute("message", message);
		}
		
		return "result";
	}
	

	@PostMapping("/update")
    public String updateUser(@ModelAttribute Booking booking, Model model) {
        try {
        	
            // 調用服務層方法更新用戶信息
        	bookingService.updateBooking(booking);
            String message = "更新成功";
            model.addAttribute("booking", booking);
            model.addAttribute("message", message);
            model.addAttribute("_method","PUT");
        } catch (Exception e) {
            // 如果更新失敗，捕獲異常並將錯誤消息返回給前端
            String errorMessage = "更新失敗: " + e.getMessage();
            model.addAttribute("errorMessage", errorMessage);
        }
        // 返回結果頁面
        return "result";
    }
	

	@PostMapping("/delete")
	public String deleteUser(@RequestParam("bookingId") Integer bookingId, Model model) {
		
		Integer rowcount = bookingService.deleteBooking(bookingId);
		System.out.print(rowcount);
		String message = "刪除" + ((rowcount>0)?"成功":"失敗");
		model.addAttribute("message", message);
		return "result";
	}
	*/
}