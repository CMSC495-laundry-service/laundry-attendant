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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public void handleLoginEvent(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        DBUtils.changeScene(event, null, "Dashboard", username, password);
        // DBUtils.changeScene(event, "App.fxml", "DashBoard", null, null);
    }
    @FXML
    public void toggleRegisterEvent(ActionEvent event) {
        DBUtils.changeScene(event, "register.fxml", "Register", null, null);
    }
}
