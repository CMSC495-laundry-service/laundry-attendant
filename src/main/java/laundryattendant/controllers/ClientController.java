package laundryattendant.controllers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.JsonArray;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import laundryattendant.laundryticket.Ticket;
import laundryattendant.laundryticket.TicketFactory;
import laundryattendant.scene.DBUtils;

public class ClientController extends Controller {
    @FXML
    public Label addButton;
    @FXML
    public VBox container;
    @FXML
    public Label usernameProfile;

    @FXML
    public void initialize() {
        addButton.setOnMouseClicked(this::handleMouseClicked);
    }

    @FXML
    public void handleSignoutClick(ActionEvent event) {

        DBUtils.changeScene(event, "Login.fxml", null, null, null);
    }

    private void displayDashboard() {
        container.getChildren().clear();
        JSONArray data = TicketFactory.getTickets(super.getUsername(), super.getPassword());
        for (Object obj : data) {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("../scene/ticket.fxml"));
            Parent root;
            try {
                root = loader.load();
                root.setScaleX(.75);
                root.setScaleY(.75);
                TicketController controller = loader.getController();

                JSONObject jsonData = (JSONObject) obj;
                controller.setAll((String) jsonData.get("type"), (String) jsonData.get("status"),
                        (String) jsonData.get("phonenum"),
                        (String) jsonData.get("name"), (String) jsonData.get("price"),
                        (String) jsonData.get("dateReceived"));
                container.getChildren().add(root);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    private void handleMouseClicked(MouseEvent event) {
        container.getChildren().clear();
        if (addButton.getText().equals("+")) {
            HBox hbox1 = new HBox();
            Label typeL = new Label("Laundry Type");
            TextField typeF = new TextField();
            hbox1.getChildren().addAll(typeL, typeF);

            HBox hbox2 = new HBox();
            Label phoneNumL = new Label("Phone Number");
            TextField phoneNumF = new TextField();
            hbox2.getChildren().addAll(phoneNumL, phoneNumF);

            HBox hbox3 = new HBox();
            Label nameL = new Label("Full Name");
            TextField nameF = new TextField();
            hbox3.getChildren().addAll(nameL, nameF);

            Button payButton = new Button("Pay");
            payButton.setOnAction(payEvent -> {
                int type = 0;

                switch (typeF.getText()) {
                    case "Rayon":
                        type = 1;
                        break;
                    case "Silk":
                        type = 2;
                        break;
                    case "Leather":
                        type = 3;
                        break;
                    case "Suede":
                        type = 4;
                        break;
                    case "Velvet":
                        type = 5;
                        break;
                    default:
                        type = 6;
                        break;
                }
                try {
                    Ticket ticket = TicketFactory.makeTicket(type, phoneNumF.getText(), nameF.getText(),
                            usernameProfile.getText());
                    TicketFactory.append(ticket, super.getPassword());
                    displayDashboard();
                    addButton.setText("+");
                } catch (Error e) {
                    System.out.println(e.getMessage());
                }
            });

            VBox contentDBox = new VBox();
            contentDBox.getChildren().addAll(hbox1, hbox2, hbox3, payButton);
            container.getChildren().add(contentDBox);

            addButton.setText("-");
        } else {
            displayDashboard();
            addButton.setText("+");
        }
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
        usernameProfile.setText(username);
        displayDashboard();
    }
}
