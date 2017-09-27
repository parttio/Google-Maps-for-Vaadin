package com.vaadin.tapio.googlemaps.client;

import java.util.ArrayList;

import com.google.gwt.ajaxloader.client.AjaxLoader;
import com.google.gwt.maps.client.LoadApi;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.LoadApi.LoadLibrary;
import com.google.gwt.maps.client.base.LatLng;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.client.ui.layout.ElementResizeEvent;
import com.vaadin.client.ui.layout.ElementResizeListener;
import com.vaadin.shared.ui.Connect;
import com.vaadin.tapio.googlemaps.AutocompleteComponent;
import com.vaadin.tapio.googlemaps.client.events.AutocompletePlaceChangeListener;
import com.vaadin.tapio.googlemaps.client.rpcs.AutocompletePlaceChangedRpc;

// Connector binds client-side widget class to server-side component class
// Connector lives in the client and the @Connect annotation specifies the
// corresponding server-side component
@Connect(AutocompleteComponent.class)
public class AutocompleteComponentConnector extends AbstractComponentConnector implements AutocompletePlaceChangeListener{

	private static final long serialVersionUID = 3708269877164761235L;
	// ServerRpc is used to send events to server. Communication implementation
    // is automatically created here
    private final AutocompletePlaceChangedRpc placeChangedRpc = RpcProxy.create(AutocompletePlaceChangedRpc.class, this);
    public static boolean apiLoaded = false;

    public static boolean loadingApi = false;
    
	public AutocompleteComponentConnector() {
//		if (!apiLoaded){
//			loadMapApi();
//		}
        // We choose listed for mouse clicks for the widget
        getWidget().setPlaceChangeListener(this);
//        getWidget().init();

    }
	
    protected void loadMapApi() {
        if (loadingApi) {
            return;
        }
        loadingApi = true;
		ArrayList<LoadApi.LoadLibrary> loadLibraries = new ArrayList<LoadApi.LoadLibrary>();
		loadLibraries.add(LoadLibrary.ADSENSE);
		loadLibraries.add(LoadLibrary.DRAWING);
		loadLibraries.add(LoadLibrary.GEOMETRY);
		loadLibraries.add(LoadLibrary.PANORAMIO);
		loadLibraries.add(LoadLibrary.PLACES);
		loadLibraries.add(LoadLibrary.WEATHER);
		loadLibraries.add(LoadLibrary.VISUALIZATION);
        Runnable onLoad = new Runnable() {
            @Override
            public void run() {
                apiLoaded = true;
                loadingApi = false;
                
                initAutocompleteComponent();
            }
        };

        LoadApi.Language language = null;
        if (getState().language != null) {
            language = LoadApi.Language.fromValue(getState().language);
        }

        String params = null;
        if (getState().clientId != null) {
            params = "client=" + getState().clientId;
        } else if (getState().apiKey != null) {
            params = "key=" + getState().apiKey;
        }

        if (getState().apiUrl != null) {
            AjaxLoader.init(getState().apiKey, getState().apiUrl);
        }

        LoadApi.go(onLoad, loadLibraries, false, language, params);
    }
    
    protected void initAutocompleteComponent() {
        getWidget().init();

        updateFromState(true);
    }
    
    protected void updateFromState(boolean initial) {
//        updateVisibleAreaAndCenterBoundLimits();
//
//        LatLng center = LatLng.newInstance(getState().center.getLat(),
//            getState().center.getLon());
//        getWidget().setCenter(center);
//        getWidget().setZoom(getState().zoom);
//        getWidget().setTrafficLayerVisible(getState().trafficLayerVisible);
//        getWidget().setMarkers(getState().markers.values());
//        getWidget().setPolygonOverlays(getState().polygons);
//        getWidget().setPolylineOverlays(getState().polylines);
//        getWidget().setKmlLayers(getState().kmlLayers);
//        getWidget().setMapType(getState().mapTypeId);
//        getWidget().setControls(getState().controls);
//        getWidget().setDraggable(getState().draggable);
//        getWidget().setKeyboardShortcutsEnabled(
//            getState().keyboardShortcutsEnabled);
//        getWidget().setScrollWheelEnabled(getState().scrollWheelEnabled);
//        getWidget().setMinZoom(getState().minZoom);
//        getWidget().setMaxZoom(getState().maxZoom);
//        getWidget().setInfoWindows(getState().infoWindows.values());
//
//        if (getState().fitToBoundsNE != null
//            && getState().fitToBoundsSW != null) {
//            getWidget().fitToBounds(getState().fitToBoundsNE,
//                getState().fitToBoundsSW);
//        }
//        getWidget().updateOptionsAndPanning();
//        if (initial) {
//            getWidget().triggerResize();
//        }
//		onConnectorHierarchyChange(null);
    }
	
    // We must implement getWidget() to cast to correct type 
    // (this will automatically create the correct widget type)
    @Override
    public AutocompleteComponentWidget getWidget() {
        return (AutocompleteComponentWidget) super.getWidget();
    }

    // We must implement getState() to cast to correct type
    @Override
    public AutocompleteComponentState getState() {
        return (AutocompleteComponentState) super.getState();
    }

    // Whenever the state changes in the server-side, this method is called
    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
        
        if (!apiLoaded) {
            loadMapApi();
            return;
        } else if (getWidget().autoComplete == null) {
            initAutocompleteComponent();
        }
        
        // State is directly readable in the client after it is set in server
//        if( getState().boundsSW != null && getState().boundsNE != null ) {
//            LatLng sw = LatLng.newInstance(getState().boundsSW.getLat(), getState().boundsSW.getLon());
//            LatLng ne = LatLng.newInstance(getState().boundsNE.getLat(), getState().boundsNE.getLon());
//
//            getWidget().setBounds(LatLngBounds.newInstance(sw, ne));
//        } else {
//        	getWidget().setBounds( null );
//        }
    }

	@Override
	public void placeChanged(LocationInfo locationInfo) {
		placeChangedRpc.placeChanged( locationInfo );
	}
}
