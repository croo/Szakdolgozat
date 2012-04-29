package croo.szakdolgozat.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TravelInfos implements IsSerializable
{
	private String startTown;
	private String endTown;
	private String travelDate;
	private ArrayList<TravelInfo> travelInfos = new ArrayList<TravelInfo>();

	public TravelInfos()
	{
		/* GWT RPC needs an empty no-arguments constructor */
	}

	public TravelInfos(String startTown, String endTown, String travelDate)
	{
		this.startTown = startTown;
		this.endTown = endTown;
		this.travelDate = travelDate;
	}

	public void add(TravelInfo travelInfo)
	{
		travelInfos.add(travelInfo);
	}

	public String getStartTown()
	{
		return startTown;
	}

	public String getEndTown()
	{
		return endTown;
	}

	public String getTravelDate()
	{
		return travelDate;
	}

	public ArrayList<TravelInfo> getTravelInfos()
	{
		return travelInfos;
	}

}
