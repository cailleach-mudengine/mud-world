package com.cailleach.mudengine.world.service.converter.todto;

import java.util.stream.Collectors;

import com.cailleach.mudengine.world.model.PlaceEntity;
import com.cailleach.mudengine.world.model.PlaceAttrEntity;
import com.cailleach.mudengine.world.model.PlaceExitEntity;
import com.cailleach.mudengine.world.rest.dto.Place;

public class PlaceConverter {
	
	private PlaceConverter() {
		
	}
	
	public static Place convert(PlaceEntity originalDbPlace) {
		
		Place result = new Place();
		
		if (originalDbPlace!=null) {
		
			result.setCode(originalDbPlace.getCode());
			
			result.setClassCode(originalDbPlace.getPlaceClass().getCode());
			
			result.setName(originalDbPlace.getName()!=null ? 
						originalDbPlace.getName() : 
						originalDbPlace.getPlaceClass().getName());
			
			result.setDescription(originalDbPlace.getDescription()!=null ?
						originalDbPlace.getDescription() :
						originalDbPlace.getPlaceClass().getDescription());
					
			result.setPlaceClass(
					PlaceClassConverter.convert(originalDbPlace.getPlaceClass())
							);

			// Map the database list with the exits in a map
			result.setExits(
				originalDbPlace.getExits().stream()
					.collect(Collectors.toMap(
							PlaceExitEntity::getDirection,
							PlaceExitConverter::convert
							))
					);
	
			// Map the database attributes
			result.setAttrs(
				originalDbPlace.getAttrs().stream()
					.collect(Collectors.toMap(
							PlaceAttrEntity::getCode, 
							PlaceAttrEntity::getValue))
					);
		}
		
		
		return result;
	}
}
