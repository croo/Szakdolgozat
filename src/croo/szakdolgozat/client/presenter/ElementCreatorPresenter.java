package croo.szakdolgozat.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.web.bindery.event.shared.EventBus;

import croo.szakdolgozat.client.display.ElementCreatorDisplay;
import croo.szakdolgozat.client.events.PlaceRequestEvent;

public class ElementCreatorPresenter
{

	private final ElementCreatorDisplay display;
	private final EventBus eventBus;
	private final PopupPanel container;

	public ElementCreatorPresenter(ElementCreatorDisplay display, EventBus eventBus, PopupPanel popup)
	{
		this.display = display;
		this.eventBus = eventBus;
		this.container = popup;
	}

	public void createNewPlace(String name, String url, String description, String image)
	{
		// TODO : CHECK THAT THE INPUT PARAMETERS ARE LEGAL
		// OTHERWISE DOUBLE PENETRATE INJECTION
		GWT.log("You pressed the Send New Place button.");
		eventBus.fireEvent(new PlaceRequestEvent(name, url, description, image));
	}

	public void hideCreatorView()
	{
		container.hide();
	}
}
