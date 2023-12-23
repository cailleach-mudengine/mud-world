package com.cailleach.mudengine.world.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.cailleach.mudengine.common.exception.EntityNotFoundException;
import com.cailleach.mudengine.world.repository.PlaceClassRepository;
import com.cailleach.mudengine.world.rest.dto.PlaceClass;
import com.cailleach.mudengine.world.service.PlaceClassService;
import com.cailleach.mudengine.world.service.converter.todto.PlaceClassConverter;
import com.cailleach.mudengine.world.util.LocalizedMessages;

@Service
public class PlaceClassServiceImpl implements PlaceClassService {

	@Autowired
	private PlaceClassRepository repository;
	
	@Override
	public PlaceClass getPlaceClass(String placeClass) {
		
		return repository.findById(placeClass)
				.map(PlaceClassConverter::convert)
				.orElseThrow(() -> new EntityNotFoundException(LocalizedMessages.PLACE_CLASS_NOT_FOUND, placeClass));
	}
}
