package croo.szakdolgozat.server.database;

public class DatabaseFactory
{

	private static final String RDF_DATABASE_FILE_PROPERTY_KEY = "rdf.database.file";

	public static Database createMockDatabase()
	{
		return new MockDatabase();
	}

	public static Database createRdfDatabase()
	{
		String databaseFile = SystemProperties.GetInstance().getFileLocation(RDF_DATABASE_FILE_PROPERTY_KEY);

		RdfDatabaseGenerator generator = new RdfDatabaseGenerator();
		generator.generateRdfDatabase(databaseFile);

		return new RdfDatabase(databaseFile);
	}
}
