package laundryattendant.scene;

import laundryattendant.controllers.AdminController;
import laundryattendant.controllers.ClientController;
import laundryattendant.controllers.Controller;
import laundryattendant.registernlogin.FormBuilder;
import laundryattendant.registernlogin.LoginForm;

import org.apache.hc.client5.http.entity.mime.FormBodyPart;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.css.CssParser.ParseError;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            System.out.println(e.getMessage());
        }

        if (root == null) {
            System.out.println("Username or password is incorrect.");
            return;
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 1200, 800));
        stage.show();
    }

    // public static void main(String[] args) {
    // try {
    // Connection connection =
    // DriverManager.getConnection("jdbc:mysql://localhost:3306/laundryservice","root","1234");
    // Statement statement = connection.createStatement();
    // ResultSet resultSet = statement.executeQuery("select * from user");
    // while (resultSet.next()) {
    // System.out.println(resultSet.getString("firstname"));
    // }
    // } catch (SQLException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }
}