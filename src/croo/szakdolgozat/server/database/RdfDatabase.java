package croo.szakdolgozat.server.database;

import java.util.ArrayList;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;

import croo.szakdolgozat.shared.Coordinate;
import croo.szakdolgozat.shared.InterestingPlace;
import croo.szakdolgozat.shared.Route;
import croo.szakdolgozat.shared.Town;

public class RdfDatabase implements Database
{

	private static final String NAMESPACE = "http://example.org/base#";

	private static Model model;
	private static Property LATITUDE;
	private static Property LONGITUDE;
	private static Property NAME;
	private static Property ROUTEWAY;
	private static Property DESCRIPTION;
	private static Property URL;
	private static Property IMAGE;
	private static Property PLACE;
	private static Property PLACES;

	/**
	 * Accepts filename with relative or absolute path
	 * 
	 * @param rdfFileName
	 */
	public RdfDatabase(String rdfFileName)
	{
		model = FileManager.get().loadModel(rdfFileName);
		LATITUDE = model.getProperty("http://www.w3.org/2003/01/geo/wgs84_pos#lat");
		LONGITUDE = model.getProperty("http://www.w3.org/2003/01/geo/wgs84_pos#long");
		NAME = model.getProperty("http://xmlns.com/foaf/0.1/Name");
		ROUTEWAY = model.getProperty("http://www.georss.org/georss#line");

		DESCRIPTION = model.getProperty("http://purl.org/dc/terms/description");
		URL = model.getProperty("http://purl.org/dc/terms/references");
		IMAGE = model.getProperty("http://xmlns.com/foaf/0.1/image");
		PLACE = model.getProperty("http://example.org/croo#place");
		PLACES = model.getProperty("http://example.org/croo#places");
	}

	@Override
	public boolean townExists(String town)
	{
		Resource townResource = model.getResource(NAMESPACE + town);
		return model.containsResource(model.getRDFNode(townResource.asNode()));
	}

	@Override
	public Route getRoute(String startTown, String endTown)
	{
		Resource routewayResource = model.getResource(NAMESPACE + "from" + startTown + "to" + endTown);		
		if(!model.contains(routewayResource, null)) return null;
		Resource startTownResource = model.getResource(NAMESPACE + startTown);
		Resource endTownResource = model.getResource(NAMESPACE + endTown);
		Town start = createTown(startTownResource);
		Town end = createTown(endTownResource);
		ArrayList<Coordinate> routeway = createRouteWay(start.getCoordinate(), routewayResource, end.getCoordinate());
		return new Route(start, end, routeway);
	}

	@Override
	public synchronized void addInterestingPlace(InterestingPlace place, String town)
	{
		Resource placeResource = model.createResource(getUniquePlaceId(place));
		if (!model.contains(placeResource, null)) {
			placeResource.addLiteral(NAME, place.getName());
			placeResource.addLiteral(DESCRIPTION, place.getDescription());
			placeResource.addLiteral(URL, place.getURL());
			placeResource.addLiteral(IMAGE, place.getImageUrl());

			Resource placesResource = model.createResource(NAMESPACE + "placesat" + town);

			placesResource.addProperty(PLACE, placeResource);
		} else
			return;
	}

	private String getUniquePlaceId(InterestingPlace place)
	{
		return NAMESPACE + place.getName().replaceAll("[ .,/\\?#&=*:;]*", "").toLowerCase()
				+ place.getURL().replaceAll("[ .,/\\?#&=*:;]*", "");
	}

	private ArrayList<Coordinate> createRouteWay(Coordinate startTownCoordinate, Resource route, Coordinate endTownCoordinate)
	{
		ArrayList<Coordinate> routeway = new ArrayList<Coordinate>();
		routeway.add(startTownCoordinate);
		String[] coordinateStrings = route.getProperty(ROUTEWAY).getString().split(" ");
		for (String coordinateString : coordinateStrings) {
			String[] latLng = coordinateString.split(",");
			routeway.add(new Coordinate(Double.parseDouble(latLng[0]), Double.parseDouble(latLng[1])));
		}
		routeway.add(endTownCoordinate);
		return routeway;
	}

	private Town createTown(Resource town)
	{
		Double lat = town.getProperty(LATITUDE).getDouble();
		Double lng = town.getProperty(LONGITUDE).getDouble();
		Coordinate coordinate = new Coordinate(lat, lng);

		String name = town.getProperty(NAME).getString();

		Town resultTown = new Town(coordinate, name);
		loadInterestingPlacesIntoTown(resultTown);

		return resultTown;
	}

	private void loadInterestingPlacesIntoTown(Town town)
	{
		String a = NAMESPACE + "placesat" + town.getName().toLowerCase().trim();
		Resource placesList = model.getResource(a);

		StmtIterator places = placesList.listProperties(PLACE);
		while (places.hasNext()) {
			Resource place = places.next().getObject().asResource();
			town.addInterestingPlace(createInterestingPlaceFromResource(place));
		}
	}

	private InterestingPlace createInterestingPlaceFromResource(Resource place)
	{
		String name = place.getProperty(NAME).getString();
		String url = place.getProperty(URL).getString();
		String description = place.getProperty(DESCRIPTION).getString();
		String imageUrl = place.getProperty(IMAGE).getString();

		return new InterestingPlace(url, name, description, imageUrl);
	}
}
