package com.vaadin.tapio.googlemaps.exception;

/**
 * Missing Google Map api-key exception
 * @author panpasy
 */
public class MissingApiKeyException extends GoogleMapException{
	
	public MissingApiKeyException() {
		super("You need to specify a  a google map api key");
	}
	
}
