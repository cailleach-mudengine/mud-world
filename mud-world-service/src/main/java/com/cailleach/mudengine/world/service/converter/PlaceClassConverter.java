package com.cailleach.mudengine.world.service.converter;

import java.util.stream.Collectors;

import com.cailleach.mudengine.world.model.MudPlaceClass;
import com.cailleach.mudengine.world.model.MudPlaceClassAttr;
import com.cailleach.mudengine.world.rest.dto.PlaceClass;

public class PlaceClassConverter {

	private PlaceClassConverter() {}
	
	public static PlaceClass convert(MudPlaceClass a) {
		
		PlaceClass response = new PlaceClass();
		
		response.setPlaceClassCode(a.getCode());
		response.setName(a.getName());
		response.setDescription(a.getDescription());
		response.setSizeCapacity(a.getSizeCapacity());
		response.setWeightCapacity(a.getWeightCapacity());
		response.setParentClassCode(a.getParentClassCode());
		response.setDemisePlaceClassCode(a.getDemisedPlaceClassCode());
		response.setBuildEffort(a.getBuildEffort());
		response.setBuildCost(a.getBuildCost());
		
		response.setAttrs(
			a.getAttrs().stream()
				.collect(Collectors.toMap(
						MudPlaceClassAttr::getCode, 
						MudPlaceClassAttr::getValue)
						)
				);
		
		return response;
	}
}
