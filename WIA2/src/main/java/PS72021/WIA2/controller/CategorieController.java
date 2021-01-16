package PS72021.WIA2.controller;

import PS72021.WIA2.model.Bus;
import PS72021.WIA2.model.Categorie;
import PS72021.WIA2.model.User;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class CategorieController {
    private static final String DATABASE = "http://localhost:3030/data_polyville";

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/categorie")
    public static Categorie categorie(@RequestParam String categorieId) {
        Categorie categorie;
        String query = "SELECT DISTINCT * WHERE {\n" +
                "<http://www.ps7-wia2.com/categories/"+categorieId+"> <http://www.ps7-wia2.com/categories#name> ?name." +
                "}";

        RDFConnection conn = RDFConnectionFactory.connect(DATABASE);
        QueryExecution qExec = conn.query(query) ;
        ResultSet results = qExec.execSelect() ;

        QuerySolution sol = results.next();
        categorie = new Categorie(Integer.parseInt(categorieId), sol.get("?name").toString());
        qExec.close();
        conn.close();
        return categorie;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/categories")
    public List<Categorie> getCategories()  {
        List<Categorie> listCategories = new ArrayList<>();
        String query = "SELECT DISTINCT * WHERE {\n" +
                "?s <http://www.ps7-wia2.com/categories#categories> ?o. " +
                "?o <http://www.ps7-wia2.com/categories#name> ?name."+
                "}";

        RDFConnection conn = RDFConnectionFactory.connect(DATABASE);
        QueryExecution qExec = conn.query(query) ;
        ResultSet results = qExec.execSelect() ;

        QuerySolution sol = results.next();
        String[] sujet = sol.get("o").toString().split("/", -1);

        Categorie categorie = new Categorie(Integer.parseInt(sujet[sujet.length - 1]), sol.get("name").toString());

        for (;results.hasNext();) {
            sujet = sol.get("o").toString().split("/", -1);
            if (Integer.parseInt(sujet[sujet.length - 1]) != categorie.getId()) {
                listCategories.add(categorie);
                categorie = new Categorie(Integer.parseInt(sujet[sujet.length - 1]), sol.get("name").toString());

            }
            sol = results.next();
        }
        listCategories.add(categorie);
        qExec.close();
        conn.close();
        return listCategories;
    }
}
