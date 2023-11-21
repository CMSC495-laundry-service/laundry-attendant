package laundryattendant;

import java.io.Console;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import laundryattendant.registernlogin.FormBuilder;

public class registerController {
    @FXML
    public TextField fNameField;
    @FXML
    public TextField lNameField;
    @FXML
    public DatePicker dateField;
    @FXML
    public ToggleGroup toggle;
    @FXML
    public TextField usernameField;
    @FXML
    public TextField emailField;
    @FXML
    public TextField phoneNumField;
    @FXML
    public TextField addressField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public PasswordField cPasswordField;
    @FXML
    public TextField sQuestionField;
    @FXML
    public TextField sAnswerField;
    @FXML
    public TextField bankCardField;
    @FXML
    public TextField cvvField;
    @FXML
    public void toggleLoginEvent(ActionEvent event) {
        DBUtils.changeScene(event, "Login.fxml", "Register", null, null);
    }
    @FXML
    public void submitRegister(ActionEvent event) {
        try {
            Toggle genderPicker = toggle.getSelectedToggle();
            if (dateField.getValue() == null) throw new Error("Date Not Picked");
            if (genderPicker == null) throw new Error("Gender Not Selected");
            if (!cPasswordField.getText().equals(passwordField.getText())) throw new Error("Passwords are not the Same");

            FormBuilder form = new FormBuilder();

            form.makeForm(fNameField.getText(), lNameField.getText(), emailField.getText(), passwordField.getText(), 
                usernameField.getText(), dateField.getValue(), ((RadioButton)genderPicker).getText(), phoneNumField.getText(), 
                addressField.getText(), sQuestionField.getText(), sAnswerField.getText(), bankCardField.getText(), cvvField.getText());
                // DBUtils.changeScene(event, "Login.fxml", "Register", null, null);
        } catch (Error e) {
            System.out.println(e.getMessage());
        }
        
    }
}
