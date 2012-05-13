package croo.szakdolgozat.client;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;
import croo.szakdolgozat.server.TravelInfoCreator;
import croo.szakdolgozat.shared.TravelInfo;
import croo.szakdolgozat.shared.TravelInfos;

public class TravelInfoCreatorTest
{

	private static final String JSON_SOURCE = "{\"date\":\"2012.05.01, kedd�tsz�ll�s n�lk�l;\",\"route\":\"BUDAPEST* - Esztergom\","
			+ "\"timetable\":["
			+ "{\"change\":\"\",\"class\":\"1.\",\"class_name\":\"1.\",\"cost1st\":\"2.120 Ft\",\"cost2nd\":\"\","
			+ "\"destination\":\"\",\"destinationtime\":\"06:17\","
			+ "\"details\":[{\"platform\":\"3\","
			+ "\"start\":\"Budapest-Nyugati\",\"starttime\":\"04:36\",\"starttime_real\":\"04:36\","
			+ "\"traininfo\":\"2010 személy ( - Esztergom)\"},"
			+ "{\"platform\":\"\",\"start\":\"Esztergom\",\"starttime\":\"06:17\",\"starttime_real\":\"06:18\""
			+ ",\"traininfo\":\"\"}],\"distance\":\"53 km\",\"reservation\":\"-\",\"start\":\"Budapest-Nyugati\","
			+ "\"starttime\":\"04:36\",\"ticket\":null,\"totaltime\":\"1:41\"},{\"change\":\"\","
			+ "\"class\":\"2.\",\"class_name\":\"2.\",\"cost1st\":\"\",\"cost2nd\":\"1.120 Ft\","
			+ "\"destination\":\"\",\"destinationtime\":\"07:42\",\"details\":[{\"platform\":\"3\","
			+ "\"start\":\"Budapest-Nyugati\",\"starttime\":\"06:10\",\"starttime_real\":\"06:12\","
			+ "\"traininfo\":\"2040 személy ( - Esztergom)\"},{\"platform\":\"\",\"start\":\"Esztergom\","
			+ "\"starttime\":\"07:42\",\"starttime_real\":\"07:42\",\"traininfo\":\"\"}],"
			+ "\"distance\":\"53 km\",\"reservation\":\"-\",\"start\":\"Budapest-Nyugati\","
			+ "\"starttime\":\"06:10\",\"ticket\":null,\"totaltime\":\"1:32\"}]}";
	private static JSONObject json = null;
	private static TravelInfos infos = null;

	@Before
	public void SetUp()
	{

		try {
			json = new JSONObject(JSON_SOURCE);
			infos = TravelInfoCreator.createTravelInfos(json);
		} catch (JSONException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testMainDataIsGood()
	{
		assertEquals(infos.getRouteName(), "BUDAPEST* - Esztergom");
		assertEquals(infos.getTravelDate(), "2012.05.01, kedd");
		assertEquals(infos.getTravelInfos().size(), 2);
	}

	@Test
	public void testTravelInfoRowsThatShouldBeEqual()
	{
		ArrayList<TravelInfo> rows = infos.getTravelInfos();
		assertEquals(rows.get(0).getDistance(), "53 km");
		assertEquals(rows.get(1).getDistance(), "53 km");
		assertEquals(rows.get(0).getStartStation(), "Budapest-Nyugati");
		assertEquals(rows.get(1).getStartStation(), "Budapest-Nyugati");
		assertEquals(rows.get(0).getEndStation(), "Esztergom");
		assertEquals(rows.get(1).getEndStation(), "Esztergom");
	}

	@Test
	public void testTravelInfoRowsThatShouldDiffer()
	{
		ArrayList<TravelInfo> rows = infos.getTravelInfos();
		assertEquals(rows.get(0).getPrice(), "2.120 Ft");
		assertEquals(rows.get(1).getPrice(), "1.120 Ft");
		assertEquals(rows.get(0).getTrainInfo(), "2010 személy ( - Esztergom)");
		assertEquals(rows.get(1).getTrainInfo(), "2040 személy ( - Esztergom)");
	}

}
