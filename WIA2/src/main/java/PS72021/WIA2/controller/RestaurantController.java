package PS72021.WIA2.controller;

import PS72021.WIA2.model.Restaurant;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.system.Txn;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class RestaurantController {

    private static final String DATABASE = "http://localhost:3030/data_polyville";
    /**
     * Renvoie la liste des restaurants
     * @return La liste des restaurants
     */
    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/restaurants")
    public List<Restaurant> getRestaurants(){
        List<Restaurant> restaurants = new ArrayList<>();
        String query = "SELECT DISTINCT * WHERE {\n" +
                    "?s <http://www.ps7-wia2.com/restaurants#restaurants> ?o. " +
                    "?o <http://www.ps7-wia2.com/restaurants#name_fr> ?name_fr." +
                    "?o <http://www.ps7-wia2.com/restaurants#address_line1> ?address_line1." +
                    "?o <http://www.ps7-wia2.com/restaurants#amenity> ?amenity." +
                    "?o <http://www.ps7-wia2.com/restaurants#service> ?service." +
                    "?o <http://www.ps7-wia2.com/restaurants#latitude> ?latitude." +
                    "?o <http://www.ps7-wia2.com/restaurants#longitude> ?longitude." +
                    "OPTIONAL { ?o <http://www.ps7-wia2.com/restaurants#likes> ?likes. }" +
                    "}";

        RDFConnection conn = RDFConnectionFactory.connect(DATABASE);
        QueryExecution qExec = conn.query(query) ;
        ResultSet results = qExec.execSelect() ;

        QuerySolution sol = results.next();

        Restaurant restaurant = null;
        Set<String> amenities = new HashSet<>();
        Set<String> services = new HashSet<>();
        Set<String> likes = new HashSet<>();
        String[] sujet = sol.get("o").toString().split("/", -1);
        services.add(sol.get("service").toString());
        amenities.add(sol.get("amenity").toString());
        if (sol.get("likes") != null)
            likes.add(sol.get("likes").toString());
        restaurant = new Restaurant(Integer.parseInt(sujet[sujet.length - 1]),  sol.get("name_fr").toString(),sol.get("address_line1").toString(),amenities,services,
                sol.get("latitude").asLiteral().getDouble() ,sol.get("longitude").asLiteral().getDouble());
        restaurant.setLikes(likes);
        while (results.hasNext()) {
            sujet = sol.get("o").toString().split("/", -1);
            if (Integer.parseInt(sujet[sujet.length - 1]) != restaurant.getId()) {
                restaurants.add(restaurant);
                restaurant = new Restaurant(Integer.parseInt(sujet[sujet.length - 1]),  sol.get("name_fr").toString(),sol.get("address_line1").toString(),amenities,services,
                            sol.get("latitude").asLiteral().getDouble() ,sol.get("longitude").asLiteral().getDouble());
                restaurant.setLikes(likes);
                amenities = new HashSet<>();
                services = new HashSet<>();
                likes = new HashSet<>();
            }
            services.add(sol.get("service").toString());
            amenities.add(sol.get("amenity").toString());
            if (sol.get("likes") != null)
                likes.add(sol.get("likes").toString());
            sol = results.next();
        }
        restaurants.add(restaurant);
        qExec.close();
        conn.close();
        return restaurants;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(value = "/restaurants/{restaurantId}/like/{userId}", produces = MediaType.ALL_VALUE)
    public boolean addLike(@PathVariable("restaurantId") String id, @PathVariable("userId") String userId){
        String query = "PREFIX rest: <http://www.ps7-wia2.com/restaurants/>" +
                "PREFIX res: <http://www.ps7-wia2.com/restaurants#>" +
                "PREFIX u: <http://www.ps7-wia2.com/users/> " +
                "INSERT DATA { rest:" + id + " res:likes u:" + userId + " }";

        RDFConnection conn2 = RDFConnectionFactory.connect(DATABASE);
        Txn.executeWrite(conn2, () -> conn2.update(query));
        conn2.close();

        return true;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(value = "/restaurants/{restaurantId}/unlike/{userId}", produces = MediaType.ALL_VALUE)
    public boolean unLike(@PathVariable("restaurantId") String id, @PathVariable("userId") String userId){
        String query = "PREFIX rest: <http://www.ps7-wia2.com/restaurants/>" +
                "PREFIX res: <http://www.ps7-wia2.com/restaurants#>" +
                "PREFIX u: <http://www.ps7-wia2.com/users/> " +
                "DELETE DATA { rest:" + id + " res:likes u:" + userId + " }";

        RDFConnection conn2 = RDFConnectionFactory.connect(DATABASE);
        Txn.executeWrite(conn2, () -> conn2.update(query));
        conn2.close();

        return true;
    }
}
