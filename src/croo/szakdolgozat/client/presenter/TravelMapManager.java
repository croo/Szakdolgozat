package croo.szakdolgozat.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.ui.Image;
import com.google.web.bindery.event.shared.EventBus;

import croo.szakdolgozat.shared.InterestingPlace;
import croo.szakdolgozat.shared.Route;
import croo.szakdolgozat.shared.Town;

public class TravelMapManager
{
	private MapWidget map;
	private final EventBus eventBus;

	public TravelMapManager(MapWidget map, EventBus eventBus)
	{
		this.map = map;
		this.eventBus = eventBus;
	}

	public void drawRoute(final Route route)
	{
		map.clearOverlays();
		map.getInfoWindow().close();
		map.getInfoWindow().setMaximizeEnabled(true);
		map.addOverlay(route.getRouteWayInJSO());
		map.addOverlay(markerOfTown(route.getEndTown()));

		GWT.log("Drawing the route and the destination marker.");
	}

	private Marker markerOfTown(final Town town)
	{
		final Marker marker = new Marker(town.getTownCoordinateInJSO());
		final PlacesListPanel placesPanel = new PlacesListPanel(town, map.getInfoWindow(), eventBus);
		marker.addMarkerClickHandler(new MarkerClickHandler() {
			@Override
			public void onClick(MarkerClickEvent event)
			{
				if (!map.getInfoWindow().isVisible())
					showInterestingPlaces(marker, placesPanel);
			}
		});
		return marker;
	}

	private void showInterestingPlaces(final Marker destination, final PlacesListPanel placesPanel)
	{
		InfoWindowContent initContent = new InfoWindowContent(placesPanel);
		Image easterEgg = new Image("http://netanimations.net/Moving-picture-steam-engine-silhouette-train-animation.gif");
		easterEgg.setAltText("All your trains are belong to us!!");
		initContent.setMaxContent(easterEgg);
		initContent.setNoCloseOnClick(true);
		map.getInfoWindow().open(destination, initContent);
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
		map.setSize("1024px", "600px");
		map.setTitle("Utazz a MÁVval!");
		map.setScrollWheelZoomEnabled(true);
		map.setContinuousZoom(true);
		map.addControl(new LargeMapControl());
	}

	public void updatePlacesListPanel(InterestingPlace place)
	{
		// TODO Auto-generated method stub

	}
}
