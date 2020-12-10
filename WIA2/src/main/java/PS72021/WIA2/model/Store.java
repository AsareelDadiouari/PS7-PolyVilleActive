package PS72021.WIA2.model;

public class Store {

    private int id;
    private String name_fr;
    private String opening;
    private String address;
    private String[] categories = new String[0];
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

    public void setCategories(Object[] categories) {
        this.categories = new String[categories.length];
        for (int i = 0; i < this.categories.length; i++) {
            this.categories[i] = categories[i].toString();
        }
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

    public String[] getCategories() {
        return categories;
    }

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
