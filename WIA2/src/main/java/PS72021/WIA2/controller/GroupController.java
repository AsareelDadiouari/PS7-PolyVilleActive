package PS72021.WIA2.controller;

import PS72021.WIA2.Application;
import PS72021.WIA2.model.Group;
import PS72021.WIA2.model.User;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class GroupController {

    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping("/group")
    public static Group group(@RequestParam String groupeId) throws Exception {
        String filePath = "database/groupes.jsonld";
        Group group;
        String query = "SELECT DISTINCT * WHERE {\n" +
                "<http://www.ps7-wia2.com/groups/"+groupeId+"> <http://www.ps7-wia2.com/groups#name> ?name." +
                "<http://www.ps7-wia2.com/groups/"+groupeId+"> <http://www.ps7-wia2.com/groups#admin> ?admin." +
                "<http://www.ps7-wia2.com/groups/"+groupeId+"> <http://www.ps7-wia2.com/groups#interests> ?interests." +
                "<http://www.ps7-wia2.com/groups/"+groupeId+"> <http://www.ps7-wia2.com/groups#types> ?types." +
                "<http://www.ps7-wia2.com/groups/"+groupeId+"> <http://www.ps7-wia2.com/groups#description> ?description." +
                "}";
        ResultSet results2 = Application.executeQuery(query, filePath);
        QuerySolution sol = results2.next();
        group = new Group (Integer.parseInt(groupeId), sol.get("?name").toString(), new User(1, sol.get("?admin").toString()), sol.get("?description").toString());
        Set<String> interests = new HashSet<>();
        Set<String> types = new HashSet<>();
        interests.add(sol.get("interests").toString());
        types.add(sol.get("types").toString());
        for (;results2.hasNext();) {
            sol = results2.next();
            interests.add(sol.get("interests").toString());
            types.add(sol.get("types").toString());
        }
        group.setInterests(interests.toArray());
        group.setTypes(types.toArray());
        return group;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/groups")
    public List<Group> getGroups() throws Exception {
        List<Group> groups = new ArrayList<>();
        String query = "SELECT DISTINCT ?s ?p ?o WHERE {\n" +
                "?s <http://www.ps7-wia2.com/groups#groups> ?o " +
                "}";

        String filePath = "database/groupes.jsonld";
        ResultSet results = Application.executeQuery(query, filePath);
        for (int i = 1; results.hasNext(); i++) {
            QuerySolution querySolution = results.next();
            query = "SELECT DISTINCT * WHERE {\n" +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/groups#name> ?name." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/groups#admin> ?admin." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/groups#interests> ?interests." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/groups#types> ?types." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/groups#description> ?description." +
                    "}";
            ResultSet results2 = Application.executeQuery(query, filePath);
            QuerySolution sol = results2.next();
            String[] sujet = querySolution.get("o").toString().split("/", -1);
            Group group = new Group (Integer.parseInt(sujet[sujet.length - 1]), sol.get("?name").toString(), new User(1, sol.get("?admin").toString()), sol.get("?description").toString());
            Set<String> interests = new HashSet<>();
            Set<String> types = new HashSet<>();
            interests.add(sol.get("interests").toString());
            types.add(sol.get("types").toString());
            for (;results2.hasNext();) {
                sol = results2.next();
                interests.add(sol.get("interests").toString());
                types.add(sol.get("types").toString());
            }
            group.setInterests(interests.toArray());
            group.setTypes(types.toArray());
            groups.add(group);
        }
        return groups;
    }
}
