package croo.szakdolgozat.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import croo.szakdolgozat.shared.InterestingPlace;

public class InterestingPlaceTest
{

	@Test
	public void PlacesShouldBeEqualIfURLsAreEqual()
	{
		InterestingPlace place1 = new InterestingPlace("http://www.bla.hu", "");
		InterestingPlace place2 = new InterestingPlace("http://www.bla.hu", "Name", "Description");
		assertEquals(place1, place2);
	}

	@Test
	public void PlacesShouldNotBeEqualIfURLsAreDifferent()
	{
		InterestingPlace place1 = new InterestingPlace("http://www.foo.hu", "Name", "Description");
		InterestingPlace place2 = new InterestingPlace("http://www.bla.hu", "Name", "Description");
		assertFalse(place1.equals(place2));
	}
}
