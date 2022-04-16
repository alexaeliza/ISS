package models;

public class Visit {
    private User user;
    private Terminal terminal;

    public Visit(User user, Terminal terminal) {
        this.user = user;
        this.terminal = terminal;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }
}
