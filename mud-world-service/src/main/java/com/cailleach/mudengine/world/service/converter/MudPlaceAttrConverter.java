package com.cailleach.mudengine.world.service.converter;

import com.cailleach.mudengine.world.model.MudPlaceAttr;
import com.cailleach.mudengine.world.model.MudPlaceClassAttr;
import com.cailleach.mudengine.world.model.pk.MudPlaceAttrPK;

public class MudPlaceAttrConverter {

	private MudPlaceAttrConverter() { }
	
	public static MudPlaceAttr convert(Integer placeCode, MudPlaceClassAttr classAttr) {
		
		MudPlaceAttr response = new MudPlaceAttr();
		MudPlaceAttrPK pk = new MudPlaceAttrPK();
		
		pk.setCode(classAttr.getId().getCode());
		pk.setPlaceCode(placeCode);
		
		response.setId(pk);
		response.setValue(classAttr.getValue());
		
		return response;
	}
	
	public static MudPlaceAttr build(Integer placeCode, String attrCode, Integer attrValue) {
		
		MudPlaceAttr response = new MudPlaceAttr();
		MudPlaceAttrPK pk = new MudPlaceAttrPK();
		
		pk.setCode(attrCode);
		pk.setPlaceCode(placeCode);
		
		response.setId(pk);
		response.setValue(attrValue);
		
		return response;
	}

}
