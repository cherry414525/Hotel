package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.text.SimpleDateFormat;

import com.example.demo.model.po.Room;
import com.example.demo.model.po.User;

@Repository
public class RoomDaoImpl implements RoomDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Room> findAllRooms() {
		String sql = "select room_id, type_id from room";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Room.class));
	}

	@Override
	public Optional<Room> getRoom(Integer id) {
		String sql = "select room_id, type_id from room where room_id=?";
		try {
			
			Room room = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Room.class), id);
			System.out.print(room);
			return Optional.of(room);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.of(null);
	}

	@Override
	public Integer addRoom(Room room) {
		String sql = "INSERT INTO room (room_id, type_id) VALUES (?, ?)";
        
        // 執行 SQL 新增語句
        int rowcount = jdbcTemplate.update(sql,room.getRoom_id(),room.getType_id());
        
        return rowcount;
	}

	@Override
	public Integer updateRoom(Integer id, Room room) {
		System.out.println(room.toString());
		String sql = "update room set type_id=?  where room_id = ?";
		int rowcount = jdbcTemplate.update(sql, room.getType_id(), id);
		return rowcount;
	}

	@Override
	public Integer deleteRoom(Integer id) {
		String sql = "delete from room where room_id = ?";
		int rowcount = jdbcTemplate.update(sql, id);
		System.out.println(rowcount);
		if(rowcount == 1) {
			System.out.println(id);
			return id;
		}
		return null;
	}

	
	
}