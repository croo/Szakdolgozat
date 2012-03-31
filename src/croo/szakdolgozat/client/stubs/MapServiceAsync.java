package croo.szakdolgozat.client.stubs;

import com.google.gwt.user.client.rpc.AsyncCallback;

import croo.szakdolgozat.shared.Coordinates;

/**
 * The async counterpart of QueryService
 */
public interface MapServiceAsync
{
	void query(String queryData, AsyncCallback<Coordinates> callback);
}
