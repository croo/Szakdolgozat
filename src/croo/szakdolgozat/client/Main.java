package croo.szakdolgozat.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

import croo.szakdolgozat.client.stubs.FilterService;
import croo.szakdolgozat.client.stubs.FilterServiceAsync;
import croo.szakdolgozat.client.stubs.MapService;
import croo.szakdolgozat.client.stubs.MapServiceAsync;
import croo.szakdolgozat.client.stubs.TravelService;
import croo.szakdolgozat.client.stubs.TravelServiceAsync;
import croo.szakdolgozat.client.view.FilterView;
import croo.szakdolgozat.client.view.TravelInfoView;
import croo.szakdolgozat.client.view.TravelMapView;

public class Main implements EntryPoint
{

	/**
	 * Entry Point
	 */
	public void onModuleLoad()
	{
		EventBus eventBus = GWT.create(SimpleEventBus.class);
		
		MapServiceAsync mapService = GWT.create(MapService.class);
		FilterServiceAsync filteringService = GWT.create(FilterService.class);
		TravelServiceAsync travelService = GWT.create(TravelService.class);
		
		TravelMapView map = new TravelMapView(eventBus, mapService);
		FilterView filtering = new FilterView(eventBus, filteringService);
		TravelInfoView travelInfo = new TravelInfoView(eventBus,travelService);
		
		RootPanel.get("map").add(map);
		RootPanel.get("filtering").add(filtering);
		RootPanel.get("travelinfo").add(travelInfo);
	}
}
