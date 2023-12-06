package laundryattendant;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scene/Login.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene scene = new Scene(root, 1200, 800);
            primaryStage.setTitle("Laundry Attendant");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        }

        
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
