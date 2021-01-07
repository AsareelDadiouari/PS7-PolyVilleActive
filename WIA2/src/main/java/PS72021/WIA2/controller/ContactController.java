package PS72021.WIA2.controller;

import PS72021.WIA2.model.User;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ContactController {

    private static final String DATABASE = "http://localhost:3030/data_polyville";

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/contactsRecommended/{userId}")
    public List<User> getContactsRecommended(@PathVariable("userId") String userId){
        List<User> contacts = new ArrayList<>();

        String prefixes =
                "PREFIX u: <http://www.ps7-wia2.com/users/> " +
                "PREFIX um: <http://www.ps7-wia2.com/users#> ";

        String query = prefixes + " SELECT DISTINCT ?o ?firstname ?lastname ?type ?role WHERE { " +
                    "?s um:users ?o. " +
                    "OPTIONAL { u:" + userId + " um:publications ?publications. } " +
                    "OPTIONAL { ?o um:publications ?otherPublications. } " +
                    "OPTIONAL { u:" + userId + " um:events ?events. } " +
                    "OPTIONAL { ?o um:events ?otherEvents. } " +
                    "OPTIONAL { u:" + userId + " um:groups ?groups. } " +
                    "OPTIONAL { ?o um:groups ?otherGroups. } " +
                    "FILTER (?publications IN (?otherPublications) || ?events IN (?otherEvents) || ?groups IN (?otherGroups)) " +
                    "FILTER (?o != u:" + userId + ") " +
                    "?o um:firstname ?firstname. " +
                    "?o um:lastname ?lastname. " +
                    "?o um:type ?type." +
                    "?o um:role ?role." +
                "}";

        RDFConnection conn = RDFConnectionFactory.connect(DATABASE);
        QueryExecution qExec = conn.query(query) ;
        ResultSet results = qExec.execSelect() ;
        QuerySolution sol;
        String[] sujet;

        while (results.hasNext()) {
            sol = results.next();
            sujet = sol.get("o").toString().split("/", -1);
            User user = new User(Integer.parseInt(sujet[sujet.length - 1]), sol.get("?firstname").toString(), sol.get("?lastname").toString(), sol.get("?type").toString(), sol.get("?role").toString());
            contacts.add(user);
        }

        conn.close();
        qExec.close();

        return contacts;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/contacts/{userId}")
    public List<User> getContacts(@PathVariable("userId") String userId){
        List<User> contacts = new ArrayList<>();

        String prefixes =
                "PREFIX u: <http://www.ps7-wia2.com/users/> " +
                "PREFIX um: <http://www.ps7-wia2.com/users#> ";

        String query = prefixes + " SELECT DISTINCT ?o ?firstname ?lastname ?type ?role WHERE { " +
                "?s um:users ?o. " +
                "?o um:firstname ?firstname. " +
                "?o um:lastname ?lastname. " +
                "?o um:type ?type." +
                "?o um:role ?role." +
                "FILTER (?o != u:" + userId + ") " +
                "}";

        RDFConnection conn = RDFConnectionFactory.connect(DATABASE);
        QueryExecution qExec = conn.query(query) ;
        ResultSet results = qExec.execSelect() ;
        QuerySolution sol;
        String[] sujet;

        while (results.hasNext()) {
            sol = results.next();
            sujet = sol.get("o").toString().split("/", -1);
            User user = new User(Integer.parseInt(sujet[sujet.length - 1]), sol.get("?firstname").toString(), sol.get("?lastname").toString(), sol.get("?type").toString(), sol.get("?role").toString());
            contacts.add(user);
        }

        conn.close();
        qExec.close();

        return contacts;
    }

}
