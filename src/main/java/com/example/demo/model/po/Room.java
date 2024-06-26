package com.example.demo.model.po;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
	private Integer room_id;
	private Integer type_id;	//房型編號 
	
}
