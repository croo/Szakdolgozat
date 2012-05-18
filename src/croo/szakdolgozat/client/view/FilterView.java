package croo.szakdolgozat.client.view;

import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.web.bindery.event.shared.EventBus;

import croo.szakdolgozat.client.display.FilterDisplay;
import croo.szakdolgozat.client.presenter.FilterPresenter;
import croo.szakdolgozat.client.stubs.FilterServiceAsync;

public class FilterView extends Composite implements FilterDisplay
{

	private static FilterViewUiBinder uiBinder = GWT.create(FilterViewUiBinder.class);

	@UiField
	ListBox discountBox;
	@UiField
	DateBox dateBox;

	private FilterPresenter presenter;

	interface FilterViewUiBinder extends UiBinder<Widget, FilterView>
	{
	}

	public FilterView(EventBus eventBus, FilterServiceAsync filteringService)
	{
		presenter = new FilterPresenter(this, eventBus, filteringService);
		initWidget(uiBinder.createAndBindUi(this));
		setUpDateBox();
		setUpDiscountBox();
	}

	private void setUpDateBox()
	{
		dateBox.setValue(new Date());
		dateBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("yyyy-MM-dd")));
		presenter.onDateBoxValueChange(dateBox.getValue());
	}

	private void setUpDiscountBox()
	{
		presenter.loadDiscounts();
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

	@Override
	public void loadDiscountBoxData(HashMap<String, String> properties)
	{
		for (Entry<String, String> item : properties.entrySet()) {
			discountBox.addItem((String) item.getValue(), (String) item.getKey());
		}
		presenter.onDiscountBoxChange(discountBox.getValue(discountBox.getSelectedIndex()));
	}
}
