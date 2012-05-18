package croo.szakdolgozat.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import croo.szakdolgozat.server.MapServiceImpl;
import croo.szakdolgozat.server.database.DatabaseFactory;
import croo.szakdolgozat.shared.Coordinate;
import croo.szakdolgozat.shared.Route;
import croo.szakdolgozat.shared.Town;

public class MapServiceImplTest
{

	private MapServiceImpl mapService;
	private static final String START_TOWN = "Esztergom";
	private static final String END_TOWN = "Budapest";

	@Before
	public void setUp() throws IOException
	{
		mapService = new MapServiceImpl(DatabaseFactory.createRdfDatabase());
	}

	@Test
	public void inputShouldBeValid()
	{
		assertTrue(mapService.verifyLocation("Budapest"));
	}

	@Test
	public void inputShouldBeValidRegardlessOfLetterCase()
	{
		assertTrue(mapService.verifyLocation("BuDaPeSt"));
	}

	@Test
	public void inputShouldBeValidRegardlessOfSpacesBeforeOrAfterInput()
	{
		assertTrue(mapService.verifyLocation("  BuDaPeSt  	"));
	}

	@Test
	public void inputShouldBeInvalid()
	{
		assertFalse(mapService.verifyLocation("asdf3456"));
	}

	@Test
	public void inputShouldBeInvalidIfNotFoundInDatabase()
	{
		assertFalse(mapService.verifyLocation("Mucsar�cs�ge"));
	}

	@Test
	public void routeShouldContainTheSameTownsAsInputButWellFormatted() throws Exception
	{
		Route route = mapService.getRoute(START_TOWN, END_TOWN);

		assertEquals(START_TOWN, route.getStartTown().getName());
		assertEquals(END_TOWN, route.getEndTown().getName());
	}

	@Test
	public void routeShouldContainTheCoordinatesOfTowns() throws Exception
	{
		Route route = mapService.getRoute(START_TOWN, END_TOWN);

		assertEquals(route.getStartTown().getCoordinate(), new Coordinate(47.49841, 19.04076));
		assertEquals(route.getEndTown().getCoordinate(), new Coordinate(47.7776069, 18.7435935));
	}

	@Test
	public void routeShouldContainAllTheCoordinatesBetweenTowns() throws Exception
	{
		Route route = mapService.getRoute(START_TOWN, END_TOWN);

		ArrayList<Coordinate> routeway = route.getRouteway();
		assertTrue(routeway.size() > 2);
	}

	@Test
	public void firstAndLastRouteCoordinateShouldBeTheTownCoordinates() throws Exception
	{
		Route route = mapService.getRoute(START_TOWN, END_TOWN);

		ArrayList<Coordinate> routeway = route.getRouteway();
		assertEquals(routeway.get(0), route.getStartTown().getCoordinate());
		assertEquals(routeway.get(routeway.size() - 1), route.getEndTown().getCoordinate());
	}

	@Test
	public void endTownInRouteShouldContainListOfInterestingPlaces() throws Exception
	{
		Route route = mapService.getRoute(START_TOWN, END_TOWN);
		Town endtown = route.getEndTown();
		assertFalse(endtown.getInterestingPlaces().isEmpty());
	}

	@Test
	public void ifOneOfTheTownsAreInvalidRouteShouldBeNull() throws Exception
	{
		Route route = mapService.getRoute(START_TOWN, "8argaer");
		assertEquals(route, null);
		route = mapService.getRoute("bebe Aghae", END_TOWN);
		assertEquals(route, null);
	}

	@Test
	public void ifBothTownsAreInvalidRouteShouldBeNull() throws Exception
	{
		Route route = mapService.getRoute("bebe Aghae", "8argaer");
		assertEquals(route, null);
	}
}
