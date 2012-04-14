package croo.szakdolgozat.client.stubs;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FilteringServiceAsync
{
	void setDate(Date date, AsyncCallback<Void> callback);

	void setDiscountRate(String rate, AsyncCallback<Void> callback);

}
