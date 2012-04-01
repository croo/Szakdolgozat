package croo.szakdolgozat.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

import croo.szakdolgozat.client.display.TravelMapDisplay;
import croo.szakdolgozat.client.presenter.TravelMapPresenter;
import croo.szakdolgozat.client.stubs.MapServiceAsync;

public class TravelMap extends Composite implements TravelMapDisplay
{
	private static TravelMapUiBinder uiBinder = GWT.create(TravelMapUiBinder.class);
	interface TravelMapUiBinder extends UiBinder<Widget, TravelMap>
	{
	}

	@UiField
	Button sendButton;
	@UiField
	TextBox startTown;
	@UiField
	TextBox destinationTown;
	@UiField
	SimplePanel mapHolder;
	@UiField
	Label errorLabel;

	TravelMapPresenter presenter;

	// private MapWidget map;

	public TravelMap(EventBus eventBus, MapServiceAsync mapService)
	{
		initWidget(uiBinder.createAndBindUi(this));
		presenter = new TravelMapPresenter(eventBus, this, mapService);
		presenter.initializeMap();
	}

	@UiHandler("sendButton")
	void onClick(ClickEvent e)
	{
		presenter.onSendButtonClicked(startTown.getText(), destinationTown.getText());
	}

	@UiHandler("startTown")
	void onStartTownChange(ChangeEvent event)
	{
		presenter.verifyStartTownInput(startTown.getText());
	}

	@UiHandler("destinationTown")
	void onDestinationTownChange(ChangeEvent event)
	{
		presenter.verifyDestinationTownInput(destinationTown.getText());
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
