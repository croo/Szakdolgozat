package croo.szakdolgozat.test;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.maps.client.geom.LatLng;

import croo.szakdolgozat.shared.Coordinate;


public class testCoordinates extends GWTTestCase
{

	@Test
	public void testCoordinateGivesBackCorrectJSOObject()
	{
		Coordinate coordinate = new Coordinate(5.5, 3.3);
		LatLng JSOCoordinate = coordinate.getCoordinateInJSO();

		assertEquals(JSOCoordinate.getLatitude(), coordinate.getLatitude(), 0.01);
		assertEquals(JSOCoordinate.getLongitude(), coordinate.getLongitude(), 0.01);
	}

	@Override
	public String getModuleName()
	{
		return "croo.szakdolgozat.main";
	}

}
