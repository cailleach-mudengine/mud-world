package com.cailleach.mudengine.world.rest.dto;

import java.util.*;

import lombok.Data;

@Data
public class Place {
	
	private Long code;
	
	private String name;
	
	private String description;

	private String classCode;
	
	private PlaceClass placeClass;
	
	private Map<String, PlaceExit> exits;
	
	private Map<String, Integer> attrs;
	
	public Place() {
		this.attrs = new HashMap<>();
		this.exits = new HashMap<>();
	}
}
