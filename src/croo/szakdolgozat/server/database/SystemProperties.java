package croo.szakdolgozat.server.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
/**
 * The application can be parameterized with a config.properties file. On
 * application start the location of that file should be passed as jvm argument:
 * -Dconfig.location=<location of the config file>
 * 
 * This singleton class loads the config.properties file and makes it available
 * for the rest of the application.
 * 
 * @author Croo
 * 
 */
public class SystemProperties
{

	private static SystemProperties INSTANCE = null;
	private Properties systemProperties;

	private SystemProperties()
	{
		String configFile = System.getProperty("config.location");
		systemProperties = new Properties();
		try {
			systemProperties.load(new FileInputStream(configFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static synchronized SystemProperties GetInstance()
	{
		if (INSTANCE == null)
			INSTANCE = new SystemProperties();
		return INSTANCE;
	}

	public String getFileLocation(String propertyKey)
	{
		String databaseFile = systemProperties.getProperty(propertyKey);
		return databaseFile;
	}
}
