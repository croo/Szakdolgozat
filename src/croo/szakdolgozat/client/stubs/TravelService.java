package croo.szakdolgozat.client.stubs;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import croo.szakdolgozat.shared.TravelInfos;

@RemoteServiceRelativePath("travel")
public interface TravelService extends RemoteService
{

	TravelInfos getTravelInfos() throws Exception;

}
