package PS72021.WIA2.model;

import java.util.ArrayList;

public class Visite {
    private final ArrayList<Lieu> listLieux;

    public Visite(ArrayList<Lieu> listLieux) {
        this.listLieux = listLieux;
    }

    public ArrayList<Lieu> getListLieux() {
        return listLieux;
    }
}
