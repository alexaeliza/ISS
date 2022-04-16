package models;

import java.util.ArrayList;
import java.util.List;

public class Terminal {
    private int id;
    private List<Visit> visits;

    public Terminal() {
        visits = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }
}
