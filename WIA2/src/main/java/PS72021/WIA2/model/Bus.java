package PS72021.WIA2.model;

public class Bus {

    private final int id;
    private final String nameStop;
    private final double latitude;
    private final double longitude;

    public Bus(int id, String nameStop, double latitude, double longitude) {
        this.id = id;
        this.nameStop = nameStop;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public String getNameStop() {
        return nameStop;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
