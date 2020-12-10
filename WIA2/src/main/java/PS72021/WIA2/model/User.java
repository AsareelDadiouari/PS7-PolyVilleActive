package PS72021.WIA2.model;

public class User {

    private int id;
    private String firstname;
    private String lastname;
    private String type;
    private String role;
    private String[] interets;
    private String[] events = new String[0];
    private String[] groups = new String[0];

    public User(int id, String firstname, String lastname, String type, String role) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.type = type;
        this.role = role;
    }

    public void setInterets(Object[] interets) {
        this.interets = new String[interets.length];
        for (int i = 0; i < this.interets.length; i++) {
            this.interets[i] = interets[i].toString();
        }
    }

    public void setEvents(String[] events) {
        this.events = events;
    }

    public void setGroups(String[] groups) {
        this.groups = groups;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getType() {
        return type;
    }

    public String getRole() {
        return role;
    }

    public String[] getInterets() {
        return interets;
    }

    public String[] getEvents() {
        return events;
    }

    public String[] getGroups() {
        return groups;
    }
}
