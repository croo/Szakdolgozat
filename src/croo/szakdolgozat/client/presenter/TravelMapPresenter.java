package croo.szakdolgozat.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.event.MapClickHandler.MapClickEvent;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.web.bindery.event.shared.EventBus;

import croo.szakdolgozat.client.display.TravelMapDisplay;

public class TravelMapPresenter
{
	private static final String MY_GOOGLEAPI_AUTH_KEY = "AIzaSyD--gmXsvTyag6v_Li5-wsYfdlXTMyauCU";
	private EventBus eventBus;
	private TravelMapDisplay display;

	public TravelMapPresenter(EventBus eventBus, TravelMapDisplay display)
	{
		this.eventBus = eventBus;
		this.display = display;
	}

	public void initializeMap(MapWidget map)
	{
		Maps.loadMapsApi(MY_GOOGLEAPI_AUTH_KEY, "2", false, getMapInitThread(map));
	}

	private Runnable getMapInitThread(final MapWidget map)
	{
		return new Runnable() {
			public void run()
			{
				buildUi();
			}

			private void buildUi()
			{
				LatLng budapest = LatLng.newInstance(47.309, 19.500);
				map.addControl(new LargeMapControl());
				map.addOverlay(new Marker(budapest));
				// map.getInfoWindow().open(map.getCenter(), new
				// InfoWindowContent("Middle of Europe... more or less."));
				// final DockLayoutPanel dock = new DockLayoutPanel(Unit.PX);
				// dock.addNorth(map, 500);
				// RootPanel.get("map").add(dock);
				// map.addMapClickHandler(this);
			}
		};
	}

	public void onSendButtonClicked()
	{
		GWT.log("You have clicked on the Send button.");
	}

	public void onMapClick(MapClickEvent event)
	{
		GWT.log("You have clicked on the map.");
	}

}
