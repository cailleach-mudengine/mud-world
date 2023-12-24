package com.cailleach.mudengine.world.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cailleach.mudengine.world.model.PlaceEntity;
import com.cailleach.mudengine.world.model.PlaceClassEntity;
import com.cailleach.mudengine.world.repository.PlaceClassRepository;
import com.cailleach.mudengine.world.repository.PlaceExitRepository;
import com.cailleach.mudengine.world.repository.PlaceRepository;
import com.cailleach.mudengine.world.rest.dto.Place;

@ExtendWith(MockitoExtension.class)
class PlaceServiceTests {
	
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
	
	
	@BeforeEach
	public void setup() throws IOException {
		
		lenient().when(mockClassRepository.findById(ArgumentMatchers.anyString()))
			.thenAnswer(i -> {
				
				return Optional.of(
						PlaceTestData.loadMudPlaceClass(i.getArgument(0, String.class))
						);
			});
		
		lenient().when(mockRepository.findById(ArgumentMatchers.anyInt()))
			.thenAnswer(i -> {
				
				return Optional.of(
						PlaceTestData.loadMudPlace(i.getArgument(0, Integer.class))
						);
			});
		
		lenient().when(mockRepository.save(ArgumentMatchers.any(PlaceEntity.class)))
			.thenAnswer(i -> {
			
			PlaceEntity placeBeingSaved = i.getArgument(0, PlaceEntity.class);
			
			// Is it being created?
			if (placeBeingSaved.getCode()==null) {
				
				// Assign a random code
				placeBeingSaved.setCode(PlaceServiceTests.CREATE_PLACE_ID);
				
			} 
			
			return placeBeingSaved;
		});
	}
	
	@Test
	void testCreatePlace() throws IOException {
		
		Place createdPlace = 
				service.createPlace(
						PlaceServiceTests.CREATE_PLACE_CLASS, 
						PlaceServiceTests.CREATE_PLACE_EXIT_DIRECTION,
						PlaceServiceTests.CREATE_PLACE_EXIT_TARGET);

		// Checking the placeClass
		assertEquals(PlaceServiceTests.CREATE_PLACE_CLASS, createdPlace.getPlaceClass().getPlaceClassCode());

		//Check if exit was created and if it points to the right direction
		assertNotNull(createdPlace.getExits().get(PlaceServiceTests.CREATE_PLACE_EXIT_DIRECTION));
		assertEquals(PlaceServiceTests.CREATE_PLACE_EXIT_TARGET, createdPlace.getExits().get(PlaceServiceTests.CREATE_PLACE_EXIT_DIRECTION).getTargetPlaceCode());

		// Checking if all attrs from mudclass are present
		checkAttrMap(createdPlace, PlaceServiceTests.CREATE_PLACE_CLASS);
	}
	
	@Test
	void testReadPlace() throws IOException {
		
		Place responsePlace = service.getPlace(PlaceTestData.READ_PLACE_ID);
		
		PlaceEntity dbPlace = PlaceTestData.loadMudPlace(PlaceTestData.READ_PLACE_ID);

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
	void testUpdateClass() throws IOException {
		
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
	void testUpdateHPAboveMAXHP() {
		
		Place originalPlace = service.getPlace(PlaceTestData.READ_PLACE_ID);
		
		// Update the HP
		originalPlace.getAttrs().put(PlaceServiceTests.HP_ATTR, PlaceServiceTests.HP_ATTR_ABOVE_VALUE);
		
		Place responsePlace = service.updatePlace(PlaceTestData.READ_PLACE_ID, originalPlace);
		
		// Checking the attributes
		// In this case, the first attribute (HP) need to have this value set to MAXHP
		assertEquals(MAXHP_ATTR_VALUE, responsePlace.getAttrs().get(PlaceServiceTests.HP_ATTR));
	}

	@Test
	void testUpdateHPBelowZero() {
		
		Place originalPlace = service.getPlace(PlaceTestData.READ_PLACE_ID);
		
		// Update the HP
		originalPlace.getAttrs().put(PlaceServiceTests.HP_ATTR, PlaceServiceTests.HP_ATTR_ZEROES_VALUE);
		
		service.updatePlace(PlaceTestData.READ_PLACE_ID, originalPlace);
		
		verify(mockRepository).deleteById(PlaceTestData.READ_PLACE_ID);
	}

	@Test
	void testUpdateAttributes() {
		
		Place originalPlace = service.getPlace(PlaceTestData.READ_PLACE_ID);
		
		// Updating the attributes
		originalPlace.getAttrs().put(PlaceServiceTests.HP_ATTR, PlaceServiceTests.HP_ATTR_CHANGED_VALUE);
		originalPlace.getAttrs().put(PlaceServiceTests.MAXHP_ATTR, PlaceServiceTests.MAXHP_ATTR_CHANGED_VALUE);
		originalPlace.getAttrs().put(PlaceServiceTests.OTHER_ATTR, PlaceServiceTests.OTHER_ATTR_CHANGED_VALUE);
		
		Place responsePlace = service.updatePlace(PlaceTestData.READ_PLACE_ID, originalPlace);
		
		// Checking the attributes
		assertEquals(PlaceServiceTests.HP_ATTR_CHANGED_VALUE, responsePlace.getAttrs().get(PlaceServiceTests.HP_ATTR));
		assertEquals(PlaceServiceTests.MAXHP_ATTR_CHANGED_VALUE, responsePlace.getAttrs().get(PlaceServiceTests.MAXHP_ATTR));
		assertEquals(PlaceServiceTests.OTHER_ATTR_CHANGED_VALUE, responsePlace.getAttrs().get(PlaceServiceTests.OTHER_ATTR));
	}

	@Test
	void testDeleteDemised() throws IOException {
		
		// Prepare the expected entity to be persisted
		PlaceEntity expectedDemisedPlace = PlaceTestData.loadMudPlace(PlaceServiceTests.DELETE_DEMISED_PLACE_ID);
		expectedDemisedPlace.setPlaceClass(PlaceTestData.loadMudPlaceClass(DELETE_DEMISED_PLACE_CLASS));
		expectedDemisedPlace.setAttrs(new HashSet<>());
		
		service.destroyPlace(PlaceServiceTests.DELETE_DEMISED_PLACE_ID);
		
		verify(mockRepository).save(expectedDemisedPlace);
	}
	
	@Test
	void testDelete() {
		
		service.destroyPlace(PlaceTestData.READ_PLACE_ID);
		
		verify(mockRepository).deleteById(PlaceTestData.READ_PLACE_ID);
	}
	
	
	private void checkAttrMap(Place changedPlace, String expectedClassName) throws IOException {
		
		PlaceClassEntity changedPlaceClass = PlaceTestData.loadMudPlaceClass(expectedClassName);
		
		// Checking if all attributes exist in updated place
		assertTrue(changedPlaceClass.getAttrs().stream()
				.allMatch(curClassAttr -> 
					changedPlace.getAttrs().containsKey(curClassAttr.getCode()) &&
					changedPlace.getAttrs().get(curClassAttr.getCode()).equals(curClassAttr.getValue())
				)
				);
	}
	
}
