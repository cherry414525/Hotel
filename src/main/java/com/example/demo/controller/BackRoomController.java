package com.example.demo.controller;

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

import com.example.demo.model.dto.RoomDto;
import com.example.demo.model.dto.SearchRequest;
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
	
	@GetMapping("/room/{room_id}")
	public RoomDto  findRoom(@PathVariable("room_id") Integer room_id) {
		Room room = roomService.getRoom(room_id);
	    RoomType roomType = roomtypeService.getRoomtype(room.getType_id());
	            
	     // 建立 RoomDTO 對象，將 Room 的資料和 RoomType 的 name 放入
	     RoomDto roomDto = new RoomDto();
	     roomDto.setRoom_id(room.getRoom_id());
	     roomDto.setType_name(roomType.getName());
	     System.out.println(roomDto);
	     return roomDto;

	}
	
	@GetMapping("/types")
	public List<RoomType>  findAllTypes() {
		List<RoomType> roomtypes = roomtypeService.findAllRoomtypes();
		return roomtypes;
	}

	@PostMapping("/search")
    public List<RoomDto> searchRooms(@RequestBody SearchRequest searchRequest) {
        // 從請求中獲取房間編號和房型
        String roomNumber = searchRequest.getRoomNumber();
        String roomType = searchRequest.getRoomType();
		Integer type_id = roomtypeService.findRoomtypebyid(roomType);
		
		List<Room> rooms;
		if(roomNumber.equals("")&& roomType.equals("")) {
			rooms = roomService.findAllRooms();
		}else if(roomNumber.equals("")) {		
			rooms = roomService.findRoomsByIdAndType( 0, type_id);
		}else if(roomType.equals("") ) {
			
			rooms = roomService.findRoomsByIdAndType( Integer.parseInt(roomNumber), 0);
		} else {
			rooms = roomService.findRoomsByIdAndType( Integer.parseInt(roomNumber), type_id);
		}
		
       
        List<RoomDto> roomDtos = new ArrayList<>();

        for (Room room : rooms) {
            // 根據 room 的 type_id 找到對應的 RoomType
            RoomType Type = roomtypeService.getRoomtype(room.getType_id());
            
            // 建立 RoomDTO 對象，將 Room 的資料和 RoomType 的 name 放入
            RoomDto roomDto = new RoomDto();
            roomDto.setRoom_id(room.getRoom_id());
            roomDto.setType_name(Type.getName());
            
            roomDtos.add(roomDto);
        }
        
        return roomDtos;
        
    }
	
	
	@PostMapping("/addroom")
	public String addRoom(@RequestBody Map<String, String> request) {
	    try {
	        // 從 request 中獲取房間編號和房型名稱
	        String room_id = request.get("roomNumber");
	        String type_name = request.get("roomType");
	        
	        // 根據房型名稱查詢對應的 RoomType
	        Integer roomType = roomtypeService.findRoomtypebyid(type_name);
	        
	        // 創建一個新的 Room 物件
	        Room room = new Room();
	        room.setRoom_id(Integer.parseInt(room_id)); 
	        room.setType_id(roomType); // 設置房型 ID
	        
	        // 呼叫服務層的方法來新增房間
	        roomService.addRoom(room);
	        
	        return "新增房間成功"; // 返回成功訊息
	    } catch (Exception e) {
	        return "新增房間失敗: " + e.getMessage(); // 返回失敗訊息
	    }
	}
	
	 	@DeleteMapping("/deleteRoom/{roomId}")
	    public String deleteRoom(@PathVariable("roomId") Integer roomId) {
	        try {
	        	System.out.println(roomId);
	            // 呼叫服務層的方法來刪除房間
	            roomService.deleteRoom(roomId);
	            return "房間成功刪除";
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "刪除房間失敗: " + e.getMessage(), e);
	        }
	    }
	
}
