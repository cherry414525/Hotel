package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.example.demo.model.dto.BookingRoomDto;
import com.example.demo.model.dto.PasswordDto;
import com.example.demo.model.dto.UserUpdateDto;
import com.example.demo.model.po.Booking;
import com.example.demo.model.po.Room;
import com.example.demo.model.po.RoomType;
import com.example.demo.model.po.User;
import com.example.demo.service.BookingRoomService;
import com.example.demo.service.RoomService;
import com.example.demo.service.RoomTypeService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	
	@Autowired
	private BookingRoomService bookingRoomService;

	@Autowired
	private RoomService roomService;
	
	@Autowired
	private RoomTypeService roomtypeService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public String findAll(Model model, HttpServletRequest request,@RequestParam(name = "page", defaultValue = "0") int page) {
		// 從 HttpServletRequest 中獲取 Session
	    HttpSession session = request.getSession(false);
	 // 檢查 Session 中是否存在登入狀態和使用者資訊
	    if (session != null && session.getAttribute("loginStatus") != null && (Boolean) session.getAttribute("loginStatus")) {
	        // 使用者已登入
	        // 獲取登入的使用者資訊
	        User loggedInUser = (User) session.getAttribute("loggedInUser");
	              
	        // 在這裡執行相應的業務處理
	        List<BookingRoomDto> bookingRoomDtos = bookingRoomService.findAllBookingsByUserId(loggedInUser.getUser_id());
	        
	        // 檢查是否有資料
	        if (bookingRoomDtos.isEmpty()) {
	            // 如果沒有資料，直接返回空列表
	            model.addAttribute("currentPage", 0);
	            model.addAttribute("totalPages", 0);
	            model.addAttribute("bookingRoomDtos", new ArrayList<>());
	            return "member";
	        }
	        
	        // 計算分頁所需的起始索引和結束索引
	        int startIndex = page * 10;
	        int endIndex = Math.min(startIndex +10, bookingRoomDtos.size());
	        
	        // 截取當前頁的資料
	        List<BookingRoomDto> currentPageBookings = bookingRoomDtos.subList(startIndex, endIndex);
	        
	        // 計算總記錄數和總頁數
	        int totalRecords = bookingRoomDtos.size();
	        int totalPages = (int) Math.ceil((double) totalRecords / 10);
	        
	        model.addAttribute("currentPage", page);
	        model.addAttribute("totalPages", totalPages);
	        model.addAttribute("bookingRoomDtos", currentPageBookings);
	        
	        return "member";
	    } else {
	        // 使用者未登入，導向登入頁面或執行其他操作
	        return "redirect:/login";
	    }
	    
		
		
	}
	
	@GetMapping("/user")
	public String findAllByUserId(Model model, HttpServletRequest request) {
		// 從 HttpServletRequest 中獲取 Session
	    HttpSession session = request.getSession(false);
	    // 檢查 Session 中是否存在登入狀態和使用者資訊
	    if (session != null && session.getAttribute("loginStatus") != null && (Boolean) session.getAttribute("loginStatus")) {
	        // 使用者已登入
	    	// 獲取登入的使用者資訊
	    	User loggedInUser = (User) session.getAttribute("loggedInUser");
	    	User user = userService.getUser(loggedInUser.getUser_id());
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    	
	    	model.addAttribute("user", user);
	    	String birthday = sdf.format(user.getBirthday());
	    	model.addAttribute("birthday", birthday);
	    	model.addAttribute("gender", user.getGender());
	    	return "user";
	    } else {
	        // 使用者未登入，導向登入頁面或執行其他操作
	        return "redirect:/login";
	    }
	}
	
	@PutMapping("/user/update")
	public String updateUser(@ModelAttribute("userupdate") UserUpdateDto userupdatedto, Model model) {
		System.out.println(userupdatedto);
		 // 將字串格式的生日轉換成 Date 類型
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = null;
        try {
            birthday = sdf.parse(userupdatedto.getBirthday());
        } catch (Exception e) {
            e.printStackTrace(); // 處理解析異常
        }
		
		
		User user =new User();
		user.setName(userupdatedto.getName());
		user.setEmail(userupdatedto.getEmail());
		user.setUser_id(userupdatedto.getUser_id());
		user.setGender(userupdatedto.getGender());
		user.setPhone(userupdatedto.getPhone());
		user.setBirthday(birthday);
		userService.updateUser(user);
		
		return "redirect:/member";
	}
	
	@PutMapping("/updatepassword")
	public String updateUser(@ModelAttribute("password") PasswordDto passwordDto, Model model, HttpSession session) {
		System.out.println(passwordDto);
		// 如果邮箱未被使用且密码长度符合要求，则执行注册逻辑
		if (passwordDto.getNewPassword().length() < 6) {
			session.setAttribute("error2", "密碼長度不得少於6碼。");
			System.out.print("密碼長度不得少於6碼。");
			return "redirect:/member/user"; // 返回注册页面，显示错误消息
		}else if(!passwordDto.getConfirmPassword().equals(passwordDto.getNewPassword())) {
			session.setAttribute("error2", "確認密碼不相同");
			System.out.print("確認密碼不相同。");
			return "redirect:/member/user"; // 返回注册页面，显示错误消息
		}

		String salt = SaltedPasswordHasher.generateSalt();
		String hashedPassword = SaltedPasswordHasher.hashPassword(passwordDto.getNewPassword(), salt);
		User user =new User();
		user.setUser_id(passwordDto.getUser_id());
		user.setSalt(salt);
		user.setPassword(hashedPassword);
		userService.updateUserPassword(passwordDto.getUser_id(),user);
		
		 // 密碼更新成功
	    session.setAttribute("passwordChangeSuccess", "密碼更新成功！請重新登入。");
		session.removeAttribute("error2");
		return "redirect:/member/user";
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
	
	@GetMapping("/update/{bookingId}")
	public String addUser(@PathVariable("bookingId") Integer bookingid, Model model) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Booking booking =bookingRoomService.getBooking(bookingid);
		model.addAttribute("update", true);
		Room room =roomService.getRoom(booking.getRoomId());
		RoomType roomtype =roomtypeService.getRoomtype(room.getType_id());
		model.addAttribute("roomtype", roomtype.getName());
		model.addAttribute("roomtypePrice", roomtype.getPrice());
		model.addAttribute("start_date", sdf.format(booking.getStart_date()));
		model.addAttribute("end_date", sdf.format(booking.getEnd_date()));
		model.addAttribute("totalPrice", booking.getPrice());
		model.addAttribute("booking", booking);
		return "/booking";
	}
	
}