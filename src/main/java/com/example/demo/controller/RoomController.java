package com.example.demo.controller;

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


import com.example.demo.model.po.Room;
import com.example.demo.service.RoomService;

@Controller
@RequestMapping("/room")
public class RoomController {
	
	@Autowired
	private RoomService  roomService;

	
	@GetMapping
	public String findAll(Model model) {
		List<Room> roomDtos = roomService.findAllRooms();
		model.addAttribute("roomDtos", roomDtos); // 給列表用
		
		return "result";
	}
	
	@GetMapping("/findroom")
	public String getUser(@RequestParam("roomId") Integer roomId, Model model) {
		
		
		 model.addAttribute("roomDtos",roomService.getRoom(roomId));
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
}
