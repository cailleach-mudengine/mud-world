package com.cailleach.mudengine.world.service.converter;

import com.cailleach.mudengine.world.model.MudPlaceExit;
import com.cailleach.mudengine.world.rest.dto.PlaceExit;

public class PlaceExitConverter {
	
	public static PlaceExit convert(MudPlaceExit dbPlaceExit) {
		
		PlaceExit result = new PlaceExit();
		
		result.setTargetPlaceCode(dbPlaceExit.getTargetPlaceCode());
		result.setLockable(dbPlaceExit.isLockable());
		result.setLocked(dbPlaceExit.isLocked());
		result.setOpened(dbPlaceExit.isOpened());
		result.setVisible(dbPlaceExit.isVisible());
		
		return result;
	}
}
