package croo.szakdolgozat.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import croo.szakdolgozat.client.stubs.FilterService;

/**
 * The server side implementation of the FilteringService service.
 */
@SuppressWarnings("serial")
public class FilteringServiceImpl extends RemoteServiceServlet implements FilterService
{

	@Override
	public void setDate(Date date)
	{
		session().setAttribute("date", date);
	}

	@Override
	public void setDiscountRate(String discountRate)
	{
		session().setAttribute("rate", discountRate);
	}

	@Override
	public HashMap<String, String> getDiscounts() throws Exception
	{
		try {
			Properties properties = new Properties();
			properties.load(new FileInputStream(getPricingProperties()));
			return convertPropertiesToHashMap(properties);
		} catch (FileNotFoundException e) {
			throw new Exception("File Not Found: Couldn't find the pricing properties file.");
		} catch (IOException e) {
			throw new Exception("IO Exception: Found, but couldn't read the pricing properties file.");
		}
	}

	private String getPricingProperties()
	{
		String pricingProperties = SystemProperties.GetInstance().get("pricing.properties.file");
		return (pricingProperties == null) ? "pricing.properties" : pricingProperties;
	}

	private HashMap<String, String> convertPropertiesToHashMap(Properties properties)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		for (Map.Entry<Object, Object> item : properties.entrySet()) {
			map.put((String) item.getKey(), (String) item.getValue());
		}
		return map;
	}

	private HttpSession session()
	{
		return this.getThreadLocalRequest().getSession();
	}

}
