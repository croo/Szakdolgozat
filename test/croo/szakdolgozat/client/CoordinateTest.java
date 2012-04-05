package croo.szakdolgozat.client;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import croo.szakdolgozat.shared.Coordinate;

public class CoordinateTest
{

	@Test
	public void coordinatesWithSameLatLgtShouldBeEqual()
	{
		Coordinate c1 = new Coordinate(13.06532346, 84.31212923);
		Coordinate c2 = new Coordinate(13.06532346, 84.31212923);

		assertTrue(c1.equals(c2));
	}

	@Test
	public void sameCoordinateWithTwoReferenceShouldBeEqual()
	{
		Coordinate c1 = new Coordinate(13.06532346, 84.31212923);
		Coordinate c2 = c1;

		assertTrue(c1.equals(c2));
	}

	@Test
	public void differentCoordinateShouldNotBeEqual()
	{
		Coordinate c1 = new Coordinate(13.06532346, 84.31212923);
		Coordinate c2 = new Coordinate(54.0071235023058, 0.10004604);

		assertFalse(c1.equals(c2));
	}

	@Test
	public void coordinatesWithChangedLatLngShouldNotBeEqual()
	{
		Coordinate c1 = new Coordinate(13.06532346, 84.31212923);
		Coordinate c2 = new Coordinate(84.31212923, 13.06532346);

		assertFalse(c1.equals(c2));
	}

	@Test
	public void coordinateAndSomethingCompletlyUnrelatedShouldNotBeEqual()
	{
		Coordinate c1 = new Coordinate(13.06532346, 84.31212923);
		Integer i2 = 723455;

		assertFalse(c1.equals(i2));
	}
}
