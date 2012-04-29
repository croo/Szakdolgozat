package croo.szakdolgozat.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

import croo.szakdolgozat.client.display.TravelInfoDisplay;
import croo.szakdolgozat.client.presenter.TravelInfoPresenter;
import croo.szakdolgozat.client.stubs.TravelServiceAsync;
import croo.szakdolgozat.shared.TravelInfos;

public class TravelInfoView extends Composite implements TravelInfoDisplay
{

	private static TravelInfoViewUiBinder uiBinder = GWT.create(TravelInfoViewUiBinder.class);

	@UiField
	Label label;

	private TravelInfoPresenter presenter;

	interface TravelInfoViewUiBinder extends UiBinder<Widget, TravelInfoView>
	{
	}

	public TravelInfoView(EventBus eventBus, TravelServiceAsync travelInfoService)
	{
		initWidget(uiBinder.createAndBindUi(this));
		presenter = new TravelInfoPresenter(this, eventBus, travelInfoService);
		label.setText("label");
	}

	@Override
	public void showTravelInfos(TravelInfos infos)
	{
		label.setText(infos.getStartTown() + ";" + infos.getEndTown() + ";" + infos.getTravelDate());

	}
}
