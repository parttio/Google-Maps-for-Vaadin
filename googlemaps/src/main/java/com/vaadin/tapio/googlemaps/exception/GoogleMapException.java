package com.vaadin.tapio.googlemaps.exception;

/**
 * Basic class for Vaadin Google Map add-on exception
 * @author panpasy
 */
public class GoogleMapException extends Exception{

	private static final long serialVersionUID = 7439261574749360082L;
	
    public GoogleMapException() {
        super();
    }

    public GoogleMapException(String message) {
        super(message);
    }

}
