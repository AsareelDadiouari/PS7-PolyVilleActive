package PS72021.WIA2.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Event {

    private int id;
    private String name;
    private LocalDate start;
    private LocalDate end;
    private String address;
    private ArrayList<String> profiles;
    private ArrayList<String> categories;
    private String decription;
    private String image;
    private Double latitude;
    private Double longitude;
    private ArrayList<String> users;

    public Event(int id, String name, LocalDate start, LocalDate end, String address, ArrayList<String> profiles, ArrayList<String> categories, String decription, String image, Double latitude, Double longitude, ArrayList<String> participants) {
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

    public ArrayList<String> getUsers() {
        return users;
    }
}
