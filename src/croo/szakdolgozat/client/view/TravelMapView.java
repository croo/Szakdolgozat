package croo.szakdolgozat.client.view;

import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.web.bindery.event.shared.EventBus;

import croo.szakdolgozat.client.display.TravelMapDisplay;
import croo.szakdolgozat.client.presenter.TravelMapPresenter;
import croo.szakdolgozat.client.stubs.FilterServiceAsync;
import croo.szakdolgozat.client.stubs.MapServiceAsync;

public class TravelMapView extends Composite implements TravelMapDisplay
{
	private static TravelMapViewUiBinder uiBinder = GWT.create(TravelMapViewUiBinder.class);
	interface TravelMapViewUiBinder extends UiBinder<Widget, TravelMapView>
	{
	}

	@UiField
	TextBox startTown;
	@UiField
	TextBox endTown;
	@UiField
	DateBox dateBox;
	@UiField
	ListBox discountBox;
	@UiField
	Button sendButton;
	@UiField
	SimplePanel mapHolder;
	@UiField
	Label errorLabel;

	TravelMapPresenter presenter;

	// private MapWidget map;

	public TravelMapView(EventBus eventBus, MapServiceAsync mapService, FilterServiceAsync filterService)
	{
		initWidget(uiBinder.createAndBindUi(this));
		presenter = new TravelMapPresenter(eventBus, this, mapService, filterService);
		presenter.initializeMap();
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

	@UiHandler("sendButton")
	void onClick(ClickEvent e)
	{
		presenter.onSendButtonClicked(startTown.getText(), endTown.getText());
	}

	@UiHandler("startTown")
	void onStartTownChange(ChangeEvent event)
	{
		presenter.verifyTownInput(startTown.getText());
	}

	@UiHandler("endTown")
	void onEndTownChange(ChangeEvent event)
	{
		presenter.verifyTownInput(endTown.getText());
	}

	@Override
	public void setTravelMap(MapWidget map)
	{
		// this.map = map;
		mapHolder.add(map);
	}

	@Override
	public void setErrorLabel(String error)
	{
		errorLabel.setText(error);
	}
}
