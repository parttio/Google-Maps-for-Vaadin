package com.vaadin.tapio.googlemaps.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.base.LatLngBounds;
import com.google.gwt.maps.client.events.place.PlaceChangeMapEvent;
import com.google.gwt.maps.client.events.place.PlaceChangeMapHandler;
import com.google.gwt.maps.client.placeslib.Autocomplete;
import com.google.gwt.maps.client.placeslib.AutocompleteOptions;
import com.google.gwt.maps.client.placeslib.AutocompleteType;
import com.google.gwt.maps.client.placeslib.PlaceGeometry;
import com.google.gwt.maps.client.placeslib.PlaceResult;
import com.google.gwt.user.client.ui.TextBox;
import com.vaadin.tapio.googlemaps.client.events.AutocompletePlaceChangeListener;

/**
 * Google Map's autocomplete component GWT widget.
 * @author panpasy
 */
public class AutocompleteComponentWidget extends TextBox {

	protected AutocompleteOptions options;
	protected Autocomplete autoComplete;
    protected AutocompletePlaceChangeListener placeChangeListener = null;
    
    public AutocompleteComponentWidget() {

        // CSS class-name should not be v- prefixed
        setStyleName("w-autocomplete");
    }
    
    public void init() {

        Element element = getElement();

        AutocompleteType[] types = new AutocompleteType[2];
        types[0] = AutocompleteType.ESTABLISHMENT;
        types[1] = AutocompleteType.GEOCODE;

        options = AutocompleteOptions.newInstance();
        options.setTypes(types);
//        options.setBounds(mapWidget.getBounds());

        autoComplete = Autocomplete.newInstance(element, options);

        autoComplete.addPlaceChangeHandler(new PlaceChangeMapHandler() {
          public void onEvent(PlaceChangeMapEvent event) {

			if( placeChangeListener != null ) {
				
	            PlaceResult result = autoComplete.getPlace();

	            PlaceGeometry geomtry = result.getGeometry();
	            LatLng center = geomtry.getLocation();

	            GWT.log("place changed center=" + center);
				                    
                placeChangeListener.placeChanged(new LocationInfo(result.getFormatted_Address(), new LatLon( center.getLatitude(), center.getLongitude() )));
			}
          }
        });
    }
	/**
	 * @see com.google.gwt.maps.client.placeslib.AutocompleteOptions#setBounds()
	 * @param bounds
	 */
	public void setBounds(LatLngBounds bounds) {
		options.setBounds(bounds);
	}
	
	public void setPlaceChangeListener(AutocompletePlaceChangeListener placeChangeListener) {
		this.placeChangeListener = placeChangeListener;
	}
}