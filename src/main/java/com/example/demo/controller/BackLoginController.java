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
import com.example.demo.model.dto.LoginDto;
import com.example.demo.model.dto.RoomDto;
import com.example.demo.model.dto.SearchBookingDto;
import com.example.demo.model.dto.SearchRequest;
import com.example.demo.model.dto.UpdateBookingDto;
import com.example.demo.model.dto.UpdateUserDto;
import com.example.demo.model.po.Booking;
import com.example.demo.model.po.Room;
import com.example.demo.model.po.RoomType;
import com.example.demo.model.po.User;
import com.example.demo.service.BookingRoomService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;



@Controller
public class BackLoginController {
	
	
	
	@Autowired
	private UserService userservice;
	
	@PostMapping("/backlogin")
	public ResponseEntity<String> backlogin(@RequestBody LoginDto loginDto, HttpSession session) {
		
		//取得管理者密碼
		Optional<User> manager = userservice.getUserByEmail("abcdefg8756@gmail.com");
	
		if(!loginDto.getEmail().equals("abcdefg8756@gmail.com")) {
			// 登入失敗，返回一個錯誤訊息或者適當的狀態碼
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("非管理員email"); // 返回未授權的狀態碼和錯誤訊息
		}else if(!SaltedPasswordHasher.verifyPassword(loginDto.getPassword(), manager.get().getSalt(), manager.get().getPassword())) {
			//判斷密碼是否正確
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("密碼錯誤");
		}
		
		 // 登入成功，返回一個成功訊息或者用戶的角色信息等
		session.setAttribute("managerStatus", true);
        return ResponseEntity.ok("backroom.html"); // 返回登入成功後的目標 URL 或者訊息
	}
	
	@GetMapping("/backlogout")
	public String backlogout(HttpSession session) {
		session.removeAttribute("managerStatus");
		return "redirect:/backlogin.html";
	}
}