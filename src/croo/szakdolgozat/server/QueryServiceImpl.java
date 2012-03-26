package croo.szakdolgozat.server;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import croo.szakdolgozat.client.stubs.QueryService;
import croo.szakdolgozat.shared.Coordinates;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class QueryServiceImpl extends RemoteServiceServlet implements QueryService
{

	Map<String, Coordinates> database = new HashMap<String, Coordinates>();

	public QueryServiceImpl()
	{
		database.put("budapest", new Coordinates(47.309, 19.500));
		database.put("esztergom", new Coordinates(47.7776069, 18.7435935));
	}

	@Override
	public Coordinates query(String place)
	{
		if (database.containsKey(place.toLowerCase()))
		{
			return database.get(place.toLowerCase());
		} else
		{
			return null;
		}
	}
	// @Override
	// public String query(String name) {
	// QueryExecution qe = null;
	// try {
	// StringBuilder answer = new StringBuilder();
	// Model m = ModelFactory.createDefaultModel();
	// String queryString =
	// "PREFIX dgp32:  <http://data-gov.tw.rpi.edu/vocab/p/32/> "
	// + "PREFIX xsd:  <http://www.w3.org/2001/XMLSchema#> "
	// + "SELECT ?magnitude ?deph ?region ?datetime ?latitude ?longitude "
	// + "FROM <http://data-gov.tw.rpi.edu/raw/34/data-34.rdf> "
	// + "WHERE {  ?entry dgp32:magnitude ?magnitude ; "
	// + "dgp32:region ?region ; "
	// + "dgp32:lat ?latitude ; "
	// + "dgp32:lon ?longitude ; "
	// + "dgp32:datetime ?datetime ; "
	// + "dgp32:depth ?deph . " +
	//
	// "} ORDER BY DESC(?magnitude) LIMIT 5";
	//
	// ResultSet resultSet = select(queryString, m);
	// ResultSetFormatter.out(System.out, resultSet);
	// List<String> resvars = resultSet.getResultVars();
	// GWT.log("lol");
	// for (String answ : resvars) {
	// answer.append(answ);
	// }
	// return answer.toString();
	// } catch (Exception e) {
	// return e.getMessage();
	// } finally {
	// if (qe != null)
	// qe.close();
	// }
	// }

	// private ResultSet select(String queryString, Model model) {
	// Query query = QueryFactory.create(queryString);
	// QueryExecution qexec = QueryExecutionFactory.create(query, model);
	// ResultSet results = ResultSetFactory.copyResults(qexec.execSelect());
	// qexec.close();
	// return results;
	// }

}
