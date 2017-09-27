package com.vaadin.tapio.googlemaps.client;

/**
 * The shared state of the Google Map's autocomplete component.
 * @author panpasy
 */
public class AutocompleteComponentState extends SharedState {
    // State can have both public variable and bean properties
	public LocationInfo locationInfo = new LocationInfo(); 
	public LatLon boundsNE = new LatLon();
	public LatLon boundsSW = new LatLon();

}