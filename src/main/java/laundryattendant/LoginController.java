package laundryattendant;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LoginController {
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public void handleLoginEvent(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        DBUtils.changeScene(event, "App.fxml", "Dashboard", username, password);
        System.out.println(username);
        // DBUtils.changeScene(event, "App.fxml", "DashBoard", null, null);
    }
}
