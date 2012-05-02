package croo.szakdolgozat.server.database;


public class DatabaseFactory {

	public Database createMockDatabase() {
		return new MockDatabase();
	}

	public Database createRdfDatabase() {
		// TODO: read this string from the config.properties file
		// TODO: make the tests work with the config.properties file
		// TODO: download, unzip and parse the database stuff into this rdf file.
		String databaseFile = "data.rdf"; 		
		
		return new RdfDatabase(databaseFile);
		
	}

}
