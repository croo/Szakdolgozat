package croo.szakdolgozat.client;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

	private static final String NON_EXISTING_STATION = "Mucsaröcsöge";
	private static final boolean WITHOUT_TRANSFER = true;
	private static final String TYPE = "27";
	private static final Date DATE = new Date();
	private static final String ESZTERGOM = "esztergom";
	private static final String BUDAPEST = "budapest";
	private JSONObject json_reference;

	@Before
	public void SetUp()
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
		try {
			json_reference = new Resty().json(
					"http://api.oroszi.net/elvira?from=" + BUDAPEST + "&to=" + ESZTERGOM + "&date=" + format.format(DATE)
							+ "&type=27&wotransfer=1").object();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (JSONException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testJsonQueryWith3ParametersShouldRunWithoutProblem() throws JSONException
	{
		JSONObject json = null;
		try {
			json = ElviraApi.getJson(BUDAPEST, ESZTERGOM, DATE);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (JSONException e) {
			System.out.println(e.getMessage());
		}
		assertEquals(json.toString(), json_reference.toString());
	}

	@Test
	public void testJsonQueryWith4Parameters() throws JSONException
	{
		JSONObject json = null;
		try {
			json = ElviraApi.getJson(BUDAPEST, ESZTERGOM, DATE, TYPE);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		assertEquals(json.toString(), json_reference.toString());
	}

	@Test
	public void testJsonQueryWith5Parameters() throws JSONException
	{
		JSONObject json = null;
		try {
			json = ElviraApi.getJson(BUDAPEST, ESZTERGOM, DATE, TYPE, WITHOUT_TRANSFER);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		assertEquals(json.toString(), json_reference.toString());
	}

	@Test(expected = IOException.class)
	public void queryWithNotExistingStationShouldGiveIOException() throws IOException
	{
		try {
			JSONObject json = ElviraApi.getJson(NON_EXISTING_STATION, ESZTERGOM, DATE, TYPE, WITHOUT_TRANSFER);
		} catch (JSONException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void ifTheDestinationAndStartStationIsSameShouldReturnEmptyList() throws JSONException
	{
		JSONObject json = null;
		try {
			json = ElviraApi.getJson(ESZTERGOM, ESZTERGOM, DATE, TYPE, WITHOUT_TRANSFER);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		assertEquals(null, json);
	}

	@Test
	public void queryWithBadTypeShouldGiveBackInfoWithType27() throws JSONException
	{
		JSONObject json = null;
		try {
			final String BAD_TYPE = "51234";
			json = ElviraApi.getJson(BUDAPEST, ESZTERGOM, DATE, BAD_TYPE, WITHOUT_TRANSFER);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		System.out.println(json_reference);
		System.out.println(json);
		assertEquals(json.toString(), json_reference.toString());
	}

	@Test
	public void queryWithOutdatedDateShouldReturnEmptyList() throws JSONException
	{
		Date outdatedDate = new Date(123);
		JSONObject json = null;
		try {
			json = ElviraApi.getJson(BUDAPEST, ESZTERGOM, outdatedDate, TYPE, WITHOUT_TRANSFER);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		System.out.println(json);
		assertEquals(json, null);
	}

}
