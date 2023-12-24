package com.cailleach.mudengine.world.service.converter.todb;

import com.cailleach.mudengine.world.model.PlaceExitEntity;
import com.cailleach.mudengine.world.model.pk.PlaceExitEntityPK;
import com.cailleach.mudengine.world.rest.dto.PlaceExit;

public class PlaceExitEntityConverter {

	private PlaceExitEntityConverter()  { }
	
	public static PlaceExitEntity build(Long placeCode, String direction, Long targetPlaceCode) {
		
		PlaceExitEntity newExit = new PlaceExitEntity();
		PlaceExitEntityPK newExitPK = new PlaceExitEntityPK();
		
		newExitPK.setDirection(direction);
		newExitPK.setPlaceCode(placeCode);
		
		newExit.setPk(newExitPK);
		newExit.setTargetPlaceCode(targetPlaceCode);
		
		newExit.setOpened(true);
		newExit.setVisible(true);
		newExit.setLocked(false);
		newExit.setLockable(false);
		
		
		return newExit;
	}
	
	public static PlaceExitEntity build(PlaceExit requestExit, Long placeCode, String direction) {
		
		
		PlaceExitEntity newExit = new PlaceExitEntity();
		PlaceExitEntityPK newExitPK = new PlaceExitEntityPK();
		
		newExitPK.setDirection(direction);
		newExitPK.setPlaceCode(placeCode);
		
		newExit.setPk(newExitPK);
		newExit.setOpened(requestExit.isOpened());
		newExit.setVisible(requestExit.isVisible());
		newExit.setLocked(requestExit.isLocked());
		newExit.setLockable(requestExit.isLockable());
		
		newExit.setTargetPlaceCode(requestExit.getTargetPlaceCode());

		return newExit;
	}
}
