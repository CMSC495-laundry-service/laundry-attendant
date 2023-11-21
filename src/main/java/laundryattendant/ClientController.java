package laundryattendant;

import java.io.FileReader;
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
import javafx.geometry.VPos;
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

public class ClientController implements Controller {
    private String username;

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
        JSONParser parser = new JSONParser();
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setStyle("-fx-background-color:coral;");

        Path filePath = Paths.get("./src/main/java/laundryattendant/tickets.json");
        try (Reader reader = new FileReader(filePath.toString())) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray ticketsArray = (JSONArray) jsonObject.get("tickets");
            System.out.println(ticketsArray.size());
            for (int i = 0; i < ticketsArray.size(); i++) {

                // Get the ticket object at index i
                JSONObject ticketObject = (JSONObject) ticketsArray.get(i);
                System.out.println((String) ticketObject.get("username"));
                if (!username.equals((String) ticketObject.get("username"))) // Unless username and the name in json file
                                                                            // are same, skip
                    continue;
                // Access ticket properties
                Label status = new Label((String) ticketObject.get("status"));
                Label type = new Label((String) ticketObject.get("type"));
                Label orderId = new Label(String.valueOf((Long) ticketObject.get("orderId")));
                Label phoneNum = new Label((String) ticketObject.get("phonenum"));
                // Label price = new Label(String.valueOf((Double) ticketObject.get("price")));
                Label name = new Label((String) ticketObject.get("name"));
                Label dateReceived = new Label((String) ticketObject.get("dateReceived"));
                // Label dateEstimated = new Label((String) ticketObject.get("dateEstimated"));

                status.setAlignment(Pos.TOP_CENTER);
                type.setAlignment(Pos.TOP_CENTER);
                orderId.setAlignment(Pos.TOP_CENTER);
                phoneNum.setAlignment(Pos.TOP_CENTER);
                name.setAlignment(Pos.TOP_CENTER);
                dateReceived.setAlignment(Pos.TOP_CENTER);

                // RowConstraints rowConstraints = new RowConstraints();
                // rowConstraints.setValignment(VPos.TOP);
                // gridPane.getRowConstraints().add(new RowConstraints());
                // gridPane.getRowConstraints().set(i, rowConstraints);
                gridPane.add(orderId, 0, i);
                gridPane.add(type, 1, i);
                gridPane.add(name, 2, i);
                gridPane.add(phoneNum, 3, i);
                gridPane.add(dateReceived, 4, i);
                gridPane.add(status, 5, i);
                container.getChildren().add(gridPane);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleMouseClicked(MouseEvent event) {

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
                TicketFactory ticketFactory = new TicketFactory();
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
                    ticketFactory.makeTicket(type, phoneNumF.getText(), nameF.getText(), usernameProfile.getText());

                    container.getChildren().clear();
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
        this.username = username;
        usernameProfile.setText(username);
        displayDashboard();
    }
}
