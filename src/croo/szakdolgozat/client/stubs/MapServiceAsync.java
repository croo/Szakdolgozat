package croo.szakdolgozat.client.stubs;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of QueryService
 */
public interface MapServiceAsync
{
	void verifyLocation(String location, AsyncCallback<Boolean> callback);
}
