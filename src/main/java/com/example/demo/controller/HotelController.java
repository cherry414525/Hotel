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
	public String findAll(@RequestParam(name = "checkInDate", required = false) String checkInDate,
            @RequestParam(name = "checkOutDate", required = false) String checkOutDate,Model model) {
		List<RoomType> roomtypeDtos = roomtypeService.findAllRoomtypes();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 
		String todayString;
		String tomorrowString;
		System.out.println(checkInDate);
		System.out.println(checkOutDate);
		if (checkInDate == null || checkOutDate == null) {
			 todayString = sdf.format(new Date());
			// 明天的日期
		    Calendar calendar = Calendar.getInstance();
		    calendar.add(Calendar.DAY_OF_YEAR, 1); // 加1天
		    tomorrowString = sdf.format(calendar.getTime());
	    
		 }else {
			 todayString=checkInDate;
			 tomorrowString=checkOutDate;
		 }
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
	
}