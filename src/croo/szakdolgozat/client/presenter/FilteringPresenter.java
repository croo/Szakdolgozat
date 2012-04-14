package croo.szakdolgozat.client.presenter;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.event.shared.EventBus;

import croo.szakdolgozat.client.display.FilteringDisplay;
import croo.szakdolgozat.client.stubs.FilteringServiceAsync;
import croo.szakdolgozat.client.stubs.callbacks.ErrorHandlingAsyncCallback;

public class FilteringPresenter
{
	private FilteringDisplay display;
	private EventBus eventBus;
	private final FilteringServiceAsync filteringService;

	public FilteringPresenter(FilteringDisplay display, EventBus eventBus, FilteringServiceAsync filteringService)
	{
		this.display = display;
		this.eventBus = eventBus;
		this.filteringService = filteringService;
	}

	public void onDateBoxValueChange(Date date)
	{
		filteringService.setDate(date, new ErrorHandlingAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result)
			{
				GWT.log("DateBox value succesfully changed on the server.");
			}

		});
	}

	public void onDiscountBoxChange(String rate)
	{
		filteringService.setDiscountRate(rate, new ErrorHandlingAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result)
			{
				GWT.log("DiscountBox value succesfully changed on the server.");
			}
		});
	}
}
