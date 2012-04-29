package croo.szakdolgozat.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TravelInfo implements IsSerializable
{

	private String startTime;
	private String endTime;
	private String travelTime;
	private String distance;
	private String price;
	private String travelClass;
	private String startStation;
	private String endStation;
	private String trainInfo;
	private String startPlatform;

	public TravelInfo()
	{
		/* GWT RPC needs an empty no-arguments constructor */
	}

	public TravelInfo(String travelClass, String price, String startTime, String endTime, String travelTime, String distance,
			String startStation, String endStation, String trainInfo, String startPlatform)
	{
		this.travelClass = travelClass;
		this.price = price;
		this.startTime = startTime;
		this.endTime = endTime;
		this.travelTime = travelTime;
		this.distance = distance;
		this.startStation = startStation;
		this.endStation = endStation;
		this.trainInfo = trainInfo;
		this.startPlatform = startPlatform;
	}

	public String getStartTime()
	{
		return startTime;
	}

	public String getEndTime()
	{
		return endTime;
	}

	public String getTravelTime()
	{
		return travelTime;
	}

	public String getDistance()
	{
		return distance;
	}

	public String getPrice()
	{
		return price;
	}

	public String getTravelClass()
	{
		return travelClass;
	}

	public String getStartStation()
	{
		return startStation;
	}

	public String getEndStation()
	{
		return endStation;
	}

	public String getTrainInfo()
	{
		return trainInfo;
	}

	public String getStartPlatform()
	{
		return startPlatform;
	}

}
