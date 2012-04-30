package croo.szakdolgozat.server;

import java.io.IOException;

import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;
import us.monoid.web.Resty;

public class ElviraApi
{
	private static final String ELVIRA_API_BASE_URL = "http://api.oroszi.net/elvira?";

	public static JSONObject getJson(String startTown, String endTown, String date) throws IOException, JSONException
	{
		String queryUrl = buildQueryUrl(startTown, endTown, date, 27, true);
		return new Resty().json(queryUrl).object();
	}

	public static JSONObject getJson(String startTown, String endTown, String date, Integer type) throws IOException,
			JSONException
	{
		String queryUrl = buildQueryUrl(startTown, endTown, date, type, true);
		return new Resty().json(queryUrl).object();
	}

	public static JSONObject getJson(String startTown, String endTown, String date, Integer type, Boolean withoutTransfer)
			throws IOException, JSONException
	{
		String queryUrl = buildQueryUrl(startTown, endTown, date, type, withoutTransfer);
		return new Resty().json(queryUrl).object();
	}

	private static String buildQueryUrl(String start, String end, String inputDate, Integer inputType, Boolean withoutTransfer)
	{
		String fromStart = "from=" + start;
		String toEnd = "to=" + end;
		String date = "date=" + inputDate;
		String type = "type=" + inputType;
		String transfer = "wotransfer=" + (withoutTransfer ? "1" : "0");
		return ELVIRA_API_BASE_URL + fromStart + '&' + toEnd + '&' + date + '&' + type + '&' + transfer;
	}
}
