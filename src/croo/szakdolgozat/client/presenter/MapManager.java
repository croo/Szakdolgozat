package croo.szakdolgozat.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;

import croo.szakdolgozat.shared.Route;

public class MapManager
{
	private MapWidget map;

	public MapManager(MapWidget map)
	{
		this.map = map;
	}

	public void drawRoute(Route route)
	{
		GWT.log("Drawing the route.");
	}

	public void addMapClickHandler(TravelMapPresenter travelMapPresenter)
	{
		map.addMapClickHandler(travelMapPresenter);
	}

	public MapWidget getMap()
	{
		return map;
	}

	public void initMap()
	{
		LatLng budapest = LatLng.newInstance(47.49841, 19.04076);
		map.setCenter(budapest);
		map.setZoomLevel(7);
		map.setSize("800px", "600px");
		map.setTitle("Utazz a MÁVval!");
		map.setScrollWheelZoomEnabled(true);
		map.setContinuousZoom(true);
		map.addControl(new LargeMapControl());
		map.addOverlay(new Marker(budapest));
	}
}
