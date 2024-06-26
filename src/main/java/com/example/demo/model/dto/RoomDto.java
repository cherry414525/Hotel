package com.example.demo.model.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private int room_id;
    private String type_name;
}