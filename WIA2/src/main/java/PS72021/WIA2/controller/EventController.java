package PS72021.WIA2.controller;

import PS72021.WIA2.Application;
import PS72021.WIA2.model.Event;
import PS72021.WIA2.model.Store;
import PS72021.WIA2.model.User;
import org.apache.jena.query.*;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;

@RestController
public class EventController {

    private static final String DATABASE = "http://localhost:3030/data_polyville";

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/events")
    public ArrayList<Event> getEvents(){

        ArrayList<Event> events = new ArrayList<>();
        String query = "SELECT * WHERE {\n" +
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
        ArrayList<String> categories = new ArrayList<>();
        ArrayList<String> users = new ArrayList<>();
        String address = null;
        while (results.hasNext()) {
            String[] sujet = sol.get("o").toString().split("/", -1);
            if(event != null ) {
                if (Integer.parseInt(sujet[sujet.length - 1]) != event.getId()) {
                    events.add(event);
                    profiles = new ArrayList<>();
                    categories = new ArrayList<>();
                    users = new ArrayList<>();
                    LocalDate start = LocalDate.parse(sol.get("?start").toString());
                    LocalDate end = LocalDate.parse(sol.get("?end").toString());
                    profiles.add(sol.get("profile").toString());
                    categories.add(sol.get("categories").toString());
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
                    if (!categories.contains(sol.get("categories").toString())){
                        categories.add(sol.get("categories").toString());
                    }
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
                categories.add(sol.get("categories").toString());
                address = sol.get("address").toString();
                event = new Event (Integer.parseInt(sujet[sujet.length - 1]), sol.get("?name_fr").toString(), start, end, address,profiles,categories,
                        sol.get("description").toString(),sol.get("images").toString(),sol.get("latitude").asLiteral().getDouble(),
                        sol.get("longitude").asLiteral().getDouble(), users);
            }

                sol = results.next();


        }
        qExec.close();
        conn.close();

        return events;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/eventsRecommandations")
    public ArrayList<Event> getEventsPersonnalises(@RequestParam String userId) throws Exception {
        User user = UserController.user(userId);
        String[] interests = user.getInterests();
        ArrayList<Event> events = new ArrayList<>();
        String query = "SELECT DISTINCT ?s ?p ?o WHERE {\n" +
                "?s <http://www.ps7-wia2.com/events#events> ?o " +
                "}";

        String filePath = "database/evenements2.jsonld";
        ResultSet results = Application.executeQuery(query, filePath);
        for (int i = 1; results.hasNext() && events.size()<10; i++) {
            QuerySolution querySolution = results.next();
            query = "SELECT * WHERE {\n" +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#name_fr> ?name_fr." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#start> ?start." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#end> ?end." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#address> ?address." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#profile> ?profile." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#categories> ?categories." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#description> ?description." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#images> ?images." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#latitude> ?latitude." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#longitude> ?longitude." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#users> ?users." +
                    "}";
            ResultSet results2 = Application.executeQuery(query, filePath);
            QuerySolution sol = results2.next();
            boolean recommanded = false;
            ArrayList<String> categories = new ArrayList<>();
            for(int j = 0; j < interests.length ; j++ ){
                if(sol.get("categories").toString().equals(interests[j])){
                    recommanded = true;
                }
            }

            categories.add(sol.get("categories").toString());
            for (;results2.hasNext();) {
                sol = results2.next();
                for(int j = 0; j < interests.length ; j++ ) {
                    if (sol.get("categories").toString().equals(interests[j])) {
                        recommanded = true;
                    }
                }
                categories.add(sol.get("categories").toString());
            }

            if(recommanded) {
                ArrayList<String> usersIndb = new ArrayList<>();
                usersIndb.add(sol.get("users").toString());
                for (;results2.hasNext();) {
                    sol = results2.next();
                    usersIndb.add(sol.get("users").toString().substring(sol.get("users").toString().length() - 1));
                }

                LocalDate start = LocalDate.parse(sol.get("?start").toString());
                LocalDate end = LocalDate.parse(sol.get("?end").toString());
                ArrayList<String> profiles = new ArrayList<>();
                profiles.add(sol.get("profile").toString());
                for (; results2.hasNext(); ) {
                    sol = results2.next();
                    profiles.add(sol.get("profile").toString());
                }

                String[] test = querySolution.get("o").toString().split("/", -1);
                Event event = new Event(Integer.parseInt(test[test.length - 1]), sol.get("?name_fr").toString(), start, end, sol.get("address").toString(),profiles,categories, sol.get("description").toString(), sol.get("images").toString(), sol.get("latitude").asLiteral().getDouble(),
                        sol.get("longitude").asLiteral().getDouble(), usersIndb);
                events.add(event);
            }
        }
        return events;
    }
}
