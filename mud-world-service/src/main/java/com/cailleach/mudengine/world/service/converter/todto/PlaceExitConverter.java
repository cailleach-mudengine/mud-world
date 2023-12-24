package com.cailleach.mudengine.world.service.converter.todto;

import com.cailleach.mudengine.world.model.PlaceExitEntity;
import com.cailleach.mudengine.world.rest.dto.PlaceExit;

public class PlaceExitConverter {
	
	private PlaceExitConverter() { }
	
	public static PlaceExit convert(PlaceExitEntity dbPlaceExit) {
		
		PlaceExit result = new PlaceExit();
		
		result.setTargetPlaceCode(dbPlaceExit.getTargetPlaceCode());
		result.setLockable(dbPlaceExit.isLockable());
		result.setLocked(dbPlaceExit.isLocked());
		result.setOpened(dbPlaceExit.isOpened());
		result.setVisible(dbPlaceExit.isVisible());
		
		return result;
	}
}
