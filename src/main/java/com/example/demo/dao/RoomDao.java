package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.po.Room;
import com.example.demo.model.po.User;

public interface RoomDao {
	List<Room> findAllRooms();
	Optional<Room> getRoom(Integer id);	//查詢單筆房間資料
	Integer addRoom(Room room);		//新增房間
	Integer updateRoom(Integer id,Room room);	//修改房間
	Integer deleteRoom(Integer id);		//刪除房間
}