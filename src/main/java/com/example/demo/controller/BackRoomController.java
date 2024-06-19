package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.swing.text.DateFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.RoomDto;
import com.example.demo.model.po.Room;
import com.example.demo.model.po.RoomType;
import com.example.demo.service.RoomService;
import com.example.demo.service.RoomTypeService;

@RestController
@RequestMapping("/api")
public class BackRoomController {
	@Autowired
	private RoomService  roomService;
	
	
	@Autowired
	private RoomTypeService roomtypeService;
	
	@GetMapping("/rooms")
	public List<RoomDto>  findAll() {
		List<Room> rooms = roomService.findAllRooms();
		 List<RoomDto> roomDtos = new ArrayList<>();

	        for (Room room : rooms) {
	            // 根據 room 的 type_id 找到對應的 RoomType
	            RoomType roomType = roomtypeService.getRoomtype(room.getType_id());
	            
	            // 建立 RoomDTO 對象，將 Room 的資料和 RoomType 的 name 放入
	            RoomDto roomDto = new RoomDto();
	            roomDto.setRoom_id(room.getRoom_id());
	            roomDto.setType_name(roomType.getName());
	            
	            roomDtos.add(roomDto);
	        }

	        return roomDtos;
		
		
	}

	@PostMapping("/search")
    public List<Room> searchRooms(@RequestBody SearchRequest searchRequest) {
        // 從請求中獲取房間編號和房型
        String roomNumber = searchRequest.getRoomNumber();
        String roomType = searchRequest.getRoomType();

        List<Room> rooms = roomService.findAllRooms();

        // 返回符合條件的房間列表
        return rooms;
    }
	
}
