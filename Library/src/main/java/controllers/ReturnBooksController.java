package controllers;

import data.Repository;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import models.Borrowing;
import models.Edition;
import models.FXML.EditionFXML;
import models.User;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ReturnBooksController implements Initializable {
    public TableView<EditionFXML> books;
    public TableColumn<EditionFXML, String> author;
    public TableColumn<EditionFXML, String> title;
    public TableColumn<EditionFXML, Button> returnBook;

    private User user;
    private Repository repository;
    private Stage stage;
    private ObservableList<EditionFXML> editions = FXCollections.observableArrayList();

    public void setUser(User user) {
        this.user = user;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
        getItems();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void getItems() {
        List<Borrowing> borrowings = repository.getBorrowingsOfUser(user);
        borrowings.forEach(x -> {
            Button returnBook = new Button();
            returnBook.setText("Return");
            returnBook.setOnAction(y -> repository.returnEdition(x.getEdition(), user));
            EditionFXML editionFXML = new EditionFXML(x.getEdition(), returnBook);
            editions.add(editionFXML);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getEdition().getBook().getTitle()));
        author.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getEdition().getBook().getAuthor()));
        returnBook.setCellValueFactory(value -> new SimpleObjectProperty(value.getValue().getAddBook()));
        books.setItems(editions);
    }

    public void back(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);
        MainController mainController = fxmlLoader.getController();
        mainController.setRepository(repository);
        mainController.setStage(stage);
        mainController.setUser(user);
        stage.setTitle("Blood4Life");
        stage.setScene(scene);
        stage.show();
    }
}
