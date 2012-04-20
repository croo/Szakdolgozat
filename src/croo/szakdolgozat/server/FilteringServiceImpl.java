package croo.szakdolgozat.server;

import java.util.Date;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import croo.szakdolgozat.client.stubs.FilteringService;

/**
 * The server side implementation of the FilteringService service.
 */
@SuppressWarnings("serial")
public class FilteringServiceImpl extends RemoteServiceServlet implements FilteringService
{

	@Override
	public void setDate(Date date)
	{
		getSession().setAttribute("date", date);
	}

	@Override
	public void setDiscountRate(String discountRate)
	{
		getSession().setAttribute("discountRate", discountRate);
	}

	private HttpSession getSession()
	{
		return this.getThreadLocalRequest().getSession();
	}

}
