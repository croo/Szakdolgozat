import java.io.FileNotFoundException;
import java.io.IOException;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.util.FileManager;


public class Main {
	private static final String RDF_FILE = "data.rdf";

	public static void main(String[] args) throws IOException,FileNotFoundException {
		RdfDataBuilder builder = new RdfDataBuilder("");
		builder.parseTxtToRdfDatabase(RDF_FILE);
		
		Model model = FileManager.get().loadModel(RDF_FILE);
		model.write(System.out);
	}
}
