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

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public String login(Model model) {
		
		 
		return "login";
	}
	@PostMapping("/login")
	public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session) {
	    try {
	    	System.out.println(email+" "+password);
	        // 根據郵箱查找使用者
	        Optional<User> existingUser = userService.getUserByEmail(email);
	        if (existingUser.isPresent()) {
	            User user = existingUser.get();
	            
	            System.out.print(user.getPassword());
	            // 驗證密碼是否正確
	            if (SaltedPasswordHasher.verifyPassword(password, user.getSalt(), user.getPassword())) {
	                // 密碼正確，將使用者設置為已登入狀態
	                session.setAttribute("loggedInUser", user);
	                session.setAttribute("loginStatus", true);
	                return "redirect:/member"; // 登入成功，重定向到會員中心
	            } else {
	                // 密碼不正確，返回登入頁面，顯示錯誤消息
	                model.addAttribute("error", "密碼不正確");
	                return "login";
	            }
	        } else {
	            // 使用者不存在，返回登入頁面，顯示錯誤消息
	            model.addAttribute("error", "使用者不存在");
	            return "login";
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        // 發生異常，返回登入頁面，顯示錯誤消息
	        model.addAttribute("error", "登入時發生錯誤");
	        return "login";
	    }
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}
	
	@PostMapping("/register")
	public String addUser(@ModelAttribute UserDto userDto, Model model, HttpSession session) throws Exception{
		
		System.out.println(userDto);
		try {
			// 檢查郵件是否已存在
			Optional<User> existingUser = userService.getUserByEmail(userDto.getEmail());
			if (existingUser.isPresent()) {
				// 用户已存在，设置登录状态为 false
				session.setAttribute("loginStatus", false);
				model.addAttribute("userDto",userDto);
				model.addAttribute("error1", "此郵箱已被註冊，請重新註冊。");
				return "/register"; // 返回注册页面，显示错误消息
			}
			System.out.print(userDto.getConfirmPassword());
			System.out.print(userDto.getPassword());
			// 如果邮箱未被使用且密码长度符合要求，则执行注册逻辑
			if (userDto.getPassword().length() < 6) {
				model.addAttribute("error2", "密碼長度不得少於6碼。");
				System.out.print("密碼長度不得少於6碼。");
				return "/register"; // 返回注册页面，显示错误消息
			}else if(!userDto.getConfirmPassword().equals(userDto.getPassword())) {
				model.addAttribute("error2", "確認密碼不相同");
				return "/register"; // 返回注册页面，显示错误消息
			}

			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        Date startDate = formatter.parse(userDto.getBirthday());
	        
	        String salt = SaltedPasswordHasher.generateSalt();
	        String hashedPassword = SaltedPasswordHasher.hashPassword(userDto.getPassword(), salt);
	        
			 User user = new User();
			 user.setName(userDto.getName());
			 user.setBirthday(startDate);
			 user.setGender(userDto.getGender());
			 user.setPhone(userDto.getPhone());
			 user.setEmail(userDto.getEmail());
			 user.setSalt(salt);
			 user.setPassword(hashedPassword);
			 
			 System.out.println(user);
			Integer rowcount = userService.addUser(user);
			String message = "新增" + ((rowcount == 1)?"成功":"失敗");
			System.out.print(rowcount);
			model.addAttribute("message", message);
			// 注册成功后，设置登录状态为 true
			session.setAttribute("loginStatus", true);
			return "redirect:/login";
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
		
		return "redirect:/register";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
	    // 清除 session 中的登入信息
	    session.removeAttribute("loggedInUser");
	    // 將登入狀態設置為 false
	    session.setAttribute("loginStatus", false);
	    // 重定向到登入頁面
	    return "redirect:/hotel";
	}
	
	@GetMapping("/forgotPassword")
	public String forgotPassword(@RequestParam("email") String email, Model model) {
		System.out.println(email);
		
		SSLEmail.sendEmail(email);
		 Optional<User> existingUser = userService.getUserByEmail(email);
	        if (existingUser.isPresent()) {
	            User user = existingUser.get();
	            String salt = SaltedPasswordHasher.generateSalt();
		        String hashedPassword = SaltedPasswordHasher.hashPassword("123456", salt);
	            user.setSalt(salt);
	            user.setPassword(hashedPassword);
		        
	            userService.updateUserPassword(user.getUser_id(),user);
	            model.addAttribute("message", "密碼已發送至您的郵箱");
	            
	        }
		return "redirect:/login";
	}
	
	
}