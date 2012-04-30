package croo.szakdolgozat.server;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import us.monoid.json.JSONObject;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import croo.szakdolgozat.client.stubs.TravelService;
import croo.szakdolgozat.shared.TravelInfos;

@SuppressWarnings("serial")
public class TravelServiceImpl extends RemoteServiceServlet implements TravelService
{

	@Override
	public TravelInfos getTravelInfos() throws Exception
	{
		JSONObject json = ElviraApi.getJson(getString("startTown"), getString("endTown"), getDateInGoodFormat(), 27);
		TravelInfos infos = TravelInfoCreator.fromJson(json);

		return infos;
	}

	private String getDateInGoodFormat()
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
		return format.format(getDate("date"));
	}

	private String getString(String key)
	{
		return (String) session().getAttribute(key);
	}

	private Date getDate(String key)
	{
		return (Date) session().getAttribute(key);
	}

	private Integer getInt(String key)
	{
		return (Integer) session().getAttribute(key);
	}

	private HttpSession session()
	{
		return this.getThreadLocalRequest().getSession();
	}
}
