package croo.szakdolgozat.client.presenter;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.InfoWindow;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import croo.szakdolgozat.shared.InterestingPlace;
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
		map.clearOverlays();
		map.addOverlay(route.getRouteWayInJSO());
		map.addOverlay(new Marker(route.getEndTown().getTownCoordinateInJSO()));

		SimplePanel wrapper = createInterestingPlacesList(route.getEndTown().getInterestingPlaces());
		InfoWindowContent content = new InfoWindowContent(wrapper);

		Frame interestingPage = new Frame(route.getEndTown().getInterestingPlaces().get(0).getURL());
		interestingPage.setHeight("98%");
		interestingPage.setWidth("98%");
		content.setMaxContent(interestingPage);
		InfoWindow info = map.getInfoWindow();

		info.open(route.getEndTown().getTownCoordinateInJSO(), content);
		GWT.log("Drawing the route, marker.");
	}

	private SimplePanel createInterestingPlacesList(ArrayList<InterestingPlace> interestingPlaces)
	{
		SimplePanel wrapper = new ScrollPanel();

		VerticalPanel panel = new VerticalPanel();
		for (InterestingPlace place : interestingPlaces) {
			HTML item = new HTML(place.getName());
			panel.add(item);
		}

		wrapper.add(panel);
		return wrapper;
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
	}
}
