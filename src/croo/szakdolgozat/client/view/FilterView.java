package croo.szakdolgozat.client.view;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.web.bindery.event.shared.EventBus;

import croo.szakdolgozat.client.display.FilteringDisplay;
import croo.szakdolgozat.client.presenter.FilteringPresenter;
import croo.szakdolgozat.client.stubs.FilterServiceAsync;

public class FilterView extends Composite implements FilteringDisplay
{

	private static FilterViewUiBinder uiBinder = GWT.create(FilterViewUiBinder.class);

	@UiField
	ListBox discountBox;
	@UiField
	DateBox dateBox;

	private FilteringPresenter presenter;

	interface FilterViewUiBinder extends UiBinder<Widget, FilterView>
	{
	}

	public FilterView(EventBus eventBus, FilterServiceAsync filteringService)
	{
		presenter = new FilteringPresenter(this, eventBus, filteringService);
		initWidget(uiBinder.createAndBindUi(this));
		setUpDateBox();
		setUpDiscountBox();
	}

	private void setUpDateBox()
	{
		dateBox.setValue(new Date());
	}

	private void setUpDiscountBox()
	{
		discountBox.addItem("100%");
		discountBox.addItem("75%");
		discountBox.addItem("66%");
		discountBox.addItem("50%");
	}

	@UiHandler("dateBox")
	void onDateBoxValueChange(ValueChangeEvent<Date> event)
	{
		presenter.onDateBoxValueChange(dateBox.getValue());
	}

	@UiHandler("discountBox")
	void onDiscountBoxChange(ChangeEvent event)
	{
		presenter.onDiscountBoxChange(discountBox.getValue(discountBox.getSelectedIndex()));
	}
}
