package croo.szakdolgozat.server;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import croo.szakdolgozat.client.stubs.TravelService;
import croo.szakdolgozat.shared.TravelInfo;

@SuppressWarnings("serial")
public class TravelServiceImpl extends RemoteServiceServlet implements TravelService
{

	private static final String ELVIRA_API_BASE_URL = "http://api.oroszi.net/elvira?";

	public ArrayList<TravelInfo> getTravelInfos()
	{
		// RequestBuilder builder = new
		// RequestBuilder(RequestBuilder.GET,"http://api.oroszi.com/elvira");
		// builder.setHeader("Content-Type", "application/json");
		// try {
		// builder.sendRequest("", new RequestCallback() {
		// @Override
		// public void onResponseReceived(Request arg0, Response arg1) {
		// // TODO Auto-generated method stub
		// }
		//
		// @Override
		// public void onError(Request arg0, Throwable arg1) {
		// // TODO Auto-generated method stub
		// }
		// });
		// } catch (RequestException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		ArrayList<TravelInfo> infos = new ArrayList<TravelInfo>();
		TravelInfo a = new TravelInfo();
		a.test = buildQueryURL();
		infos.add(a);
		return infos;
	}

	private String buildQueryURL()
	{
		String from = createUrlParameter("from", "startTown");
		String to = createUrlParameter("to", "endTown");
		String type = "type=0"; // createUrlParameter("type", "discountRate");

		String date = createDateUrlParameter();

		return ELVIRA_API_BASE_URL + from + '&' + to + '&' + date + '&' + type;
	}

	private String createDateUrlParameter()
	{
		Date d;
		if (session().getAttribute("date") != null) {
			d = (Date) session().getAttribute("date");
		} else {
			d = new Date();
		}
		String date = "date=" + (1900 + d.getYear()) + '.' + d.getMonth() + '.' + d.getDay();
		return date;
	}

	private String createUrlParameter(String parameterName, String sessionAttributeKey)
	{
		if (session().getAttribute(sessionAttributeKey) != null) {
			return parameterName + "=" + (String) session().getAttribute(sessionAttributeKey);
		} else if (parameterName.equals("type")) {
			return parameterName + "=0";
		} else {
			return parameterName + "=\"\"";
		}
	}

	private HttpSession session()
	{
		return this.getThreadLocalRequest().getSession();
	}
}
