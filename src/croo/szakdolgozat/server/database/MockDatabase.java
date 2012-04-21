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
		ArrayList<InterestingPlace> placesInBudapest = new ArrayList<InterestingPlace>();
		placesInBudapest.add(new InterestingPlace("http://budapest.hu", "Budapest", "A f�v�ros honlapja."));
		placesInBudapest.add(new InterestingPlace("http://skanzen.hu", "Skanzen Klub", "A v�ros egyetlen doh�nyz� kocsm�ja."));
		placesInBudapest.add(new InterestingPlace("http://index.hu", "H�rek mindenr�l", "A legl�togatottabb bulv�r oldal."));

		db.put("budapest", new Town(new Coordinate(47.49841, 19.04076), "budapest", placesInBudapest));
		db.put("esztergom", new Town(new Coordinate(47.7776069, 18.7435935), "esztergom"));
	}

	@Override
	public boolean townExists(String town)
	{
		return db.containsKey(formatted(town));
	}

	@Override
	public Route getRoute(String startTownName, String endTownName)
	{
		ArrayList<Coordinate> routeway = new ArrayList<Coordinate>();
		routeway.add(db.get(formatted(startTownName)).getCoordinate());
		routeway.add(new Coordinate(47.500001, 18.800001));
		routeway.add(new Coordinate(47.600001, 18.990001));
		routeway.add(new Coordinate(47.550001, 19.01301));
		routeway.add(db.get(formatted(endTownName)).getCoordinate());

		return new Route(db.get(formatted(startTownName)), db.get(formatted(endTownName)), routeway);
	}	
	
	private String formatted(String text)
	{
		return text.trim().toLowerCase();
	}
}
