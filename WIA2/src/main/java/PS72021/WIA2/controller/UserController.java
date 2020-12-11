package PS72021.WIA2.controller;

import PS72021.WIA2.Application;
import PS72021.WIA2.model.User;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class UserController {
    String filePath = "database/utilisateurs.jsonld";

    /*@CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping("/user")
    public User user(@RequestParam Map<String,String> requestParams) {
        return new User(1, requestParams.get("firstname"), requestParams.get("lastname"), "Visiteur", "role");
    }*/

    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping("/user")
    public static User user(@RequestParam String userId) throws Exception {
        String filePath = "database/utilisateurs.jsonld";
        User user;
        String query = "SELECT DISTINCT * WHERE {\n" +
                "<http://www.ps7-wia2.com/users/"+userId+"> <http://www.ps7-wia2.com/users#type> ?type." +
                "<http://www.ps7-wia2.com/users/"+userId+"> <http://www.ps7-wia2.com/users#type> ?type." +
                "<http://www.ps7-wia2.com/users/"+userId+"> <http://www.ps7-wia2.com/users#firstname> ?firstname." +
                "<http://www.ps7-wia2.com/users/"+userId+"> <http://www.ps7-wia2.com/users#lastname> ?lastname." +
                "<http://www.ps7-wia2.com/users/"+userId+"> <http://www.ps7-wia2.com/users#role> ?role." +
                "<http://www.ps7-wia2.com/users/"+userId+"> <http://www.ps7-wia2.com/users#interests> ?interests." +
                    "}";
            ResultSet results2 = Application.executeQuery(query, filePath);
            QuerySolution sol = results2.next();
                user = new User(Integer.parseInt(userId), sol.get("?firstname").toString(), sol.get("?lastname").toString(), sol.get("?type").toString(), sol.get("role").toString());
                Set<String> interests = new HashSet<>();
                interests.add(sol.get("interests").toString());
                for (; results2.hasNext(); ) {
                    sol = results2.next();
                    interests.add(sol.get("interests").toString());
                }
                user.setInterests(interests.toArray());
        return user;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/users")
    public List<User> getUsers() throws Exception {
        List<User> users = new ArrayList<>();
        String query = "SELECT DISTINCT ?s ?p ?o WHERE {\n" +
                "?s <http://www.ps7-wia2.com/users#users> ?o " +
                "}";
        ResultSet results = Application.executeQuery(query, filePath);
        for (; results.hasNext();) {
            QuerySolution querySolution = results.next();
            query = "SELECT DISTINCT * WHERE {\n" +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/users#type> ?type." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/users#firstname> ?firstname." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/users#lastname> ?lastname." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/users#role> ?role." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/users#interests> ?interests." +
                    "}";
            ResultSet results2 = Application.executeQuery(query, filePath);
            QuerySolution sol = results2.next();
            String[] sujet = querySolution.get("o").toString().split("/", -1);
            User user = new User (Integer.parseInt(sujet[sujet.length - 1]), sol.get("?firstname").toString(), sol.get("?lastname").toString(), sol.get("?type").toString(), sol.get("role").toString());
            Set<String> interests = new HashSet<>();
            interests.add(sol.get("interests").toString());
            for (;results2.hasNext();) {
                sol = results2.next();
                interests.add(sol.get("interests").toString());
            }
            user.setInterests(interests.toArray());
            users.add(user);
        }
        return users;
    }

}
