package croo.szakdolgozat.server;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;
import us.monoid.web.Resty;

public class ElviraApi
{
	private static final String ELVIRA_API_BASE_URL = "http://api.oroszi.net/elvira?";
	private static final String EMPTY_JSON_RESULT = "{\"date\":\"\",\"route\":\"\",\"timetable\":[]}";

	public static JSONObject getJson(String startTown, String endTown, Date date) throws IOException, JSONException
	{
		String queryUrl = buildQueryUrl(startTown, endTown, date, "27", true);
		return executeQuery(queryUrl);
	}

	public static JSONObject getJson(String startTown, String endTown, Date date, String type) throws IOException,
			JSONException
	{
		String queryUrl = buildQueryUrl(startTown, endTown, date, type, true);
		return executeQuery(queryUrl);
	}

	public static JSONObject getJson(String startTown, String endTown, Date date, String type, Boolean withoutTransfer)
			throws IOException, JSONException
	{
		String queryUrl = buildQueryUrl(startTown, endTown, date, type, withoutTransfer);
		return executeQuery(queryUrl);
	}

	private static String buildQueryUrl(String start, String end, Date inputDate, String inputType, Boolean withoutTransfer)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
		String fromStart = "from=" + start;
		String toEnd = "to=" + end;
		String date = "date=" + format.format(inputDate);
		String type = "type=" + inputType;
		String transfer = "wotransfer=" + (withoutTransfer ? "1" : "0");
		return ELVIRA_API_BASE_URL + fromStart + '&' + toEnd + '&' + date + '&' + type + '&' + transfer;
	}

	private static JSONObject executeQuery(String queryUrl) throws IOException, JSONException
	{
		JSONObject json = new Resty().json(queryUrl).object();
		if (json.toString().equals(EMPTY_JSON_RESULT))
			return null;
		else if (json.has("error") && json.getString("error").equals("1"))
			throw new IOException("Az állomás nem található az Elvira adatbázisban.");
		else
			return json;
	}
}
