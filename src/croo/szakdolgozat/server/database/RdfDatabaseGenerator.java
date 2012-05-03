package croo.szakdolgozat.server.database;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Generates the RDF database from a downloaded source.
 * The source should be exactly in the format described on
 * https://developers.google.com/transit/gtfs/reference
 * 
 * The public methods can be used with absolute and relative path and describes 
 * where the generated RDF database will be.
 * 
 * The class uses the default java temp directory for unzipping and parsing the files.
 * You can modify that temp dir by changing the java.io.tmpdir JVM environment variable.
 * 
 * @author epterba
 *
 */
public class RdfDatabaseGenerator
{
	private static final String TEMP_DATA_ZIP = "MavSourceData.zip";
	private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
	
	public void generateRdfDatabase(String rdfDatabaseFile) throws IOException
	{
		downloadSource();
		unzip(TEMP_DIR + TEMP_DATA_ZIP);
		deleteZipFile(TEMP_DIR + TEMP_DATA_ZIP);
		new RdfDatabaseBuilder().parseTxtToRdfDatabase(TEMP_DIR,rdfDatabaseFile);
	}
	
	private void deleteZipFile(String file) {
		File zipFile = new File(file);
		zipFile.delete();
	}

	private void downloadSource() throws IOException
	{
		String sourceURL = SystemProperties.GetInstance().getFileLocation("mav.source.zip.url");
		saveUrl(TEMP_DIR + TEMP_DATA_ZIP, sourceURL);
	}
	
	private void saveUrl(String file, String urlString) throws IOException
	{
		URL google = new URL(urlString);
		ReadableByteChannel rbc = Channels.newChannel(google.openStream());
		FileOutputStream fos = new FileOutputStream(file);
		fos.getChannel().transferFrom(rbc, 0, 1 << 24);
	}
	
	private void unzip(String file) throws IOException
	{
		try {
			ZipFile zipFile = new ZipFile(file);
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				System.err.println("Extracting file: " + entry.getName());
				copyInputStream(zipFile.getInputStream(entry), new BufferedOutputStream(new FileOutputStream(entry.getName())));
			}
			zipFile.close();
		} catch (SecurityException e) {
			System.err.println("Security Exception. Don't have access to read or write: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void copyInputStream(InputStream in, OutputStream out) throws IOException
	{
		byte[] buffer = new byte[1024];
		int length;
		while ((length = in.read(buffer)) >= 0)
			out.write(buffer, 0, length);
		in.close();
		out.close();
	}
}
