package croo.szakdolgozat.server;

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
	public Route getRoute(String startTown, String destinationTown)
	{
		startTown = startTown.trim().toLowerCase();
		destinationTown = destinationTown.trim().toLowerCase();
		Boolean bothLocationExists = verifyLocation(startTown) && verifyLocation(destinationTown);
		if (bothLocationExists)
			return database.getRoute(startTown, destinationTown);
		else
			return null;
	}

	public void setDatabase(Database database)
	{
		this.database = database;
	}
}
