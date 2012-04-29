package croo.szakdolgozat.server;

import java.util.Date;

import javax.servlet.http.HttpSession;

import us.monoid.json.JSONArray;
import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;
import croo.szakdolgozat.shared.TravelInfo;
import croo.szakdolgozat.shared.TravelInfos;

public class ElviraApi
{
	private static final String ELVIRA_API_BASE_URL = "http://api.oroszi.net/elvira?";

	public static String buildQueryURL(HttpSession session)
	{
		String from = createUrlParameter(session, "from", "startTown");
		String to = createUrlParameter(session, "to", "endTown");
		String type = "type=0"; // createUrlParameter("type", "discountRate");
		String date = createDateUrlParameter(session);
		return ELVIRA_API_BASE_URL + from + '&' + to + '&' + date + '&' + type;
	}

	private static String createDateUrlParameter(HttpSession session)
	{
		Date d;
		if (session.getAttribute("date") != null) {
			d = (Date) session.getAttribute("date");
		} else {
			d = new Date();
		}
		String date = "date=" + (1900 + d.getYear()) + '.' + (d.getMonth() + 1) + '.' + d.getDay();
		return date;
	}

	private static String createUrlParameter(HttpSession session, String parameterName, String sessionAttributeKey)
	{
		if (session.getAttribute(sessionAttributeKey) != null) {
			return parameterName + "=" + (String) session.getAttribute(sessionAttributeKey);
		} else if (parameterName.equals("type")) {
			return parameterName + "=0";
		} else {
			return parameterName + "=\"\"";
		}
	}

	public static TravelInfos getTravelInfosFromJson(HttpSession session, JSONObject json) throws JSONException
	{
		TravelInfos infos = createTravelInfos(session, json);
		return infos;
	}

	private static TravelInfos createTravelInfos(HttpSession session, JSONObject json) throws JSONException
	{
		String startTown = (String) session.getAttribute("startTown");
		String endTown = (String) session.getAttribute("endTown");
		Date date = (Date) session.getAttribute("date");
		TravelInfos infos = new TravelInfos(startTown, endTown, date.toString());
		loadJsonIntoTravelInfos(json, infos);
		return infos;
	}

	private static void loadJsonIntoTravelInfos(JSONObject json, TravelInfos infos) throws JSONException
	{
		JSONArray timeTables = json.getJSONArray("timetable");
		for (int i = 0; i < timeTables.length(); i++) {
			TravelInfo info = createTravelInfo(timeTables.getJSONObject(i));
			infos.add(info);
		}
	}

	private static TravelInfo createTravelInfo(JSONObject timeTable) throws JSONException
	{
		String travelClass = timeTable.getString("class");
		String price = (travelClass.equals("1.")) ? timeTable.getString("cost1st") : timeTable.getString("cost2nd");
		String startTime = timeTable.getString("starttime");
		String endTime = timeTable.getString("destinationtime");
		String travelTime = timeTable.getString("totaltime");
		String distance = timeTable.getString("distance");

		JSONArray details = timeTable.getJSONArray("details");
		JSONObject startStationDetail = details.getJSONObject(0);
		String startStation = startStationDetail.getString("start");
		String trainInfo = startStationDetail.getString("traininfo");
		String startPlatform = startStationDetail.getString("platform");
		startPlatform = startPlatform.isEmpty() ? "-" : startPlatform;

		JSONObject endStationDetail = details.getJSONObject(1);
		String endStation = endStationDetail.getString("start");

		TravelInfo info = new TravelInfo(travelClass, price, startTime, endTime, travelTime, distance, startStation,
				endStation, trainInfo, startPlatform);
		return info;
	}

	/**
	 * 
	 * The third "String date" parameter should be in yyyy.mm.dd format.<br>
	 * Example: 2012.05.12 or 2012.5.12
	 * 
	 * @param date
	 * @return
	 */
	public static JSONObject getJson(String startTown, String endTown, String date)
	{
		return null;
	}
}
