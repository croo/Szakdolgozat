package croo.szakdolgozat.client.display;

import java.util.HashMap;

import com.google.gwt.maps.client.MapWidget;

public interface TravelMapDisplay
{
	public void setTravelMap(MapWidget map);

	public void setErrorLabel(String error);

	void loadDiscountBoxData(HashMap<String, String> properties);
}
