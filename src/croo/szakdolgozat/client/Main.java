package croo.szakdolgozat.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

import croo.szakdolgozat.client.stubs.QueryService;
import croo.szakdolgozat.client.stubs.QueryServiceAsync;
import croo.szakdolgozat.shared.Coordinates;

public class Main implements EntryPoint, MapClickHandler, ClickHandler
{

	private static final String MY_GOOGLEAPI_AUTH_KEY = "AIzaSyD--gmXsvTyag6v_Li5-wsYfdlXTMyauCU";
	private final QueryServiceAsync reverseService = GWT.create(QueryService.class);
	private TextBox inputField;
	private Button sendButton;
	private HTML htmlBlock;
	private MapWidget map;

	/**
	 * Entry Point
	 */
	public void onModuleLoad()
	{
		Maps.loadMapsApi(MY_GOOGLEAPI_AUTH_KEY, "2", false, new Runnable() {
			public void run()
			{
				buildUi();
			}
		});

		inputField = new TextBox();
		sendButton = new Button("Send");
		htmlBlock = new HTML();

		sendButton.addStyleName("sendButton");

		RootPanel.get("input").add(inputField);
		RootPanel.get("button").add(sendButton);
		RootPanel.get("label").add(htmlBlock);

		inputField.setFocus(true);
		inputField.selectAll();

		sendButton.addClickHandler(this);
	}

	private void buildUi()
	{
		LatLng budapest = LatLng.newInstance(47.309, 19.500);
		map = new MapWidget(budapest, 2);
		map.setSize("640px", "480px");
		map.addControl(new LargeMapControl());
		map.setZoomLevel(7);
		map.addOverlay(new Marker(budapest));
		map.getInfoWindow().open(map.getCenter(), new InfoWindowContent("Middle of Europe... more or less."));
		final DockLayoutPanel dock = new DockLayoutPanel(Unit.PX);
		map.setScrollWheelZoomEnabled(true);
		map.setContinuousZoom(true);
		dock.addNorth(map, 500);
		RootPanel.get("map").add(dock);
		map.addMapClickHandler(this);
	}

	private void locateInputOnMap(String place)
	{
		sendButton.setEnabled(false);
		reverseService.query(place, new AsyncCallback<Coordinates>() {
			public void onFailure(Throwable caught)
			{
				htmlBlock.setHTML(caught.getMessage());
				GWT.log(caught.getMessage());
				sendButton.setEnabled(true);
			}

			public void onSuccess(Coordinates result)
			{
				if (result != null)
				{
					htmlBlock.setHTML(result.getLatitude() + ", " + result.getLongitude());
					sendButton.setEnabled(true);
					map.setCenter(LatLng.newInstance(result.getLatitude(), result.getLongitude()));
				} else
				{
					htmlBlock.setHTML("This place cannot be found in the database. Please try another one.");
				}
			}
		});
	}

	@Override
	public void onClick(MapClickEvent event)
	{
		htmlBlock.setHTML("The coords you clicked on: " + String.valueOf(event.getLatLng().getLatitude()) + " , "
				+ String.valueOf(event.getLatLng().getLongitude()));
	}

	@Override
	public void onClick(ClickEvent event)
	{
		locateInputOnMap(inputField.getText());
	}
}
