package croo.szakdolgozat.client.stubs;

import java.util.Date;
import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FilterServiceAsync
{
	void setDate(Date date, AsyncCallback<Void> callback);

	void setDiscountRate(String rate, AsyncCallback<Void> callback);

	void getDiscounts(AsyncCallback<HashMap<String, String>> callback);

}
