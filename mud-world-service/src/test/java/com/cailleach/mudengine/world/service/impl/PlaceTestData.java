package com.cailleach.mudengine.world.service.impl;

import java.io.File;
import java.io.IOException;

import com.cailleach.mudengine.world.model.PlaceEntity;
import com.cailleach.mudengine.world.model.PlaceClassEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PlaceTestData {
	
	private static final String MUD_PLACE_PREFIX = "src/test/resources/mudplace-";
	private static final String MUD_PLACE_SUFFIX = ".json";
	
	private static final String MUD_PLACE_CLASS_PREFIX = "src/test/resources/mudplaceclass-";
	private static final String MUD_PLACE_CLASS_SUFFIX = ".json";
	
	public static final Integer READ_PLACE_ID = 1;
	public static final String UPDATE_CLASS_PLACE_CLASS = "TESTBLDG";
	
	public static final Integer TARGET_PLACE_ID = 2;
	
	private static ObjectMapper jsonMapper = new ObjectMapper();
	
	public static PlaceClassEntity loadMudPlaceClass(String className) throws IOException{
		
		return jsonMapper.readValue(new File(
				PlaceTestData.MUD_PLACE_CLASS_PREFIX +
				className + 
				PlaceTestData.MUD_PLACE_CLASS_SUFFIX
				), PlaceClassEntity.class);
	}
	
	public static PlaceEntity loadMudPlace(Integer placeId) throws IOException{
		
		return jsonMapper.readValue(new File(
				PlaceTestData.MUD_PLACE_PREFIX +
				placeId + 
				PlaceTestData.MUD_PLACE_SUFFIX
				), PlaceEntity.class);
	}

}
