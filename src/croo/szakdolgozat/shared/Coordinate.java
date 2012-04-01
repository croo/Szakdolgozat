package croo.szakdolgozat.shared;

import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.user.client.rpc.IsSerializable;

public class Coordinate implements IsSerializable
{
	private Double latitude;
	private Double longitude;

	public Coordinate()
	{
		/* GWT RPC needs an empty no-arguments constructor */
	}

	public Coordinate(Double lat, Double lng)
	{
		latitude = lat;
		longitude = lng;
	}

	public LatLng getCoordinateInJSO()
	{
		return LatLng.newInstance(latitude, longitude);
	}

	public double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(Double latitude)
	{
		this.latitude = latitude;
	}

	public Double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(Double longitude)
	{
		this.longitude = longitude;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj instanceof Coordinate) {
			Coordinate other = (Coordinate) obj;
			return latitude.equals(other.latitude) && longitude.equals(other.longitude);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode()
	{
		return latitude.hashCode() / longitude.hashCode();
	}
}
