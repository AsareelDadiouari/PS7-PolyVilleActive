package PS72021.WIA2.controller;

import PS72021.WIA2.Application;
import PS72021.WIA2.model.Restaurant;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class RestaurantController {

    /**
     * Renvoie la liste des restaurants
     * @return La liste des restaurants
     */
    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/restaurants")
    public List<Restaurant> getRestaurants()throws Exception{
        List<Restaurant> restaurants = new ArrayList<>();
        String filePath = "database/restaurants2.jsonld";

        String query = "SELECT DISTINCT ?s ?p ?o WHERE {\n" +
                "?s <http://www.ps7-wia2.com/restaurants#restaurants> ?o " +
                "}";
        ResultSet results = Application.executeQuery(query, filePath);


        for (int i = 1; results.hasNext()  && i < 25; i++){
            QuerySolution querySolution = results.next();
            query = "SELECT DISTINCT * WHERE {\n" +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/restaurants#name_fr> ?name_fr." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/restaurants#address_line1> ?address_line1." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/restaurants#amenity> ?amenity." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/restaurants#service> ?service." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/restaurants#latitude> ?latitude." +
                    "<" + querySolution.get("o") + "> <http://www.ps7-wia2.com/restaurants#longitude> ?longitude." +
                    "}";

            ResultSet results2 = Application.executeQuery(query, filePath);
            QuerySolution sol = results2.next();
            Restaurant restaurant = new Restaurant(i,  sol.get("name_fr").toString(),sol.get("address_line1").toString(),new HashSet<>(),new HashSet<>(),
                    sol.get("latitude").asLiteral().getDouble() ,sol.get("longitude").asLiteral().getDouble());
            Set<String> amenities = new HashSet<>();
            Set<String> services = new HashSet<>();
            services.add(sol.get("service").toString());
            amenities.add(sol.get("amenity").toString());

            for (;results2.hasNext();) {
                sol = results2.next();
                services.add(sol.get("service").toString());
                amenities.add(sol.get("amenity").toString());
            }
            restaurant.setAmenities(amenities);
            restaurant.setServices(services);
            restaurants.add(restaurant);
        }
        return restaurants;
    }
}
