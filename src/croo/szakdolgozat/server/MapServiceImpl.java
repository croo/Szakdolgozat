package croo.szakdolgozat.server;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import croo.szakdolgozat.client.stubs.MapService;
import croo.szakdolgozat.server.database.Database;
import croo.szakdolgozat.server.database.DatabaseFactory;
import croo.szakdolgozat.shared.Route;

/**
 * The server side implementation of the MapService service.
 */
@SuppressWarnings("serial")
public class MapServiceImpl extends RemoteServiceServlet implements MapService
{

	private static final Database database = new DatabaseFactory().createRdfDatabase();

	@Override
	public Boolean verifyLocation(String location)
	{
		return database.townExists(formatted(location));
	}

	@Override
	public Route getRoute(String startTown, String endTown)
	{
		if (bothLocationExists(startTown, endTown)) {
			if (session() != null) { // while running JUnit test there are no
										// sessions. Thats why this check is
										// needed.
				session().setAttribute("startTown", formatted(startTown));
				session().setAttribute("endTown", formatted(endTown));
			}
			return database.getRoute(formatted(startTown), formatted(endTown));
		} else
			return null;
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

	private String formatted(String text)
	{
		return text.trim().toLowerCase();
	}
}
