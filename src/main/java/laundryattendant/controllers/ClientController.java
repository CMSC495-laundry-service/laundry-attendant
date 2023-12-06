package laundryattendant.controllers;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import laundryattendant.laundryticket.TicketFactory;
import laundryattendant.scene.DBUtils;

public class ClientController extends Controller {
    JSONArray data;

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
    private void handleSignoutClick(ActionEvent event) {

        DBUtils.changeScene(event, "Login.fxml", null, null, null);
    }

    private void displayDashboard() {
        container.getChildren().clear();

        for (int i = 0; i < data.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../scene/ticket.fxml"));
                Parent root = (Parent) loader.load();

                ClientTicketController controller = loader.getController();
                controller.setData((JSONObject) data.get(i));
                controller.setPassword(getPassword());
                controller.setUsername(getUsername());
                controller.setParentController(this);
                container.getChildren().add(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void refresh() {
        data = TicketFactory.getTickets(super.getUsername(), super.getPassword());
        addButton.setText("+");
        displayDashboard();
    }

    private void handleMouseClicked(MouseEvent event) {
        container.getChildren().clear();

        if (addButton.getText().equals("+")) {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("../scene/order.fxml"));
            Parent root;
            try {
                root = (Parent) loader.load();
                Controller controller = loader.getController();
                ((PayController) controller).setController(this);
                controller.setPassword(getPassword());
                controller.setUsername(getUsername());

                container.getChildren().add(root);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

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
        // retrieve data from database
        refresh();
    }
}
