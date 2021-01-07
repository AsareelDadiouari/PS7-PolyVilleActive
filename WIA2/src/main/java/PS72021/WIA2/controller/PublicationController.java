package PS72021.WIA2.controller;

import PS72021.WIA2.model.Group;
import PS72021.WIA2.model.Publication;
import PS72021.WIA2.model.User;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.sparql.util.Utils;
import org.apache.jena.system.Txn;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
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
                "?o <http://www.ps7-wia2.com/publications#comment> ?comment." +
                "?o <http://www.ps7-wia2.com/publications#description> ?description." +
                "OPTIONAL { ?o <http://www.ps7-wia2.com/publications#likes> ?likes. }" +
                "}";
        RDFConnection conn = RDFConnectionFactory.connect(DATABASE);
        QueryExecution qExec = conn.query(query) ;
        ResultSet results = qExec.execSelect() ;

        QuerySolution sol = results.next();

        String[] sujet = sol.get("o").toString().split("/", -1);

        Publication publication = new Publication(Integer.parseInt(sujet[sujet.length - 1]), sol.get("?title").toString(), sol.get("?description").toString());
        Set<String> interests = new HashSet<>();
        Set<String> types = new HashSet<>();
        Set<String> likes = new HashSet<>();
        String[] authorSplit = sol.get("?author").toString().split("/",-1);
        String authorType = authorSplit[authorSplit.length-2];
        while (results.hasNext()) {
            sujet = sol.get("o").toString().split("/", -1);
            if (Integer.parseInt(sujet[sujet.length - 1]) != publication.getId()) {
                publication.setInterests(interests.toArray());
                publication.setComments(types.toArray());
                publication.setLikes(likes.toArray());
                if(authorType.equals("users")){
                    User user = UserController.user(authorSplit[authorSplit.length-1]);
                    publication.setAuthor(user);
                }else{
                    Group group = GroupController.group(authorSplit[authorSplit.length-1]);
                    publication.setAuthorGroup(group);
                }
                publications.add(publication);
                publication = new Publication(Integer.parseInt(sujet[sujet.length - 1]), sol.get("?title").toString(), sol.get("?description").toString());
                interests = new HashSet<>();
                types = new HashSet<>();
                likes = new HashSet<>();
            }
            interests.add(sol.get("interests").toString());
            types.add(sol.get("comment").toString());
            if (sol.get("likes") != null)
                likes.add(sol.get("likes").toString());
            authorSplit = sol.get("?author").toString().split("/",-1);
            authorType = authorSplit[authorSplit.length-2];
            sol = results.next();
        }
        publication.setInterests(interests.toArray());
        publication.setComments(types.toArray());
        publication.setLikes(likes.toArray());
        if(authorType.equals("users")){
            User user = UserController.user(authorSplit[authorSplit.length-1]);
            publication.setAuthor(user);
        }else{
            Group group = GroupController.group(authorSplit[authorSplit.length-1]);
            publication.setAuthorGroup(group);
        }
        publications.add(publication);
        sujet = sol.get("o").toString().split("/", -1);
        publication = new Publication(Integer.parseInt(sujet[sujet.length - 1]), sol.get("?title").toString(), sol.get("?description").toString());
        interests = new HashSet<>();
        types = new HashSet<>();
        likes = new HashSet<>();
        interests.add(sol.get("interests").toString());
        types.add(sol.get("comment").toString());
        if (sol.get("likes") != null)
            likes.add(sol.get("likes").toString());
        authorSplit = sol.get("?author").toString().split("/",-1);
        authorType = authorSplit[authorSplit.length-2];
        publication.setInterests(interests.toArray());
        publication.setComments(types.toArray());
        publication.setLikes(likes.toArray());
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

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(value = "/publications/{publicationId}/like/{userId}", produces = MediaType.ALL_VALUE)
    public boolean addLike(@PathVariable("publicationId") String id, @PathVariable("userId") String userId){
        String query = "PREFIX publ: <http://www.ps7-wia2.com/publications/>" +
                "PREFIX pub: <http://www.ps7-wia2.com/publications#>" +
                "PREFIX u: <http://www.ps7-wia2.com/users/> " +
                "PREFIX um: <http://www.ps7-wia2.com/users#> " +
                "INSERT DATA { publ:" + id + " pub:likes u:" + userId + " };" +
                "INSERT DATA { u:" + userId + " um:publications publ:" + id + " }";

        RDFConnection conn2 = RDFConnectionFactory.connect(DATABASE);
        Txn.executeWrite(conn2, () -> conn2.update(query));
        conn2.close();

        return true;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(value = "/publications/{publicationId}/unlike/{userId}", produces = MediaType.ALL_VALUE)
    public boolean unLike(@PathVariable("publicationId") String id, @PathVariable("userId") String userId){
        String query = "PREFIX publ: <http://www.ps7-wia2.com/publications/>" +
                "PREFIX pub: <http://www.ps7-wia2.com/publications#>" +
                "PREFIX u: <http://www.ps7-wia2.com/users/> " +
                "PREFIX um: <http://www.ps7-wia2.com/users#> " +
                "DELETE DATA { publ:" + id + " pub:likes u:" + userId + " };" +
                "DELETE DATA { u:" + userId + " um:publications publ:" + id + " }";

        RDFConnection conn2 = RDFConnectionFactory.connect(DATABASE);
        Txn.executeWrite(conn2, () -> conn2.update(query));
        conn2.close();

        return true;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(value = "/publications/{publicationId}/comment", produces = MediaType.ALL_VALUE)
    public boolean addComment(HttpServletRequest req, HttpServletResponse res, @PathVariable("publicationId")String id) throws IOException {
        BufferedReader bufferedReader = req.getReader();
        String body = bufferedReader.readLine();

        String query = "PREFIX publ: <http://www.ps7-wia2.com/publications/>\n" +
                "PREFIX pub: <http://www.ps7-wia2.com/publications#>\n" +
                "\n" +
                "INSERT DATA { publ:" + id + " pub:comment \"" + body + "\" }";

        RDFConnection conn = RDFConnectionFactory.connect("http://localhost:3030/data_polyville");
        Txn.executeWrite(conn, () -> conn.update(query));
        conn.close();

        res.setStatus(200);
        return true;
    }

}
