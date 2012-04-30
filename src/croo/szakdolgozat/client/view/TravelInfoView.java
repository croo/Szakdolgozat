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
		Grid grid = new Grid(travelInfos.size() + 1, 9);
		grid.setBorderWidth(1);
		addHeader(grid);
		for (int i = 1; i <= travelInfos.size(); i++) {
			TravelInfo info = travelInfos.get(i - 1);
			grid.setWidget(i, 0, new HTML(info.getDistance()));
			grid.setWidget(i, 1, new HTML(info.getEndStation()));
			grid.setWidget(i, 2, new HTML(info.getEndTime()));
			grid.setWidget(i, 3, new HTML(info.getStartPlatform()));
			grid.setWidget(i, 4, new HTML(info.getStartStation()));
			grid.setWidget(i, 5, new HTML(info.getStartTime()));
			grid.setWidget(i, 6, new HTML(info.getTrainInfo()));
			grid.setWidget(i, 7, new HTML(info.getTravelClass()));
			grid.setWidget(i, 8, new HTML(info.getTravelTime()));
		}
		table.add(grid);
	}

	private void addHeader(Grid grid)
	{
		grid.setWidget(0, 0, new HTML("Distance"));
		grid.setWidget(0, 1, new HTML("EndStation"));
		grid.setWidget(0, 2, new HTML("EndTime"));
		grid.setWidget(0, 3, new HTML("StartPlatform"));
		grid.setWidget(0, 4, new HTML("StartSTation"));
		grid.setWidget(0, 5, new HTML("StartTime"));
		grid.setWidget(0, 6, new HTML("TrainInfo"));
		grid.setWidget(0, 7, new HTML("Class"));
		grid.setWidget(0, 8, new HTML("TravelTime"));
	}
}
