package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.po.Room;
import com.example.demo.model.po.RoomType;
import com.example.demo.model.po.User;

public interface RoomTypeDao {
	Integer findRoomtypebyid(String name);
	List<RoomType> findAllRoomtypes();
	RoomType getRoomtype(Integer id);	//查詢單筆房型資料
	Integer addRoomType(RoomType roomtype);		//新增房型
	Integer updateRoomType(Integer id,RoomType roomtype);	//修改房型
	Integer deleteRoomType(Integer id);		//刪除房型
	Integer GroupTypebyid(Integer id);
}