package croo.szakdolgozat.client.stubs;
import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("filtering")
public interface FilteringService extends RemoteService
{
	void setDate(Date date);

	void setDiscountRate(String rate);
}
