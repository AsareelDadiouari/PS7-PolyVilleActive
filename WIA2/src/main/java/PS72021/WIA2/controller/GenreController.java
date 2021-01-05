package PS72021.WIA2.controller;

import PS72021.WIA2.model.Bus;
import PS72021.WIA2.model.Categorie;
import PS72021.WIA2.model.Genre;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GenreController {
    private static final String DATABASE = "http://localhost:3030/data_polyville";

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/genres")
    public List<Genre> getGenres()  {
        List<Genre> listGenre = new ArrayList<>();
        String query = "SELECT DISTINCT * WHERE {\n" +
                "?s <http://www.ps7-wia2.com/genres#genres> ?o. " +
                "?o <http://www.ps7-wia2.com/genres#name> ?name." +
                "?o <http://www.ps7-wia2.com/genres#categories> ?categories." +
                "}";

        RDFConnection conn = RDFConnectionFactory.connect(DATABASE);
        QueryExecution qExec = conn.query(query) ;
        ResultSet results = qExec.execSelect() ;

        QuerySolution sol = results.next();
        String[] sujet = sol.get("o").toString().split("/", -1);

        ArrayList<Categorie> categories = new ArrayList<>();
        String queryUsers = "PREFIX genre: <http://www.ps7-wia2.com/genres/>\n" +
                "PREFIX el: <http://www.ps7-wia2.com/genres#>\n" +
                "SELECT DISTINCT * WHERE {\n" +
                "genre:" + sujet[sujet.length - 1] + " el:categories + ?categories }";


        RDFConnection conn1 = RDFConnectionFactory.connect(DATABASE);
        QueryExecution qExec1 = conn1.query(queryUsers);
        ResultSet results1 = qExec1.execSelect();


        while (results1.hasNext()) {
            String[] genreSplit = results1.next().get("?categories").toString().split(":",-1);
            Categorie cat = CategorieController.categorie(genreSplit[genreSplit.length-1]);
            categories.add(cat);
        }
        qExec1.close();
        conn1.close();
        Genre genre = new Genre(Integer.parseInt(sujet[sujet.length - 1]), sol.get("name").toString(), categories);

        for (;results.hasNext();) {
            sujet = sol.get("o").toString().split("/", -1);
            if (Integer.parseInt(sujet[sujet.length - 1]) != genre.getId()) {
                listGenre.add(genre);
                categories = new ArrayList<>();
                queryUsers = "PREFIX genre: <http://www.ps7-wia2.com/genres/>\n" +
                        "PREFIX el: <http://www.ps7-wia2.com/genres#>\n" +
                        "SELECT DISTINCT * WHERE {\n" +
                        "genre:" + sujet[sujet.length - 1] + " el:categories + ?categories }";


                conn1 = RDFConnectionFactory.connect(DATABASE);
                qExec1 = conn1.query(queryUsers);
                results1 = qExec1.execSelect();

                while (results1.hasNext()) {
                    String[] genreSplit = results1.next().get("?categories").toString().split(":",-1);
                    Categorie cat = CategorieController.categorie(genreSplit[genreSplit.length-1]);
                    categories.add(cat);
                }
                qExec1.close();
                conn1.close();
                genre = new Genre(Integer.parseInt(sujet[sujet.length - 1]), sol.get("name").toString(), categories);

            }
            sol = results.next();
        }
        listGenre.add(genre);
        qExec.close();
        conn.close();
        return listGenre;
    }
}
