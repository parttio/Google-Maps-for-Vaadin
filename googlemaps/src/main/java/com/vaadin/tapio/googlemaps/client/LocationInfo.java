package com.vaadin.tapio.googlemaps.client;

import java.io.Serializable;

/**
 * Class representing the information of a location,
 * contains its address and lat&lng position.
 * @author panpasy
 */
public class LocationInfo implements Serializable {
	private static final long serialVersionUID = 6954292211597639331L;
	private LatLon position = new LatLon(0, 0);
    private String address = "";
    
    public LocationInfo() {
	}
    
    public LocationInfo( String address, LatLon position ) {
		super();
		this.position = position;
		this.address = address;
	}

	public String getAddress() {
		return address;
	}
    
    public LatLon getPosition() {
		return position;
	}
    
    public void setAddress(String address) {
		this.address = address;
	}
    
    public void setPosition(LatLon position) {
		this.position = position;
	}
}
