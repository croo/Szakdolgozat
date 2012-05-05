package croo.szakdolgozat.server.database;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;

public class RdfDatabaseBuilder
{
	private static final String STOPS = "stops.txt";
	private static final String STOP_TIMES = "stop_times.txt";
	private static final String TRIPS = "trips.txt";
	private static final String SHAPES = "shapes.txt";
	private static List<String[]> trips;
	private static Map<String, List<String[]>> shapesMap;
	private static Map<String, List<String[]>> stopTimesMap;
	private static Map<String, String[]> stopsMap;
	private Writer out;

	public RdfDatabaseBuilder(final String tempDir) throws IOException, FileNotFoundException
	{
		stopsMap = createMap(new CSVReader(new FileReader(tempDir + STOPS)));
		System.out.println("stops loaded.");
		trips = new CSVReader(new FileReader(tempDir + TRIPS)).readAll();
		System.out.println("trips loaded.");
		stopTimesMap = createMultiMap(new CSVReader(new FileReader(tempDir + STOP_TIMES)));
		System.out.println("stoptimes loaded.");
		shapesMap = createMultiMap(new CSVReader(new FileReader(tempDir + SHAPES)));
		System.out.println("shapes loaded.");
	}

	public void parseTxtToRdfDatabase(String rdfDatabaseFile) throws IOException
	{

		File database = createDatabaseFile(rdfDatabaseFile);
		out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(database), "UTF-8"), 1024 * 1024 * 10);
		initializeDatabase();

		generateStops();
		generateRoutes();
		finalizeDatabase();
		clearThoseDamnBigLists();
		Runtime.getRuntime().gc();
	}

	private Map<String, List<String[]>> createMultiMap(CSVReader data) throws IOException
	{
		Map<String, List<String[]>> map = new HashMap<String, List<String[]>>();
		String[] row = data.readNext(); // throw away headers
		row = data.readNext();
		while (row != null) {
			if (!map.containsKey(row[0])) {
				List<String[]> values = new ArrayList<String[]>();
				values.add(row);
				map.put(row[0], values);
			} else {
				map.get(row[0]).add(row);
			}
			row = data.readNext();
		}
		return map;
	}

	private Map<String, String[]> createMap(CSVReader data) throws IOException
	{
		Map<String, String[]> map = new HashMap<String, String[]>();
		String[] row = data.readNext(); // throw away headers
		row = data.readNext();
		while (row != null) {
			if (!map.containsKey(row[0])) {
				map.put(row[0], row);
			}
			row = data.readNext();
		}
		return map;
	}

	private void clearThoseDamnBigLists() throws IOException
	{
		stopsMap.clear();
		trips.clear();
		shapesMap.clear();
		stopTimesMap.clear();
		out.close();
	}

	// in trip.txt
	// 8 - shapes_id ; 2 - trips_id
	private void generateRoutes() throws IOException
	{
		for (int i = 1; i < trips.size(); i++) {
			generateRoutesOfTrip(trips.get(i)[2], trips.get(i)[8]);
			System.out.println("" + i + " - generating routes for trip: " + trips.get(i)[2]);

		}
	}

	// in stop_times.txt
	// 3 - stop_id ; 8 - shape_dist_traveled
	private void generateRoutesOfTrip(String tripId, String shapesId) throws IOException
	{
		final List<String[]> stopTimesInTrip = stopTimesMap.get(tripId);// getStopTimesInTrip(tripId);
		for (int i = 0; i < stopTimesInTrip.size(); i++) {
			String startTownId = stopTimesInTrip.get(i)[3];
			String startTown_shape_dist_traveled = stopTimesInTrip.get(i)[8];
			for (int j = i + 1; j < stopTimesInTrip.size(); j++) {
				String endTownId = stopTimesInTrip.get(j)[3];
				String endTown_shape_dist_traveled = stopTimesInTrip.get(j)[8];
				createRouteBetweenTwoStopsInTrip(shapesId, startTownId, endTownId, startTown_shape_dist_traveled,
						endTown_shape_dist_traveled);
			}
		}
	}

	private void createRouteBetweenTwoStopsInTrip(String shapesId, String startTownId, String endTownId,
			String startTown_shape_dist_traveled, String endTown_shape_dist_traveled) throws IOException
	{
		String[] startTownRow = stopsMap.get(startTownId);
		String[] endTownRow = stopsMap.get(endTownId);

		String startTown = ifPlatformConvertToStation(startTownRow[2]);
		String endTown = ifPlatformConvertToStation(endTownRow[2]);

		writeToDatabase("\n");
		writeToDatabase("<croo:Route rdf:ID=\"from" + formatted(startTown) + "to" + formatted(endTown) + "\">");
		writeToDatabase("<croo:startTown rdf:resource=\"#" + formatted(startTown) + "\" />");
		writeToDatabase("<croo:endTown rdf:resource=\"#" + formatted(endTown) + "\" />");
		writeToDatabase("<georss:line>"
				+ getCoorinateList(shapesId, startTown_shape_dist_traveled, endTown_shape_dist_traveled) + "</georss:line>");
		writeToDatabase("</croo:Route>");
	}

	// in shapes.txt
	// 1 - shape latitude ; 2 - shape longitude
	private String getCoorinateList(String shapesId, String start_dist_traveled, String end_dist_traveled) throws IOException
	{
		List<String[]> shapesOfTrip = shapesMap.get(shapesId);
		StringBuilder coordinateList = new StringBuilder();
		for (String[] shape : shapesOfTrip) {
			if (shapeIsBetweenStops(start_dist_traveled, end_dist_traveled, shape))
				coordinateList.append(shape[1] + "," + shape[2] + " ");
		}
		return coordinateList.toString();
	}

	// in shapes.txt
	// 4 - shape_dist_traveled
	private boolean shapeIsBetweenStops(String start_dist_traveled, String end_dist_traveled, String[] shape)
	{
		return Double.parseDouble(shape[4]) >= Double.parseDouble(start_dist_traveled)
				&& Double.parseDouble(shape[4]) <= Double.parseDouble(end_dist_traveled);
	}

	private String ifPlatformConvertToStation(String stopName)
	{
		if (platformOfStation(stopName))
			return stopName.replaceAll(", *[0-9]*. *vágány", "");
		else
			return stopName;
	}

	private void writeToDatabase(String data) throws IOException
	{
		String postfix = data.equals("\n") ? "" : "\n";
		out.write(data + postfix);
	}

	private void initializeDatabase() throws IOException
	{
		writeToDatabase("<?xml version=\"1.0\"?>");
		writeToDatabase("<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"");
		writeToDatabase("xmlns:geo=\"http://www.w3.org/2003/01/geo/wgs84_pos#\"");
		writeToDatabase("xmlns:georss=\"http://www.georss.org/georss#\"");
		writeToDatabase("xmlns:foaf=\"http://xmlns.com/foaf/0.1/\"");
		writeToDatabase("xml:base=\"http://example.org/base#\"");
		writeToDatabase("xmlns:croo=\"http://example.org/croo#\">");
	}

	/*
	 * stops_headers: 0-stop_id; 1-stop_code; 2-stop_name; 3-stop_desc;
	 * 4-stop_lat; 5-stop_lon; 6-zone_id; 7-stop_url; 8-location_type;
	 * 9-parent_station; 10-stop_osm_entity; 11-stop_timezone
	 */
	private void generateStops() throws IOException
	{
		Collection<String[]> stops = stopsMap.values();
		for (String[] stop : stops) {
			if (!platformOfStation(stop[2])) {
				writeToDatabase("\n");
				writeToDatabase("<croo:Town rdf:ID=\"" + formatted(stop[2]) + "\">");
				writeToDatabase("<foaf:Name>" + stop[2] + "</foaf:Name>");
				writeToDatabase("<geo:lat>" + stop[4] + "</geo:lat>");
				writeToDatabase("<geo:long>" + stop[5] + "</geo:long>");
				writeToDatabase("</croo:Town>");
			}
		}
	}

	private boolean platformOfStation(String stopName)
	{
		return formatted(stopName).contains("vágány");
	}

	private String formatted(String text)
	{
		return text.trim().toLowerCase();
	}

	private File createDatabaseFile(String rdfDatabaseFile) throws IOException
	{
		File databaseFile = new File(rdfDatabaseFile);
		databaseFile.delete();
		databaseFile.createNewFile();
		return databaseFile;
	}

	private void finalizeDatabase() throws IOException
	{
		writeToDatabase("</rdf:RDF>");
	}
}
