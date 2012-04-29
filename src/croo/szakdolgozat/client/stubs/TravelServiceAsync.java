package croo.szakdolgozat.client.stubs;

import com.google.gwt.user.client.rpc.AsyncCallback;

import croo.szakdolgozat.shared.TravelInfos;

public interface TravelServiceAsync
{

	void getTravelInfos(AsyncCallback<TravelInfos> callback);

}
