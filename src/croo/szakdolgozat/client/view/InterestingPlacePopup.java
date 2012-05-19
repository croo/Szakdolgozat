package croo.szakdolgozat.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class InterestingPlacePopup extends Composite
{

	private static InterestingPlacePopupUiBinder uiBinder = GWT.create(InterestingPlacePopupUiBinder.class);
	@UiField
	VerticalPanel urlList;
	@UiField
	Image image;
	@UiField
	SimplePanel description;
	@UiField
	Label title;

	interface InterestingPlacePopupUiBinder extends UiBinder<Widget, InterestingPlacePopup>
	{
	}

	public InterestingPlacePopup()
	{
		initWidget(uiBinder.createAndBindUi(this));
		title.addStyleName("h1");
	}

	public InterestingPlacePopup(String name, String description, String url, String imageUrl)
	{
		initWidget(uiBinder.createAndBindUi(this));
		title.setText(name);
		this.description.add(new HTML(description));
		image.setUrl(imageUrl);
		image.setSize("400px", "400px");
		HTML urlLink = new HTML("<a href=\"" + url + "\" target=\"_blank\">" + url + "</a>");
		urlLink.setStyleName("placeButton-link");
		urlList.add(urlLink);
	}
}
