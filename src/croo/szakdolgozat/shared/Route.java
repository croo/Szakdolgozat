package croo.szakdolgozat.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class Route implements Serializable
{
	private static final long serialVersionUID = 3955046507192507058L;

	private Town startTown;
	private Town endTown;
	private ArrayList<Coordinates> routeway;

	public Route(Town startTown, Town endTown, ArrayList<Coordinates> routeway)
	{
		super();
		this.startTown = startTown;
		this.endTown = endTown;
		this.routeway = routeway;
	}

	public Route(String startTownName, String endTownName, ArrayList<Coordinates> routeway)
	{
		this.routeway = routeway;
		startTown = new Town(routeway.get(0), startTownName);
	}

	public Town getStartTown()
	{
		return startTown;
	}

	public void setStartTown(Town startTown)
	{
		this.startTown = startTown;
	}

	public Town getEndTown()
	{
		return endTown;
	}

	public void setEndTown(Town endTown)
	{
		this.endTown = endTown;
	}

	public ArrayList<Coordinates> getRouteway()
	{
		return routeway;
	}

	public void setRouteway(ArrayList<Coordinates> routeway)
	{
		this.routeway = routeway;
	}
}
