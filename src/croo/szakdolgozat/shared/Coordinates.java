package croo.szakdolgozat.shared;

import java.io.Serializable;

public class Coordinates implements Serializable
{
	private static final long serialVersionUID = -2391428460640808199L;

	private double latitude;
	private double longitude;

	public Coordinates()
	{
		/* GWT RPC needs an empty no-arguments constructor */
	}

	public Coordinates(double lat, double lng)
	{
		latitude = lat;
		longitude = lng;
	}

	public double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}

	public double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}
}
