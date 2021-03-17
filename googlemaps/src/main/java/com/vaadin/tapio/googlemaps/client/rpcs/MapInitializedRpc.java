package com.vaadin.tapio.googlemaps.client.rpcs;

import com.vaadin.shared.communication.ServerRpc;

/**
 * An RPC from client to server that is called when the map has been initialized.
 */
public interface MapInitializedRpc extends ServerRpc {
    void mapInitialized();
}
