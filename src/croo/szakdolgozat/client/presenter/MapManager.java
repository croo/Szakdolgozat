package croo.szakdolgozat.client.presenter;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.maps.client.InfoWindow;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
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

	public void drawRoute(final Route route)
	{
		map.clearOverlays();

		final VerticalPanel placesPanel = createPlacesPanel(route);
		final Marker destination = new Marker(route.getDestinationCoordinateInJSO());
		destination.addMarkerClickHandler(new MarkerClickHandler() {
			@Override
			public void onClick(MarkerClickEvent event)
			{
				if (!map.getInfoWindow().isVisible())
					showInterestingPlaces(destination, placesPanel);
			}
		});

		map.addOverlay(route.getRouteWayInJSO());
		map.addOverlay(destination);

		GWT.log("Drawing the route and the destination marker.");
	}

	// TODO : This 3 method should be a different class!

	private InfoWindow showInterestingPlaces(final Marker destination, final VerticalPanel panel)
	{
		InfoWindowContent initContent = new InfoWindowContent(panel);
		initContent.setMaxContent(createFrame("http://www.asdf.com"));
		final InfoWindow infoWindow = map.getInfoWindow();
		infoWindow.setMaximizeEnabled(true);
		initContent.setNoCloseOnClick(true);
		infoWindow.open(destination, initContent);
		return infoWindow;
	}

	// On every click we have to create new content for the InfoView
	// becouse of a Google Maps API v2 bug
	private VerticalPanel createPlacesPanel(final Route route)
	{
		final VerticalPanel panel = new VerticalPanel();
		ArrayList<InterestingPlace> places = route.getEndTown().getInterestingPlaces();
		final InfoWindow infoWindow = map.getInfoWindow();
		for (final InterestingPlace place : places) {
			final HTML listElement = new HTML(place.getName());
			listElement.setStylePrimaryName("placeButton");
			listElement.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event)
				{
					InfoWindowContent content = new InfoWindowContent(panel);
					content.setMaxContent(createFrame(place.getURL()));
					infoWindow.open(route.getDestinationCoordinateInJSO(), content);
					infoWindow.maximize();
				}
			});
			listElement.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event)
				{
					listElement.setStyleName("placeButton-hover", true);
				}
			});
			listElement.addMouseOutHandler(new MouseOutHandler() {

				@Override
				public void onMouseOut(MouseOutEvent event)
				{
					listElement.setStyleName("placeButton-hover", false);
				}
			});

			panel.add(listElement);
		}

		return panel;
	}

	private Frame createFrame(String url)
	{
		Frame interestingPage = new Frame(url);
		interestingPage.setHeight("98%");
		interestingPage.setWidth("98%");
		return interestingPage;
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
