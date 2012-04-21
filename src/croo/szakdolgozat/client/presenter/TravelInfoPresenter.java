package croo.szakdolgozat.client.presenter;

import java.util.ArrayList;

import com.google.web.bindery.event.shared.EventBus;

import croo.szakdolgozat.client.display.TravelInfoDisplay;
import croo.szakdolgozat.client.stubs.TravelServiceAsync;
import croo.szakdolgozat.client.stubs.callbacks.ErrorHandlingAsyncCallback;
import croo.szakdolgozat.shared.TravelInfo;

public class TravelInfoPresenter {

	private TravelInfoDisplay display;
	private EventBus eventBus;
	private TravelServiceAsync travelService;

	public TravelInfoPresenter(TravelInfoDisplay display, EventBus eventBus, TravelServiceAsync travelService) {
		this.display = display;		
		this.eventBus = eventBus;
		this.travelService = travelService;			
	}
	
	public void onButtonClick(){
		travelService.getTravelInfos(new ErrorHandlingAsyncCallback<ArrayList<TravelInfo>>() {
			@Override
			public void onSuccess(ArrayList<TravelInfo> result) {
				TravelInfoPresenter.this.display.setLabel(result.get(0).test);
			}
		});
	}

}
