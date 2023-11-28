package laundryattendant.controllers;

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
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import laundryattendant.laundryticket.TicketFactory;
import laundryattendant.scene.DBUtils;

public class AdminController extends Controller {
    @FXML
    private GridPane ticketContainer;

    @FXML
    private Label usernameProfile;

    @FXML
    private Button signoutButton;

    private void displayDashboard() {

        try {
            JSONArray ticketsArray = TicketFactory.getTickets(super.getUsername(), super.getPassword());
            for (int i = 0; i < ticketsArray.size(); i++) {
                // Get the ticket object at index i
                JSONObject ticketObject = (JSONObject) ticketsArray.get(i);

                // Access ticket properties
                Button status = new Button((String) ticketObject.get("status"));
                status.setId(String.valueOf((Long) ticketObject.get("orderid")));
                status.setOnAction(statusEvent -> {
                    if (status.getText().equals("COMPLETED"))
                        ;
                    else {
                        if (status.getText().equals("PENDING")) {
                            status.setText("PROCESSING");
                            TicketFactory.update(getUsername(), getPassword(), Integer.parseInt(status.getId()),"PROCESSING");
                        }
                            
                        else
                            status.setText("COMPLETED");
                        // TODO: Change data.
                    }
                });
                Label type = new Label((String) ticketObject.get("type"));
                Label orderId = new Label(String.valueOf((Long) ticketObject.get("orderid")));
                Label phoneNum = new Label((String) ticketObject.get("phonenum"));
                Label name = new Label((String) ticketObject.get("name"));
                Label dateReceived = new Label((String) ticketObject.get("datereceived"));
                // Label dateEstimated = new Label((String) ticketObject.get("dateEstimated"));

                status.setAlignment(Pos.TOP_CENTER);
                type.setAlignment(Pos.TOP_CENTER);
                orderId.setAlignment(Pos.TOP_CENTER);
                phoneNum.setAlignment(Pos.TOP_CENTER);
                name.setAlignment(Pos.TOP_CENTER);
                dateReceived.setAlignment(Pos.TOP_CENTER);

                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setValignment(VPos.TOP);
                ticketContainer.getRowConstraints().add(new RowConstraints());
                ticketContainer.getRowConstraints().set(i + 1, rowConstraints);
                ticketContainer.add(orderId, 0, i + 1);
                ticketContainer.add(type, 1, i + 1);
                ticketContainer.add(name, 2, i + 1);
                ticketContainer.add(phoneNum, 3, i + 1);
                ticketContainer.add(dateReceived, 4, i + 1);
                ticketContainer.add(status, 5, i + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSignoutClick(ActionEvent event) {
        DBUtils.changeScene(event, "Login.fxml", "Login", null, null);
    }
    @Override
    public void setUsername(String username) {
        super.setUsername(username);
        usernameProfile.setText(username);
        displayDashboard();
    }
}
