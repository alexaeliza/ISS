package controllers;

import data.Repository;
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
import models.Edition;
import models.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public Button logOut;
    public TableView<Edition> books;
    public TableColumn<Edition, String> title;
    public TableColumn<Edition, String> author;
    public TableColumn<Edition, Button> add;

    private User user;
    private Stage stage;
    private Repository repository;
    private ObservableList<Edition> editions = FXCollections.observableArrayList();

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
        editions.setAll(repository.getEditions());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getBook().getTitle()));
        author.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getBook().getAuthor()));
        books.setItems(editions);
    }
}
