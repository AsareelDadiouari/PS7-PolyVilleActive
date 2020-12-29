package PS72021.WIA2.controller;

import PS72021.WIA2.model.Bus;
import org.apache.jena.query.*;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
public class BusController {
    private static final String DATABASE = "http://localhost:3030/data_polyville";

    private double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {
        double pk =  (180/3.14169);

        double a1 = lat_a / pk;
        double a2 = lng_a / pk;
        double b1 = lat_b / pk;
        double b2 = lng_b / pk;

        double t1 = Math.cos(a1)*Math.cos(a2)*Math.cos(b1)*Math.cos(b2);
        double t2 = Math.cos(a1)*Math.sin(a2)*Math.cos(b1)*Math.sin(b2);
        double t3 = Math.sin(a1)*Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);

        return 6366000*tt;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/bus")
    public List<Bus> getBus()  {
        List<Bus> listBus = new ArrayList<>();
        String query = "SELECT DISTINCT * WHERE {\n" +
                "?s <http://www.ps7-wia2.com/arrets#arrets> ?o. " +
                "?o <http://www.ps7-wia2.com/arrets#nom_arret> ?nom_arret." +
                "?o <http://www.ps7-wia2.com/arrets#latitude> ?latitude." +
                "?o <http://www.ps7-wia2.com/arrets#longitude> ?longitude." +
                "}";

        RDFConnection conn = RDFConnectionFactory.connect(DATABASE);
        QueryExecution qExec = conn.query(query) ;
        ResultSet results = qExec.execSelect() ;

        QuerySolution sol = results.next();
        String[] sujet = sol.get("o").toString().split("/", -1);

        Bus bus = new Bus(Integer.parseInt(sujet[sujet.length - 1]), sol.get("nom_arret").toString(), sol.get("latitude").asLiteral().getDouble(), sol.get("longitude").asLiteral().getDouble());

        for (;results.hasNext();) {
            sujet = sol.get("o").toString().split("/", -1);
            if (Integer.parseInt(sujet[sujet.length - 1]) != bus.getId()) {
                listBus.add(bus);
                bus = new Bus(Integer.parseInt(sujet[sujet.length - 1]), sol.get("nom_arret").toString(), sol.get("latitude").asLiteral().getDouble(), sol.get("longitude").asLiteral().getDouble());

            }
            sol = results.next();
        }
        listBus.add(bus);
        qExec.close();
        conn.close();
        return listBus;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/busRecommandations")
    public List<Bus> getBusEvenement(@RequestParam double latitude, @RequestParam double longitude)  {
        List<Bus> listBus = new ArrayList<>();
        String query = "SELECT DISTINCT * WHERE {\n" +
                "?s <http://www.ps7-wia2.com/arrets#arrets> ?o. " +
                "?o <http://www.ps7-wia2.com/arrets#nom_arret> ?nom_arret." +
                "?o <http://www.ps7-wia2.com/arrets#latitude> ?latitude." +
                "?o <http://www.ps7-wia2.com/arrets#longitude> ?longitude." +
                "}";

        RDFConnection conn = RDFConnectionFactory.connect(DATABASE);
        QueryExecution qExec = conn.query(query) ;
        ResultSet results = qExec.execSelect() ;

        QuerySolution sol = results.next();



            String[] sujet = sol.get("o").toString().split("/", -1);

            Bus bus = new Bus(Integer.parseInt(sujet[sujet.length - 1]), sol.get("nom_arret").toString(), sol.get("latitude").asLiteral().getDouble(), sol.get("longitude").asLiteral().getDouble());

            for (; results.hasNext(); ) {
                sujet = sol.get("o").toString().split("/", -1);
                if (Integer.parseInt(sujet[sujet.length - 1]) != bus.getId()) {
                    if(gps2m(bus.getLatitude(), bus.getLongitude(),latitude,longitude) < 250) {
                        listBus.add(bus);
                    }
                    bus = new Bus(Integer.parseInt(sujet[sujet.length - 1]), sol.get("nom_arret").toString(), sol.get("latitude").asLiteral().getDouble(), sol.get("longitude").asLiteral().getDouble());

                }
                sol = results.next();
            }
        if(gps2m(bus.getLatitude(), bus.getLongitude(),latitude,longitude) < 250) {
            listBus.add(bus);
        }
        qExec.close();
        conn.close();
        return listBus;
    }

}
