package croo.szakdolgozat.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import croo.szakdolgozat.shared.Coordinate;
import croo.szakdolgozat.shared.InterestingPlace;
import croo.szakdolgozat.shared.Town;

public class TownTest
{

	private static final String NO_IMAGE_AVAILABLE = "http://www.findhomestaykorea.com/images/no_image.gif";
	private static final InterestingPlace EMPTY_PAGE_PLACE = new InterestingPlace("http://emptypage.org", "");
	private static final InterestingPlace INTERESTING_PLACE_1 = new InterestingPlace("http://www.thedailywtf.com",
			"The Daily WTF", "From programmers to programmers", NO_IMAGE_AVAILABLE);
	private static final InterestingPlace INTERESTING_PLACE_2 = new InterestingPlace("http://www.cracked.com", "Cracked",
			"Americas only humor site since 1777", NO_IMAGE_AVAILABLE);
	private static final InterestingPlace INTERESTING_PLACE_3 = new InterestingPlace("http://www.9gag.com", "9-Gag",
			"Best of the worst", NO_IMAGE_AVAILABLE);

	private static final String badly_formatted_name = "   	BuDaPeST  	 ";
	ArrayList<InterestingPlace> places;

	@Before
	public void SetUp()
	{
		places = new ArrayList<InterestingPlace>();
		places.add(INTERESTING_PLACE_1);
		places.add(INTERESTING_PLACE_2);
		places.add(INTERESTING_PLACE_3);
	}

	@Test
	public void townNameShouldBeWellFormatted()
	{
		Town town = new Town(null, badly_formatted_name);
		assertEquals("Budapest", town.getName());
	}

	@Test
	public void shouldGiveBackInterestingPages()
	{
		Town town = new Town(null, "Budapest", places);
		assertTrue(town.getInterestingPlaces().contains(INTERESTING_PLACE_1));
		assertTrue(town.getInterestingPlaces().contains(INTERESTING_PLACE_3));
		assertTrue(town.getInterestingPlaces().contains(INTERESTING_PLACE_2));
	}

	@Test
	public void IfInitalizedShouldNotContainEmptyPage()
	{
		Town town = new Town(null, "Budapest", places);
		assertFalse(town.getInterestingPlaces().contains(EMPTY_PAGE_PLACE));
	}

	@Test
	public void ifInterestingPlacesIsntInitialisedShouldGiveBackListWithEmptyPage()
	{
		Town town = new Town(null, "Budapest");

		assertTrue(town.getInterestingPlaces().contains(EMPTY_PAGE_PLACE));
	}

	@Test
	public void townsShouldBeEqual()
	{
		Town town1 = new Town(new Coordinate(47.49841, 19.04076), "Budapest");
		Town town2 = new Town(new Coordinate(47.49841, 19.04076), "Budapest");
		assertEquals(town1, town2);
	}

	@Test
	public void townsShouldNotBeEqualIfNameNotEqual()
	{
		Town town1 = new Town(new Coordinate(47.49841, 19.04076), "Budapest-Kozepe");
		Town town2 = new Town(new Coordinate(47.49841, 19.04076), "Budapest");
		assertFalse(town1.equals(town2));
	}

	@Test
	public void townsShouldNotBeEqualIfCoordinateNotEqual()
	{
		Town town1 = new Town(new Coordinate(47.49841, 19.04076), "Budapest");
		Town town2 = new Town(new Coordinate(47.49841, 20.04076), "Budapest");
		assertFalse(town1.equals(town2));
	}
}
