package croo.szakdolgozat.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.event.MapClickHandler.MapClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

import croo.szakdolgozat.client.display.TravelMapDisplay;
import croo.szakdolgozat.client.presenter.TravelMapPresenter;

public class TravelMap extends Composite implements TravelMapDisplay
{
	private static TravelMapUiBinder uiBinder = GWT.create(TravelMapUiBinder.class);
	interface TravelMapUiBinder extends UiBinder<Widget, TravelMap>
	{
	}

	@UiField
	Button sendButton;
	@UiField
	Button startField;
	@UiField
	Button endField;
	@UiField
	SimplePanel mapHolder;
	@UiField
	MapWidget map;
	TravelMapPresenter presenter;

	public TravelMap(EventBus eventBus)
	{
		initWidget(uiBinder.createAndBindUi(this));
		presenter = new TravelMapPresenter(eventBus, this);
		presenter.initializeMap(map);
		mapHolder.add(map);
	}

	@UiHandler("map")
	void onClick(MapClickEvent event)
	{
		presenter.onMapClick(event);
	}

	@UiHandler("sendButton")
	void onClick(ClickEvent e)
	{
		presenter.onSendButtonClicked();
	}

	@Override
	public MapWidget getTravelMap()
	{
		return map;
	}
}
