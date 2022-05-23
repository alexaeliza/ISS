package models.FXML;

import javafx.scene.control.Button;
import models.Edition;

public class EditionFXML {
    private final Edition edition;
    private final Button addBook;

    public EditionFXML(Edition edition, Button addBook) {
        this.edition = edition;
        this.addBook = addBook;
    }

    public Edition getEdition() {
        return edition;
    }

    public Button getAddBook() {
        return addBook;
    }
}
