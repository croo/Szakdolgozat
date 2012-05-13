package croo.szakdolgozat.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface PlaceRequestEventHandler extends EventHandler
{
	void onNewPlaceRequest(PlaceRequestEvent event);
}
