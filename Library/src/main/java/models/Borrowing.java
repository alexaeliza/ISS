package models;

public class Borrowing {
    private User user;
    private Edition edition;

    public Borrowing(User user, Edition edition) {
        this.user = user;
        this.edition = edition;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }
}
