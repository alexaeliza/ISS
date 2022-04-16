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

public class LogInController implements Initializable {
    public Button logIn;
    public Button signUp;
    public TextField cnp;
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

    public void signUp(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../SignUp.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);
        SignUpController signUpController = fxmlLoader.getController();
        signUpController.setRepository(repository);
        signUpController.setStage(stage);
        stage.setTitle("Library");
        stage.setScene(scene);
        stage.show();
    }

    public void logIn(ActionEvent actionEvent) throws IOException {
        User user = repository.getUserByCnp(cnp.getText());
        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("User with this cnp does not exist");
            alert.showAndWait();
        }
        else
            if (user.getPassword().equals(password.getText())) {
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
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Password is incorrect");
                alert.showAndWait();
            }
    }
}
