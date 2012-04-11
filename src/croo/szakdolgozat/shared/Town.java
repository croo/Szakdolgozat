package croo.szakdolgozat.shared;

import java.util.ArrayList;

import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.user.client.rpc.IsSerializable;

public class Town implements IsSerializable
{
	private static final InterestingPlace EMPTY_PLACE = new InterestingPlace("http://emptypage.org", "Nothing interesting...");
	private Coordinate coordinate;
	private String name;
	private ArrayList<InterestingPlace> interestingPlaces = null;

	public Town()
	{
		/* GWT RPC needs an empty no-arguments constructor */
	}

	public Town(Coordinate coordinate, String name)
	{
		this.coordinate = coordinate;
		this.name = name;
	}

	public Town(Coordinate coordinate, String name, ArrayList<InterestingPlace> interestingPlaces)
	{
		this.coordinate = coordinate;
		this.name = name;
		this.interestingPlaces = interestingPlaces;
	}

	public ArrayList<InterestingPlace> getInterestingPlaces()
	{
		if (interestingPlaces != null)
			return interestingPlaces;
		else
			return createEmptyInterestingPlaces();
	}

	private ArrayList<InterestingPlace> createEmptyInterestingPlaces()
	{
		ArrayList<InterestingPlace> emptyList = new ArrayList<InterestingPlace>();
		emptyList.add(EMPTY_PLACE);
		return emptyList;
	}

	public LatLng getTownCoordinateInJSO()
	{
		return coordinate.getCoordinateInJSO();
	}

	public Coordinate getCoordinate()
	{
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate)
	{
		this.coordinate = coordinate;
	}

	public String getName()
	{
		String s = name.trim();
		String wellFormattedName = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
		return wellFormattedName;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj instanceof Town) {
			Town other = (Town) obj;
			return getName().equals(other.getName()) && getCoordinate().equals(other.getCoordinate());
		} else {
			return false;
		}
	}

	@Override
	public int hashCode()
	{
		return 1013 * getName().hashCode() ^ 1009 * getCoordinate().hashCode();
	}
}
