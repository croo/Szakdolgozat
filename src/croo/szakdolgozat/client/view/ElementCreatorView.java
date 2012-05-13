package croo.szakdolgozat.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

import croo.szakdolgozat.client.display.ElementCreatorDisplay;
import croo.szakdolgozat.client.presenter.ElementCreatorPresenter;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextArea;

public class ElementCreatorView extends Composite implements ElementCreatorDisplay
{

	private static ElementCreatorViewUiBinder uiBinder = GWT.create(ElementCreatorViewUiBinder.class);
	@UiField
	Button sendButton;
	@UiField TextBox name;
	@UiField TextBox webpage;
	@UiField TextArea description;
	@UiField TextBox image;
	private ElementCreatorPresenter presenter;

	interface ElementCreatorViewUiBinder extends UiBinder<Widget, ElementCreatorView>
	{
	}

	public ElementCreatorView(EventBus eventBus)
	{
		initWidget(uiBinder.createAndBindUi(this));
		presenter = new ElementCreatorPresenter(this, eventBus);
	}

	@UiHandler("sendButton")
	void onSendButtonClick(ClickEvent event)
	{
		presenter.createNewPlace(name.getText(),webpage.getText(),description.getText(),image.getText());
	}
}
