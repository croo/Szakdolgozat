package croo.szakdolgozat.client.view;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.web.bindery.event.shared.EventBus;

import croo.szakdolgozat.client.display.FilteringDisplay;
import croo.szakdolgozat.client.display.TravelInfoDisplay;
import croo.szakdolgozat.client.presenter.FilteringPresenter;
import croo.szakdolgozat.client.presenter.TravelInfoPresenter;
import croo.szakdolgozat.client.stubs.FilterServiceAsync;
import croo.szakdolgozat.client.stubs.TravelServiceAsync;

public class TravelInfoView extends Composite implements TravelInfoDisplay
{

	private static TravelInfoViewUiBinder uiBinder = GWT.create(TravelInfoViewUiBinder.class);

	@UiField
	Label label;
	
	@UiField
	Button button;

	private TravelInfoPresenter presenter;

	interface TravelInfoViewUiBinder extends UiBinder<Widget, TravelInfoView>
	{
	}

	public TravelInfoView(EventBus eventBus, TravelServiceAsync travelInfoService)
	{
		initWidget(uiBinder.createAndBindUi(this));
		presenter = new TravelInfoPresenter(this, eventBus, travelInfoService);		
		label.setText("label");
		button.setText("gomb");
	}

	@Override
	public void setLabel(String text){
		label.setText(text);
	}
	
	@UiHandler("button")
	void onClick(ClickEvent e)
	{
		presenter.onButtonClick();
	}
}
