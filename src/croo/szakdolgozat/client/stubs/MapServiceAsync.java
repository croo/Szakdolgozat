package croo.szakdolgozat.client.stubs;

import com.google.gwt.user.client.rpc.AsyncCallback;

import croo.szakdolgozat.shared.Route;

/**
 * The async counterpart of QueryService
 */
public interface MapServiceAsync
{
	void verifyLocation(String location, AsyncCallback<Boolean> callback);

	void getRoute(String startTown, String destinationTown, AsyncCallback<Route> callback);
}
