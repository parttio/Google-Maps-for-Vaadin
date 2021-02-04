package com.vaadin.tapio.googlemaps;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.maps.client.placeslib.AutocompleteType;
import com.vaadin.tapio.googlemaps.client.AutocompleteComponentState;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.LocationInfo;
import com.vaadin.tapio.googlemaps.client.events.AutocompletePlaceChangeListener;
import com.vaadin.tapio.googlemaps.client.rpcs.AutocompletePlaceChangedRpc;
import com.vaadin.tapio.googlemaps.exception.GoogleMapException;
import com.vaadin.tapio.googlemaps.exception.MissingApiKeyException;

/**
 * The class representing Google Map's autocomplete component.
 * @author panpasy
 */
public class AutocompleteComponent extends com.vaadin.ui.AbstractComponent { 
	private static final long serialVersionUID = 3798086745196528037L;
	private GoogleMap googleMap;
    private final List<AutocompletePlaceChangeListener> autocompletePlaceChangeListeners = new ArrayList<>();

    // To process events from the client, we implement ServerRpc
    private final AutocompletePlaceChangedRpc placeChangedRpc = new AutocompletePlaceChangedRpc() {

		private static final long serialVersionUID = -4983298073186697156L;

		@Override
        public void placeChanged( LocationInfo locationInfo ) {
        	getState().locationInfo = locationInfo;
            for (AutocompletePlaceChangeListener listener : autocompletePlaceChangeListeners) {
                listener.placeChanged( locationInfo );
            }
        }
    };
    
    /**
     * Constructor
     * 
     * @param googleMap google map used to initial the auto-complete component, API key is required
     * @throws GoogleMapException
     */
    public AutocompleteComponent(GoogleMap googleMap) throws GoogleMapException {
    	this(googleMap.getState().apiKey, googleMap.getState().clientId, googleMap.getState().language);
    	this.googleMap = googleMap;
//    	setBounds(this.googleMap.getState().boundsNE, this.googleMap.getState().boundsSW );
    }
    
    public AutocompleteComponent(String apiKey, String clientId, String language) throws GoogleMapException {    	
        // Need an API key to use auto-complete component
    	if (apiKey != null && !apiKey.isEmpty()) {
            getState().apiKey = apiKey;
        } else {
        	throw new MissingApiKeyException();
        }
        
        if (clientId != null && !clientId.isEmpty()) {
            getState().clientId = clientId;
        }

        if (language != null && !language.isEmpty()) {
            getState().language = language;
        }
    	
        // To receive events from the client, we register ServerRpc
        registerRpc(placeChangedRpc);
    }

    // We must override getState() to cast the state to MyComponentState
    @Override
    protected AutocompleteComponentState getState() {
        return (AutocompleteComponentState) super.getState();
    }
    
    /**
     * @return the current place lat & lng
     */
    public LocationInfo getLocationInfo() {
        return getState().locationInfo;
    }
    
    /**
     * Set bound
     * @param boundsSW
     * @param boundsNE
     */
    public void setBounds(LatLon boundsSW, LatLon boundsNE) {
    	getState().boundsSW = boundsSW;
    	getState().boundsNE = boundsNE;
    }
    
    /**
     * Set types, ESTABLISHMENT&GEOCODE by default if not set the types
     * @param types
     */
    public void setTypes( List<AutocompleteType> types ) {
    	getState().types = types;
    }
    
    /**
     * Adds an AutocompletePlaceChangeListener to the autocomplete component.
     *
     * @param listener The listener to add.
     */
    public void addAutocompletePlaceChangeListener(AutocompletePlaceChangeListener listener) {
    	autocompletePlaceChangeListeners.add(listener);
    }

    /**
     * Removes a AutocompletePlaceChangeListener from the autocomplete component.
     *
     * @param listener The listener to remove.
     */
    public void removeAutocompletePlaceChangeListener(AutocompletePlaceChangeListener listener) {
    	autocompletePlaceChangeListeners.remove(listener);
    }
}
