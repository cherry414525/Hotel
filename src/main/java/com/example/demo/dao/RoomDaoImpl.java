package com.example.demo.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import com.example.demo.model.dto.RoomAvailabilityDto;
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
	public List<RoomAvailabilityDto> findRoomsBydate(String start_date, String end_date) {
		/*	SELECT r.type_id, COUNT(r.room_id) AS available_rooms
			FROM room r
			WHERE NOT EXISTS (
    		SELECT 1
    		FROM booking b
    		WHERE b.room_id = r.room_id
    		AND (
        		(b.start_date <= '2024-06-07' AND b.end_date >= '2024-06-06' and b.status<>'已取消')
        	-- 給定的日期區間
    			)
			)
			GROUP BY r.type_id;
		 	*
			*	SELECT r.type_id, COUNT(r.room_id) as total,  COUNT(r.room_id) - COALESCE(COUNT(b.room_id), 0) AS available_rooms
				FROM room r
				LEFT JOIN booking b ON b.room_id = r.room_id
				    AND (
				        (b.start_date <= '2024-06-15' AND b.end_date >= '2024-06-14' AND b.status <> '已取消')
				        -- 給定的日期區間
				    )
				GROUP BY r.type_id;	
			
		 * 
		 */
		String sql = "SELECT r.type_id, COUNT(r.room_id) as total,  COUNT(r.room_id) - COALESCE(COUNT(b.room_id), 0) AS available_rooms "
				+"FROM room r "
				+ "LEFT JOIN booking b ON b.room_id = r.room_id "
				+ "AND ( "
				+ "(b.start_date < ? AND b.end_date > ? AND b.status <> '已取消') "
				+ ") "
				+ "GROUP BY r.type_id ";
		
		return jdbcTemplate.query(sql, new RowMapper<RoomAvailabilityDto>() {
	        @Override
	        public RoomAvailabilityDto mapRow(ResultSet rs, int rowNum) throws SQLException {
	            RoomAvailabilityDto dto = new RoomAvailabilityDto();
	            dto.setTypeId(rs.getInt("type_id"));
	            dto.setAvailableRooms(rs.getInt("available_rooms"));
	            System.out.println(dto);
	            return dto;
	        }
	    }, end_date, start_date);
	}
	
	@Override
	public Integer findRoomsBydateAndType(String start_date, String end_date, Integer type_id) {
		/*	SELECT r.type_id, r.room_id
			FROM room r
			WHERE NOT EXISTS (
			    SELECT 1
			    FROM booking b
			    WHERE b.room_id = r.room_id
			    AND (
			        (b.start_date <= '2024-06-12' AND b.end_date >= '2024-06-11' and b.status<>'已取消')
			        -- 給定的日期區間
			    )
			) AND type_id=1
		 * 
		 */
		String sql = "select r.room_id "
				+"FROM room r "
				+ "WHERE NOT EXISTS ( "
				+ "SELECT 1 "
				+ "FROM booking b "
				+ "WHERE b.room_id = r.room_id "
				+ "AND ( "
				+ "	(b.start_date < ? AND b.end_date > ? and b.status<>'已取消') "
				+ ") "
				+ ") "
				+ "AND type_id=? "
				+ "LIMIT 1 ";
		
		return jdbcTemplate.queryForObject(sql, Integer.class, end_date, start_date, type_id);
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