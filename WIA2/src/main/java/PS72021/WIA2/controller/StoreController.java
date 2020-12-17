package PS72021.WIA2.controller;

import PS72021.WIA2.Application;
import PS72021.WIA2.model.Store;
import PS72021.WIA2.model.User;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class StoreController {

    private static final String DATABASE = "http://localhost:3030/data_polyville";

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/stores")
    public List<Store> getUsers()  {
        List<Store> stores = new ArrayList<>();
            String query = "SELECT DISTINCT * WHERE {\n" +
                    "?s <http://www.ps7-wia2.com/stores#stores> ?o. " +
                    "?o <http://www.ps7-wia2.com/stores#name_fr> ?name_fr." +
                    "?o <http://www.ps7-wia2.com/stores#opening> ?opening." +
                    "?o <http://www.ps7-wia2.com/stores#address_line1> ?address_line1." +
                    "?o <http://www.ps7-wia2.com/stores#category> ?category." +
                    "?o <http://www.ps7-wia2.com/stores#description> ?description." +
                    "?o <http://www.ps7-wia2.com/stores#latitude> ?latitude." +
                    "?o <http://www.ps7-wia2.com/stores#longitude> ?longitude." +
                    "}";

        RDFConnection conn = RDFConnectionFactory.connect(DATABASE);
        QueryExecution qExec = conn.query(query) ;
        ResultSet results = qExec.execSelect() ;

        QuerySolution sol = results.next();
        String[] sujet = sol.get("o").toString().split("/", -1);

        Store store = new Store(Integer.parseInt(sujet[sujet.length - 1]), sol.get("name_fr").toString(), sol.get("opening").toString(), sol.get("address_line1").toString(),
                sol.get("description").toString(), sol.get("latitude").asLiteral().getDouble(), sol.get("longitude").asLiteral().getDouble());

        Set<String> categories = new HashSet<>();
        categories.add(sol.get("category").toString());
        store.setCategories(categories.toArray());

        for (;results.hasNext();) {
            sujet = sol.get("o").toString().split("/", -1);
            if (Integer.parseInt(sujet[sujet.length - 1]) != store.getId()) {
                store.setCategories(categories.toArray());
                stores.add(store);
                store = new Store(Integer.parseInt(sujet[sujet.length - 1]), sol.get("name_fr").toString(), sol.get("opening").toString(), sol.get("address_line1").toString(),
                        sol.get("description").toString(), sol.get("latitude").asLiteral().getDouble(), sol.get("longitude").asLiteral().getDouble());
                categories = new HashSet<>();
            }
            categories.add(sol.get("category").toString());
            sol = results.next();
        }
        qExec.close();
        conn.close();
        return stores;
    }
}
