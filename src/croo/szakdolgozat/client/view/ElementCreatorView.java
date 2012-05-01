package croo.szakdolgozat.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ElementCreatorView extends Composite
{

	private static ElementCreatorViewUiBinder uiBinder = GWT.create(ElementCreatorViewUiBinder.class);

	interface ElementCreatorViewUiBinder extends UiBinder<Widget, ElementCreatorView>
	{
	}

	public ElementCreatorView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}

}
