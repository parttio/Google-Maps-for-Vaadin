package com.vaadin.tapio.googlemaps.client.events;

import java.io.Serializable;

import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.LocationInfo;

public interface AutocompletePlaceChangeListener extends Serializable {
	void placeChanged(LocationInfo locationInfo);
}
