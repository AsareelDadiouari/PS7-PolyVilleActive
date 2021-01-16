package PS72021.WIA2.model;

import java.util.ArrayList;

public class Genre {

    private final int id;
    private final String name;
    private final ArrayList<Categorie> categories;

    public Genre(int id, String name, ArrayList<Categorie> categories) {
        this.id = id;
        this.name = name;
        this.categories = categories;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Categorie> getCategories() {
        return categories;
    }
}
