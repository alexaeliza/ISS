import controllers.LogInController;
import data.Repository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);
        LogInController logInController = fxmlLoader.getController();
        logInController.setRepository(new Repository("jdbc:postgresql://localhost:1810/Library",
                "postgres", "101802"));
        logInController.setStage(stage);
        stage.setTitle("Library");
        stage.setScene(scene);
        stage.show();
    }

    public void main() {
        launch();
    }
}
