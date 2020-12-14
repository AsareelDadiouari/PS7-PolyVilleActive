package PS72021.WIA2.model;

public class Publication {
    private int id;
    private String title;
    private User author;
    private String authorName;
    private Group authorGroup;
    private String description;
    private int like;
    private String[] interests;
    private String[] comments;

    public Publication(int id, String title, User author, String description, int like, String[] interests, String[] comments) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.like = like;
        this.interests = interests;
        this.comments = comments;
        this.authorName = "";
    }

    public Publication(int id, String title, Group authorGroup, String description, int like, String[] interests, String[] comments) {
        this.id = id;
        this.title = title;
        this.authorGroup = authorGroup;
        this.description = description;
        this.like = like;
        this.interests = interests;
        this.comments = comments;
        this.authorName = "";
    }

    public Publication(int id, String title, String description, int like) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.like = like;
        this.authorName = "";
    }


    public void setInterests(Object[] interests) {
        this.interests = new String[interests.length];
        for (int i = 0; i < this.interests.length; i++) {
            this.interests[i] = interests[i].toString();
        }
    }

    public void setComments(Object[] types) {
        this.comments = new String[types.length];
        for (int i = 0; i < this.comments.length; i++) {
            this.comments[i] = types[i].toString();
        }
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public User getAuthor() {
        return author;
    }

    public Group getAuthorGroup() {
        return authorGroup;
    }

    public String getDescription() {
        return description;
    }

    public int getLike() {
        return like;
    }

    public String[] getInterests() {
        return interests;
    }

    public String[] getComments() {
        return comments;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthor(User author) {
        this.author = author;
        this.authorName = author.getFirstname() + " " + author.getLastname();
    }

    public void setAuthorGroup(Group authorGroup) {
        this.authorGroup = authorGroup;
        this.authorName = authorGroup.getName();
    }
}
