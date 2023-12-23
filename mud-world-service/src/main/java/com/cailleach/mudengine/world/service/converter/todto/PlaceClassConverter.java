package com.cailleach.mudengine.world.service.converter.todto;

import java.util.stream.Collectors;

import com.cailleach.mudengine.world.model.PlaceClassEntity;
import com.cailleach.mudengine.world.model.PlaceClassAttrEntity;
import com.cailleach.mudengine.world.rest.dto.PlaceClass;

public class PlaceClassConverter {

	private PlaceClassConverter() {}
	
	public static PlaceClass convert(PlaceClassEntity a) {
		
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
						PlaceClassAttrEntity::getCode, 
						PlaceClassAttrEntity::getValue)
						)
				);
		
		return response;
	}
}
