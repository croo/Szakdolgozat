package croo.szakdolgozat.client.stubs.callbacks;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;

public abstract class ErrorHandlingAsyncCallback<T> implements AsyncCallback<T>
{
	PopupPanel popup;

	public ErrorHandlingAsyncCallback()
	{
		popup = new PopupPanel(true, true);
	}

	@Override
	public void onFailure(Throwable caught)
	{
		popup.add(new HTML(caught.getMessage()));
		popup.show();
	}
}
