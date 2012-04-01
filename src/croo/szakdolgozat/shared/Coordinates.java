package croo.szakdolgozat.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Coordinates implements IsSerializable
{
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
