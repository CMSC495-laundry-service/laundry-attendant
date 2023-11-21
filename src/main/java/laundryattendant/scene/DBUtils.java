package laundryattendant.scene;

import laundryattendant.controllers.AdminController;
import laundryattendant.controllers.Controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.protobuf.TextFormat.ParseException;

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
                JSONParser parser = new JSONParser();

                Path filePath = Paths.get("./src/main/java/laundryattendant/users.json");
                try (Reader reader = new FileReader(filePath.toString())) {
                    JSONObject jsonObject = (JSONObject) parser.parse(reader);
                    JSONArray ticketsArray = (JSONArray) jsonObject.get("users");

                    for (int i = 0; i < ticketsArray.size(); i++) {
                        // Get the ticket object at index i
                        JSONObject ticketObject = (JSONObject) ticketsArray.get(i);

                        // Access ticket properties
                        String usernameString = ((String) ticketObject.get("username"));
                        String passwordString = ((String) ticketObject.get("password"));
                        if (username.equals(usernameString) && password.equals(passwordString)) {
                            boolean isAdmin = (boolean) ticketObject.get("isAdmin");
                            FXMLLoader loader;
                            if (isAdmin)
                                loader = new FXMLLoader(DBUtils.class.getResource("admin.fxml"));
                            else
                                loader = new FXMLLoader(DBUtils.class.getResource("App.fxml"));
                            root = (Parent) loader.load();
                            Controller controller = loader.getController();
                            controller.setUsername(username);

                        }
                    }
                } catch (ParseException e) {
                    System.out.println("File not found");
                }
            } else {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
            }

        } catch (Exception e) {
            e.printStackTrace();
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