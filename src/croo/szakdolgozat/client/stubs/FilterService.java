package croo.szakdolgozat.client.stubs;
import java.util.Date;
import java.util.HashMap;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("filter")
public interface FilterService extends RemoteService
{
	void setDate(Date date);

	void setDiscountRate(String rate);

	HashMap<String, String> getDiscounts() throws Exception;
}
