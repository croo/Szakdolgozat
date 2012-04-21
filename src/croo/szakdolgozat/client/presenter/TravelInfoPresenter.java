package croo.szakdolgozat.client.presenter;

import com.google.web.bindery.event.shared.EventBus;

import croo.szakdolgozat.client.display.TravelInfoDisplay;
import croo.szakdolgozat.client.stubs.TravelServiceAsync;

public class TravelInfoPresenter {

	
	private final TravelInfoDisplay display;
	private final EventBus eventBus;
	private final TravelServiceAsync travelService;

	public TravelInfoPresenter(TravelInfoDisplay display, EventBus eventBus, TravelServiceAsync travelService) {
		this.display = display;		
		this.eventBus = eventBus;
		this.travelService = travelService;			
	}

}
