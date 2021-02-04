package com.vaadin.tapio.googlemaps.client;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.ajaxloader.client.AjaxLoader;
import com.google.gwt.maps.client.LoadApi;
import com.google.gwt.maps.client.LoadApi.LoadLibrary;
import com.google.gwt.maps.client.placeslib.AutocompleteType;
import com.vaadin.client.annotations.OnStateChange;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;
import com.vaadin.tapio.googlemaps.AutocompleteComponent;
import com.vaadin.tapio.googlemaps.client.events.AutocompletePlaceChangeListener;
import com.vaadin.tapio.googlemaps.client.rpcs.AutocompletePlaceChangedRpc;

@Connect(AutocompleteComponent.class)
public class AutocompleteComponentConnector extends AbstractComponentConnector implements AutocompletePlaceChangeListener{

	private final static Logger logger = Logger.getLogger(AutocompleteComponentConnector.class.getName());
	private static final long serialVersionUID = 3708269877164761235L;
	// ServerRpc is used to send events to server. Communication implementation
    // is automatically created here
    private final AutocompletePlaceChangedRpc placeChangedRpc = RpcProxy.create(AutocompletePlaceChangedRpc.class, this);
    public static boolean apiLoaded = false;

    public static boolean loadingApi = false;
    
	public AutocompleteComponentConnector() {
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
        logger.log(Level.INFO, "************libraries load successfully********" + params);
    }
    
    protected void initAutocompleteComponent() {
        getWidget().init();
        getWidget().setPlaceChangeListener(this);
//        onBoundsChange();
//        onTypesChange();
        getWidget().setBounds( getState().boundsNE, getState().boundsSW );
        getWidget().setTypes( getState().types.toArray( new AutocompleteType[getState().types.size()] ) );
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
    
//    @OnStateChange(value={"boundsNE", "boundsSW"})
//    private void onBoundsChange() {
//    	logger.log(Level.INFO, "Auto complete component bounds changed to NE(" +  getState().boundsNE.getLat() + ", " + getState().boundsNE.getLon() + "), SW(" + getState().boundsSW.getLat() + ", " + getState().boundsSW.getLon() + ")");
//        getWidget().setBounds( getState().boundsNE, getState().boundsSW );
//    }
//    
//    @OnStateChange(value={"types"})
//    private void onTypesChange() {
//    	logger.log(Level.INFO, "Auto complete component type's count " + getState().types.size() );
//        getWidget().setTypes( getState().types.toArray( new AutocompleteType[getState().types.size()] ) );
//    }

    // Whenever the state changes in the server-side, this method is called
    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
        logger.log(Level.INFO, "*********************Start loading auto-complete component*********************" + apiLoaded);
        if (!apiLoaded) {
            loadMapApi();
            return;
        } else if (getWidget().autoComplete == null) {
            initAutocompleteComponent();
        }
        
        getWidget().setBounds( getState().boundsNE, getState().boundsSW );
        getWidget().setTypes( getState().types.toArray( new AutocompleteType[getState().types.size()] ) );
    }

	@Override
	public void placeChanged(LocationInfo locationInfo) {
		placeChangedRpc.placeChanged( locationInfo );
	}
}
