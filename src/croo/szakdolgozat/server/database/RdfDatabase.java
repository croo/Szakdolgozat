package croo.szakdolgozat.server.database;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;

import croo.szakdolgozat.shared.Route;

public class RdfDatabase implements Database
{

	@Override
	public boolean townExists(String town)
	{
		String namespace = "http://example.org/base#";
		Model model = FileManager.get().loadModel("data.rdf");
		Resource townResource = model.getResource(namespace + town);
		return model.containsResource(model.getRDFNode(townResource.asNode()));
	}

	@Override
	public Route getRoute(String startTown, String endTown)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
