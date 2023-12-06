package laundryattendant.controllers;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
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
                Hyperlink moredetailHLink = new Hyperlink("More Detail");
                moredetailHLink.setOnAction(statusEvent -> {
                    //new stage for ticket detail
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../scene/ticketForm.fxml"));
                    Parent root;
                    try {
                        root = (Parent) loader.load();
                        TicketController controller = loader.getController();
                        controller.setAll(String.valueOf((Long) ticketObject.get("orderid")),
                                (String) ticketObject.get("type"), (String) ticketObject.get("status"),
                                (String) ticketObject.get("phonenum"), (String) ticketObject.get("name"),
                                (String) ticketObject.get("price"),
                                (String) ticketObject.get("datereceived"), (String) ticketObject.get("address"),
                                (String) ticketObject.get("comment"));
                        Scene scene = new Scene(root, 450, 800);
                        Stage stage = new Stage();
                        stage.setTitle("Ticket");
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                });

                Label type = new Label((String) ticketObject.get("type"));
                Label status = new Label((String) ticketObject.get("status"));
                Label phoneNum = new Label((String) ticketObject.get("phonenum"));
                Label name = new Label((String) ticketObject.get("name"));
                // Label dateReceived = new Label((String) ticketObject.get("datereceived"));
                Label dateEstimated = new Label(((String) ticketObject.get("dateextimated")).substring(0, 10));

                moredetailHLink.setAlignment(Pos.TOP_CENTER);
                type.setAlignment(Pos.TOP_CENTER);
                status.setAlignment(Pos.TOP_CENTER);
                phoneNum.setAlignment(Pos.TOP_CENTER);
                name.setAlignment(Pos.TOP_CENTER);
                dateEstimated.setAlignment(Pos.TOP_CENTER);

                // set status color and EventListner
                status.setOnMouseClicked(statusEvent -> {
                    if (status.getText().equals("COMPLETED") || status.getText().equals("ACCEPTED"))
                        ;
                    else {
                        if (status.getText().equals("PENDING"))
                            status.setText("PROCESSING");
                        else
                            status.setText("COMPLETED");
                        TicketFactory.update(getUsername(), getPassword(),
                                Math.toIntExact((long) ticketObject.get("orderid")), status.getText());
                    }
                    renderStatus(status);
                });

                // set row constraints and add to ticketContainer
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setValignment(VPos.TOP);
                ticketContainer.getRowConstraints().add(new RowConstraints());
                ticketContainer.getRowConstraints().set(i + 1, rowConstraints);
                ticketContainer.add(renderStatus(status), 0, i + 1);
                ticketContainer.add(type, 1, i + 1);
                ticketContainer.add(name, 2, i + 1);
                ticketContainer.add(phoneNum, 3, i + 1);
                ticketContainer.add(dateEstimated, 4, i + 1);
                ticketContainer.add(moredetailHLink, 5, i + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // set Label color based on status
    private Label renderStatus(Label statusLabel) {
        String stylesheet = "-fx-padding: 2px;-fx-border-color: black;";
        if (statusLabel.getText().equals("ACCEPTED"))
            statusLabel.setStyle("-fx-text-fill: black; " + stylesheet);
        else if (statusLabel.getText().equals("COMPLETED")) {
            statusLabel.setStyle("-fx-text-fill: green; " + stylesheet);
        } else if (statusLabel.getText().equals("PROCESSING")) {
            statusLabel.setStyle("-fx-text-fill: blue; " + stylesheet);
        } else
            statusLabel.setStyle("-fx-text-fill: red; " + stylesheet);
        return statusLabel;
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
