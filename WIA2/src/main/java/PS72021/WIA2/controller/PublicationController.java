package PS72021.WIA2.controller;

import PS72021.WIA2.model.Group;
import PS72021.WIA2.model.Publication;
import PS72021.WIA2.model.User;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class PublicationController {

    private static final String DATABASE = "http://localhost:3030/data_polyville";

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/publications")
    public List<Publication> getPublications() throws Exception {
        List<Publication> publications = new ArrayList<>();
        String query = "SELECT DISTINCT * WHERE {\n" +
                    "?s <http://www.ps7-wia2.com/publications#publications> ?o. " +
                    "?o <http://www.ps7-wia2.com/publications#title> ?title." +
                    "?o <http://www.ps7-wia2.com/publications#author> ?author." +
                    "?o <http://www.ps7-wia2.com/publications#interests> ?interests." +
                    "?o <http://www.ps7-wia2.com/publications#like> ?like." +
                    "?o <http://www.ps7-wia2.com/publications#comment> ?comment." +
                    "?o <http://www.ps7-wia2.com/publications#description> ?description." +
                    "}";
        RDFConnection conn = RDFConnectionFactory.connect(DATABASE);
        QueryExecution qExec = conn.query(query) ;
        ResultSet results = qExec.execSelect() ;

        QuerySolution sol = results.next();

        String[] sujet = sol.get("o").toString().split("/", -1);

        Publication publication = new Publication(Integer.parseInt(sujet[sujet.length - 1]), sol.get("?title").toString(), sol.get("?description").toString(), sol.get("?like").asLiteral().getInt());
            Set<String> interests = new HashSet<>();
            Set<String> types = new HashSet<>();
        String[] authorSplit = sol.get("?author").toString().split("/",-1);
        String authorType = authorSplit[authorSplit.length-2];
        while (results.hasNext()) {
            sujet = sol.get("o").toString().split("/", -1);
            if (Integer.parseInt(sujet[sujet.length - 1]) != publication.getId()) {
                publication.setInterests(interests.toArray());
                publication.setComments(types.toArray());
                if(authorType.equals("users")){
                    User user = UserController.user(authorSplit[authorSplit.length-1]);
                    publication.setAuthor(user);
                }else{
                    Group group = GroupController.group(authorSplit[authorSplit.length-1]);
                    publication.setAuthorGroup(group);
                }
                publications.add(publication);
                publication = new Publication(Integer.parseInt(sujet[sujet.length - 1]), sol.get("?title").toString(), sol.get("?description").toString(), sol.get("?like").asLiteral().getInt());
                interests = new HashSet<>();
                types = new HashSet<>();
            }
            interests.add(sol.get("interests").toString());
            types.add(sol.get("comment").toString());
            authorSplit = sol.get("?author").toString().split("/",-1);
            authorType = authorSplit[authorSplit.length-2];
            sol = results.next();
        }
        publication.setInterests(interests.toArray());
        publication.setComments(types.toArray());
        if(authorType.equals("users")){
            User user = UserController.user(authorSplit[authorSplit.length-1]);
            publication.setAuthor(user);
        }else{
            Group group = GroupController.group(authorSplit[authorSplit.length-1]);
            publication.setAuthorGroup(group);
        }
        publications.add(publication);
        publication = new Publication(Integer.parseInt(sujet[sujet.length - 1]), sol.get("?title").toString(), sol.get("?description").toString(), sol.get("?like").asLiteral().getInt());
        interests = new HashSet<>();
        types = new HashSet<>();
        interests.add(sol.get("interests").toString());
        types.add(sol.get("comment").toString());
        authorSplit = sol.get("?author").toString().split("/",-1);
        authorType = authorSplit[authorSplit.length-2];
        publication.setInterests(interests.toArray());
        publication.setComments(types.toArray());
        if(authorType.equals("users")){
            User user = UserController.user(authorSplit[authorSplit.length-1]);
            publication.setAuthor(user);
        }else{
            Group group = GroupController.group(authorSplit[authorSplit.length-1]);
            publication.setAuthorGroup(group);
        }
        publications.add(publication);
        qExec.close();
        conn.close();
        return publications;
    }
}
