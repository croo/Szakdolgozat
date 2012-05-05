package croo.szakdolgozat.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class InterestingPlace implements IsSerializable
{
	private String url;
	private String name;
	private String description = "";
	private String imageUrl;

	public InterestingPlace()
	{
		/* GWT RPC needs an empty no-arguments constructor */
	}

	public InterestingPlace(String url, String name)
	{
		this.url = url;
		this.name = name;
	}

	public InterestingPlace(String url, String name, String description, String imageUrl)
	{
		this.url = url;
		this.name = name;
		this.description = description;
		this.imageUrl = imageUrl;
	}

	public String getURL()
	{
		return url;
	}

	public String getName()
	{
		return name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj instanceof InterestingPlace) {
			InterestingPlace other = (InterestingPlace) obj;
			return url.equals(other.url);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode()
	{
		return url.hashCode();
	}

	public String getImageUrl()
	{
		return imageUrl;
	}
}
