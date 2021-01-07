package PS72021.WIA2.model;

public class Lieu {
    private final int id;
    private final String name;
    private final Double latitude;
    private final Double longitude;

    public Lieu(int id, String name, Double latitude, Double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
