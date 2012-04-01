package croo.szakdolgozat.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Route implements IsSerializable
{
	private Town startTown;
	private Town endTown;
	private ArrayList<Coordinates> routeway;

	public Route()
	{
		/* GWT RPC needs an empty no-arguments constructor */
	}

	public Route(Town startTown, Town endTown, ArrayList<Coordinates> routeway)
	{
		this.startTown = startTown;
		this.endTown = endTown;
		this.routeway = routeway;
	}

	public Route(String startTownName, String endTownName, ArrayList<Coordinates> routeway)
	{
		this.routeway = routeway;
		startTown = new Town(routeway.get(0), startTownName);
		endTown = new Town(routeway.get(routeway.size() - 1), startTownName);
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