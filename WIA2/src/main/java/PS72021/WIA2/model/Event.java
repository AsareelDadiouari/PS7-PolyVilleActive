package PS72021.WIA2.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

public class Event {

    private int id;
    private String name;
    private LocalDate start;
    private LocalDate end;
    private String address;
    private ArrayList<String> profiles;
    private ArrayList<String> categories;
    private Set<String> likes;
    private String decription;
    private String image;
    private Double latitude;
    private Double longitude;
    private Set<String> users;

    public Event(int id, String name, LocalDate start, LocalDate end, String address, ArrayList<String> profiles,
                 ArrayList<String> categories, String decription, String image, Double latitude, Double longitude,
                 Set<String> participants, Set<String> likes) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
        this.address = address;
        this.profiles = profiles;
        this.categories = categories;
        this.decription = decription;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
        this.users = participants;
        this.likes = likes;
    }

    public Event(String name) {
        this.name = name;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public String getAddress() {
        return address;
    }

    public ArrayList<String> getProfiles() {
        return profiles;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public String getDecription() {
        return decription;
    }

    public String getImage() {
        return image;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Set<String> getUsers() {
        return users;
    }

    public Set<String> getLikes() {
        return likes;
    }
}
