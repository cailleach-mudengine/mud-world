package com.cailleach.mudengine.world.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.*;

import java.io.IOException;
import java.util.*;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cailleach.mudengine.world.model.MudPlace;
import com.cailleach.mudengine.world.model.MudPlaceClass;
import com.cailleach.mudengine.world.repository.PlaceClassRepository;
import com.cailleach.mudengine.world.repository.PlaceExitRepository;
import com.cailleach.mudengine.world.repository.PlaceRepository;
import com.cailleach.mudengine.world.rest.dto.Place;

import jakarta.annotation.PostConstruct;

@ExtendWith(MockitoExtension.class)
public class PlaceTests {
	
	private static final String HP_ATTR = "HP";
	private static final Integer HP_ATTR_CHANGED_VALUE = 5;
	private static final Integer HP_ATTR_ZEROES_VALUE = 0;
	private static final Integer HP_ATTR_ABOVE_VALUE = 5000;
	
	private static final String MAXHP_ATTR = "MAXHP";
	private static final Integer MAXHP_ATTR_CHANGED_VALUE = 530;
	private static final Integer MAXHP_ATTR_VALUE = 500;
	
	private static final String OTHER_ATTR = "OTH";
	private static final Integer OTHER_ATTR_CHANGED_VALUE = 8;

	private static final Integer CREATE_PLACE_ID = 99;
	private static final String CREATE_PLACE_CLASS = "TEST";
	private static final String CREATE_PLACE_EXIT_DIRECTION = "UP";
	private static final Integer CREATE_PLACE_EXIT_TARGET = 1;
	
	private static final Integer DELETE_DEMISED_PLACE_ID = 6;
	private static final String DELETE_DEMISED_PLACE_CLASS = "RUIN";

	@Mock
	private PlaceRepository mockRepository;
	
	@Mock
	private PlaceExitRepository mockExitRepository;
	
	@Mock
	private PlaceClassRepository mockClassRepository;
	
	@InjectMocks
	private PlaceServiceImpl service;
	
	
	@PostConstruct
	public void setup() throws IOException {
		
		given(mockClassRepository.findById(ArgumentMatchers.anyString()))
			.willAnswer(i -> {
				
				return Optional.of(
						PlaceTestData.loadMudPlaceClass(i.getArgument(0, String.class))
						);
			});
		
		given(mockRepository.findById(ArgumentMatchers.anyInt()))
			.willAnswer(i -> {
				
				return Optional.of(
						PlaceTestData.loadMudPlace(i.getArgument(0, Integer.class))
						);
			});
		
		given(mockRepository.save(ArgumentMatchers.any(MudPlace.class)))
		.willAnswer(i -> {
			
			MudPlace placeBeingSaved = i.getArgument(0, MudPlace.class);
			
			// Is it being created?
			if (placeBeingSaved.getCode()==null) {
				
				// Assign a random code
				placeBeingSaved.setCode(PlaceTests.CREATE_PLACE_ID);
				
			} 
			
			return placeBeingSaved;
		});
	}
	
	@Test
	public void testCreatePlace() throws IOException {
		
		Place createdPlace = 
				service.createPlace(
						PlaceTests.CREATE_PLACE_CLASS, 
						PlaceTests.CREATE_PLACE_EXIT_DIRECTION,
						PlaceTests.CREATE_PLACE_EXIT_TARGET);

		// Checking the placeClass
		assertEquals(PlaceTests.CREATE_PLACE_CLASS, createdPlace.getPlaceClass().getPlaceClassCode());

		//Check if exit was created and if it points to the right direction
		assertNotNull(createdPlace.getExits().get(PlaceTests.CREATE_PLACE_EXIT_DIRECTION));
		assertEquals(PlaceTests.CREATE_PLACE_EXIT_TARGET, createdPlace.getExits().get(PlaceTests.CREATE_PLACE_EXIT_DIRECTION).getTargetPlaceCode());

		// Checking if all attrs from mudclass are present
		checkAttrMap(createdPlace, PlaceTests.CREATE_PLACE_CLASS);
	}
	
	@Test
	public void testReadPlace() throws IOException {
		
		Place responsePlace = service.getPlace(PlaceTestData.READ_PLACE_ID);
		
		MudPlace dbPlace = PlaceTestData.loadMudPlace(PlaceTestData.READ_PLACE_ID);

		// Checking basic fields		
		assertEquals(responsePlace.getPlaceClass().getPlaceClassCode(), dbPlace.getPlaceClass().getCode());
		
		// Checking the exits
		assertTrue(dbPlace.getExits().stream()
				.allMatch(curExit -> 
					responsePlace.getExits().containsKey(curExit.getDirection())
					&&
					responsePlace.getExits().get(curExit.getDirection()).getTargetPlaceCode().equals(curExit.getTargetPlaceCode())
					)
				);
		
		// Checking the attributes
		checkAttrMap(responsePlace, responsePlace.getPlaceClass().getPlaceClassCode());
	}
	
