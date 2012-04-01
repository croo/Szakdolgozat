package croo.szakdolgozat.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Town implements IsSerializable
{
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
