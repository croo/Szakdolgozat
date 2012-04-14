package croo.szakdolgozat.server;

import java.util.Date;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import croo.szakdolgozat.client.stubs.FilteringService;

/**
 * The server side implementation of the FilteringService service.
 */
@SuppressWarnings("serial")
public class FilteringServiceImpl extends RemoteServiceServlet implements FilteringService, RestApiFilter
{
	private Date date;
	private String discountRate;

	@Override
	public void setDate(Date date)
	{
		this.date = date;
	}

	@Override
	public void setDiscountRate(String discountRate)
	{
		this.discountRate = discountRate;
	}

	@Override
	public Date getDate()
	{
		return date;
	}

	@Override
	public String getDiscountRate()
	{
		return discountRate;
	}

}
