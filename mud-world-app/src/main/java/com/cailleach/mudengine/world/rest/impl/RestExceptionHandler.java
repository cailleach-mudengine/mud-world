package com.cailleach.mudengine.world.rest.impl;

import java.time.OffsetDateTime;
import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cailleach.mudengine.common.exception.EntityNotFoundException;
import com.cailleach.mudengine.common.security.Session;
import com.cailleach.mudengine.common.utils.ApiErrorMessage;
import com.cailleach.mudengine.common.utils.CommonConstants;
import com.cailleach.mudengine.common.utils.LocalizedMessages;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(EntityNotFoundException.class)
	public ApiErrorMessage handleNotFoundExceptions(EntityNotFoundException ex) {
		
		String localizedMessage = LocalizedMessages.getMessage(getLocale(), ex.getMessageKey(), ex.getMessageParams());

		ApiErrorMessage result = ApiErrorMessage.builder()
				.status(HttpStatus.BAD_REQUEST.getReasonPhrase())
				.error(HttpStatus.BAD_REQUEST.toString())
				.message(localizedMessage)
				.timestamp(OffsetDateTime.now())
				.build();


		return result;
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(RuntimeException.class)
	public ApiErrorMessage handleAnyExceptions(RuntimeException ex) {

		log.error("Error na chamada", ex);

		ApiErrorMessage result = ApiErrorMessage.builder()
				.status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
				.error(HttpStatus.INTERNAL_SERVER_ERROR.toString())
				.message(ex.getMessage())
				.timestamp(OffsetDateTime.now())
				.build();

		return result;
	}
	
	/**
	 * Returns the locale to be used.
	 * If the call is authenticated, that will be the player's locale.
	 * If the call is anonymous, or any other error arises returns default locale
	 * @return
	 */
	private static Locale getLocale() {
		
		try {
			return Locale.forLanguageTag(
					((Session)SecurityContextHolder.getContext().getAuthentication().getDetails())
						.getLocale()
					);
			
		} catch(RuntimeException e) {
			return Locale.forLanguageTag(CommonConstants.DEFAULT_LOCALE);
		}
	}
	
}
