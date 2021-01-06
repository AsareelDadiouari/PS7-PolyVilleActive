package PS72021.WIA2.model;

import java.util.Set;

public class Store {

    private int id;
    private String name_fr;
    private String opening;
    private String address;
    private Set<String> categories;
    private Set<String> likes;
    private String description;
    private double latitude;
    private double longitude;

    public Store(int id, String name_fr, String opening, String address, String description, double latitude, double longitude) {
        this.id = id;
        this.name_fr = name_fr;
        this.opening = opening;
        this.address = address;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public void setLikes(Set<String> likes) {
        this.likes = likes;
    }

    public int getId() {
        return id;
    }

    public String getName_fr() {
        return name_fr;
    }

    public String getOpening() {
        return opening;
    }

    public String getAddress() {
        return address;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public Set<String> getLikes() { return likes; }

    public String getDescription() {
        return description;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
