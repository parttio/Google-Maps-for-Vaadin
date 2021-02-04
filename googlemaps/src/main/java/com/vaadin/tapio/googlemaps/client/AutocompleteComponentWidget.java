package com.vaadin.tapio.googlemaps.client;

import java.util.logging.Level;
import java.util.logging.Logger;

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
	private final static Logger logger = Logger.getLogger(AutocompleteComponentWidget.class.getName());

	protected AutocompleteOptions options;
	protected Autocomplete autoComplete;
    protected AutocompletePlaceChangeListener placeChangeListener = null;
    
    public AutocompleteComponentWidget() {

        // CSS class-name should not be v- prefixed
        setStyleName("w-autocomplete");
    }
    
    public void init() {

    	logger.log(Level.INFO, "AutocompleteComponentWidget intialization");
        Element element = getElement();

        options = AutocompleteOptions.newInstance();
        
        // Initial type is EXPLICIT_TYPES
//        options.setTypes( AutocompleteType.ESTABLISHMENT, AutocompleteType.GEOCODE );
//        options.setBounds(mapWidget.getBounds());
//        AutocompleteType[] types = new AutocompleteType[2];
//        types[0] = AutocompleteType.ESTABLISHMENT;
//        types[1] = AutocompleteType.GEOCODE;
//        
//        options.setTypes(types);
//    	logger.log(Level.INFO, "AutocompleteComponentWidget%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

        autoComplete = Autocomplete.newInstance(element, options);
    	logger.log(Level.INFO, "AutocompleteComponentWidget )))))))))))))))))))))))))))))");

        autoComplete.addPlaceChangeHandler(new PlaceChangeMapHandler() {
          public void onEvent(PlaceChangeMapEvent event) {

			if( placeChangeListener != null ) {
				
	            PlaceResult result = autoComplete.getPlace();

	            PlaceGeometry geomtry = result.getGeometry();
	            LatLng center = geomtry.getLocation();

	            logger.log(Level.INFO, "place changed center=" + center);
				                    
                placeChangeListener.placeChanged(new LocationInfo(result.getFormatted_Address(), new LatLon( center.getLatitude(), center.getLongitude() )));
			}
          }
        });
    }
	/**
	 * @see com.google.gwt.maps.client.placeslib.AutocompleteOptions#setBounds()
	 * @param bounds
	 */
	public void setBounds(LatLon boundsNE, LatLon boundsSW) {
        LatLng ne = LatLng.newInstance(boundsNE.getLat(), boundsNE.getLon());
        LatLng sw = LatLng.newInstance(boundsSW.getLat(), boundsSW.getLon());

        LatLngBounds bounds = LatLngBounds.newInstance(sw, ne);
        
        options.setBounds(bounds);
	}
	
	public void setPlaceChangeListener(AutocompletePlaceChangeListener placeChangeListener) {
		this.placeChangeListener = placeChangeListener;
	}
	
	/**
	 * @see com.google.gwt.maps.client.placeslib.AutocompleteOptions#setTypes()
	 * @param bounds
	 */
	public void setTypes(AutocompleteType... types) {
		this.options.setTypes(types);
	}
}