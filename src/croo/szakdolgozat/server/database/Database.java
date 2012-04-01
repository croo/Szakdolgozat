package croo.szakdolgozat.server.database;

import croo.szakdolgozat.shared.Route;

public interface Database
{
	public boolean townExists(String town);

	public Route getRoute(String startTown, String endTown);
}
