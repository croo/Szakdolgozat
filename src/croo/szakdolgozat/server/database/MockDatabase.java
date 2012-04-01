package croo.szakdolgozat.server.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import croo.szakdolgozat.shared.Coordinates;
import croo.szakdolgozat.shared.Route;

public class MockDatabase implements Database
{
	Map<String, Coordinates> db = new HashMap<String, Coordinates>();

	public MockDatabase()
	{
		db.put("budapest", new Coordinates(47.309, 19.500));
		db.put("esztergom", new Coordinates(47.7776069, 18.7435935));
	}

	@Override
	public boolean townExists(String town)
	{
		return db.containsKey(town);
	}

	@Override
	public Route getRoute(String startTown, String endTown)
	{
		ArrayList<Coordinates> routeway = new ArrayList<Coordinates>();
		routeway.add(db.get(startTown));
		routeway.add(new Coordinates(47.500001, 18.800001));
		routeway.add(new Coordinates(47.600001, 18.990001));
		routeway.add(new Coordinates(47.400001, 19.20001));
		routeway.add(db.get(endTown));
		return new Route(startTown, endTown, routeway);
	}
}
