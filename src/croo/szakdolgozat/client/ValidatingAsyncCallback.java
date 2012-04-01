package croo.szakdolgozat.client;

import com.google.gwt.core.client.GWT;

public abstract class ValidatingAsyncCallback extends ErrorHandlingAsyncCallback<Boolean>
{
	@Override
	public void onSuccess(Boolean inputIsValid)
	{
		GWT.log("Validating of input was successful.");
		if (inputIsValid)
		{
			GWT.log("Input is valid.");
			onValidInput();
		} else
		{
			GWT.log("Input is invalid.");
			onInvalidInput();
		}
	}

	public abstract void onValidInput();

	public abstract void onInvalidInput();

}
