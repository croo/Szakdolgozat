package croo.szakdolgozat.server;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import croo.szakdolgozat.client.stubs.TravelService;
import croo.szakdolgozat.shared.TravelInfo;

@SuppressWarnings("serial")
public class TravelServiceImpl extends RemoteServiceServlet implements TravelService {

	public ArrayList<TravelInfo> getTravelInfos(){
//		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,"http://api.oroszi.com/elvira");
//		builder.setHeader("Content-Type", "application/json");
//		try {
//			builder.sendRequest("", new RequestCallback() {
//				@Override
//				public void onResponseReceived(Request arg0, Response arg1) {
//					// TODO Auto-generated method stub
//				}
//				
//				@Override
//				public void onError(Request arg0, Throwable arg1) {
//					// TODO Auto-generated method stub
//				}
//			});
//		} catch (RequestException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		ArrayList<TravelInfo> infos = new ArrayList<TravelInfo>();
		TravelInfo a = new TravelInfo();
		a.test = (String)session().getAttribute("endTown"); 
		infos.add(a);
		return infos;
	}
	
	private HttpSession session()
	{
		return this.getThreadLocalRequest().getSession();
	}
}
