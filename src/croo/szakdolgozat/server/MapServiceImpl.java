package croo.szakdolgozat.server;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import croo.szakdolgozat.client.stubs.MapService;
import croo.szakdolgozat.server.database.Database;
import croo.szakdolgozat.server.database.MockDatabase;
import croo.szakdolgozat.shared.Route;

/**
 * The server side implementation of the MapService service.
 */
@SuppressWarnings("serial")
public class MapServiceImpl extends RemoteServiceServlet implements MapService
{

	private Database database = new MockDatabase();

	@Override
	public Boolean verifyLocation(String location)
	{
		return database.townExists(location.trim().toLowerCase());
	}

	@Override
	public Route getRoute(String startTown, String endTown)
	{		
		if (bothLocationExists(startTown, endTown)) {
			session().setAttribute("startTown", startTown);
			session().setAttribute("endTown", endTown);
			return database.getRoute(startTown, endTown);
		} else
			return null;
	}

	public void setDatabase(Database database)
	{
		this.database = database;
	}

	private Boolean bothLocationExists(String startTown, String endTown) {
		return verifyLocation(startTown) && verifyLocation(endTown);
	}

	private HttpSession session()
	{
		return this.getThreadLocalRequest().getSession();
	}
}
