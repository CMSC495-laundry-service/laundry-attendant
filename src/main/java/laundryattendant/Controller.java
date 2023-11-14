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
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Controller {

    @FXML
    private GridPane ticketContainer;

    @FXML
    private Label iconName;

    @FXML
    private Button signoutButton;

    @FXML
    public void initialize() {
        // Initialization code, if needed
        JSONParser parser = new JSONParser();

        Path filePath = Paths.get("./src/main/java/laundryattendant/tickets.json");
        try (Reader reader = new FileReader(filePath.toString())) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray ticketsArray = (JSONArray) jsonObject.get("tickets");

            for (int i = 0; i < ticketsArray.size(); i++) {
                // Get the ticket object at index i
                JSONObject ticketObject = (JSONObject) ticketsArray.get(i);

                // Access ticket properties
                Label status = new Label((String) ticketObject.get("status"));
                Label type = new Label((String) ticketObject.get("type"));
                Label orderId = new Label();
                orderId.setText(String.valueOf((Long) ticketObject.get("orderId")));

                Label price = new Label(String.valueOf((Double) ticketObject.get("price")));
                Label name = new Label((String) ticketObject.get("name"));
                Label dateReceived = new Label((String) ticketObject.get("dateReceived"));
                Label dateEstimated = new Label((String) ticketObject.get("dateEstimated"));

                // FlowPane ticketPane = new FlowPane(Orientation.VERTICAL);
                // ticketPane.setStyle("-fs-background-color:yellow");
                // ticketPane.getChildren().addAll(status, type, orderId, price, name,
                // dateReceived, dateEstimated);

                HBox ticketInfoBox = new HBox(10); // Adjust spacing as needed
                ticketInfoBox.getChildren().addAll(orderId, status, type, price);

                // Create another HBox for additional information
                HBox additionalInfoBox = new HBox(10);
                additionalInfoBox.getChildren().addAll(name, dateReceived, dateEstimated);

                ticketContainer.setStyle("-fx-background-color:lightblue");
                // Set alignment and spacing for the VBox
                VBox ticketVBox = new VBox();
                ticketVBox.setAlignment(Pos.CENTER_LEFT);
                ticketVBox.setSpacing(10); // Adjust spacing between elements
                ticketVBox.setPadding(new Insets(10));
                ticketVBox.setAlignment(Pos.CENTER);
                ticketVBox.setStyle("-fx-background-color:white");

                // Add the HBoxes to the VBox
                ticketVBox.getChildren().addAll(ticketInfoBox, additionalInfoBox);
                ticketContainer.add(ticketVBox, 0, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSignoutClick(ActionEvent event) {
        DBUtils.changeScene(event, "Login.fxml", "Login", null, null);
    }
}
