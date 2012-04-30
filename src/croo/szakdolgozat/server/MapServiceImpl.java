package croo.szakdolgozat.server;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import croo.szakdolgozat.client.stubs.MapService;
import croo.szakdolgozat.server.database.Database;
import croo.szakdolgozat.server.database.RdfDatabase;
import croo.szakdolgozat.shared.Route;

/**
 * The server side implementation of the MapService service.
 */
@SuppressWarnings("serial")
public class MapServiceImpl extends RemoteServiceServlet implements MapService
{

	private Database database = new RdfDatabase();

	@Override
	public Boolean verifyLocation(String location)
	{
		return database.townExists(location);
	}

	@Override
	public Route getRoute(String startTown, String endTown)
	{
		if (bothLocationExists(startTown, endTown)) {
			if (session() != null) {
				session().setAttribute("startTown", startTown);
				session().setAttribute("endTown", endTown);
			}

			return database.getRoute(startTown, endTown);
		} else
			return null;
	}

	public void setDatabase(Database db)
	{
		database = db;
	}

	private Boolean bothLocationExists(String startTown, String endTown)
	{
		return verifyLocation(startTown) && verifyLocation(endTown);
	}

	private HttpSession session()
	{
		if (this.getThreadLocalRequest() != null)
			return this.getThreadLocalRequest().getSession();
		else
			return null;
	}
}
