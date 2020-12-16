package PS72021.WIA2.controller;

import PS72021.WIA2.Application;
import PS72021.WIA2.model.User;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class UserController {

    private static final String DATABASE = "http://localhost:3030/bdd";

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/user")
    public static User user(@RequestParam String userId) {
        User user;
        String query = "SELECT DISTINCT * WHERE {\n" +
                "<http://www.ps7-wia2.com/users/"+userId+"> <http://www.ps7-wia2.com/users#type> ?type." +
                "<http://www.ps7-wia2.com/users/"+userId+"> <http://www.ps7-wia2.com/users#firstname> ?firstname." +
                "<http://www.ps7-wia2.com/users/"+userId+"> <http://www.ps7-wia2.com/users#lastname> ?lastname." +
                "<http://www.ps7-wia2.com/users/"+userId+"> <http://www.ps7-wia2.com/users#role> ?role." +
                "<http://www.ps7-wia2.com/users/"+userId+"> <http://www.ps7-wia2.com/users#interests> ?interests." +
                    "}";

        RDFConnection conn = RDFConnectionFactory.connect(DATABASE);
        QueryExecution qExec = conn.query(query) ;
        ResultSet results = qExec.execSelect() ;

        QuerySolution sol = results.next();
            user = new User(Integer.parseInt(userId), sol.get("?firstname").toString(), sol.get("?lastname").toString(), sol.get("?type").toString(), sol.get("role").toString());
            Set<String> interests = new HashSet<>();
            interests.add(sol.get("interests").toString());
            for (; results.hasNext(); ) {
                sol = results.next();
                interests.add(sol.get("interests").toString());
            }
            user.setInterests(interests.toArray());

            qExec.close();
            conn.close();
        return user;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/users")
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT DISTINCT * WHERE {\n" +
                "                      ?s <http://www.ps7-wia2.com/users#users> ?o." +
                "                      ?o <http://www.ps7-wia2.com/users#type> ?type." +
                "                      ?o <http://www.ps7-wia2.com/users#firstname> ?firstname." +
                "                      ?o <http://www.ps7-wia2.com/users#lastname> ?lastname." +
                "                      ?o <http://www.ps7-wia2.com/users#role> ?role." +
                "                      ?o <http://www.ps7-wia2.com/users#interests> ?interests." +
                "                  }";

        RDFConnection conn = RDFConnectionFactory.connect(DATABASE);
        QueryExecution qExec = conn.query(query) ;
        ResultSet results = qExec.execSelect() ;

        QuerySolution sol = results.next();
        String[] sujet = sol.get("o").toString().split("/", -1);

        User user = new User (Integer.parseInt(sujet[sujet.length - 1]), sol.get("?firstname").toString(), sol.get("?lastname").toString(), sol.get("?type").toString(), sol.get("role").toString());
        Set<String> interests = new HashSet<>();

        for (;results.hasNext();) {
            sujet = sol.get("o").toString().split("/", -1);
            if (Integer.parseInt(sujet[sujet.length - 1]) != user.getId()) {
                user.setInterests(interests.toArray());
                users.add(user);
                user = new User (Integer.parseInt(sujet[sujet.length - 1]), sol.get("?firstname").toString(), sol.get("?lastname").toString(), sol.get("?type").toString(), sol.get("role").toString());
                interests = new HashSet<>();
            }
            interests.add(sol.get("interests").toString());
            sol = results.next();
        }

        qExec.close();
        conn.close();
        return users;
    }

}
