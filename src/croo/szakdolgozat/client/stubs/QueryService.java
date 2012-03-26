package croo.szakdolgozat.client.stubs;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import croo.szakdolgozat.shared.Coordinates;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("query")
public interface QueryService extends RemoteService
{
	Coordinates query(String queryData);
}
