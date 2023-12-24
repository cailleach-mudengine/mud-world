package com.cailleach.mudengine.world.rest.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cailleach.mudengine.world.rest.PlaceEndpoint;
import com.cailleach.mudengine.world.rest.dto.Place;
import com.cailleach.mudengine.world.service.PlaceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PlaceController implements PlaceEndpoint {
	
	private final PlaceService service;
	
	@Override
	public Place getPlace(
			@PathVariable Integer placeId) {

		return service.getPlace(placeId);
	}

	
	@Override
	public Place updatePlace(@PathVariable Integer placeId, @RequestBody Place requestPlace) {
		
		return service.updatePlace(placeId, requestPlace);
	}

	@Override
	public void destroyPlace(@PathVariable Integer placeId) {
		
		service.destroyPlace(placeId);
	}


	@Override
	public ResponseEntity<Place> createPlace(String placeClassCode, String direction, Integer targetPlaceCode) {
		
		Place response = service.createPlace(placeClassCode, direction, targetPlaceCode);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
