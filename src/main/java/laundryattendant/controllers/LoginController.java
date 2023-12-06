package laundryattendant.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import laundryattendant.scene.DBUtils;

public class LoginController {
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Label alertMessage;
    @FXML
    public HBox alert;
    @FXML
    public void handleLoginEvent(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        try {
            DBUtils.changeScene(event, null, "Dashboard", username, password);
        } catch(Error e) {
            alertMessage.setText(e.getMessage());
            alert.setVisible(true);
        }
    }
    @FXML
    public void toggleRegisterEvent(ActionEvent event) {
        DBUtils.changeScene(event, "register.fxml", "Register", null, null);
    }
}
