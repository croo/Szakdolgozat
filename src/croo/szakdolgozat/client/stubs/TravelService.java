package croo.szakdolgozat.client.stubs;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import croo.szakdolgozat.shared.TravelInfo;


@RemoteServiceRelativePath("travel")
public interface TravelService extends RemoteService{
	
	ArrayList<TravelInfo> getTravelInfos(); 
	
}
