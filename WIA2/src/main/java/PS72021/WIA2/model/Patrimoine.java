package PS72021.WIA2.model;

import java.util.Set;

public class Patrimoine {
    private final int id;
    private final String name;
    private final double latitude;
    private final double longitude;
    private final String image;
    private Set<String> likes;

    public Patrimoine(int id, String name, double latitude, double longitude, String image) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getImage() {
        return image;
    }

    public Set<String> getLikes() {
        return likes;
    }

    public void setLikes(Set<String> likes) {
        this.likes = likes;
    }
}
