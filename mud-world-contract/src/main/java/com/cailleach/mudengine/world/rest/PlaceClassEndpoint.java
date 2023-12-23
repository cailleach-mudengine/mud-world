package com.cailleach.mudengine.world.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cailleach.mudengine.world.rest.dto.PlaceClass;

@RequestMapping("/place/class")
public interface PlaceClassEndpoint {

	@GetMapping(value="/{placeClass}")
	PlaceClass getPlaceClass(@PathVariable("placeClass") String placeClass);

}