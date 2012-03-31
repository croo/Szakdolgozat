package croo.szakdolgozat.client.stubs;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("query")
public interface MapService extends RemoteService
{
	Boolean verifyLocation(String location);
}
