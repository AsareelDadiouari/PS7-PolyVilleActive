package PS72021.WIA2.controller;

import PS72021.WIA2.model.Group;
import PS72021.WIA2.model.User;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.system.Txn;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class GroupController {

    private static final String DATABASE = "http://localhost:3030/data_polyville";

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/group")
    public static Group group(@RequestParam String groupeId){
        Group group;
        String query = "SELECT DISTINCT * WHERE {\n" +
                "<http://www.ps7-wia2.com/groups/"+groupeId+"> <http://www.ps7-wia2.com/groups#name> ?name." +
                "<http://www.ps7-wia2.com/groups/"+groupeId+"> <http://www.ps7-wia2.com/groups#admin> ?admin." +
                "<http://www.ps7-wia2.com/groups/"+groupeId+"> <http://www.ps7-wia2.com/groups#interests> ?interests." +
                "<http://www.ps7-wia2.com/groups/"+groupeId+"> <http://www.ps7-wia2.com/groups#types> ?types." +
                "<http://www.ps7-wia2.com/groups/"+groupeId+"> <http://www.ps7-wia2.com/groups#description> ?description." +
                "OPTIONAL { ?o <http://www.ps7-wia2.com/groups#members> ?members }" +
                "}";
        RDFConnection conn = RDFConnectionFactory.connect(DATABASE);
        QueryExecution qExec = conn.query(query) ;
        ResultSet results = qExec.execSelect() ;

        QuerySolution sol = results.next();
        group = new Group (Integer.parseInt(groupeId), sol.get("?name").toString(), new User(1, sol.get("?admin").toString()), sol.get("?description").toString());
        Set<String> interests = new HashSet<>();
        Set<String> types = new HashSet<>();
        Set<String> members = new HashSet<>();
        interests.add(sol.get("interests").toString());
        types.add(sol.get("types").toString());
        RDFNode rdfNode = sol.get("members");
        if (rdfNode != null)
            members.add(rdfNode.toString());
        for (;results.hasNext();) {
            sol = results.next();
            interests.add(sol.get("interests").toString());
            types.add(sol.get("types").toString());
            rdfNode = sol.get("members");
            if (rdfNode != null)
                members.add(rdfNode.toString());
        }
        group.setInterests(interests.toArray());
        group.setTypes(types.toArray());
        group.setMembers(members.toArray());
        qExec.close();
        conn.close();
        return group;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/groups")
    public List<Group> getGroups(@RequestParam String userId) {
        List<Group> groups = new ArrayList<>();
        String prefixes =
                "PREFIX u: <http://www.ps7-wia2.com/users/> " +
                "PREFIX g: <http://www.ps7-wia2.com/groups/> " +
                "PREFIX gm: <http://www.ps7-wia2.com/groups#> ";

        String query = prefixes + "SELECT DISTINCT * WHERE {\n" +
                    "?s gm:groups ?o." +
                    "?o gm:interests ?interests." +
                    "?o gm:name ?name." +
                    "?o gm:admin ?admin." +
                    "?o gm:types ?types." +
                    "?o gm:description ?description." +
                    "?o gm:members ?members." +
                    "FILTER NOT EXISTS {" +
                        "OPTIONAL { ?o gm:members ?othersMembers. }" +
                        "FILTER( u:" + userId + " IN( ?othersMembers) )" +
                    "}" +
                "} ORDER BY ?s";

        return executeGetGroupsQuery(groups, query);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/mygroups")
    public List<Group> getMyGroups(@RequestParam String userId) {
        List<Group> groups = new ArrayList<>();

        String prefixes =
                "PREFIX u: <http://www.ps7-wia2.com/users/> " +
                        "PREFIX g: <http://www.ps7-wia2.com/groups/> " +
                        "PREFIX gm: <http://www.ps7-wia2.com/groups#> ";

        String query = prefixes + " SELECT DISTINCT * WHERE {\n" +
                "?s gm:groups ?o." +
                "?o gm:interests ?interests." +
                "?o gm:name ?name." +
                "?o gm:admin ?admin." +
                "?o gm:types ?types." +
                "?o gm:description ?description." +
                "?o gm:members ?members." +
                "FILTER EXISTS {" +
                "OPTIONAL { ?o gm:members ?othersMembers. }" +
                "FILTER( u:" + userId + " IN( ?othersMembers) )" +
                "}" +
                "} ORDER BY ?s";

        return executeGetGroupsQuery(groups, query);
    }

    private List<Group> executeGetGroupsQuery(List<Group> groups, String query) {
        RDFConnection conn = RDFConnectionFactory.connect(DATABASE);
        QueryExecution qExec = conn.query(query);
        ResultSet results = qExec.execSelect();

        if (results.hasNext()) {
            QuerySolution sol = results.next();
            String[] sujet = sol.get("o").toString().split("/", -1);

            Group group = new Group(Integer.parseInt(sujet[sujet.length - 1]), sol.get("?name").toString(), new User(1, sol.get("?admin").toString()), sol.get("?description").toString());
            Set<String> interests = new HashSet<>();
            Set<String> types = new HashSet<>();
            Set<String> members = new HashSet<>();
            RDFNode rdfNode;

            for (; results.hasNext(); ) {
                sujet = sol.get("o").toString().split("/", -1);
                if (Integer.parseInt(sujet[sujet.length - 1]) != group.getId()) {
                    group.setInterests(interests.toArray());
                    group.setTypes(types.toArray());
                    group.setMembers(members.toArray());
                    groups.add(group);
                    group = new Group(Integer.parseInt(sujet[sujet.length - 1]), sol.get("?name").toString(), new User(1, sol.get("?admin").toString()), sol.get("?description").toString());
                    interests.clear();
                    types.clear();
                    members.clear();
                }
                interests.add(sol.get("interests").toString());
                types.add(sol.get("types").toString());
                rdfNode = sol.get("members");
                if (rdfNode != null)
                    members.add(rdfNode.toString());
                sol = results.next();
            }
            group.setInterests(interests.toArray());
            group.setTypes(types.toArray());
            group.setMembers(members.toArray());
            groups.add(group);
        }

        qExec.close();
        conn.close();
        return groups;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value = "/addmember", method = RequestMethod.POST)
    public void addMember(@RequestParam String groupId, @RequestParam String userId) {
        String prefixes =
                "PREFIX u: <http://www.ps7-wia2.com/users/> " +
                "PREFIX um: <http://www.ps7-wia2.com/users#> " +
                "PREFIX g: <http://www.ps7-wia2.com/groups/> " +
                "PREFIX gm: <http://www.ps7-wia2.com/groups#> ";

        String query = prefixes +
                    "INSERT DATA { g:" + groupId + " gm:members u:" + userId + " }; " +
                    "INSERT DATA { u:" + userId + " um:groups g:" + groupId + " }";

        RDFConnection conn = RDFConnectionFactory.connect(DATABASE + "/update");
        Txn.executeWrite(conn, () -> conn.update(query));
        conn.close();
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value = "/removemember", method = RequestMethod.POST)
    public void removeMember(@RequestParam String groupId, @RequestParam String userId) {
        String prefixes =
                "PREFIX u: <http://www.ps7-wia2.com/users/> " +
                "PREFIX um: <http://www.ps7-wia2.com/users#> " +
                "PREFIX g: <http://www.ps7-wia2.com/groups/> " +
                "PREFIX gm: <http://www.ps7-wia2.com/groups#> ";

        String query = prefixes + "" +
                "DELETE DATA { g:" + groupId + " gm:members u:" + userId + " };" +
                "DELETE DATA { u:" + userId + " um:groups g:" + groupId + " }";

        RDFConnection conn = RDFConnectionFactory.connect(DATABASE + "/update");
        Txn.executeWrite(conn, () -> conn.update(query));
        conn.close();
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value = "/recommendedGroups", method = RequestMethod.GET)
    public List<Group> recommendedGroup(@RequestParam String userId) {
        String prefixes =
                "PREFIX u: <http://www.ps7-wia2.com/users/> " +
                "PREFIX um: <http://www.ps7-wia2.com/users#> " +
                "PREFIX g: <http://www.ps7-wia2.com/groups/> " +
                "PREFIX gm: <http://www.ps7-wia2.com/groups#> ";

        String query = prefixes + " SELECT DISTINCT * WHERE {\n" +
                "?s gm:groups ?o." +
                "?o gm:interests ?interests." +
                "?o gm:name ?name." +
                "?o gm:admin ?admin." +
                "?o gm:types ?types." +
                "?o gm:description ?description." +
                "?o gm:members ?members." +
                "u:" + userId + " um:interests ?userInterests." +
                "FILTER ( ?userInterests IN (?interests) )" +
                "FILTER NOT EXISTS {" +
                    "OPTIONAL { ?o gm:members ?othersMembers. }" +
                    "FILTER( u:" + userId + " IN( ?othersMembers) )" +
                "}" +
                "} ORDER BY ?s";

        RDFConnection conn = RDFConnectionFactory.connect(DATABASE);
        QueryExecution qExec = conn.query(query) ;
        ResultSet results = qExec.execSelect() ;

        List<Group> groups = new ArrayList<>();

        if (results.hasNext()) {

            QuerySolution sol = results.next();
            String[] sujet = sol.get("o").toString().split("/", -1);

            Group group = new Group(Integer.parseInt(sujet[sujet.length - 1]), sol.get("?name").toString(), new User(1, sol.get("?admin").toString()), sol.get("?description").toString());
            Set<String> interests = new HashSet<>();
            Set<String> types = new HashSet<>();
            Set<String> members = new HashSet<>();
            RDFNode rdfNode;

            for (; results.hasNext(); ) {
                sujet = sol.get("o").toString().split("/", -1);
                if (Integer.parseInt(sujet[sujet.length - 1]) != group.getId()) {
                    group.setInterests(interests.toArray());
                    group.setTypes(types.toArray());
                    group.setMembers(members.toArray());
                    groups.add(group);
                    group = new Group(Integer.parseInt(sujet[sujet.length - 1]), sol.get("?name").toString(), new User(1, sol.get("?admin").toString()), sol.get("?description").toString());
                    interests.clear();
                    types.clear();
                    members.clear();
                }
                interests.add(sol.get("interests").toString());
                types.add(sol.get("types").toString());
                rdfNode = sol.get("members");
                if (rdfNode != null)
                    members.add(rdfNode.toString());
                sol = results.next();
            }
            group.setInterests(interests.toArray());
            group.setTypes(types.toArray());
            group.setMembers(members.toArray());
            groups.add(group);
        }
        qExec.close();
        conn.close();
        return groups;
    }
}
