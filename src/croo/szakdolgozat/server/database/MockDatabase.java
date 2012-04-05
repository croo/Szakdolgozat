package croo.szakdolgozat.server.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import croo.szakdolgozat.shared.Coordinate;
import croo.szakdolgozat.shared.Route;

public class MockDatabase implements Database
{
	Map<String, Coordinate> db = new HashMap<String, Coordinate>();

	public MockDatabase()
	{
		db.put("budapest", new Coordinate(47.49841, 19.04076));
		db.put("esztergom", new Coordinate(47.7776069, 18.7435935));
	}

	@Override
	public boolean townExists(String town)
	{
		return db.containsKey(town);
	}

	@Override
	public Route getRoute(String startTown, String endTown)
	{
		ArrayList<Coordinate> routeway = new ArrayList<Coordinate>();
		routeway.add(db.get(startTown));
		routeway.add(new Coordinate(47.500001, 18.800001));
		routeway.add(new Coordinate(47.600001, 18.990001));
		routeway.add(new Coordinate(47.550001, 19.01301));
		routeway.add(db.get(endTown));
		// sortCoordinatesInRouteway(routeway);

		return new Route(startTown, endTown, routeway);
	}

	// private void sortCoordinatesInRouteway(ArrayList<Coordinate> routeway)
	// {
	// Collections.sort(routeway, new Comparator<Coordinate>() {
	// @Override
	// public int compare(Coordinate a, Coordinate b)
	// {
	// return a.getLongitude().compareTo(b.getLongitude());
	// }
	// });
	// }
}
