package laundryattendant.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import laundryattendant.registernlogin.FormBuilder;
import laundryattendant.registernlogin.RegisterForm;
import laundryattendant.scene.DBUtils;

public class RegisterController{
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
    public Label alertMessage;
    @FXML
    public HBox alert;
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


            RegisterForm form = (RegisterForm)FormBuilder.makeForm(fNameField.getText(), lNameField.getText(), emailField.getText(), passwordField.getText(), 
                usernameField.getText(), dateField.getValue(), ((RadioButton)genderPicker).getText(), phoneNumField.getText(), 
                addressField.getText(), sQuestionField.getText(), sAnswerField.getText(), bankCardField.getText(), cvvField.getText());

            FormBuilder.append(form);    
    
            DBUtils.changeScene(event, "Login.fxml", "Register", null, null);
        } catch (Error e) {
            alert.setVisible(true);
            alertMessage.setText(e.getMessage());
        }
        
    }
}
