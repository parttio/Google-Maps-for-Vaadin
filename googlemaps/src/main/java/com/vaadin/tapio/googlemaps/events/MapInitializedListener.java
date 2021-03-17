package com.vaadin.tapio.googlemaps.events;

import com.vaadin.tapio.googlemaps.GoogleMap;

import java.io.Serializable;

/**
 * Interface for listening map initialization event.
 */
public interface MapInitializedListener extends Serializable {
    /**
     * Handle a MapInitializedListener.
     */
    void mapInitialized(GoogleMap source);
}
