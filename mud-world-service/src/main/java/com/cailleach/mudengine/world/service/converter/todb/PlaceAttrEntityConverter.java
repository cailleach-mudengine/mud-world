package com.cailleach.mudengine.world.service.converter.todb;

import com.cailleach.mudengine.world.model.PlaceAttrEntity;
import com.cailleach.mudengine.world.model.PlaceClassAttrEntity;
import com.cailleach.mudengine.world.model.pk.PlaceAttrEntityPK;

public class PlaceAttrEntityConverter {

	private PlaceAttrEntityConverter() { }
	
	public static PlaceAttrEntity convert(Long placeCode, PlaceClassAttrEntity classAttr) {
		
		PlaceAttrEntity response = new PlaceAttrEntity();
		PlaceAttrEntityPK pk = new PlaceAttrEntityPK();
		
		pk.setCode(classAttr.getId().getCode());
		pk.setPlaceCode(placeCode);
		
		response.setId(pk);
		response.setValue(classAttr.getValue());
		
		return response;
	}
	
	public static PlaceAttrEntity build(Long placeCode, String attrCode, Integer attrValue) {
		
		PlaceAttrEntity response = new PlaceAttrEntity();
		PlaceAttrEntityPK pk = new PlaceAttrEntityPK();
		
		pk.setCode(attrCode);
		pk.setPlaceCode(placeCode);
		
		response.setId(pk);
		response.setValue(attrValue);
		
		return response;
	}

}
