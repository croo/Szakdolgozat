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
		GWT.log("You pressed the Send New Place button.");
		if(name.isEmpty() || url.isEmpty()){
			display.setErrorLabel("A név és a honlapcím kitöltése kötelező.");
			GWT.log("Missing input at new place request.");
		} else {
			GWT.log("New place request started...");
			eventBus.fireEvent(new PlaceRequestEvent(name, url, description, image));
			hideCreatorView();
		}
	}

	public void hideCreatorView()
	{
		container.hide();
	}
}
