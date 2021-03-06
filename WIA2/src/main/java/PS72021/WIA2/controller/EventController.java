package PS72021.WIA2.controller;

import PS72021.WIA2.model.Categorie;
import PS72021.WIA2.model.Event;
import PS72021.WIA2.model.User;
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
                "OPTIONAL { ?o <http://www.ps7-wia2.com/events#likes> ?likes. }" +
                "}";

        RDFConnection conn = RDFConnectionFactory.connect(DATABASE);
        QueryExecution qExec = conn.query(query) ;
        ResultSet results = qExec.execSelect() ;

        QuerySolution sol = results.next();

        Event event = null;
        ArrayList<String> profiles = new ArrayList<>();
        Set<String> users = new HashSet<>();
        Set<String> likes = new HashSet<>();
        String address = null;
        while (results.hasNext()) {
            String[] sujet = sol.get("o").toString().split("/", -1);

            if(event != null ) {
                if (Integer.parseInt(sujet[sujet.length - 1]) != event.getId()) {

                    events.add(event);
                    profiles = new ArrayList<>();
                    users = new HashSet<>();
                    likes = new HashSet<>();
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
                    if (sol.get("likes") != null)
                        likes.add(sol.get("likes").toString());
                    event = new Event (Integer.parseInt(sujet[sujet.length - 1]), sol.get("?name_fr").toString(), start, end, address,profiles,categories,
                            sol.get("description").toString(),sol.get("images").toString(),sol.get("latitude").asLiteral().getDouble(),
                            sol.get("longitude").asLiteral().getDouble(), users, likes);

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
                    if (sol.get("likes") != null)
                        likes.add(sol.get("likes").toString());
                    event = new Event (Integer.parseInt(sujet[sujet.length - 1]), sol.get("?name_fr").toString(), start, end, address,profiles,categories,
                            sol.get("description").toString(),sol.get("images").toString(),sol.get("latitude").asLiteral().getDouble(),
                            sol.get("longitude").asLiteral().getDouble(), users, likes);
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
                if (sol.get("likes") != null)
                    likes.add(sol.get("likes").toString());
                event = new Event (Integer.parseInt(sujet[sujet.length - 1]), sol.get("?name_fr").toString(), start, end, address,profiles,categories,
                        sol.get("description").toString(),sol.get("images").toString(),sol.get("latitude").asLiteral().getDouble(),
                        sol.get("longitude").asLiteral().getDouble(), users, likes);
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

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/eventsRecommandations")
    public ArrayList<Event> getEventsPersonnalises(@RequestParam String userId) {
        User user = UserController.user(userId);
        String[] interestsUser = user.getInterests();
        ArrayList<Event> events = new ArrayList<>();

        ArrayList<String> interests = new ArrayList<>();
        for (int i = 0 ; i < interestsUser.length ; i++) {
            String queryCats = "SELECT * WHERE {\n" +
                    "?sub <http://www.ps7-wia2.com/categories#categories> ?obj." +
                    "?obj <http://www.ps7-wia2.com/categories#name> ?\"" + interestsUser[i] + "\"." +
                    "}";

            RDFConnection conn3 = RDFConnectionFactory.connect(DATABASE);
            QueryExecution qExec3 = conn3.query(queryCats);
            ResultSet results3 = qExec3.execSelect();


            while (results3.hasNext()) {
                String[] genreSplit = results3.next().get("?obj").toString().split("/", -1);
                interests.add("c:"+genreSplit[genreSplit.length - 1]);
            }
            qExec3.close();
            conn3.close();
        }
        eventsWithInterests(interests,events);
        if(events.size() == 0){
            ArrayList<String> genres = new ArrayList<>();
            for(int i = 0 ; i < interests.size() ; i++) {
                String query = "SELECT * WHERE {\n" +
                        "?s <http://www.ps7-wia2.com/genres#genres> ?o. " +
                        "?o <http://www.ps7-wia2.com/genres#categories> ?\"" + interests.get(i) + "\"." +
                        "?o <http://www.ps7-wia2.com/genres#name> ?name." +
                        "}";
                RDFConnection conn = RDFConnectionFactory.connect(DATABASE);
                QueryExecution qExec = conn.query(query);
                ResultSet results = qExec.execSelect();
                QuerySolution sol = results.next();
                if(!genres.contains(sol.get("name").toString())) {
                    genres.add(sol.get("name").toString());
                }
                qExec.close();
                conn.close();
            }
            for(int i = 0 ; i < genres.size() ; i++) {
                String query = "SELECT * WHERE {\n" +
                        "?s <http://www.ps7-wia2.com/genres#genres> ?o. " +
                        "?o <http://www.ps7-wia2.com/genres#name> ?\"" + genres.get(i) + "\"." +
                        "?o <http://www.ps7-wia2.com/genres#categories> ?categories." +
                        "}";
                RDFConnection conn = RDFConnectionFactory.connect(DATABASE);
                QueryExecution qExec = conn.query(query);
                ResultSet results = qExec.execSelect();
                while (results.hasNext()) {
                    QuerySolution sol = results.next();
                    if (!interests.contains(sol.get("categories").toString())) {
                        interests.add(sol.get("categories").toString());
                    }
                }

                qExec.close();
                conn.close();
            }
            eventsWithInterests(interests,events);
        }
        if(events.size() == 0) {
            events = getEvents();
        }
        return events;

    }

    public void eventsWithInterests(ArrayList<String> interests, ArrayList<Event> events){
        for (int i = 0 ; i < interests.size()  ; i++) {
            String query = "SELECT * WHERE {\n" +
                    "?s <http://www.ps7-wia2.com/events#events> ?o. " +
                    "?o <http://www.ps7-wia2.com/events#name_fr> ?name_fr." +
                    "?o <http://www.ps7-wia2.com/events#start> ?start." +
                    "?o <http://www.ps7-wia2.com/events#end> ?end." +
                    "?o <http://www.ps7-wia2.com/events#address> ?address." +
                    "?o <http://www.ps7-wia2.com/events#profile> ?profile." +
                    "?o <http://www.ps7-wia2.com/events#categories> ?\"" + interests.get(i) +"\"." +
                    "?o <http://www.ps7-wia2.com/events#categories> ?categories."+
                    "?o <http://www.ps7-wia2.com/events#description> ?description." +
                    "?o <http://www.ps7-wia2.com/events#images> ?images." +
                    "?o <http://www.ps7-wia2.com/events#latitude> ?latitude." +
                    "?o <http://www.ps7-wia2.com/events#longitude> ?longitude." +
                    "?o <http://www.ps7-wia2.com/events#users> ?users." +
                    "OPTIONAL { ?o <http://www.ps7-wia2.com/events#likes> ?likes. }" +
                    "}";

            RDFConnection conn = RDFConnectionFactory.connect(DATABASE);
            QueryExecution qExec = conn.query(query);
            ResultSet results = qExec.execSelect();

            if(results.hasNext()){
                QuerySolution sol = results.next();

                Event event = null;
                ArrayList<String> profiles = new ArrayList<>();
                ArrayList<String> categories = new ArrayList<>();
                Set<String> users = new HashSet<>();
                Set<String> likes = new HashSet<>();
                String address = null;
                while (results.hasNext()) {
                    String[] sujet = sol.get("o").toString().split("/", -1);
                    if (event != null) {
                        if (Integer.parseInt(sujet[sujet.length - 1]) != event.getId()) {
                            if(!events.contains(event)) {
                                events.add(event);
                            }
                            profiles = new ArrayList<>();
                            categories = new ArrayList<>();
                            users = new HashSet<>();
                            likes = new HashSet<>();
                            LocalDate start = LocalDate.parse(sol.get("?start").toString());
                            LocalDate end = LocalDate.parse(sol.get("?end").toString());
                            profiles.add(sol.get("profile").toString());
                            categories.add(sol.get("categories").toString());
                            address = sol.get("address").toString();
                            if (sol.get("likes") != null)
                                likes.add(sol.get("likes").toString());
                            event = new Event(Integer.parseInt(sujet[sujet.length - 1]), sol.get("?name_fr").toString(), start, end, address, profiles, categories,
                                    sol.get("description").toString(), sol.get("images").toString(), sol.get("latitude").asLiteral().getDouble(),
                                    sol.get("longitude").asLiteral().getDouble(), users, likes);

                            String queryUsers = "PREFIX event: <http://www.ps7-wia2.com/events/>\n" +
                                    "PREFIX el: <http://www.ps7-wia2.com/events#>\n" +
                                    "SELECT DISTINCT * WHERE {\n" +
                                    "event:" + sujet[sujet.length - 1] + " el:users + ?users }";


                            RDFConnection conn1 = RDFConnectionFactory.connect(DATABASE);
                            QueryExecution qExec1 = conn1.query(queryUsers);
                            ResultSet results1 = qExec1.execSelect();

                            while (results1.hasNext()) {
                                users.add(results1.next().get("users").toString());
                            }
                            qExec1.close();
                            conn1.close();

                        } else {
                            LocalDate start = LocalDate.parse(sol.get("?start").toString());
                            LocalDate end = LocalDate.parse(sol.get("?end").toString());
                            if (!profiles.contains(sol.get("profile").toString())) {
                                profiles.add(sol.get("profile").toString());
                            }
                            if (!categories.contains(sol.get("categories").toString())) {
                                categories.add(sol.get("categories").toString());
                            }
                            if (!address.contains(sol.get("address").toString())) {
                                address = address + " " + sol.get("address").toString();
                            }
                            if (sol.get("likes") != null)
                                likes.add(sol.get("likes").toString());
                            event = new Event(Integer.parseInt(sujet[sujet.length - 1]), sol.get("?name_fr").toString(), start, end, address, profiles, categories,
                                    sol.get("description").toString(), sol.get("images").toString(), sol.get("latitude").asLiteral().getDouble(),
                                    sol.get("longitude").asLiteral().getDouble(), users, likes);
                        }
                    } else {
                        LocalDate start = LocalDate.parse(sol.get("?start").toString());
                        LocalDate end = LocalDate.parse(sol.get("?end").toString());
                        profiles.add(sol.get("profile").toString());
                        categories.add(sol.get("categories").toString());
                        address = sol.get("address").toString();
                        if (sol.get("likes") != null)
                            likes.add(sol.get("likes").toString());
                        event = new Event(Integer.parseInt(sujet[sujet.length - 1]), sol.get("?name_fr").toString(), start, end, address, profiles, categories,
                                sol.get("description").toString(), sol.get("images").toString(), sol.get("latitude").asLiteral().getDouble(),
                                sol.get("longitude").asLiteral().getDouble(), users, likes);
                    }

                    String queryUsers = "PREFIX event: <http://www.ps7-wia2.com/events/>\n" +
                            "PREFIX el: <http://www.ps7-wia2.com/events#>\n" +
                            "SELECT DISTINCT * WHERE {\n" +
                            "event:" + sujet[sujet.length - 1] + " el:users + ?users }";

                    RDFConnection conn1 = RDFConnectionFactory.connect(DATABASE);
                    QueryExecution qExec1 = conn1.query(queryUsers);
                    ResultSet results1 = qExec1.execSelect();

                    while (results1.hasNext()) {
                        users.add(results1.next().get("users").toString());
                    }

                    qExec1.close();
                    conn1.close();
                    sol = results.next();
                }
                qExec.close();
                conn.close();
            }}
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
        return "Vous participez ?? cet evenement";
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping(value = "/events/{eventId}/user/{userId}", produces= MediaType.APPLICATION_JSON_VALUE)
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

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(value = "/events/{eventId}/like/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean addLike(@PathVariable("eventId") String id, @PathVariable("userId") String userId){
        String query = "PREFIX even: <http://www.ps7-wia2.com/events/>" +
                "PREFIX eve: <http://www.ps7-wia2.com/events#>" +
                "PREFIX u: <http://www.ps7-wia2.com/users/> " +
                "PREFIX um: <http://www.ps7-wia2.com/users#> " +
                "INSERT DATA { even:" + id + " eve:likes u:" + userId + " };" +
                "INSERT DATA { u:" + userId + " um:events even:" + id + " }";

        RDFConnection conn2 = RDFConnectionFactory.connect(DATABASE);
        Txn.executeWrite(conn2, () -> conn2.update(query));
        conn2.close();

        return true;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(value = "/events/{eventId}/unlike/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean unLike(@PathVariable("eventId") String id, @PathVariable("userId") String userId){
        String query = "PREFIX even: <http://www.ps7-wia2.com/events/>" +
                "PREFIX eve: <http://www.ps7-wia2.com/events#>" +
                "PREFIX u: <http://www.ps7-wia2.com/users/> " +
                "PREFIX um: <http://www.ps7-wia2.com/users#> " +
                "DELETE DATA { even:" + id + " eve:likes u:" + userId + " };" +
                "DELETE DATA { u:" + userId + " um:events even:" + id + " }";

        RDFConnection conn2 = RDFConnectionFactory.connect(DATABASE);
        Txn.executeWrite(conn2, () -> conn2.update(query));
        conn2.close();

        return true;
    }

}