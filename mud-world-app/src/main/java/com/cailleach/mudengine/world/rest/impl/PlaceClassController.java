package com.cailleach.mudengine.world.rest.impl;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;

import com.cailleach.mudengine.world.rest.PlaceClassEndpoint;
import com.cailleach.mudengine.world.rest.dto.PlaceClass;
import com.cailleach.mudengine.world.service.PlaceClassService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PlaceClassController implements PlaceClassEndpoint {

	private PlaceClassService service;
	
	@Override
	public PlaceClass getPlaceClass(@PathVariable String placeClass) {
		
		return service.getPlaceClass(placeClass);
	}
}
