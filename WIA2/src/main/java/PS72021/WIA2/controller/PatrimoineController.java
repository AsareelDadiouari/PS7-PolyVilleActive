package PS72021.WIA2.controller;

import PS72021.WIA2.model.Patrimoine;
import PS72021.WIA2.model.Restaurant;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class PatrimoineController {

    private static final String DATABASE = "http://localhost:3030/data_polyville";

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/patrimoines")
    public List<Patrimoine> getPatrimoines()throws Exception{
        List<Patrimoine> patrimoines = new ArrayList<>();
        String query = "SELECT DISTINCT * WHERE {\n" +
                "?s <http://www.ps7-wia2.com/patrimoines#patrimoines> ?o. " +
                "?o <http://www.ps7-wia2.com/patrimoines#name> ?name." +
                "?o <http://www.ps7-wia2.com/patrimoines#latitude> ?latitude." +
                "?o <http://www.ps7-wia2.com/patrimoines#longitude> ?longitude." +
                "?o <http://www.ps7-wia2.com/patrimoines#image> ?image." +
                "}";

        RDFConnection conn = RDFConnectionFactory.connect(DATABASE);
        QueryExecution qExec = conn.query(query) ;
        ResultSet results = qExec.execSelect() ;

        QuerySolution sol = results.next();

        Patrimoine patrimoine = null;
        String[] sujet = sol.get("o").toString().split("/", -1);
        patrimoine = new Patrimoine(Integer.parseInt(sujet[sujet.length - 1]),  sol.get("name").toString(),sol.get("latitude").asLiteral().getDouble() ,sol.get("longitude").asLiteral().getDouble(), sol.get("image").toString());

        while (results.hasNext()) {
            sujet = sol.get("o").toString().split("/", -1);
            if (Integer.parseInt(sujet[sujet.length - 1]) != patrimoine.getId()) {
                patrimoines.add(patrimoine);
                patrimoine = new Patrimoine(Integer.parseInt(sujet[sujet.length - 1]),  sol.get("name").toString(),sol.get("latitude").asLiteral().getDouble() ,sol.get("longitude").asLiteral().getDouble(), sol.get("image").toString());

            }
            sol = results.next();
        }
        patrimoines.add(patrimoine);
        return patrimoines;
    }
}
