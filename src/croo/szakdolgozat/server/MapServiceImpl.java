package croo.szakdolgozat.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import croo.szakdolgozat.client.stubs.MapService;
import croo.szakdolgozat.server.database.Database;
import croo.szakdolgozat.server.database.MockDatabase;
import croo.szakdolgozat.shared.Route;

/**
 * The server side implementation of the RPC service.
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
		if (bothLocationExists) {
			return createResultWithWellFormattedTownNames(database.getRoute(startTown, destinationTown));
		} else {
			return null;
		}
	}

	private Route createResultWithWellFormattedTownNames(Route result)
	{
		String wellFormattedStartTown = result.getStartTown().getName();
		String wellFormattedEndTown = result.getEndTown().getName();
		wellFormattedStartTown = wellFormattedStartTown.substring(0, 1).toUpperCase() + wellFormattedStartTown.substring(1);
		wellFormattedEndTown = wellFormattedEndTown.substring(0, 1).toUpperCase() + wellFormattedEndTown.substring(1);
		result.getStartTown().setName(wellFormattedStartTown);
		result.getEndTown().setName(wellFormattedEndTown);
		return result;
	}

	public void setDatabase(Database database)
	{
		this.database = database;
	}
}
