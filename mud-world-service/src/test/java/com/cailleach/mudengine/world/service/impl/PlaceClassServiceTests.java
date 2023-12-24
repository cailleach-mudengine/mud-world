package com.cailleach.mudengine.world.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cailleach.mudengine.world.model.PlaceClassEntity;
import com.cailleach.mudengine.world.repository.PlaceClassRepository;
import com.cailleach.mudengine.world.rest.dto.PlaceClass;

@ExtendWith(MockitoExtension.class)
class PlaceClassServiceTests {

	@Mock
	private PlaceClassRepository mockClassRepository;

	
	@InjectMocks
	private PlaceClassServiceImpl service;
	
	
	@BeforeEach
	public void setup() throws IOException {
		
		given(mockClassRepository.findById(ArgumentMatchers.anyString()))
			.willAnswer(i -> {
				
				return Optional.of(
						PlaceTestData.loadMudPlaceClass(i.getArgument(0, String.class))
						);
			});
	}
	
	@Test
	void testGetPlaceClass() throws IOException {
		
		PlaceClassEntity dbClass = PlaceTestData.loadMudPlaceClass(PlaceTestData.UPDATE_CLASS_PLACE_CLASS);
		
		PlaceClass responseClass = 
				service.getPlaceClass(PlaceTestData.UPDATE_CLASS_PLACE_CLASS);
		
		// Check the fields
		assertEquals(dbClass.getCode(), responseClass.getPlaceClassCode());
		
		assertEquals(dbClass.getDescription(), responseClass.getDescription());
		assertEquals(dbClass.getName(), responseClass.getName());
		assertEquals(dbClass.getSizeCapacity(), responseClass.getSizeCapacity());
		assertEquals(dbClass.getWeightCapacity(), responseClass.getWeightCapacity());
		assertEquals(dbClass.getBuildCost(), responseClass.getBuildCost());
		assertEquals(dbClass.getBuildEffort(), responseClass.getBuildEffort());
		assertEquals(dbClass.getDemisedPlaceClassCode(), responseClass.getDemisePlaceClassCode());
		assertEquals(dbClass.getParentClassCode(), responseClass.getParentClassCode());
		
		assertTrue(dbClass.getAttrs().stream()
				.allMatch(curAttr ->
					responseClass.getAttrs().containsKey(curAttr.getCode())
					&&
					responseClass.getAttrs().get(curAttr.getCode()).equals(curAttr.getValue())
				)
				);
	}
}
