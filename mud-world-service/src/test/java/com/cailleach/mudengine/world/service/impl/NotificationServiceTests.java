package com.cailleach.mudengine.world.service.impl;

import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;

import com.cailleach.mudengine.common.utils.NotificationMessage;
import com.cailleach.mudengine.common.utils.NotificationMessage.EnumNotificationEvent;
import com.cailleach.mudengine.world.model.PlaceEntity;
import com.cailleach.mudengine.world.model.PlaceExitEntity;
import com.cailleach.mudengine.world.model.pk.PlaceExitEntityPK;

import jakarta.jms.Destination;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTests {

	
	private static final String NEW_EXIT_DIRECTION = "UP";
	private static final Long NEW_EXIT_TARGET = 2L;
	
	@Mock
	private JmsTemplate jmsTemplate;
	
	@InjectMocks
	private NotificationService service;
	
	@Test
	void testPlaceDestroy() throws IOException {
		
		List<NotificationMessage> notifications =
				service.handlePlaceDestroy(
						PlaceTestData.loadMudPlace(PlaceTestData.READ_PLACE_ID)
						);

		service.dispatchNotifications(notifications);
		
		//  Check if correct notification was sent
		NotificationMessage placeNotification = NotificationMessage.builder()
				.entity(NotificationMessage.EnumEntity.PLACE)
				.entityId(PlaceTestData.READ_PLACE_ID.longValue())
				.event(EnumNotificationEvent.PLACE_DESTROY)
			.build();
		
		verify(jmsTemplate).convertAndSend((Destination)ArgumentMatchers.any(), 
				ArgumentMatchers.eq(placeNotification), 
				ArgumentMatchers.any());
		
	}
	
	@Test
	void testPlaceClassChanges() throws IOException {
		
		PlaceEntity afterPlace = PlaceTestData.loadMudPlace(PlaceTestData.READ_PLACE_ID);
		
		afterPlace.setPlaceClass(
				PlaceTestData.loadMudPlaceClass(PlaceTestData.UPDATE_CLASS_PLACE_CLASS)
				);
		
		List<NotificationMessage> notifications =
				service.handlePlaceChange(
						PlaceTestData.loadMudPlace(PlaceTestData.READ_PLACE_ID),
						afterPlace
						);

		service.dispatchNotifications(notifications);
		
		//  Check if correct notification was sent
		NotificationMessage placeClassNotification = NotificationMessage.builder()
				.entity(NotificationMessage.EnumEntity.PLACE)
				.entityId(PlaceTestData.READ_PLACE_ID.longValue())
				.event(EnumNotificationEvent.PLACE_CLASS_CHANGE)
			.build();
		
		verify(jmsTemplate).convertAndSend((Destination)ArgumentMatchers.any(), 
				ArgumentMatchers.eq(placeClassNotification), 
				ArgumentMatchers.any());		
	}
	
	@Test
	void testNewlyCreatedExits() throws IOException {
		
		PlaceEntity afterPlace = PlaceTestData.loadMudPlace(PlaceTestData.READ_PLACE_ID);

		// Creating the new exit
		PlaceExitEntity newExit = new PlaceExitEntity();
		newExit.setPk(new PlaceExitEntityPK());
		newExit.getPk().setDirection(NotificationServiceTests.NEW_EXIT_DIRECTION);
		newExit.setTargetPlaceCode(NEW_EXIT_TARGET);
		
		afterPlace.getExits().add(newExit);
		
		
		List<NotificationMessage> notifications =
				service.handlePlaceChange(
						PlaceTestData.loadMudPlace(PlaceTestData.READ_PLACE_ID),
						afterPlace
						);
		
		service.dispatchNotifications(notifications);
		
		//  Check if correct notification was sent
		NotificationMessage placeExitNotification = NotificationMessage.builder()
				.entity(NotificationMessage.EnumEntity.PLACE)
				.entityId(PlaceTestData.READ_PLACE_ID.longValue())
				.event(EnumNotificationEvent.PLACE_EXIT_CREATE)
			.build();
		
		NotificationMessage placeExit2Notification = NotificationMessage.builder()
				.entity(NotificationMessage.EnumEntity.PLACE)
				.entityId(NEW_EXIT_TARGET.longValue())
				.event(EnumNotificationEvent.PLACE_EXIT_CREATE)
			.build();
		
		verify(jmsTemplate).convertAndSend((Destination)ArgumentMatchers.any(), 
				ArgumentMatchers.eq(placeExitNotification), 
				ArgumentMatchers.any());
		
		verify(jmsTemplate).convertAndSend((Destination)ArgumentMatchers.any(), 
				ArgumentMatchers.eq(placeExit2Notification), 
				ArgumentMatchers.any());
	}
	
	@Test
	void testCloseExit() throws IOException {
		
		PlaceEntity afterPlace = PlaceTestData.loadMudPlace(PlaceTestData.READ_PLACE_ID);
		afterPlace.getExits().iterator().next().setOpened(false);

		List<NotificationMessage> notifications =
				service.handlePlaceChange(
						PlaceTestData.loadMudPlace(PlaceTestData.READ_PLACE_ID),
						afterPlace
						);
		
		service.dispatchNotifications(notifications);

		//  Check if correct notification was sent
		NotificationMessage placeExitNotification = NotificationMessage.builder()
				.entity(NotificationMessage.EnumEntity.PLACE)
				.entityId(PlaceTestData.READ_PLACE_ID.longValue())
				.event(EnumNotificationEvent.PLACE_EXIT_CLOSE)
			.build();
		
		verify(jmsTemplate).convertAndSend((Destination)ArgumentMatchers.any(), 
				ArgumentMatchers.eq(placeExitNotification), 
				ArgumentMatchers.any());		
		
	}
	
	@Test
	void testLockExit() throws IOException {
		
		PlaceEntity afterPlace = PlaceTestData.loadMudPlace(PlaceTestData.READ_PLACE_ID);
		afterPlace.getExits().iterator().next().setLocked(true);

		List<NotificationMessage> notifications =
				service.handlePlaceChange(
						PlaceTestData.loadMudPlace(PlaceTestData.READ_PLACE_ID),
						afterPlace
						);
		
		service.dispatchNotifications(notifications);

		//  Check if correct notification was sent
		NotificationMessage placeExitNotification = NotificationMessage.builder()
				.entity(NotificationMessage.EnumEntity.PLACE)
				.entityId(PlaceTestData.READ_PLACE_ID.longValue())
				.event(EnumNotificationEvent.PLACE_EXIT_LOCK)
			.build();
		
		verify(jmsTemplate).convertAndSend((Destination)ArgumentMatchers.any(), 
				ArgumentMatchers.eq(placeExitNotification), 
				ArgumentMatchers.any());		
		
	}
	
	
	@Test
	void testOpenExit() throws IOException {
		
		PlaceEntity afterPlace = PlaceTestData.loadMudPlace(PlaceTestData.TARGET_PLACE_ID);
		afterPlace.getExits().iterator().next().setOpened(true);

		List<NotificationMessage> notifications =
				service.handlePlaceChange(
						PlaceTestData.loadMudPlace(PlaceTestData.TARGET_PLACE_ID),
						afterPlace
						);
		
		service.dispatchNotifications(notifications);

		//  Check if correct notification was sent
		NotificationMessage placeExitNotification = NotificationMessage.builder()
				.entity(NotificationMessage.EnumEntity.PLACE)
				.entityId(PlaceTestData.TARGET_PLACE_ID.longValue())
				.event(EnumNotificationEvent.PLACE_EXIT_OPEN)
			.build();
		
		verify(jmsTemplate).convertAndSend((Destination)ArgumentMatchers.any(), 
				ArgumentMatchers.eq(placeExitNotification), 
				ArgumentMatchers.any());		
		
	}
	
	@Test
	void testUnlockExit() throws IOException {
		
		PlaceEntity afterPlace = PlaceTestData.loadMudPlace(PlaceTestData.TARGET_PLACE_ID);
		afterPlace.getExits().iterator().next().setLocked(false);

		List<NotificationMessage> notifications =
				service.handlePlaceChange(
						PlaceTestData.loadMudPlace(PlaceTestData.TARGET_PLACE_ID),
						afterPlace
						);
		
		service.dispatchNotifications(notifications);

		//  Check if correct notification was sent
		NotificationMessage placeExitNotification = NotificationMessage.builder()
				.entity(NotificationMessage.EnumEntity.PLACE)
				.entityId(PlaceTestData.TARGET_PLACE_ID.longValue())
				.event(EnumNotificationEvent.PLACE_EXIT_UNLOCK)
			.build();
		
		verify(jmsTemplate).convertAndSend((Destination)ArgumentMatchers.any(), 
				ArgumentMatchers.eq(placeExitNotification), 
				ArgumentMatchers.any());		
		
	}

}
