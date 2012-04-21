package croo.szakdolgozat.server;

import java.util.Date;

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
		session().setAttribute("discountRate", discountRate);
	}

	private HttpSession session()
	{
		return this.getThreadLocalRequest().getSession();
	}

}
