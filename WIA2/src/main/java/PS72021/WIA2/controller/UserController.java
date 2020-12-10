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

    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping("/user")
    public User user(@RequestParam Map<String,String> requestParams) {
        return new User(1, requestParams.get("firstname"), requestParams.get("lastname"), "Visiteur", "role");
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/users")
    public List<User> getUsers() throws Exception {
        List<User> users = new ArrayList<>();
        String query = "SELECT DISTINCT ?s ?p ?o WHERE {\n" +
                "?s <http://www.ps7-wia2.com/users#users> ?o " +
                "}";

        String filePath = "database/utilisateurs.jsonld";
        ResultSet results = Application.executeQuery(query, filePath);
        for (int i = 1; results.hasNext(); i++) {
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
            User user = new User (i, sol.get("?firstname").toString(), sol.get("?lastname").toString(), sol.get("?type").toString(), sol.get("role").toString());
            Set<String> interets = new HashSet<>();
            interets.add(sol.get("interests").toString());
            for (;results2.hasNext();) {
                sol = results2.next();
                interets.add(sol.get("interests").toString());
            }
            user.setInterets(interets.toArray());
            users.add(user);
        }
        return users;
    }

}
