package croo.szakdolgozat.server.database;


public class RdfDatabaseBuilder {

	private static final String SHAPES = "shapes.txt";
	private static final String STOP_TIMES = "stop_times.txt";
	private static final String TRIPS = "trips.txt";
	private static final String CALENDAR_DATES = "calendar_dates.txt";
	private static final String ROUTES = "routes.txt";
	private static final String AGENCY = "agency.txt";
	private static final String CALENDAR = "calendar.txt";
	private static final String STOPS = "stops.txt";
	
	public void parseTxtToRdfDatabase(String tempDir, String rdfDatabaseFile) {
		generateStops(rdfDatabaseFile);
	}

	private void generateStops(String rdfDatabaseFile) {
		// TODO Auto-generated method stub
		
	}
	
}
