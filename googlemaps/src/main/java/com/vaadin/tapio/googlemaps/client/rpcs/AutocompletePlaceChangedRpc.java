package com.vaadin.tapio.googlemaps.client.rpcs;

import com.vaadin.shared.communication.ServerRpc;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.LocationInfo;

/**
 * An RPC from client to server that is called when the place changed in the 
 * auto complete text box
 * 
 * @author panpasy
 */
public interface AutocompletePlaceChangedRpc extends ServerRpc {
    void placeChanged(LocationInfo position);
}
