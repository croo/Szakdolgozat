package croo.szakdolgozat.client.events;

import com.google.web.bindery.event.shared.Event;

public class SendEvent extends Event<SendEventHandler> {

	public static final Type<SendEventHandler> TYPE = new Type<SendEventHandler>();
	
	@Override
	protected void dispatch(SendEventHandler handler) {
		handler.onSendButtonClicked(this);
	}

	@Override
	public Type<SendEventHandler> getAssociatedType() {
		return TYPE;
	}

}
