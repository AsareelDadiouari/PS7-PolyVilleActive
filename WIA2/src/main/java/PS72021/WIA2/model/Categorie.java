package PS72021.WIA2.model;

import java.util.ArrayList;

public class Categorie {

    private final int id;
    private final String name;

    public Categorie(int id, String name) {
        this.id = id;
        this.name = name;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
