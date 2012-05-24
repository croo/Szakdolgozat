package croo.szakdolgozat.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

import croo.szakdolgozat.client.display.ElementCreatorDisplay;
import croo.szakdolgozat.client.presenter.ElementCreatorPresenter;

public class ElementCreatorView extends Composite implements ElementCreatorDisplay
{

	private static ElementCreatorViewUiBinder uiBinder = GWT.create(ElementCreatorViewUiBinder.class);
	@UiField
	Button sendButton;
	@UiField
	TextBox name;
	@UiField
	TextBox webpage;
	@UiField
	TextArea description;
	@UiField
	TextBox image;
	@UiField
	Button cancelButton;
	@UiField
	Label errorLabel;
	private ElementCreatorPresenter presenter;

	interface ElementCreatorViewUiBinder extends UiBinder<Widget, ElementCreatorView>
	{
	}

	public ElementCreatorView(EventBus eventBus, PopupPanel popup)
	{
		initWidget(uiBinder.createAndBindUi(this));

		presenter = new ElementCreatorPresenter(this, eventBus, popup);
	}

	@UiHandler("sendButton")
	void onSendButtonClick(ClickEvent event)
	{
		GWT.log("UI Binderes gomb megnyomódott.");
		presenter.createNewPlace(name.getText(), webpage.getText(), description.getText(), image.getText());		
	}

	@UiHandler("cancelButton")
	void onCancelButtonClick(ClickEvent event)
	{
		presenter.hideCreatorView();
	}

	@Override
	public void setErrorLabel(String error) {		
		errorLabel.setText(error);
	}
}
