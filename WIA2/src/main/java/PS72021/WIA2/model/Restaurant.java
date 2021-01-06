package PS72021.WIA2.model;

import java.util.List;
import java.util.Set;

public class Restaurant {
    private  int id;
    private String name;
    private  String adress;
    private  Set<String> amenities;
    private  Set<String> services;
    private Set<String> likes;
    private  double longitude;
    private  double latitude;

    public Restaurant(){

    }

    public Restaurant(int id, String name, String adress, Set<String> amenities, Set<String> services, double lat, double lon){
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.amenities = amenities;
        this.services = services;
        this.latitude = lat;
        this.longitude = lon;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAdress() {
        return adress;
    }

    public Set<String> getAmenities() {
        return amenities;
    }

    public Set<String> getServices() {
        return services;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Set<String> getLikes() { return likes; }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setAmenities(Set<String> amenities) {
        this.amenities = amenities;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setServices(Set<String> services) {
        this.services = services;
    }

    public void setLikes(Set<String> likes) {
        this.likes = likes;
    }
}
