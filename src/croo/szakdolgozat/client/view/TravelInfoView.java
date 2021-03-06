package croo.szakdolgozat.client.view;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

import croo.szakdolgozat.client.display.TravelInfoDisplay;
import croo.szakdolgozat.client.presenter.TravelInfoPresenter;
import croo.szakdolgozat.client.stubs.TravelServiceAsync;
import croo.szakdolgozat.shared.TravelInfo;
import croo.szakdolgozat.shared.TravelInfos;

public class TravelInfoView extends Composite implements TravelInfoDisplay
{

	private static TravelInfoViewUiBinder uiBinder = GWT.create(TravelInfoViewUiBinder.class);

	@UiField
	VerticalPanel infoPanel;
	@UiField
	Label routeLabel;
	@UiField
	Label dateLabel;
	@UiField
	SimplePanel table;

	private TravelInfoPresenter presenter;

	interface TravelInfoViewUiBinder extends UiBinder<Widget, TravelInfoView>
	{
	}

	public TravelInfoView(EventBus eventBus, TravelServiceAsync travelInfoService)
	{
		initWidget(uiBinder.createAndBindUi(this));
		presenter = new TravelInfoPresenter(this, eventBus, travelInfoService);
		routeLabel.setStyleName("headerLabel");
		dateLabel.setStyleName("headerLabel");
	}

	@Override
	public void loadInfosToInfoTable(TravelInfos infos)
	{

		routeLabel.setText(infos.getRouteName());
		dateLabel.setText(infos.getTravelDate());
		loadTable(infos.getTravelInfos());
	}

	private void loadTable(ArrayList<TravelInfo> travelInfos)
	{
		table.clear();
		Grid grid = new Grid(travelInfos.size() + 1, 10);
		grid.setBorderWidth(1);
		addHeaders(grid);
		for (int i = 1; i <= travelInfos.size(); i++) {
			TravelInfo info = travelInfos.get(i - 1);
			int column = 0;
			grid.setWidget(i, column++, new HTML(info.getStartStation()));
			grid.setWidget(i, column++, new HTML(info.getStartPlatform()));
			grid.setWidget(i, column++, new HTML(info.getEndStation()));
			grid.setWidget(i, column++, new HTML(info.getStartTime()));
			grid.setWidget(i, column++, new HTML(info.getEndTime()));
			grid.setWidget(i, column++, new HTML(info.getTravelTime()));
			grid.setWidget(i, column++, new HTML(info.getDistance()));
			grid.setWidget(i, column++, new HTML(info.getTrainInfo()));
			grid.setWidget(i, column++, new HTML(info.getTravelClass()));
			grid.setWidget(i, column++, new HTML(info.getPrice()));
		}
		table.add(grid);
	}

	private void addHeaders(Grid grid)
	{
		int column = 0;
		grid.setWidget(0, column++, getHeader("Honnan"));
		grid.setWidget(0, column++, getHeader("Vágány"));
		grid.setWidget(0, column++, getHeader("Hova"));
		grid.setWidget(0, column++, getHeader("Indulás"));
		grid.setWidget(0, column++, getHeader("Érkezés"));
		grid.setWidget(0, column++, getHeader("Itőtartam"));
		grid.setWidget(0, column++, getHeader("Távolság"));
		grid.setWidget(0, column++, getHeader("Járat"));
		grid.setWidget(0, column++, getHeader("Osztály"));
		grid.setWidget(0, column++, getHeader("Ár"));
	}

	private HTML getHeader(String text)
	{
		HTML header = new HTML(text);
		header.setStyleName("headerLabel");
		return header;
	}
}
