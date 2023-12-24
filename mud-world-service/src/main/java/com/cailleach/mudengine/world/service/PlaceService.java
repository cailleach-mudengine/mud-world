package com.cailleach.mudengine.world.service;

import com.cailleach.mudengine.world.rest.dto.Place;

public interface PlaceService {

	Place getPlace(Long placeId);

	Place updatePlace(Long placeId, Place requestPlace);

	void destroyPlace(Long placeId);

	Place createPlace(String placeClassCode, String direction, Long targetPlaceCode);

}