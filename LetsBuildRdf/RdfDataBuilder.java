import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import au.com.bytecode.opencsv.CSVReader;

public class RdfDataBuilder
{

	// private static final String CALENDAR_DATES = "calendar_dates.txt";
	// private static final String ROUTES = "routes.txt";
	// private static final String AGENCY = "agency.txt";
	// private static final String CALENDAR = "calendar.txt";
	private static final String STOPS = "stops.txt";
	private static final String STOP_TIMES = "stop_times.txt";
	private static final String TRIPS = "trips.txt";
	private static final String SHAPES = "shapes.txt";
	private static List<String[]> stops;
	private static Map<String, List<String[]>> stop_times;
	private static List<String[]> trips;
	private static CSVReader shapesCsv;
	private File database;

	public RdfDataBuilder(String tempDir) throws IOException, FileNotFoundException
	{
		stops = new CSVReader(new FileReader(tempDir + STOPS)).readAll();
		List<String[]> stoptimesCsv = new CSVReader(new FileReader(tempDir + STOP_TIMES)).readAll();
		stop_times = createMapWithIdAsKeyFromCsvList(stoptimesCsv);
		trips = new CSVReader(new FileReader(tempDir + TRIPS)).readAll();
		shapesCsv = new CSVReader(new FileReader(tempDir + SHAPES));
	}

	public void parseTxtToRdfDatabase(String rdfDatabaseFile) throws IOException
	{
		database = createDatabaseFile(rdfDatabaseFile);
		initializeDatabase();

		generateStops();
		generateRoutes();

		finalizeDatabase();
		clearThoseDamnBigLists();
	}

	private void clearThoseDamnBigLists()
	{
		stops.clear();
	}

	private void generateRoutes() throws IOException
	{
		Map<String, List<String[]>> stopsMap = createMapWithIdAsKeyFromCsvList(stops);

		for (String[] trip : trips) {
			String tripId = trip[0];
			List<String[]> stopsInTrip = stop_times.get(tripId);

			String startTown = "";
			String endTown = "";
			List<String> block = new ArrayList<String>();
			block.add("<croo:Route rdf:ID=\"from" + startTown + "to" + endTown + "\">");
			block.add("<croo:startTown rdf:resource=\"#" + startTown + "\" />");
			block.add("<croo:endTown rdf:resource=\"#" + endTown + "\" />");
			block.add("<georss:line>" + getCoorinateList() + "</georss:line>");
			block.add("</croo:Route>");
			writeBlockToDatabase(block);
		}
	}

	private String getCoorinateList()
	{
		// TODO Auto-generated method stub
		return null;
	}

	private void writeBlockToDatabase(List<String> data) throws IOException
	{
		FileUtils.writeLines(database, data, true);
	}

	private void initializeDatabase() throws IOException
	{
		List<String> block = new ArrayList<String>();
		block.add("<?xml version=\"1.0\"?>");
		block.add("<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"");
		block.add("xmlns:geo=\"http://www.w3.org/2003/01/geo/wgs84_pos#\"");
		block.add("xmlns:georss=\"http://www.georss.org/georss#\"");
		block.add("xmlns:foaf=\"http://xmlns.com/foaf/0.1/\"");
		block.add("xml:base=\"http://example.org/base#\"");
		block.add("xmlns:croo=\"http://example.org/croo#\">");
		writeBlockToDatabase(block);
	}

	/*
	 * stops_headers: 0-stop_id; 1-stop_code; 2-stop_name; 3-stop_desc;
	 * 4-stop_lat; 5-stop_lon; 6-zone_id; 7-stop_url; 8-location_type;
	 * 9-parent_station; 10-stop_osm_entity; 11-stop_timezone
	 */
	private void generateStops() throws IOException
	{
		for (int i = 1; i < stops.size(); i++) {
			String[] row = stops.get(i);
			if (!platformOfStation(row)) {
				List<String> block = new ArrayList<String>();
				block.add("\n");
				block.add("<croo:Town rdf:ID=\"" + formatted(row[2]) + "\">");
				block.add("<foaf:Name>" + row[2] + "</foaf:Name>");
				block.add("<geo:lat>" + row[4] + "</geo:lat>");
				block.add("<geo:long>" + row[5] + "</geo:long>");
				block.add("</croo:Town>");
				writeBlockToDatabase(block);
			}
		}
	}

	private boolean platformOfStation(String[] row)
	{
		return formatted(row[2]).contains("vágány");
	}

	private String formatted(String text)
	{
		return text.trim().toLowerCase();
	}

	// private HashMap<String,Integer> createHeaders(String[] header){
	// HashMap<String,Integer> headerResult = new HashMap<String, Integer>();
	// for(int i = 0;i<header.length;i++){
	// headerResult.put(header[i],i);
	// }
	// return headerResult;
	// }

	private File createDatabaseFile(String rdfDatabaseFile) throws IOException
	{
		File databaseFile = new File(rdfDatabaseFile);
		databaseFile.delete();
		databaseFile.createNewFile();
		return databaseFile;
	}

	private void finalizeDatabase() throws IOException
	{
		FileUtils.write(database, "\n</rdf:RDF>\n", "UTF-8", true);
	}

	private Map<String, List<String[]>> createMapWithIdAsKeyFromCsvList(List<String[]> table)
	{
		Map<String, List<String[]>> stopTimes = new HashMap<String, List<String[]>>();
		for (String[] row : table) {
			addRowToMap(stopTimes, row);
		}
		return stopTimes;
	}

	private void addRowToMap(Map<String, List<String[]>> stopTimes, String[] row)
	{
		String id = row[0];
		if (!stopTimes.containsKey(id)) {
			List<String[]> rowsWithSameId = new ArrayList<String[]>();
			String[] elementsInRow = getElementsInRow(row);
			rowsWithSameId.add(elementsInRow);
			stopTimes.put(id, rowsWithSameId);
		} else {
			stopTimes.get(id).add(getElementsInRow(row));
		}
	}

	private String[] getElementsInRow(String[] row)
	{
		String[] elementsInRow = new String[row.length - 1];
		for (int i = 1; i < row.length; i++) {
			elementsInRow[i - 1] = row[i];
		}
		return elementsInRow;
	}
}
