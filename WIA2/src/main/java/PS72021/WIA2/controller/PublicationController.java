package PS72021.WIA2.controller;

import PS72021.WIA2.Application;
import PS72021.WIA2.model.Group;
import PS72021.WIA2.model.Publication;
import PS72021.WIA2.model.User;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class PublicationController {

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/publications")
    public List<Publication> getPublications() throws Exception {
        List<Publication> publications = new ArrayList<>();
        String query = "SELECT DISTINCT ?s ?p ?o WHERE {\n" +
                "?s <http://www.ps7-wia2.com/publications#publications> ?o " +
                "}";

        String filePath = "database/publications.jsonld";
        ResultSet results = Application.executeQuery(query, filePath);
        for (; results.hasNext();) {
            QuerySolution querySolution = results.next();
            query = "SELECT DISTINCT * WHERE {\n" +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/publications#title> ?title." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/publications#author> ?author." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/publications#interests> ?interests." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/publications#like> ?like." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/publications#comment> ?comment." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/publications#description> ?description." +
                    "}";
            ResultSet results2 = Application.executeQuery(query, filePath);
            QuerySolution sol = results2.next();
            String[] sujet = querySolution.get("o").toString().split("/", -1);
            Publication publication = new Publication(Integer.parseInt(sujet[sujet.length - 1]), sol.get("?title").toString(), sol.get("?description").toString(), sol.get("?like").asLiteral().getInt());
            Set<String> interests = new HashSet<>();
            Set<String> types = new HashSet<>();
            interests.add(sol.get("interests").toString());
            types.add(sol.get("comment").toString());
            for (;results2.hasNext();) {
                sol = results2.next();
                interests.add(sol.get("interests").toString());
                types.add(sol.get("comment").toString());
            }
            publication.setInterests(interests.toArray());
            publication.setComments(types.toArray());
            publications.add(publication);
            String[] authorSplit = sol.get("?author").toString().split("/",-1);
            String authorType = authorSplit[authorSplit.length-2];
            if(authorType.equals("users")){
                User user = UserController.user(authorSplit[authorSplit.length-1]);
                publication.setAuthor(user);
            }else{
                Group group = GroupController.group(authorSplit[authorSplit.length-1]);
                publication.setAuthorGroup(group);
            }

            System.out.println(publication.getAuthorName());
        }
        return publications;
    }
}
