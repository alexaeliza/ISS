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

public class MainController implements Initializable {
    public Button logOut;
    public TableView<EditionFXML> books;
    public TableColumn<EditionFXML, String> title;
    public TableColumn<EditionFXML, String> author;
    public TableColumn<EditionFXML, Button> add;
    public Button seeCart;

    private User user;
    private Stage stage;
    private Repository repository;
    private ObservableList<EditionFXML> editions = FXCollections.observableArrayList();
    private ObservableList<Borrowing> borrowings = FXCollections.observableArrayList();

    public void setUser(User user) {
        this.user = user;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
        getItems();
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../LogIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);
        LogInController logInController = fxmlLoader.getController();
        logInController.setRepository(repository);
        logInController.setStage(stage);
        stage.setTitle("Library");
        stage.setScene(scene);
        stage.show();
    }

    private void getItems() {
        List<Edition> editionList = repository.getEditions();
        editionList.forEach(x -> {
            Button addBook = new Button();
            addBook.setText("Add");
            addBook.setOnAction(y -> repository.borrowEditionForUser(x, user));
            EditionFXML editionFXML = new EditionFXML(x, addBook);
            editions.add(editionFXML);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getEdition().getBook().getTitle()));
        author.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getEdition().getBook().getAuthor()));
        add.setCellValueFactory(value -> new SimpleObjectProperty(value.getValue().getAddBook()));
        books.setItems(editions);
    }

    public void seeCart(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../ReturnBooks.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);
        ReturnBooksController returnBooksController = fxmlLoader.getController();
        returnBooksController.setUser(user);
        returnBooksController.setRepository(repository);
        returnBooksController.setStage(stage);
        stage.setTitle("Library");
        stage.setScene(scene);
        stage.show();
    }
}
