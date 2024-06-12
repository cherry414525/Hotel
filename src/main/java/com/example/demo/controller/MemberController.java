package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.dto.BookingRoomDto;
import com.example.demo.model.po.User;
import com.example.demo.service.BookingRoomService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	
	@Autowired
	private BookingRoomService bookingRoomService;

	
	@GetMapping
	public String findAll(Model model, HttpServletRequest request) {
		// 從 HttpServletRequest 中獲取 Session
	    HttpSession session = request.getSession(false);
	 // 檢查 Session 中是否存在登入狀態和使用者資訊
	    if (session != null && session.getAttribute("loginStatus") != null && (Boolean) session.getAttribute("loginStatus")) {
	        // 使用者已登入
	        // 獲取登入的使用者資訊
	        User loggedInUser = (User) session.getAttribute("loggedInUser");
	        
	        // 在這裡執行相應的業務處理
	        List<BookingRoomDto> bookingRoomDtos = bookingRoomService.findAllBookingsByUserId(loggedInUser.getUser_id());
	        model.addAttribute("bookingRoomDtos", bookingRoomDtos);
	        
	        return "member";
	    } else {
	        // 使用者未登入，導向登入頁面或執行其他操作
	        return "redirect:/login";
	    }
	    
		
		
	}
	
	@GetMapping("/userId")
	public String findAllByUserId(@RequestParam("userId") Integer userId,Model model) {
		List<BookingRoomDto> bookingRoomDtos =bookingRoomService.findAllBookingsByUserId(userId);
		model.addAttribute("bookingRoomDtos", bookingRoomDtos); // 給列表用
		return "member";
	}
	
	@DeleteMapping("/delete/{bookingId}")
	public String deleteUser(@PathVariable("bookingId") Integer bookingId, Model model) {
		
		Integer rowcount = bookingRoomService.deleteBooking(bookingId);
		System.out.print(rowcount);
		String message = "刪除" + ((rowcount>0)?"成功":"失敗");
		
		model.addAttribute("message", message);
		return "redirect:/member";
	}
	
	/*
	
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
	
*/
	
	
}
