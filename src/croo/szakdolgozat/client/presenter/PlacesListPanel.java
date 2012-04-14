package croo.szakdolgozat.client.presenter;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.maps.client.InfoWindow;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import croo.szakdolgozat.shared.InterestingPlace;
import croo.szakdolgozat.shared.Town;

public class PlacesListPanel extends VerticalPanel
{
	public PlacesListPanel(final Town endTown, final InfoWindow infoWindow)
	{
		ArrayList<InterestingPlace> places = endTown.getInterestingPlaces();
		for (final InterestingPlace place : places) {
			add(createListElement(endTown.getTownCoordinateInJSO(), infoWindow, place));
		}
	}

	public Frame createMaxContent(String url)
	{
		Frame interestingPage = new Frame(url);
		interestingPage.setHeight("98%");
		interestingPage.setWidth("98%");
		return interestingPage;
	}

	private HTML createListElement(final LatLng coordinateInJSO, final InfoWindow infoWindow, final InterestingPlace place)
	{
		final HTML listElement = new HTML(place.getName());
		listElement.setStylePrimaryName("placeButton");
		addHoverEffectToElement(listElement);
		listElement.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event)
			{
				InfoWindowContent content = new InfoWindowContent(PlacesListPanel.this);
				content.setMaxContent(createMaxContent(place.getURL()));
				infoWindow.open(coordinateInJSO, content);
				infoWindow.maximize();
			}
		});
		return listElement;
	}

	private void addHoverEffectToElement(final HTML listElement)
	{
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
	}
}
