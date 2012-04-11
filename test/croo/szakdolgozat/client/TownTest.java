package croo.szakdolgozat.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import croo.szakdolgozat.shared.Town;

public class TownTest
{

	private static final String badly_formatted_name = "   	BuDaPeST  	 ";

	@Test
	public void townNameShouldBeWellFormatted()
	{
		Town town = new Town(null, badly_formatted_name);
		assertEquals("Budapest", town.getName());
	}

	@Test
	public void shouldGiveBackInterestingPages()
	{
		ArrayList<String> places = new ArrayList<String>();
		places.add("http://www.cracked.com");
		places.add("http://www.9gag.com");
		places.add("http://www.thedailywtf.com");

		Town town = new Town(null, "Budapest", places);
		assertTrue(town.getInterestingPlaceURIs().contains("http://www.9gag.com"));
		assertTrue(town.getInterestingPlaceURIs().contains("http://www.cracked.com"));
		assertTrue(town.getInterestingPlaceURIs().contains("http://www.thedailywtf.com"));

	}

	@Test
	public void IfInitalizedShouldntContainEmptyPage()
	{
		ArrayList<String> places = new ArrayList<String>();
		places.add("http://www.cracked.com");
		places.add("http://www.9gag.com");
		places.add("http://www.thedailywtf.com");

		Town town = new Town(null, "Budapest", places);
		assertFalse(town.getInterestingPlaceURIs().contains("http://emptypage.org"));
	}

	@Test
	public void ifInterestingPlacesIsntInitialisedShouldGiveBackListWithEmptyPage()
	{
		Town town = new Town(null, "Budapest");

		assertTrue(town.getInterestingPlaceURIs().contains("http://emptypage.org"));
	}

}
