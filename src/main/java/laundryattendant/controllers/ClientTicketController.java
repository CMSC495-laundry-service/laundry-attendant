package laundryattendant.controllers;

import java.io.IOException;

import org.json.simple.JSONObject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import laundryattendant.laundryticket.TicketFactory;

public class ClientTicketController extends Controller {
    @FXML
    private Label typeLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label phonenumLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Hyperlink moredetailHLink;

    public void setData(JSONObject object) {
        // set label data
        String phoneNum = (String) object.get("phonenum");
        String phoneNumFormat = String.format("(%s)-%s-%s", phoneNum.substring(0, 3), phoneNum.substring(3, 6),
                phoneNum.substring(6));
        typeLabel.setText((String) object.get("type"));
        statusLabel.setText((String) object.get("status"));
        nameLabel.setText((String) object.get("name"));
        phonenumLabel.setText(phoneNumFormat);
        priceLabel.setText("$" + (String) object.get("price"));
        dateLabel.setText(((String) object.get("datereceived")).substring(0, 10));

        moredetailHLink.setOnAction(statusEvent -> {
            Parent parent = getDetailForm(object);
            if (parent == null)
                parent = new Label("Ticket is not found");
            Scene scene = new Scene(parent, 450, 800);
            Stage stage = new Stage();
            stage.setTitle("Ticket");
            stage.setScene(scene);
            stage.show();

        });

        // if status is completed, send a notification to the client
        if (statusLabel.getText().equals("COMPLETED")) {
            // send notification
            sendNotification(object);
        }

    }

    private Parent getDetailForm(JSONObject object) {
        FXMLLoader detailLoader = new FXMLLoader(getClass().getResource("../scene/ticketForm.fxml"));
        Parent detailNode = null;
        try {
            detailNode = (Parent) detailLoader.load();
            TicketController controller = detailLoader.getController();
            controller.setAll(String.valueOf((Long) object.get("orderid")),
                    (String) object.get("type"), (String) object.get("status"),
                    (String) object.get("phonenum"), (String) object.get("name"),
                    (String) object.get("price"),
                    (String) object.get("datereceived"), (String) object.get("address"),
                    (String) object.get("comment"));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return detailNode;
    }

    private void sendNotification(JSONObject object) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);

        Parent parent = getDetailForm(object);
        if (parent == null)
            parent = new Label("Ticket is not found");
        

        
        Button okButton = new Button("Accept");
        okButton.setOnAction(event -> {
            TicketFactory.update(getUsername(), getPassword(), Math.toIntExact((Long) object.get("orderid")), "ACCEPTED");
            //reference from scene
            Stage stage = (Stage) okButton.getScene().getWindow();
            stage.close();
        });

        vBox.getChildren().addAll(parent, okButton);
        Stage stage = new Stage();
        Scene scene = new Scene(vBox, 450, 900);
        stage.setTitle("Notification");
        stage.setScene(scene);
        stage.show();
    }
}
