package croo.szakdolgozat.client.presenter;

import java.util.Date;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.event.shared.EventBus;

import croo.szakdolgozat.client.display.FilterDisplay;
import croo.szakdolgozat.client.stubs.FilterServiceAsync;
import croo.szakdolgozat.client.stubs.callbacks.ErrorHandlingAsyncCallback;

public class FilterPresenter
{
	private FilterDisplay display;
	private EventBus eventBus;
	private final FilterServiceAsync filteringService;

	public FilterPresenter(FilterDisplay display, EventBus eventBus, FilterServiceAsync filteringService)
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

	public void loadDiscounts()
	{
		filteringService.getDiscounts(new ErrorHandlingAsyncCallback<HashMap<String, String>>() {
			@Override
			public void onSuccess(HashMap<String, String> properties)
			{
				display.loadDiscountBoxData(properties);
			}
		});
	}
}
