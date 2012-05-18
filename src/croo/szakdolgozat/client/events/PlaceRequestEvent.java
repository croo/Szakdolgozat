package croo.szakdolgozat.client.events;

import com.google.web.bindery.event.shared.Event;

import croo.szakdolgozat.shared.InterestingPlace;

public class PlaceRequestEvent extends Event<PlaceRequestEventHandler>
{
	public static final Type<PlaceRequestEventHandler> TYPE = new Type<PlaceRequestEventHandler>();
	private InterestingPlace place;

	public PlaceRequestEvent(String name, String url, String description, String image)
	{
		place = new InterestingPlace(url, name, description, image);
	}

	@Override
	protected void dispatch(PlaceRequestEventHandler handler)
	{
		handler.onNewPlaceRequest(this);
	}

	@Override
	public Type<PlaceRequestEventHandler> getAssociatedType()
	{
		return TYPE;
	}

	public InterestingPlace getPlace()
	{
		return place;
	}
}
