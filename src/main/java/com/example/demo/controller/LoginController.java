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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.po.Booking;
import com.example.demo.model.dto.BookingDto;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.po.Room;
import com.example.demo.model.po.User;
import com.example.demo.service.BookingService;
import com.example.demo.service.RoomService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public String login(Model model) {
		
		 
		return "login";
	}
	
	@PostMapping("/register")
	public String addUser(@ModelAttribute UserDto userDto, Model model) {
		
		System.out.println(userDto);
		try {
 
			 User user = new User();
			 
			 System.out.println(user);
			Integer rowcount = userService.addUser(user);
			String message = "新增" + ((rowcount == 1)?"成功":"失敗");
			System.out.print(rowcount);
			model.addAttribute("message", message);
			return "redirect:/member";
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
		
		return "login";
	}
}