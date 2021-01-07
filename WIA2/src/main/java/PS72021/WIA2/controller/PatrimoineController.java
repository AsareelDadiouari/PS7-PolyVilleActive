package PS72021.WIA2.controller;

import PS72021.WIA2.model.Patrimoine;
import PS72021.WIA2.model.Restaurant;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.system.Txn;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class PatrimoineController {

    private static final String DATABASE = "http://localhost:3030/data_polyville";

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/patrimoines")
    public List<Patrimoine> getPatrimoines()throws Exception{
        List<Patrimoine> patrimoines = new ArrayList<>();
        String query = "SELECT DISTINCT * WHERE {\n" +
                "?s <http://www.ps7-wia2.com/patrimoines#patrimoines> ?o. " +
                "?o <http://www.ps7-wia2.com/patrimoines#name> ?name." +
                "?o <http://www.ps7-wia2.com/patrimoines#latitude> ?latitude." +
                "?o <http://www.ps7-wia2.com/patrimoines#longitude> ?longitude." +
                "?o <http://www.ps7-wia2.com/patrimoines#image> ?image." +
                "OPTIONAL { ?o <http://www.ps7-wia2.com/patrimoines#likes> ?likes. }" +
                "}";

        RDFConnection conn = RDFConnectionFactory.connect(DATABASE);
        QueryExecution qExec = conn.query(query) ;
        ResultSet results = qExec.execSelect() ;

        QuerySolution sol = results.next();

        String[] sujet = sol.get("o").toString().split("/", -1);
        Patrimoine patrimoine = new Patrimoine(Integer.parseInt(sujet[sujet.length - 1]),  sol.get("name").toString(),sol.get("latitude").asLiteral().getDouble() ,sol.get("longitude").asLiteral().getDouble(), sol.get("image").toString());
        Set<String> likes = new HashSet<>();
        patrimoine.setLikes(likes);
        if (sol.get("likes") != null)
            likes.add(sol.get("likes").toString());
        while (results.hasNext()) {
            sujet = sol.get("o").toString().split("/", -1);
            if (Integer.parseInt(sujet[sujet.length - 1]) != patrimoine.getId()) {
                patrimoine.setLikes(likes);
                patrimoines.add(patrimoine);
                patrimoine = new Patrimoine(Integer.parseInt(sujet[sujet.length - 1]),  sol.get("name").toString(),sol.get("latitude").asLiteral().getDouble() ,sol.get("longitude").asLiteral().getDouble(), sol.get("image").toString());
                likes = new HashSet<>();
            }
            if (sol.get("likes") != null)
                likes.add(sol.get("likes").toString());
            sol = results.next();
        }
        patrimoine.setLikes(likes);
        patrimoines.add(patrimoine);
        qExec.close();
        conn.close();
        return patrimoines;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(value = "/patrimoines/{patrimoineId}/like/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean addLike(@PathVariable("patrimoineId") String id, @PathVariable("userId") String userId){
        String query = "PREFIX patr: <http://www.ps7-wia2.com/patrimoines/>" +
                "PREFIX pat: <http://www.ps7-wia2.com/patrimoines#>" +
                "PREFIX u: <http://www.ps7-wia2.com/users/> " +
                "INSERT DATA { patr:" + id + " pat:likes u:" + userId + " }";

        RDFConnection conn2 = RDFConnectionFactory.connect(DATABASE);
        Txn.executeWrite(conn2, () -> conn2.update(query));
        conn2.close();

        return true;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(value = "/patrimoines/{patrimoineId}/unlike/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean unLike(@PathVariable("patrimoineId") String id, @PathVariable("userId") String userId){
        String query = "PREFIX patr: <http://www.ps7-wia2.com/patrimoines/>" +
                "PREFIX pat: <http://www.ps7-wia2.com/patrimoines#>" +
                "PREFIX u: <http://www.ps7-wia2.com/users/> " +
                "DELETE DATA { patr:" + id + " pat:likes u:" + userId + " }";

        RDFConnection conn2 = RDFConnectionFactory.connect(DATABASE);
        Txn.executeWrite(conn2, () -> conn2.update(query));
        conn2.close();

        return true;
    }
}
