package PS72021.WIA2.model;

public class Store extends Lieu{

    private final String opening;
    private final String address;
    private String[] categories = new String[0];
    private final String description;

    public Store(int id, String name, String opening, String address, String description, double latitude, double longitude) {
        super(id, name, latitude, longitude);
        this.opening = opening;
        this.address = address;
        this.description = description;
    }

    public void setCategories(Object[] categories) {
        this.categories = new String[categories.length];
        for (int i = 0; i < this.categories.length; i++) {
            this.categories[i] = categories[i].toString();
        }
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

}
