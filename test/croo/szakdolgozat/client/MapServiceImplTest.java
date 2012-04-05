package croo.szakdolgozat.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import croo.szakdolgozat.server.MapServiceImpl;
import croo.szakdolgozat.server.database.MockDatabase;
import croo.szakdolgozat.shared.Coordinate;
import croo.szakdolgozat.shared.Route;

public class MapServiceImplTest
{

	private MapServiceImpl mapService;
	private String startTown;
	private String destinationTown;

	@Before
	public void setUp()
	{
		startTown = "Budapest";
		destinationTown = "Esztergom";

		mapService = new MapServiceImpl();
		mapService.setDatabase(new MockDatabase());
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
		assertFalse(mapService.verifyLocation("Mucsaröcsöge"));
	}

	@Test
	public void routeShouldContainTheSameTownsAsInputButWellFormatted()
	{
		Route route = mapService.getRoute(startTown, destinationTown);

		assertEquals(startTown, route.getStartTown().getName());
		assertEquals(destinationTown, route.getEndTown().getName());
	}

	@Test
	public void routeShouldContainTheCoordinatesOfTowns()
	{
		Route route = mapService.getRoute(startTown, destinationTown);

		assertEquals(route.getStartTown().getCoordinate(), new Coordinate(47.49841, 19.04076));
		assertEquals(route.getEndTown().getCoordinate(), new Coordinate(47.7776069, 18.7435935));
	}

	@Test
	public void routeShouldContainAllTheCoordinatesBetweenTowns()
	{
		Route route = mapService.getRoute(startTown, destinationTown);

		ArrayList<Coordinate> routeway = route.getRouteway();
		assertEquals(5, routeway.size());
	}

	@Test
	public void firstAndLastRouteCoordinateShouldBeTheTownCoordinates()
	{
		Route route = mapService.getRoute(startTown, destinationTown);

		ArrayList<Coordinate> routeway = route.getRouteway();
		assertEquals(routeway.get(0), route.getStartTown().getCoordinate());
		assertEquals(routeway.get(routeway.size() - 1), route.getEndTown().getCoordinate());

	}
}
