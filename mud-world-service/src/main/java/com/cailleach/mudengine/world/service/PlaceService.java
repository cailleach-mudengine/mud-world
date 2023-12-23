package com.cailleach.mudengine.world.service;

import com.cailleach.mudengine.world.rest.dto.Place;

public interface PlaceService {

	Place getPlace(Integer placeId);

	Place updatePlace(Integer placeId, Place requestPlace);

	void destroyPlace(Integer placeId);

	Place createPlace(String placeClassCode, String direction, Integer targetPlaceCode);

}