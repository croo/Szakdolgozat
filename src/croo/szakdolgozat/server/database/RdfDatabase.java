package croo.szakdolgozat.server.database;

import java.util.ArrayList;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;

import croo.szakdolgozat.shared.Coordinate;
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

	/**
	 * Accepts filename with relative or absolute path
	 * @param rdfFileName 
	 */
	public RdfDatabase(String rdfFileName){
		model = FileManager.get().loadModel(rdfFileName);
		LATITUDE = model.getProperty("http://www.w3.org/2003/01/geo/wgs84_pos#lat");
		LONGITUDE = model.getProperty("http://www.w3.org/2003/01/geo/wgs84_pos#long");
		NAME = model.getProperty("http://xmlns.com/foaf/0.1/Name");
		ROUTEWAY = model.getProperty("http://www.georss.org/georss#line");
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
		Resource startTownResource = model.getResource(NAMESPACE + startTown);
		Resource endTownResource = model.getResource(NAMESPACE + endTown);
		Resource routewayResource = model.getResource(NAMESPACE + "from" + startTown + "to" + endTown);

		Town start = createTown(startTownResource);
		Town end = createTown(endTownResource);
		ArrayList<Coordinate> routeway = createRouteWay(start.getCoordinate(), routewayResource, end.getCoordinate());
		return new Route(start, end, routeway);
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

		// TODO: List of interesting places are yet to add.

		return new Town(coordinate, name);
	}
}
