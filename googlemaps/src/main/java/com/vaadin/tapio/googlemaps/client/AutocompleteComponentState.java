package com.vaadin.tapio.googlemaps.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.maps.client.placeslib.AutocompleteType;

/**
 * The shared state of the Google Map's autocomplete component.
 * @author panpasy
 */
public class AutocompleteComponentState extends SharedState {
    // State can have both public variable and bean properties
	public LocationInfo locationInfo = new LocationInfo(); 
	public LatLon boundsNE = new LatLon();
	public LatLon boundsSW = new LatLon();
	
	public List<AutocompleteType> types = new ArrayList<>();
	
	public AutocompleteComponentState() {
		super();
		types.add(AutocompleteType.ESTABLISHMENT);
		types.add(AutocompleteType.GEOCODE);
	}
}