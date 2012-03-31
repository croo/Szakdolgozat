package croo.szakdolgozat.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

import croo.szakdolgozat.client.stubs.MapService;
import croo.szakdolgozat.client.stubs.MapServiceAsync;
import croo.szakdolgozat.client.view.TravelMap;

public class Main implements EntryPoint
{

	/**
	 * Entry Point
	 */
	public void onModuleLoad()
	{
		EventBus eventBus = GWT.create(SimpleEventBus.class);
		MapServiceAsync mapService = GWT.create(MapService.class);

		TravelMap map = new TravelMap(eventBus, mapService);
		RootPanel.get("map").add(map);

	}
}
