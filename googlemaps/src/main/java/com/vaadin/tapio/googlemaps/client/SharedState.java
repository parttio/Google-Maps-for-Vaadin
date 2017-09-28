package com.vaadin.tapio.googlemaps.client;

import com.vaadin.shared.ui.AbstractComponentContainerState;

/**
 * The class contains the shared states for all Google Map components,
 * such as apiKey, clientId, language, apiUrl
 * @author panpasy
 */
public abstract class SharedState extends AbstractComponentContainerState {
    public String apiKey = null;
    public String clientId = null;

    // defaults to the language setting of the browser
    public String language = null;
    public String apiUrl = null;
}
