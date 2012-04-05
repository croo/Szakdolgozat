package croo.szakdolgozat.client;

import static org.junit.Assert.assertEquals;

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

}
