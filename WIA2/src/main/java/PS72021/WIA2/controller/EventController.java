package PS72021.WIA2.controller;

import PS72021.WIA2.Application;
import PS72021.WIA2.model.Event;
import PS72021.WIA2.model.User;
import org.apache.jena.base.Sys;
import org.apache.jena.dboe.transaction.txn.SysTransState;
import org.apache.jena.query.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;

@RestController
public class EventController {
    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/events")
    public ArrayList<Event> getEvents() throws Exception {
        ArrayList<Event> events = new ArrayList<>();
        String query = "SELECT DISTINCT ?s ?p ?o WHERE {\n" +
                "?s <http://www.ps7-wia2.com/events#events> ?o " +
                "}";
        String query2;

        String filePath = "database/evenements.jsonld";
        ResultSet results = Application.executeQuery(query, filePath);
        for (int i = 1; results.hasNext(); i++) {
            QuerySolution querySolution = results.next();
            query = "SELECT * WHERE {\n" +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#name_fr> ?name_fr." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#start> ?start." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#end> ?end." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#address> ?address." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#profile> ?profile." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#categories> ?categories." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#description> ?description." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#image> ?image." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#latitude> ?latitude." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#longitude> ?longitude." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#users> ?users." +
                    "}";
            ResultSet results2 = Application.executeQuery(query, filePath);
            QuerySolution sol = results2.next();

            LocalDate start = LocalDate.parse(sol.get("?start").toString());
            LocalDate end = LocalDate.parse(sol.get("?end").toString());

            ArrayList<String> profiles = new ArrayList<>();
            profiles.add(sol.get("profile").toString());
            for (;results2.hasNext();) {
                sol = results2.next();
                profiles.add(sol.get("profile").toString());
            }

            ArrayList<String> categories = new ArrayList<>();
            categories.add(sol.get("categories").toString());
            for (;results2.hasNext();) {
                sol = results2.next();
                categories.add(sol.get("categories").toString());
            }

            query2 = "SELECT ?users WHERE {" +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#users> ?users." +
                    "}";
            ResultSet resultSet3 = Application.executeQuery(query2, filePath);
            QuerySolution soln = resultSet3.nextSolution();

            ArrayList<String> users = new ArrayList<>();
            users.add(soln.get("users").toString());
            for (;resultSet3.hasNext();){
                String temp = resultSet3.next().get("users").toString();
                users.add(temp);
            }

            String[] test = querySolution.get("o").toString().split("/", -1);
            Event event = new Event (Integer.parseInt(test[test.length-1]), sol.get("?name_fr").toString(), start, end, sol.get("address").toString(),profiles,categories,
                    sol.get("description").toString(),sol.get("image").toString(),sol.get("latitude").asLiteral().getDouble(),
                    sol.get("longitude").asLiteral().getDouble(), users);
            events.add(event);
        }
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

        String filePath = "database/evenements.jsonld";
        ResultSet results = Application.executeQuery(query, filePath);
        for (int i = 1; results.hasNext(); i++) {
            QuerySolution querySolution = results.next();
            query = "SELECT * WHERE {\n" +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#name_fr> ?name_fr." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#start> ?start." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#end> ?end." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#address> ?address." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#profile> ?profile." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#categories> ?categories." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#description> ?description." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/events#image> ?image." +
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
                Event event = new Event(Integer.parseInt(test[test.length - 1]), sol.get("?name_fr").toString(), start, end, sol.get("address").toString(),
                        profiles, categories, sol.get("description").toString(), sol.get("image").toString(), sol.get("latitude").asLiteral().getDouble(),
                        sol.get("longitude").asLiteral().getDouble(), usersIndb);
                events.add(event);
            }
        }
        return events;
    }
}
