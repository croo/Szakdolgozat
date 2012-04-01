package croo.szakdolgozat.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.web.bindery.event.shared.EventBus;

import croo.szakdolgozat.client.ValidatingAsyncCallback;
import croo.szakdolgozat.client.display.TravelMapDisplay;
import croo.szakdolgozat.client.stubs.MapServiceAsync;

public class TravelMapPresenter implements MapClickHandler
{
	private static final String MY_GOOGLEAPI_AUTH_KEY = "AIzaSyD--gmXsvTyag6v_Li5-wsYfdlXTMyauCU";
	private EventBus eventBus;
	private TravelMapDisplay display;
	private Boolean startTownInputIsValid;
	private Boolean destinationTownInputIsValid;

	private MapServiceAsync mapService;

	public TravelMapPresenter(EventBus eventBus, TravelMapDisplay display, MapServiceAsync mapService)
	{
		this.eventBus = eventBus;
		this.display = display;
		this.mapService = mapService;
	}

	public void verifyStartTownInput(final String text)
	{
		GWT.log("Validating of input " + text + ".");
		mapService.verifyLocation(text, new ValidatingAsyncCallback() {
			@Override
			public void onValidInput()
			{
				startTownInputIsValid = true;
				display.setErrorLabel("");
			}

			@Override
			public void onInvalidInput()
			{
				display.setErrorLabel(text + " nevû város nincs az adatbázisban.");
				startTownInputIsValid = false;
			}
		});
	}

	public void verifyDestinationTownInput(final String text)
	{
		GWT.log("Validating of input " + text + ".");
		mapService.verifyLocation(text, new ValidatingAsyncCallback() {
			@Override
			public void onValidInput()
			{
				destinationTownInputIsValid = true;
				display.setErrorLabel("");
			}

			@Override
			public void onInvalidInput()
			{
				display.setErrorLabel(text + " nevû város nincs az adatbázisban.");
				destinationTownInputIsValid = false;
			}
		});
	}

	public void onSendButtonClicked()
	{
		if (startTownInputIsValid && destinationTownInputIsValid) {
			GWT.log("You have clicked on the Send button and both input is valid.");
			display.setErrorLabel("");
		} else {
			GWT.log("You have clicked on the Send button but some of the input is invalid.");
		}
	}

	@Override
	public void onClick(MapClickEvent event)
	{
		GWT.log("You have clicked on the map.");
	}

	public void initializeMap()
	{
		Maps.loadMapsApi(MY_GOOGLEAPI_AUTH_KEY, "2", false, getMapInitThread());
	}

	private Runnable getMapInitThread()
	{
		return new Runnable() {
			public void run()
			{
				buildUi();
			}

			private void buildUi()
			{
				LatLng budapest = LatLng.newInstance(47.49841, 19.04076);
				MapWidget map = new MapWidget(budapest, 7);
				map.setSize("800px", "600px");
				map.setTitle("Utazz a MÁVval!");
				map.setScrollWheelZoomEnabled(true);
				map.setContinuousZoom(true);
				map.addControl(new LargeMapControl());
				map.addOverlay(new Marker(budapest));
				map.addMapClickHandler(TravelMapPresenter.this);
				display.setTravelMap(map);
			}
		};
	}
}
