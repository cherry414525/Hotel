package com.example.demo.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.text.DateFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.dto.RoomAvailabilityDto;
import com.example.demo.model.po.Room;
import com.example.demo.model.po.RoomType;
import com.example.demo.service.BookingRoomService;
import com.example.demo.service.RoomService;
import com.example.demo.service.RoomTypeService;

@Controller
@RequestMapping("/hotel")
public class HotelController {
	
	@Autowired
	private RoomTypeService  roomtypeService;
	
	@Autowired
	private RoomService roomService;
	@Autowired
	private BookingRoomService bookingroomService;

	
	@GetMapping
	public String findAll(Model model) {
		List<RoomType> roomtypeDtos = roomtypeService.findAllRoomtypes();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String todayString = sdf.format(new Date());
		// 明天的日期
	    Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.DAY_OF_YEAR, 1); // 加1天
	    String tomorrowString = sdf.format(calendar.getTime());
	    
	    //依照日期查詢剩餘房間數
	   List<RoomAvailabilityDto> roomAvailabilityDto = roomService.findRoomsBydate(todayString, tomorrowString);
	   // 将 roomAvailabilityDto 转换为 Map<typeId, availableRooms> 以便在 JSP 中使用
	    Map<Integer, Integer> availabilityMap = roomAvailabilityDto.stream()
	        .collect(Collectors.toMap(RoomAvailabilityDto::getTypeId, RoomAvailabilityDto::getAvailableRooms));

	    model.addAttribute("availabilityMap", availabilityMap);
		model.addAttribute("roomAvailabilityDto", roomAvailabilityDto);
		model.addAttribute("roomtypeDtos", roomtypeDtos); // 給列表用
		model.addAttribute("today", todayString);
		model.addAttribute("tomorrow", tomorrowString);
		return "hotel";
	}
	/*
	@GetMapping("/findroom")
	public String getUser(@RequestParam("roomId") Integer roomId, Model model) {
		
		
		 model.addAttribute("roomDtos",roomtypeService.getRoom(roomId));
		return "result";
	}
	
	@PostMapping("/addroom")
	public String addUser(@ModelAttribute Room room, Model model) {
		
		try {
			
			Integer rowcount = roomService.addRoom(room);
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
    public String updateUser(@ModelAttribute Room room, Model model) {
        try {
        	
        	System.out.println(room);
            // 調用服務層方法更新用戶信息
            roomService.updateRoom(room);
            String message = "更新成功";
            model.addAttribute("room",room);
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
	public String deleteUser(@RequestParam("roomId") Integer roomId, Model model) {
		
		Integer rowcount = roomService.deleteRoom(roomId);
		System.out.print(rowcount);
		String message = "刪除" + ((rowcount>0)?"成功":"失敗");
		model.addAttribute("message", message);
		return "result";
	}
	*/
}