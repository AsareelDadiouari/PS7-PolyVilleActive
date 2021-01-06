package PS72021.WIA2.model;

public class Patrimoine extends Lieu{
    private final String image;

    public Patrimoine(int id, String name, double latitude, double longitude, String image) {
        super(id, name, latitude, longitude);
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
