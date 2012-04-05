package croo.szakdolgozat.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.junit.GWTMockUtilities;

import croo.szakdolgozat.server.MapServiceImpl;
import croo.szakdolgozat.server.database.MockDatabase;
import croo.szakdolgozat.shared.Coordinate;
import croo.szakdolgozat.shared.Route;

public class MapServiceImplTest
{

	private MapServiceImpl mapService;

	@Before
	public void setUp()
	{
		GWTMockUtilities.disarm();
		mapService = new MapServiceImpl();
		mapService.setDatabase(new MockDatabase());
	}

	@After
	public void tearDown()
	{
		GWTMockUtilities.restore();
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
		String startTown = "Budapest";
		String destinationTown = "Esztergom";
		Route route = mapService.getRoute(startTown, destinationTown);
		assertEquals(startTown, route.getStartTown().getName());
		assertEquals(destinationTown, route.getEndTown().getName());
	}

	@Test
	public void routeShouldContainTheCoordinatesOfTowns()
	{
		String startTown = "Budapest";
		String destinationTown = "Esztergom";
		Route route = mapService.getRoute(startTown, destinationTown);

		route.getStartTown().getCoordinate().equals(new Coordinate(47.49841, 19.04076));
		route.getEndTown().getCoordinate().equals(new Coordinate(47.7776069, 18.7435935));
	}

	@Test
	public void routeShouldContainAllTheCoordinatesBetweenTowns()
	{
		String startTown = "Budapest";
		String destinationTown = "Esztergom";
		Route route = mapService.getRoute(startTown, destinationTown);

		ArrayList<Coordinate> routeway = route.getRouteway();
		assertEquals(5, routeway.size());
	}

	@Test
	public void firstAndLastRouteCoordinateShouldBeTheTownCoordinates()
	{
		String startTown = "Budapest";
		String destinationTown = "Esztergom";
		Route route = mapService.getRoute(startTown, destinationTown);

		ArrayList<Coordinate> routeway = route.getRouteway();
		assertEquals(routeway.get(0), route.getStartTown().getCoordinate());
		assertEquals(routeway.get(routeway.size() - 1), route.getEndTown().getCoordinate());

	}
}
