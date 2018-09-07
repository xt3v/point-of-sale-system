
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Sale;
import services.DatabaseService;
import services.ViewService;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/login.fxml"));
        primaryStage.setTitle("Uwezo Shop");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.show();
        ViewService.getViewService().setStage(primaryStage);
    }



    public static void main(String[] args) {
        launch(args);
    }
}
