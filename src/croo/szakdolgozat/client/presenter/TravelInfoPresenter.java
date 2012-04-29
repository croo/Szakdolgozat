package croo.szakdolgozat.client.presenter;

import com.google.web.bindery.event.shared.EventBus;

import croo.szakdolgozat.client.display.TravelInfoDisplay;
import croo.szakdolgozat.client.events.SendEvent;
import croo.szakdolgozat.client.events.SendEventHandler;
import croo.szakdolgozat.client.stubs.TravelServiceAsync;
import croo.szakdolgozat.client.stubs.callbacks.ErrorHandlingAsyncCallback;
import croo.szakdolgozat.shared.TravelInfos;

public class TravelInfoPresenter implements SendEventHandler
{

	private TravelInfoDisplay display;
	private EventBus eventBus;
	private TravelServiceAsync travelService;

	public TravelInfoPresenter(TravelInfoDisplay display, EventBus eventBus, TravelServiceAsync travelService)
	{
		this.display = display;
		this.eventBus = eventBus;
		this.travelService = travelService;

		this.eventBus.addHandler(SendEvent.TYPE, this);
	}

	@Override
	public void onSendButtonClicked(SendEvent event)
	{
		travelService.getTravelInfos(new ErrorHandlingAsyncCallback<TravelInfos>() {
			@Override
			public void onSuccess(TravelInfos result)
			{
				display.showTravelInfos(result);
			}
		});
	}
}
