package croo.szakdolgozat.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.web.bindery.event.shared.EventBus;

import croo.szakdolgozat.client.display.TravelMapDisplay;

public class TravelMapPresenter implements MapClickHandler
{
	private static final String MY_GOOGLEAPI_AUTH_KEY = "AIzaSyD--gmXsvTyag6v_Li5-wsYfdlXTMyauCU";
	private EventBus eventBus;
	private TravelMapDisplay display;

	public TravelMapPresenter(EventBus eventBus, TravelMapDisplay display)
	{
		this.eventBus = eventBus;
		this.display = display;
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

	public void onSendButtonClicked()
	{
		GWT.log("You have clicked on the Send button.");
	}

	@Override
	public void onClick(MapClickEvent event)
	{
		GWT.log("You have clicked on the map.");
	}

}
