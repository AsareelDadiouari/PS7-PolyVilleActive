package PS72021.WIA2.model;

import java.util.Set;

public class Store extends Lieu{


    private final String opening;
    private final String address;
    private final String description;
    private Set<String> categories;
    private Set<String> likes;

    public Store(int id, String name, String opening, String address, String description, double latitude, double longitude) {
        super(id, name, latitude, longitude, "Boutique");
        this.opening = opening;
        this.address = address;
        this.description = description;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public void setLikes(Set<String> likes) {
        this.likes = likes;
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

}
