package PS72021.WIA2;

import org.apache.jena.query.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.sparql.core.DatasetImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@RestController
@SpringBootApplication
public class Application {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);

		Application test = new Application();

		String query = "SELECT DISTINCT ?s ?p ?o WHERE {\n" +
				" ?s ?p ?o " +
				"}";

		String filePath = "database/utilisateur.jsonld";

		ResultSet results = executeQuery(query, filePath);
		for ( ; results.hasNext() ; ) {
			QuerySolution soln = results.nextSolution() ;
			//System.out.println(soln);
		}

		//RDFDataMgr.write(System.out, results.getResourceModel(), Lang.JSONLD) ;
	}

	public static ResultSet executeQuery(String queryString, String filePath) throws Exception {

        QueryExecution exec = QueryExecutionFactory.create(QueryFactory.create(queryString), new
                DatasetImpl(RDFDataMgr.loadModel(filePath)));
        return exec.execSelect();

    }
}
