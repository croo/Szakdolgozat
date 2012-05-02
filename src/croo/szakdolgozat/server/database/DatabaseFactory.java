package croo.szakdolgozat.server.database;

import java.io.BufferedOutputStream;
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
	
	private void copyInputStream(InputStream in, OutputStream out)
			throws IOException {
		byte[] buffer = new byte[1024];
		int len;
		while ((len = in.read(buffer)) >= 0)
			out.write(buffer, 0, len);
		in.close();
		out.close();
	}

	private void unZipper() throws IOException {
		saveUrl("a.html", "http://www.asdf.org");
		try {
			ZipFile zipFile = new ZipFile("test.zip");
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				System.out.println("Extracting file: " + entry.getName());
				copyInputStream(
						zipFile.getInputStream(entry),
						new BufferedOutputStream(new FileOutputStream(entry
								.getName())));
			}
			zipFile.close();
		} catch (SecurityException e) {
			System.err.println("Security Exception. Cannot read or write :"
					+ e.getMessage());
			e.printStackTrace();
		}
	}

	private void saveUrl(String filename, String urlString)
			throws IOException {
		URL google = new URL(urlString);
		ReadableByteChannel rbc = Channels.newChannel(google.openStream());
		FileOutputStream fos = new FileOutputStream(filename);
		fos.getChannel().transferFrom(rbc, 0, 1 << 24);
	}

}
