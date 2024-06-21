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
import com.example.demo.model.dto.UpdateUserDto;
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

	 @PutMapping("/updateUser")
	    public String updateUser(@RequestBody UpdateUserDto updateUserDto) {
	        try {
	            // 將 JSON 資料轉換成 User 物件
	            String userId = updateUserDto.getUser_id();
	            String name = updateUserDto.getName();
	            String birthday = updateUserDto.getBirthday();
	            
	            String phone = updateUserDto.getPhone();
	            String email = updateUserDto.getEmail();
	            
	            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		        Date birthDate = formatter.parse(birthday);
	            
		        String gender;
		        if(updateUserDto.getGender().equals("女") ){
			        gender = "female";
		        }else {
		        	 gender = "male";
		        }
		        
	            // 建立 User 物件
	            User user = new User();
	            user.setUser_id(Integer.parseInt(userId)); 
	            user.setName(name); 
	            user.setBirthday(birthDate); 
	            user.setGender(gender); 
	            user.setPhone(phone); 
	            user.setEmail(email); 

	            // 	呼叫服務層的方法來更新會員
	            userservice.updateUser(user);

	            return "修改會員成功";
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "修改會員失败: " + e.getMessage(), e);
	        }
	    }
}