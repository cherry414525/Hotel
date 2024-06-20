package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.RoomDao;
import com.example.demo.dao.RoomTypeDao;
import com.example.demo.dao.UserDao;
import com.example.demo.model.po.Room;
import com.example.demo.model.po.RoomType;
import com.example.demo.model.po.User;

@Service
public class RoomTypeService {
	
	@Autowired
	private RoomTypeDao roomtypeDao;
	
	public Integer findRoomtypebyid(String name) {
		return roomtypeDao.findRoomtypebyid(name);
	}
	
	public List<RoomType> findAllRoomtypes() {
		return roomtypeDao.findAllRoomtypes();
	}

	public RoomType getRoomtype(Integer id) {
		return roomtypeDao.getRoomtype(id);
	}
	
	public Integer addRoomtype(RoomType roomtype) {
		System.out.println(roomtype);
		return roomtypeDao.addRoomType(roomtype);
	}

	public Integer updateRoomtype(RoomType roomtype) {
		
		roomtype.setType_id(3);
		roomtype.setName("經典四人房");
		roomtype.setPrice(5500);
		roomtype.setCapacity(4);
		roomtype.setPhoto("");
		return roomtypeDao.updateRoomType(roomtype.getType_id(), roomtype);
	}
	
	public Integer deleteRoomtype(Integer id) {
		
		return roomtypeDao.deleteRoomType(id);
	}

	public Integer GroupTypebyid(Integer id) {
		return roomtypeDao.GroupTypebyid(id);
	}
	
}