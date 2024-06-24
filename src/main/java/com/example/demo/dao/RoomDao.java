package com.example.demo.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.dto.RoomAvailabilityDto;
import com.example.demo.model.po.Room;
import com.example.demo.model.po.User;

public interface RoomDao {
	Integer findRoomsBydateAndType(String start_date, String end_date, Integer type_id) ;
	List<Room> findAllRooms();
	Room getRoom(Integer id);	//查詢單筆房間資料
	Integer addRoom(Room room);		//新增房間
	Integer updateRoom(Integer id,Room room);	//修改房間
	Integer deleteRoom(Integer id);		//刪除房間
	List<RoomAvailabilityDto> findRoomsBydate(String todayString, String tomorrowString);
	List<Room> findRoomsByIdAndType(Integer id, Integer type);
	List<Room> findAllRoomsbyType(Integer type_id);
}