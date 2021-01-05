package PS72021.WIA2.controller;

import PS72021.WIA2.model.*;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class VisiteController {

    private static final String DATABASE = "http://localhost:3030/data_polyville";

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/visites")
    public Visite getVisite(){
        return new Visite(getEvents(), getPatrimoines(), getStores());
    }

    public ArrayList<Event> getEvents(){
        ArrayList<Event> events = new ArrayList<>();
        String query = "SELECT DISTINCT * WHERE {\n" +
                "?s <http://www.ps7-wia2.com/events#events> ?o. " +
                "?o <http://www.ps7-wia2.com/events#name_fr> ?name_fr." +
                "?o <http://www.ps7-wia2.com/events#start> ?start." +
                "?o <http://www.ps7-wia2.com/events#end> ?end." +
                "?o <http://www.ps7-wia2.com/events#address> ?address." +
                "?o <http://www.ps7-wia2.com/events#profile> ?profile." +
                "?o <http://www.ps7-wia2.com/events#categories> ?categories." +
                "?o <http://www.ps7-wia2.com/events#description> ?description." +
                "?o <http://www.ps7-wia2.com/events#images> ?images." +
                "?o <http://www.ps7-wia2.com/events#latitude> ?latitude." +
                "?o <http://www.ps7-wia2.com/events#longitude> ?longitude." +
                "?o <http://www.ps7-wia2.com/events#users> ?users." +
                "}";

        RDFConnection conn = RDFConnectionFactory.connect(DATABASE);
        QueryExecution qExec = conn.query(query) ;
        ResultSet results = qExec.execSelect() ;

        QuerySolution sol = results.next();

        Event event = null;
        ArrayList<String> profiles = new ArrayList<>();
        Set<String> users = new HashSet<>();
        String address = null;
        while (results.hasNext() && events.size() <5) {
            String[] sujet = sol.get("o").toString().split("/", -1);

            if(event != null ) {
                if (Integer.parseInt(sujet[sujet.length - 1]) != event.getId()) {

                    events.add(event);
                    profiles = new ArrayList<>();
                    users = new HashSet<>();
                    LocalDate start = LocalDate.parse(sol.get("?start").toString());
                    LocalDate end = LocalDate.parse(sol.get("?end").toString());
                    profiles.add(sol.get("profile").toString());
                    ArrayList<String> categories = new ArrayList<>();
                    String queryUsers = "PREFIX events: <http://www.ps7-wia2.com/events/>\n" +
                            "PREFIX el: <http://www.ps7-wia2.com/events#>\n" +
                            "SELECT DISTINCT * WHERE {\n" +
                            "events:" + sujet[sujet.length - 1] + " el:categories + ?categories }";


                    RDFConnection conn1 = RDFConnectionFactory.connect(DATABASE);
                    QueryExecution qExec1 = conn1.query(queryUsers);
                    ResultSet results1 = qExec1.execSelect();


                    while (results1.hasNext()) {
                        String[] genreSplit = results1.next().get("?categories").toString().split(":",-1);
                        Categorie cat = CategorieController.categorie(genreSplit[genreSplit.length-1]);
                        categories.add(cat.getName());
                    }
                    qExec1.close();
                    conn1.close();
                    address = sol.get("address").toString();
                    event = new Event (Integer.parseInt(sujet[sujet.length - 1]), sol.get("?name_fr").toString(), start, end, address,profiles,categories,
                            sol.get("description").toString(),sol.get("images").toString(),sol.get("latitude").asLiteral().getDouble(),
                            sol.get("longitude").asLiteral().getDouble(), users);

                }else {
                    LocalDate start = LocalDate.parse(sol.get("?start").toString());
                    LocalDate end = LocalDate.parse(sol.get("?end").toString());
                    if(!profiles.contains(sol.get("profile").toString())){
                        profiles.add(sol.get("profile").toString());
                    }
                    ArrayList<String> categories = new ArrayList<>();
                    String queryUsers = "PREFIX events: <http://www.ps7-wia2.com/events/>\n" +
                            "PREFIX el: <http://www.ps7-wia2.com/events#>\n" +
                            "SELECT DISTINCT * WHERE {\n" +
                            "events:" + sujet[sujet.length - 1] + " el:categories + ?categories }";


                    RDFConnection conn1 = RDFConnectionFactory.connect(DATABASE);
                    QueryExecution qExec1 = conn1.query(queryUsers);
                    ResultSet results1 = qExec1.execSelect();


                    while (results1.hasNext()) {
                        String[] genreSplit = results1.next().get("?categories").toString().split(":",-1);
                        Categorie cat = CategorieController.categorie(genreSplit[genreSplit.length-1]);
                        if (!categories.contains(cat.getName())) {
                            categories.add(cat.getName());
                        }
                    }
                    qExec1.close();
                    conn1.close();
                    if(!address.contains(sol.get("address").toString())) {
                        address = address + " " + sol.get("address").toString();
                    }
                    event = new Event (Integer.parseInt(sujet[sujet.length - 1]), sol.get("?name_fr").toString(), start, end, address,profiles,categories,
                            sol.get("description").toString(),sol.get("images").toString(),sol.get("latitude").asLiteral().getDouble(),
                            sol.get("longitude").asLiteral().getDouble(), users);
                }
            }else{
                LocalDate start = LocalDate.parse(sol.get("?start").toString());
                LocalDate end = LocalDate.parse(sol.get("?end").toString());
                profiles.add(sol.get("profile").toString());
                ArrayList<String> categories = new ArrayList<>();
                String queryUsers = "PREFIX events: <http://www.ps7-wia2.com/events/>\n" +
                        "PREFIX el: <http://www.ps7-wia2.com/events#>\n" +
                        "SELECT DISTINCT * WHERE {\n" +
                        "events:" + sujet[sujet.length - 1] + " el:categories + ?categories }";


                RDFConnection conn1 = RDFConnectionFactory.connect(DATABASE);
                QueryExecution qExec1 = conn1.query(queryUsers);
                ResultSet results1 = qExec1.execSelect();


                while (results1.hasNext()) {
                    String[] genreSplit = results1.next().get("?categories").toString().split(":",-1);
                    Categorie cat = CategorieController.categorie(genreSplit[genreSplit.length-1]);
                    categories.add(cat.getName());
                }
                qExec1.close();
                conn1.close();
                address = sol.get("address").toString();
                event = new Event (Integer.parseInt(sujet[sujet.length - 1]), sol.get("?name_fr").toString(), start, end, address,profiles,categories,
                        sol.get("description").toString(),sol.get("images").toString(),sol.get("latitude").asLiteral().getDouble(),
                        sol.get("longitude").asLiteral().getDouble(), users);
            }

            String queryUsers = "PREFIX event: <http://www.ps7-wia2.com/events/>\n" +
                    "PREFIX el: <http://www.ps7-wia2.com/events#>\n" +
                    "SELECT DISTINCT * WHERE {\n" +
                    "event:" + sujet[sujet.length-1] + " el:users + ?users }";

            RDFConnection conn1 = RDFConnectionFactory.connect(DATABASE);
            QueryExecution qExec1 = conn1.query(queryUsers) ;
            ResultSet results1 = qExec1.execSelect() ;

            while (results1.hasNext()){
                users.add(results1.next().get("users").toString());
            }

            qExec1.close();
            conn1.close();
            sol = results.next();
        }
        qExec.close();
        conn.close();
        return events;
    }

    public ArrayList<Patrimoine> getPatrimoines(){
        ArrayList<Patrimoine> patrimoines = new ArrayList<>();
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

        while (results.hasNext() && patrimoines.size() < 4) {
            sujet = sol.get("o").toString().split("/", -1);
            if (Integer.parseInt(sujet[sujet.length - 1]) != patrimoine.getId()) {
                patrimoines.add(patrimoine);
                patrimoine = new Patrimoine(Integer.parseInt(sujet[sujet.length - 1]),  sol.get("name").toString(),sol.get("latitude").asLiteral().getDouble() ,sol.get("longitude").asLiteral().getDouble(), sol.get("image").toString());

            }
            sol = results.next();
        }
        patrimoines.add(patrimoine);
        qExec.close();
        conn.close();
        return patrimoines;
    }

    public ArrayList<Store> getStores()  {
        ArrayList<Store> stores = new ArrayList<>();
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
        while (results.hasNext() && stores.size()<1) {
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
        stores.add(store);
        qExec.close();
        conn.close();
        return stores;
    }
}
