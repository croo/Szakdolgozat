package croo.szakdolgozat.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TravelInfos implements IsSerializable
{
	private String routeName;
	private String travelDate;
	private ArrayList<TravelInfo> travelInfos = new ArrayList<TravelInfo>();

	public TravelInfos()
	{
		/* GWT RPC needs an empty no-arguments constructor */
	}

	public TravelInfos(String routeName, String travelDate)
	{
		this.routeName = routeName;
		this.travelDate = travelDate;
	}

	public void add(TravelInfo travelInfo)
	{
		travelInfos.add(travelInfo);
	}

	public String getRouteName()
	{
		return routeName;
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
