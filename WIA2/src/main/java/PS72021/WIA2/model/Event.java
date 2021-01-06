package PS72021.WIA2.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

public class Event extends Lieu{
    private final LocalDate start;
    private final LocalDate end;
    private final String address;
    private final ArrayList<String> profiles;
    private final ArrayList<String> categories;
    private final String decription;
    private final String image;
    private final Set<String> users;

    public Event(int id, String name, LocalDate start, LocalDate end, String address, ArrayList<String> profiles, ArrayList<String> categories, String decription, String image, Double latitude, Double longitude, Set<String> participants) {
        super(id, name, latitude, longitude);
        this.start = start;
        this.end = end;
        this.address = address;
        this.profiles = profiles;
        this.categories = categories;
        this.decription = decription;
        this.image = image;
        this.users = participants;
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


    public Set<String> getUsers() {
        return users;
    }
}
