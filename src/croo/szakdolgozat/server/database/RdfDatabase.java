package croo.szakdolgozat.server.database;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.util.FileManager;

import croo.szakdolgozat.shared.Route;

public class RdfDatabase implements Database
{

	@Override
	public boolean townExists(String town)
	{
		String namespace = "http://example.org/test#";
		Model m = FileManager.get().loadModel("data.rdf");

		return false;

	}

	@Override
	public Route getRoute(String startTown, String endTown)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
