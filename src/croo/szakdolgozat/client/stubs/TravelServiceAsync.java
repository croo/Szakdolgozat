package croo.szakdolgozat.client.stubs;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import croo.szakdolgozat.shared.TravelInfo;

public interface TravelServiceAsync {

	void getTravelInfos(AsyncCallback<ArrayList<TravelInfo>> callback);

}
