package croo.szakdolgozat.client;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;
import us.monoid.web.Resty;
import croo.szakdolgozat.server.ElviraApi;

/**
 * To run these tests you need internet connection and access to
 * http://api.oroszi.net/elvira REST API.
 * 
 * @author Croo
 * 
 */

public class ElviraApiTest
{

	private static final String EMPTY_JSON_RESULT = "{\"date\":\"\",\"route\":\"\",\"timetable\":[]}";
	private static final String NON_EXISTING_STATION = "Mucsaröcsöge";
	private static final boolean WITHOUT_TRANSFER = true;
	private static final int TYPE = 27;
	private static final String DATE = "2012.05.01";
	private static final String ESZTERGOM = "esztergom";
	private static final String BUDAPEST = "budapest";
	private JSONObject json_reference;

	@Before
	public void SetUp()
	{
		try {
			json_reference = new Resty().json(
					"http://api.oroszi.net/elvira?from=" + BUDAPEST + "&to=" + ESZTERGOM + "&date=" + DATE
							+ "&type=27&wotransfer=1").object();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (JSONException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testJsonQueryWith3Parameters() throws JSONException
	{
		JSONObject json = null;
		try {
			json = ElviraApi.getJson(BUDAPEST, ESZTERGOM, DATE);
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		assertEquals(json.toString(), json_reference.toString());
	}

	@Test
	public void testJsonQueryWith4Parameters() throws JSONException
	{
		JSONObject json = null;
		try {
			json = ElviraApi.getJson(BUDAPEST, ESZTERGOM, DATE, TYPE);
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		assertEquals(json.toString(), json_reference.toString());
	}

	@Test
	public void testJsonQueryWith5Parameters() throws JSONException
	{
		JSONObject json = null;
		try {
			json = ElviraApi.getJson(BUDAPEST, ESZTERGOM, DATE, TYPE, WITHOUT_TRANSFER);
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		assertEquals(json.toString(), json_reference.toString());
	}

	@Test
	public void queryWithNotExistingStationShouldGiveJsonWithErrorCode() throws JSONException
	{
		JSONObject json = null;
		try {
			json = ElviraApi.getJson(NON_EXISTING_STATION, ESZTERGOM, DATE, TYPE, WITHOUT_TRANSFER);
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		assertEquals(json.getString("error"), "1");
		assertEquals(json.getString("errormsg"), "ismeretlen állomásnév");
	}

	@Test
	public void ifTheDestinationAndStartStationIsSameShouldReturnEmptyList() throws JSONException
	{
		JSONObject json = null;
		try {
			json = ElviraApi.getJson(ESZTERGOM, ESZTERGOM, DATE, TYPE, WITHOUT_TRANSFER);
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}

		assertEquals(json.toString(), EMPTY_JSON_RESULT);
	}

	@Test
	public void queryWithBadTypeShouldGiveBackInfoWithType27() throws JSONException
	{
		JSONObject json = null;
		try {
			json = ElviraApi.getJson(BUDAPEST, ESZTERGOM, DATE, 51234, WITHOUT_TRANSFER);
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		System.out.println(json);
		assertEquals(json.toString(), json_reference.toString());
	}

	@Test
	public void queryWithOutdatedDateShouldReturnEmptyList() throws JSONException
	{
		JSONObject json = null;
		try {
			json = ElviraApi.getJson(BUDAPEST, ESZTERGOM, "2010.05.01", TYPE, WITHOUT_TRANSFER);
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		System.out.println(json);
		assertEquals(json.toString(), EMPTY_JSON_RESULT);
	}

}
