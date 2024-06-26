package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.text.SimpleDateFormat;

import com.example.demo.model.po.Room;
import com.example.demo.model.po.RoomType;
import com.example.demo.model.po.User;

@Repository
public class RoomTypeDaoImpl implements RoomTypeDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Integer findRoomtypebyid(String name) {
		String sql = "select type_id from roomtype where name = ?";
	    try {
	        return jdbcTemplate.queryForObject(sql, Integer.class, name);
	    } catch (Exception e) {
	        // 處理沒有找到結果的情況，根據需求返回 null 或者拋出異常
	        return null;
	    }
	}
	

	@Override
	public List<RoomType> findAllRoomtypes() {
		String sql = "select  type_id,name,price,capacity,photo from roomtype";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(RoomType.class));
	}

	@Override
	public RoomType getRoomtype(Integer id) {
		String sql = "select type_id,name,price,capacity,photo from roomtype where type_id=?";
		try {
			
			RoomType roomtype = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(RoomType.class), id);
			System.out.print(roomtype);
			return roomtype;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer addRoomType(RoomType roomtype) {
		String sql = "INSERT INTO roomtype (name,price,capacity,photo) VALUES (?,?,?,?)";
		try {
			// 執行 SQL 新增語句
			int rowcount = jdbcTemplate.update(sql,roomtype.getName(),roomtype.getPrice(),roomtype.getCapacity(),roomtype.getPhoto());
			return rowcount;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
        return null;
	}
	

	@Override
	public Integer updateRoomType(Integer id, RoomType roomtype) {
		
		String sql = "update roomtype set name=?,price=?,capacity=?,photo=?  where type_id = ?";
		int rowcount = jdbcTemplate.update(sql, roomtype.getName(),roomtype.getPrice(),roomtype.getCapacity(),roomtype.getPhoto(), id);
		return rowcount;
	}

	@Override
	public Integer deleteRoomType(Integer id) {
		String sql = "delete from roomtype where type_id = ?";
		int rowcount = jdbcTemplate.update(sql, id);
		System.out.println(rowcount);
		if(rowcount == 1) {
			System.out.println(id);
			return id;
		}
		return null;
	}

	@Override
	public Integer GroupTypebyid(Integer id) {
		
		String sql = "SELECT count(*) as total FROM hotel.room where type_id=? ;";
		int rowcount = jdbcTemplate.queryForObject(sql, Integer.class, id);
		return rowcount;
	}
	
}