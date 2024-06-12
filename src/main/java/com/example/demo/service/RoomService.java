package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.RoomDao;
import com.example.demo.dao.UserDao;
import com.example.demo.model.dto.RoomAvailabilityDto;
import com.example.demo.model.po.Room;
import com.example.demo.model.po.User;

@Service
public class RoomService {
	
	@Autowired
	private RoomDao roomDao;
	
	public List<Room> findAllRooms() {
		return roomDao.findAllRooms();
	}

	public Integer findRoomsBydateAndType(String start_date, String end_date, Integer type_id) {
		return roomDao.findRoomsBydateAndType(start_date, end_date, type_id);
	}
	
	public List<RoomAvailabilityDto> findRoomsBydate(String todayString, String tomorrowString) {
		return roomDao.findRoomsBydate(todayString, tomorrowString);
	}
	
	public Optional<Room> getRoom(Integer id) {
		return roomDao.getRoom(id);
	}
	
	public Integer addRoom(Room room) {
		
		room.setRoom_id(203);
		room.setType_id(1);
		return roomDao.addRoom(room);
		
	}

	public Integer updateRoom(Room room) {
		
		room.setRoom_id(202);
		room.setType_id(2);
		return roomDao.updateRoom(room.getRoom_id(), room);
	}
	
	public Integer deleteRoom(Integer id) {
		
		return roomDao.deleteRoom(id);
	}

	
	
}