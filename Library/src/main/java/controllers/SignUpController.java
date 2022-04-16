package controllers;

import data.Repository;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    public Button back;
    public Button signUp;
    public TextField cnp;
    public TextField name;
    public TextField address;
    public TextField password;

    private Stage stage;
    private Repository repository;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void back(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../LogIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);
        LogInController logInController = fxmlLoader.getController();
        logInController.setRepository(repository);
        logInController.setStage(stage);
        stage.setTitle("Library");
        stage.setScene(scene);
        stage.show();
    }

    public void signUp(ActionEvent actionEvent) {
        User user = repository.getUserByCnp(cnp.getText());
        if (user != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("User with this cnp already exists");
            alert.showAndWait();
        }
        else {
            user = new User(cnp.getText(), name.getText(), password.getText(), address.getText());
            repository.addUser(user);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("User created");
            alert.showAndWait();
        }
    }
}
