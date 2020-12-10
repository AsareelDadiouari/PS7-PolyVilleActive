package PS72021.WIA2.controller;

import PS72021.WIA2.Application;
import PS72021.WIA2.model.Event;
import org.apache.jena.query.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;

@RestController
public class EventController {
    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping("/events")
    public ArrayList<Event> getEvents() throws Exception {
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
            String[] test = querySolution.get("o").toString().split("/", -1);
            Event event = new Event (Integer.parseInt(test[test.length-1]), sol.get("?name_fr").toString(), start, end, sol.get("address").toString(),profiles,categories,sol.get("description").toString(),sol.get("image").toString(),sol.get("latitude").asLiteral().getDouble(), sol.get("longitude").asLiteral().getDouble());
            events.add(event);
        }
        return events;
    }
}
