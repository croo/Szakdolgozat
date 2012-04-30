package croo.szakdolgozat.server;

import us.monoid.json.JSONArray;
import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;
import croo.szakdolgozat.shared.TravelInfo;
import croo.szakdolgozat.shared.TravelInfos;

public class TravelInfoCreator
{
	public static TravelInfos fromJson(JSONObject json) throws JSONException
	{
		return createTravelInfos(json);
	}

	private static TravelInfos createTravelInfos(JSONObject json) throws JSONException
	{
		String routeName = json.getString("route");
		String travelDate = json.getString("date");
		TravelInfos infos = new TravelInfos(routeName, travelDate);

		JSONArray timetables = json.getJSONArray("timetable");
		for (int i = 0; i < timetables.length(); i++) {
			TravelInfo info = createTravelInfo(timetables.getJSONObject(i));
			infos.add(info);
		}
		return infos;
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

}
