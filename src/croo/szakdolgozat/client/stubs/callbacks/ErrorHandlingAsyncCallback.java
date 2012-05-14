package croo.szakdolgozat.client.stubs.callbacks;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;

public abstract class ErrorHandlingAsyncCallback<T> implements AsyncCallback<T>
{
	PopupPanel popup = new PopupPanel(true, true);

	@Override
	public void onFailure(Throwable caught)
	{
		popup.clear();
		popup.add(new HTML(caught.getMessage()));
		popup.show();
	}

	@Override
	public abstract void onSuccess(T result);
}
