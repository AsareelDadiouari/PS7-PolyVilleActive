package PS72021.WIA2.controller;

import PS72021.WIA2.Application;
import PS72021.WIA2.model.Store;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class StoreController {

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/stores")
    public List<Store> getUsers() throws Exception {
        List<Store> stores = new ArrayList<>();
        String query = "SELECT DISTINCT ?s ?p ?o WHERE {\n" +
                "?s <http://www.ps7-wia2.com/stores#stores> ?o " +
                "}";

        String filePath = "database/magasins2.jsonld";
        ResultSet results = Application.executeQuery(query, filePath);
        for (int i = 1; results.hasNext() && i <25; i++) {
            QuerySolution querySolution = results.next();
            query = "SELECT DISTINCT * WHERE {\n" +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/stores#name_fr> ?name_fr." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/stores#opening> ?opening." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/stores#address_line1> ?address_line1." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/stores#category> ?category." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/stores#description> ?description." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/stores#latitude> ?latitude." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/stores#longitude> ?longitude." +
                    "}";
            ResultSet results2 = Application.executeQuery(query, filePath);
            QuerySolution sol = results2.next();
            Store store = new Store(i, sol.get("name_fr").toString(), sol.get("opening").toString(), sol.get("address_line1").toString(),
                    sol.get("description").toString(), sol.get("latitude").asLiteral().getDouble(), sol.get("longitude").asLiteral().getDouble());
            Set<String> categories = new HashSet<>();
            categories.add(sol.get("category").toString());
            for (;results2.hasNext();) {
                sol = results2.next();
                categories.add(sol.get("category").toString());
            }
            store.setCategories(categories.toArray());
            stores.add(store);
        }
        return stores;
    }
}
