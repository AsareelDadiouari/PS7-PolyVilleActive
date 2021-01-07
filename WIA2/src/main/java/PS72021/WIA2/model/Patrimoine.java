package PS72021.WIA2.model;

import java.util.Set;

public class Patrimoine extends Lieu{
    private final String image;
    private Set<String> likes;

    public Patrimoine(int id, String name, double latitude, double longitude, String image) {
        super(id, name, latitude, longitude);
        this.image = image;
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
