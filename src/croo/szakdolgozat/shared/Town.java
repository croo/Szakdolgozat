package croo.szakdolgozat.shared;

import java.io.Serializable;

public class Town implements Serializable
{
	private static final long serialVersionUID = 8688979676885314458L;

	private Coordinates coordinate;
	private String name;

	public Town()
	{
		/* GWT RPC needs an empty no-arguments constructor */
	}

	public Town(Coordinates coordinate, String name)
	{
		super();
		this.coordinate = coordinate;
		this.name = name;
	}

	public Coordinates getCoordinate()
	{
		return coordinate;
	}

	public void setCoordinate(Coordinates coordinate)
	{
		this.coordinate = coordinate;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
