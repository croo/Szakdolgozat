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
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class RdfDatabaseBuilder
{
	private static final String STOPS = "stops.txt";
	private static final String STOP_TIMES = "stop_times.txt";
	private static final String TRIPS = "trips.txt";
	private static final String SHAPES = "shapes.txt";
	private static List<String[]> stops;
	private static List<String[]> trips;
	private static List<String[]> stopTimes;
	private static List<String[]> shapesCsv;
	private File database;
	private Writer out;

	public RdfDatabaseBuilder(final String tempDir) throws IOException, FileNotFoundException
	{
		stops = new CSVReader(new FileReader(tempDir + STOPS)).readAll();
		System.out.println("stops loaded.");
		trips = new CSVReader(new FileReader(tempDir + TRIPS)).readAll();
		System.out.println("trips loaded.");
		stopTimes = new CSVReader(new FileReader(tempDir + STOP_TIMES)).readAll();
		System.out.println("stoptimes loaded.");
		shapesCsv = new CSVReader(new FileReader(tempDir + SHAPES)).readAll();
		System.out.println("shapes loaded.");
	}

	public void parseTxtToRdfDatabase(String rdfDatabaseFile) throws IOException
	{
		Runtime r = Runtime.getRuntime();
		database = createDatabaseFile(rdfDatabaseFile);
		out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(database), "UTF-8"), 1024 * 1024 * 2);
		initializeDatabase();

		generateStops();
		r.gc();
		generateRoutes();
		r.gc();
		finalizeDatabase();
		clearThoseDamnBigLists();
		r.gc();
	}

	private void clearThoseDamnBigLists() throws IOException
	{
		stops.clear();
		trips.clear();
		stopTimes.clear();
		out.close();
	}

	// in trip.txt
	// 8 - shapes_id ; 2 - trips_id
	private void generateRoutes() throws IOException
	{
		int i = 1;
		for (String[] trip : trips) {
			generateRoutesOfTrip(trip[2], trip[8]);
			System.out.println("" + i + " - generating routes for trip: " + trip[2]);
			i++;
			if (i % 10 == 0)
				Runtime.getRuntime().gc();
		}
	}

	// in stop_times.txt
	// 3 - stop_id ; 8 - shape_dist_traveled
	private void generateRoutesOfTrip(String tripId, String shapesId) throws IOException
	{
		final List<String[]> stopTimesInTrip = getStopTimesInTrip(tripId);
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

	private List<String[]> getStopTimesInTrip(String tripId)
	{
		List<String[]> stopsInTrip = new ArrayList<String[]>();
		for (String[] stopTime : stopTimes) {
			if (stopTime[0].equals(tripId)) {
				stopsInTrip.add(stopTime);
			}
		}
		return stopsInTrip;
	}

	private void createRouteBetweenTwoStopsInTrip(String shapesId, String startTownId, String endTownId,
			String startTown_shape_dist_traveled, String endTown_shape_dist_traveled) throws IOException
	{
		String[] startTownRow = null;
		String[] endTownRow = null;

		boolean startTownFound = false;
		boolean endTownFound = false;
		for (String[] stop : stops) {
			if (stop[0].equals(startTownId)) {
				startTownRow = stop;
				startTownFound = true;
			}
			if (stop[0].equals(endTownId)) {
				endTownRow = stop;
				endTownFound = true;
			}
			if (startTownFound && endTownFound)
				break;
		}

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
	// 4 - shape_dist_traveled
	private String getCoorinateList(String shapesId, String start_dist_traveled, String end_dist_traveled) throws IOException
	{
		List<String[]> shapesOfTrip = getShapesOfTrip(shapesId);
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

	private List<String[]> getShapesOfTrip(String shapesId) throws IOException
	{
		List<String[]> shapes = new ArrayList<String[]>();

		Boolean alreadyFound = false;
		Boolean finishedReading = false;
		for (int i = 1; i < shapesCsv.size(); i++) {
			String[] shape = shapesCsv.get(i);
			if (shape[0].equals(shapesId)) {
				shapes.add(shape);
				alreadyFound = true;
			} else if (!shape[0].equals(shapesId) && alreadyFound) {
				finishedReading = true;
			}
			if (finishedReading) {
				break;
			}
		}
		return shapes;
	}

	// in shapes.txt
	// 0 - shape_id
	// private List<String[]> getShapesOfTrip(String shapesId) throws
	// IOException
	// {
	// List<String[]> shapes = new ArrayList<String[]>();
	// shapesCsv = new CSVReader(new FileReader(tempDir + SHAPES));
	// String[] shape = shapesCsv.readNext();// throw away headers
	// Boolean alreadyFound = false;
	// Boolean finishedReading = false;
	// shape = shapesCsv.readNext();
	// while (shape != null) {
	// if (shape[0].equals(shapesId)) {
	// shapes.add(shape);
	// alreadyFound = true;
	// } else if (!shape[0].equals(shapesId) && alreadyFound) {
	// finishedReading = true;
	// }
	// if (finishedReading) {
	// break;
	// }
	// shape = shapesCsv.readNext();
	// }
	// return shapes;
	// }

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
		// FileUtils.writeLines(database, data, true);
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
		for (int i = 1; i < stops.size(); i++) {
			String[] stop = stops.get(i);
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
