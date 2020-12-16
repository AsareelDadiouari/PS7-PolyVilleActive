package PS72021.WIA2.model;

public class Group {

    private int id;
    private String name;
    private User admin;
    private String description;
    private String[] members = new String[0];
    private String[] interests;
    private String[] types;

    public Group(int id, String name, User admin, String description) {
        this.id = id;
        this.name = name;
        this.admin = admin;
        this.description = description;
    }

    public void setMembers(Object[] members) {
        this.members = new String[members.length];
        for (int i = 0; i < this.members.length; i++) {
            this.members[i] = members[i].toString();
        }
    }

    public void setInterests(Object[] interests) {
        this.interests = new String[interests.length];
        for (int i = 0; i < this.interests.length; i++) {
            this.interests[i] = interests[i].toString();
        }
    }

    public void setTypes(Object[] types) {
        this.types = new String[types.length];
        for (int i = 0; i < this.types.length; i++) {
            this.types[i] = types[i].toString();
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getAdmin() {
        return admin;
    }

    public String[] getMembers() {
        return members;
    }

    public String[] getInterests() {
        return interests;
    }

    public String[] getTypes() {
        return types;
    }

    public String getDescription() {
        return description;
    }
}
