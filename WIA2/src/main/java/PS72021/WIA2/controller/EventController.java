package PS72021.WIA2.controller;

import PS72021.WIA2.Application;
import PS72021.WIA2.model.Event;
import PS72021.WIA2.model.User;
import org.apache.jena.base.Sys;
import org.apache.jena.query.*;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.system.Txn;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@RestController
public class EventController {

    private static final String DATABASE = "http://localhost:3030/data_polyville";

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/events")
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
        ArrayList<String> categories = new ArrayList<>();
        Set<String> users = new HashSet<>();
        String address = null;
        while (results.hasNext()) {
            String[] sujet = sol.get("o").toString().split("/", -1);
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

            if(event != null ) {
                if (Integer.parseInt(sujet[sujet.length - 1]) != event.getId()) {
                    events.add(event);
                    profiles = new ArrayList<>();
                    categories = new ArrayList<>();
                    users = new HashSet<>();
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

            users.clear();
        }
        qExec.close();
        conn.close();

        return events;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/eventsRecommandations")
    public ArrayList<Event> getEventsPersonnalises(@RequestParam String userId) {
        User user = UserController.user(userId);
        String[] interests = user.getInterests();
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
        Set<String> users = new HashSet<>();
        String address = null;
        boolean recommanded = false;
        while (results.hasNext()) {
            String[] sujet = sol.get("o").toString().split("/", -1);
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

            if(event != null ) {
                if (Integer.parseInt(sujet[sujet.length - 1]) != event.getId()) {
                    for(int j = 0; j < interests.length ; j++ ){
                        if(categories.contains(interests[j])){
                            recommanded = true;
                        }
                    }
                    if(recommanded) {
                        events.add(event);
                    }
                    profiles = new ArrayList<>();
                    categories = new ArrayList<>();
                    users = new HashSet<>();
                    LocalDate start = LocalDate.parse(sol.get("?start").toString());
                    LocalDate end = LocalDate.parse(sol.get("?end").toString());
                    profiles.add(sol.get("profile").toString());
                    categories.add(sol.get("categories").toString());
                    address = sol.get("address").toString();
                    event = new Event (Integer.parseInt(sujet[sujet.length - 1]), sol.get("?name_fr").toString(), start, end, address,profiles,categories,
                            sol.get("description").toString(),sol.get("images").toString(),sol.get("latitude").asLiteral().getDouble(),
                            sol.get("longitude").asLiteral().getDouble(), users);
                    recommanded = false;

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
    @PostMapping(value = "/events/{eventId}", produces= MediaType.APPLICATION_JSON_VALUE)
    public String joinEvent(HttpServletRequest req, HttpServletResponse res, @PathVariable("eventId") String eventId) throws IOException {
        BufferedReader bufferedReader = req.getReader();
        String userId = bufferedReader.readLine();

        String query = "PREFIX event: <http://www.ps7-wia2.com/events/> " +
                "PREFIX el: <http://www.ps7-wia2.com/events#> " +
                "PREFIX u: <http://www.ps7-wia2.com/users/> " +
                "INSERT DATA { event:" + eventId + " el:users u:" + userId + " }";

        RDFConnection conn = RDFConnectionFactory.connect("http://localhost:3030/data_polyville");
        Txn.executeWrite(conn, () -> conn.update(query));
        conn.close();

        res.setStatus(201);
        return "Vous participez à ce evenement";
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping(value = "/events/{eventId}/user/{userId}", produces= MediaType.ALL_VALUE)
    public boolean checkParticipant(HttpServletResponse res, @PathVariable("eventId") String eventId,
                                    @PathVariable("userId") String userId) {

        String query = "PREFIX event: <http://www.ps7-wia2.com/events/>\n" +
                "PREFIX el: <http://www.ps7-wia2.com/events#>\n" +
                "PREFIX root: <http://www.ps7-wia2.com/events#events>\n" +
                "PREFIX u: <http://www.ps7-wia2.com/users/>\n" +
                " SELECT ?user \n" +
                "WHERE {\n" +
                "  \tevent:" + eventId + " el:users ?user\n" +
                "  \tFILTER ( ?user = u:" + userId + " )}\n" +
                "\n";

        RDFConnection conn = RDFConnectionFactory.connect("http://localhost:3030/data_polyville");
        QueryExecution qExec = conn.query(query) ;
        ResultSet results = qExec.execSelect();

        if (results.hasNext()){
            res.setStatus(200);
            qExec.close();
            conn.close();
            return true;
        }

        res.setStatus(200);
        qExec.close();
        conn.close();
        return false;
    }
}