	@Test
	public void testUpdateClass() throws IOException {
		
		Place originalPlace = service.getPlace(PlaceTestData.READ_PLACE_ID);
		
		// Change the placeClass
		originalPlace.setClassCode(PlaceTestData.UPDATE_CLASS_PLACE_CLASS);
		
		Place changedPlace = service.updatePlace(PlaceTestData.READ_PLACE_ID, originalPlace);
		
		// Check if the placeClass has changed accordingly
		assertEquals(PlaceTestData.UPDATE_CLASS_PLACE_CLASS, changedPlace.getClassCode());

		// Checking if all attributes exist in updated place
		checkAttrMap(changedPlace, PlaceTestData.UPDATE_CLASS_PLACE_CLASS);
	}
	
	@Test
	public void testUpdateHPAboveMAXHP() {
		
		Place originalPlace = service.getPlace(PlaceTestData.READ_PLACE_ID);
		
		// Update the HP
		originalPlace.getAttrs().put(PlaceTests.HP_ATTR, PlaceTests.HP_ATTR_ABOVE_VALUE);
		
		Place responsePlace = service.updatePlace(PlaceTestData.READ_PLACE_ID, originalPlace);
		
		// Checking the attributes
		// In this case, the first attribute (HP) need to have this value set to MAXHP
		assertEquals(MAXHP_ATTR_VALUE, responsePlace.getAttrs().get(PlaceTests.HP_ATTR));
	}

	@Test
	public void testUpdateHPBelowZero() {
		
		Place originalPlace = service.getPlace(PlaceTestData.READ_PLACE_ID);
		
		// Update the HP
		originalPlace.getAttrs().put(PlaceTests.HP_ATTR, PlaceTests.HP_ATTR_ZEROES_VALUE);
		
		service.updatePlace(PlaceTestData.READ_PLACE_ID, originalPlace);
		
		verify(mockRepository).deleteById(PlaceTestData.READ_PLACE_ID);
	}

	@Test
	public void testUpdateAttributes() {
		
		Place originalPlace = service.getPlace(PlaceTestData.READ_PLACE_ID);
		
		// Updating the attributes
		originalPlace.getAttrs().put(PlaceTests.HP_ATTR, PlaceTests.HP_ATTR_CHANGED_VALUE);
		originalPlace.getAttrs().put(PlaceTests.MAXHP_ATTR, PlaceTests.MAXHP_ATTR_CHANGED_VALUE);
		originalPlace.getAttrs().put(PlaceTests.OTHER_ATTR, PlaceTests.OTHER_ATTR_CHANGED_VALUE);
		
		Place responsePlace = service.updatePlace(PlaceTestData.READ_PLACE_ID, originalPlace);
		
		// Checking the attributes
		assertEquals(PlaceTests.HP_ATTR_CHANGED_VALUE, responsePlace.getAttrs().get(PlaceTests.HP_ATTR));
		assertEquals(PlaceTests.MAXHP_ATTR_CHANGED_VALUE, responsePlace.getAttrs().get(PlaceTests.MAXHP_ATTR));
		assertEquals(PlaceTests.OTHER_ATTR_CHANGED_VALUE, responsePlace.getAttrs().get(PlaceTests.OTHER_ATTR));
	}

	@Test
	public void testDeleteDemised() throws IOException {
		
		// Prepare the expected entity to be persisted
		MudPlace expectedDemisedPlace = PlaceTestData.loadMudPlace(PlaceTests.DELETE_DEMISED_PLACE_ID);
		expectedDemisedPlace.setPlaceClass(PlaceTestData.loadMudPlaceClass(DELETE_DEMISED_PLACE_CLASS));
		expectedDemisedPlace.setAttrs(new HashSet<>());
		
		service.destroyPlace(PlaceTests.DELETE_DEMISED_PLACE_ID);
		
		verify(mockRepository).save(expectedDemisedPlace);
	}
	
	@Test
	public void testDelete() {
		
		service.destroyPlace(PlaceTestData.READ_PLACE_ID);
		
		verify(mockRepository).deleteById(PlaceTestData.READ_PLACE_ID);
	}
	
	
	private void checkAttrMap(Place changedPlace, String expectedClassName) throws IOException {
		
		MudPlaceClass changedPlaceClass = PlaceTestData.loadMudPlaceClass(expectedClassName);
		
		// Checking if all attributes exist in updated place
		assertTrue(changedPlaceClass.getAttrs().stream()
				.allMatch(curClassAttr -> 
					changedPlace.getAttrs().containsKey(curClassAttr.getCode()) &&
					changedPlace.getAttrs().get(curClassAttr.getCode()).equals(curClassAttr.getValue())
				)
				);
	}
	
}
