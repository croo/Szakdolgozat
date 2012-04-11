package croo.szakdolgozat.server.database;

import java.util.ArrayList;
import java.util.HashMap;

import croo.szakdolgozat.shared.Coordinate;
import croo.szakdolgozat.shared.InterestingPlace;
import croo.szakdolgozat.shared.Route;
import croo.szakdolgozat.shared.Town;

public class MockDatabase implements Database
{
	HashMap<String, Town> db = new HashMap<String, Town>();

	public MockDatabase()
	{
		ArrayList<InterestingPlace> placesInBudapest = new ArrayList<>();
		placesInBudapest.add(new InterestingPlace("http://budapest.hu", "Budapest", "A fõváros honlapja."));
		placesInBudapest.add(new InterestingPlace("http://skanzen.hu", "Skanzen Klub", "A város egyetlen dohányzó kocsmája."));
		placesInBudapest.add(new InterestingPlace("http://index.hu", "Hírek mindenrõl", "A leglátogatottabb bulvár oldal."));

		db.put("budapest", new Town(new Coordinate(47.49841, 19.04076), "budapest", placesInBudapest));
		db.put("esztergom", new Town(new Coordinate(47.7776069, 18.7435935), "esztergom"));
	}

	@Override
	public boolean townExists(String town)
	{
		return db.containsKey(town);
	}

	@Override
	public Route getRoute(String startTownName, String endTownName)
	{
		ArrayList<Coordinate> routeway = new ArrayList<Coordinate>();
		routeway.add(db.get(startTownName).getCoordinate());
		routeway.add(new Coordinate(47.500001, 18.800001));
		routeway.add(new Coordinate(47.600001, 18.990001));
		routeway.add(new Coordinate(47.550001, 19.01301));
		routeway.add(db.get(endTownName).getCoordinate());
		// sortCoordinatesInRouteway(routeway);

		return new Route(db.get(startTownName), db.get(endTownName), routeway);
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
