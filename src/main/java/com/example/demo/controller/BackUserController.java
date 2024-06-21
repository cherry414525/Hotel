package com.example.demo.controller;
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

import com.example.demo.model.dto.AllUserDto;
import com.example.demo.model.dto.RoomDto;
import com.example.demo.model.dto.SearchUserDto;
import com.example.demo.model.po.Room;
import com.example.demo.model.po.RoomType;
import com.example.demo.model.po.User;
import com.example.demo.service.BookingRoomService;
import com.example.demo.service.UserService;



@RestController
@RequestMapping("/api")
public class BackUserController {
	@Autowired
	private UserService userservice;
	
	@Autowired
	private BookingRoomService bookingroomService;
	
	@GetMapping("/members")
	public List<AllUserDto>  findAll() {
		List<User> users = userservice.findAllUsers();
		List<AllUserDto> userDtos = new ArrayList<>();

        for (User user : users) {	
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        String birthday = sdf.format(user.getBirthday());
	       
	        String gender;
	        if(user.getGender().equals("female") ){
		        gender = "女";
	        }else {
	        	 gender = "男";
	        }
			// 建立 UserDTO 對象，將 User 的資料放入
			AllUserDto userDto = new AllUserDto();
			userDto.setUser_id(user.getUser_id());
			userDto.setName(user.getName());
			userDto.setEmail(user.getEmail());
			userDto.setBirthday(birthday);
			userDto.setPhone(user.getPhone());
			userDto.setGender(gender);
            
			userDtos.add(userDto);
		}
            
          return userDtos;

        
		
	}
	
	@GetMapping("/user/{user_id}")
	public AllUserDto  finduser(@PathVariable("user_id") Integer user_id) {

			User user = userservice.getUser(user_id);
        	
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        String birthday = sdf.format(user.getBirthday());
	       
	        String gender;
	        if(user.getGender().equals("female") ){
		        gender = "女";
	        }else {
	        	 gender = "男";
	        }
			// 建立 UserDTO 對象，將 User 的資料放入
			AllUserDto userDto = new AllUserDto();
			userDto.setUser_id(user.getUser_id());
			userDto.setName(user.getName());
			userDto.setEmail(user.getEmail());
			userDto.setBirthday(birthday);
			userDto.setPhone(user.getPhone());
			userDto.setGender(gender);
                    
          return userDto;
	
	}
	
	@PostMapping("/searchusers")
	public List<AllUserDto>  findUsers(@RequestBody SearchUserDto searchUserDto) {
		
		
		String userid = searchUserDto.getUserId();
		String username = searchUserDto.getUserName();
		
		
		List<User> users ;
		if(userid.equals("")&& username.equals("")) {
			users = userservice.findAllUsers();
		}else if(userid.equals("")) {
			users = userservice.getUsersByIdOrName(0, username);
		}else if (username.equals("")) {
			users = userservice.getUsersByIdOrName(Integer.parseInt(userid), null);
			
		}else{
			users = userservice.getUsersByIdOrName(Integer.parseInt(userid), username);
		}
		
		
		List<AllUserDto> userDtos = new ArrayList<>();

        for (User user : users) {	
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        String birthday = sdf.format(user.getBirthday());
	        String gender;
	        
	        if(user.getGender().equals("female") ){
		        gender = "女";
	        }else {
	        	 gender = "男";
	        }
			// 建立 UserDTO 對象，將 User 的資料放入
			AllUserDto userDto = new AllUserDto();
			userDto.setUser_id(user.getUser_id());
			userDto.setName(user.getName());
			userDto.setEmail(user.getEmail());
			userDto.setBirthday(birthday);
			userDto.setPhone(user.getPhone());
			userDto.setGender(gender);
            
			userDtos.add(userDto);
		}
            
          return userDtos;

        
		
	}
	
	@DeleteMapping("/deleteUser/{userId}")
    public String deleteRoomType(@PathVariable("userId") Integer userId) {
        try {
        	//刪除該會員所有booking資料
            bookingroomService.deleteBookingByUserId(userId);
            // 呼叫服務層的方法來刪除會員
            userservice.deleteUser(userId);
            
            return "會員成功刪除";
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "刪除會員失敗: " + e.getMessage(), e);
        }
    }

}