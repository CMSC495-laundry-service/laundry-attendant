package laundryattendant.scene;
import laundryattendant.controllers.Controller;
import laundryattendant.registernlogin.FormBuilder;
import laundryattendant.registernlogin.LoginForm;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

public class DBUtils {
    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username,
            String password) {
        Parent root = null;
        try {
            if (title == "Dashboard") {
                LoginForm loginForm = FormBuilder.get(username, password);
                FXMLLoader loader;
                if (loginForm.isAdmin()) {
                    loader = new FXMLLoader(DBUtils.class.getResource("admin.fxml"));
                } else
                    loader = new FXMLLoader(DBUtils.class.getResource("App.fxml"));
                
                root = (Parent) loader.load();
                Controller controller = loader.getController();
                    controller.setPassword(password);
                    controller.setUsername(username);

            } else {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
            }

        } catch (Exception e) {
            throw new Error(e.getMessage());
        }

        if (root == null) {
            throw new Error("Username or password is incorrect.");
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 1200, 800));
        stage.show();
    }
}