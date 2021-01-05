package PS72021.WIA2.model;

import java.util.ArrayList;

public class Visite {
    private ArrayList<Event> listEvents;
    private ArrayList<Patrimoine> listPatrimoines;
    private ArrayList<Store> listStore;

    public Visite(ArrayList<Event> listEvents, ArrayList<Patrimoine> listPatrimoines, ArrayList<Store> listStore) {
        this.listEvents = listEvents;
        this.listPatrimoines = listPatrimoines;
        this.listStore = listStore;
    }

    public ArrayList<Event> getListEvents() {
        return listEvents;
    }

    public void setListEvents(ArrayList<Event> listEvents) {
        this.listEvents = listEvents;
    }

    public ArrayList<Patrimoine> getListPatrimoines() {
        return listPatrimoines;
    }

    public void setListPatrimoines(ArrayList<Patrimoine> listPatrimoines) {
        this.listPatrimoines = listPatrimoines;
    }

    public ArrayList<Store> getListStore() {
        return listStore;
    }

    public void setListStore(ArrayList<Store> listStore) {
        this.listStore = listStore;
    }
}
