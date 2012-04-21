package croo.szakdolgozat.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface SendEventHandler extends EventHandler {
	void onSendButtonClicked(SendEvent event);
}
