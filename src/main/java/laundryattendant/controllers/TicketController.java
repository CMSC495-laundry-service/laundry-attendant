package laundryattendant.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class TicketController {
    @FXML
    public Label shirtLabel;
    @FXML
    public Label poloLabel;
    @FXML
    public Label blouserLabel;
    @FXML
    public Label sweatherLabel;
    @FXML
    public Label jacketLabel;
    @FXML
    public Label vestLabel;
    @FXML
    public Label pantsLabel;
    @FXML
    public Label skirtLabel;
    @FXML
    public Label shortsLabel;
    @FXML
    public Label casualDressLabel;
    @FXML
    public Label formalDressLabel;
    @FXML
    public Label coatLabel;
    @FXML
    public Label fullSuitLabel;
    @FXML
    public Label bathMatLabel;
    @FXML
    public Label blanketLabel;
    @FXML
    public Label coverLabel;
    @FXML
    public Label accessoryLabel;

    @FXML
    public Label statusLabel;
    @FXML
    public Label phonenumLabel;
    @FXML
    public Label nameLabel;
    @FXML
    public Label priceLabel;
    @FXML
    public Label dateLabel;
    @FXML
    public Label addressLabel;
    @FXML
    public Label orderIdLabel;
    @FXML
    public Label commentArea;

    public void setAll(String orderID, String type, String status, String phonenum, String name,
            String price, String date, String address, String comment) {
        orderIdLabel.setText(orderID);
        // selecting Label
        switch (type) {
            case "SHIRTS":
                shirtLabel.setText("1");
                break;
            case "POLO":
                poloLabel.setText("1");
                break;
            case "BLOUSE":
                blouserLabel.setText("1");
                break;
            case "SWEATHER":
                sweatherLabel.setText("1");
                break;
            case "JACKET":
                jacketLabel.setText("1");
                break;
            case "VEST":
                vestLabel.setText("1");
                break;
            case "PANTS":
                pantsLabel.setText("1");
                break;
            case "SKIRT":
                skirtLabel.setText("1");
                break;
            case "SHORTS":
                shortsLabel.setText("1");
                break;
            case "CASUALDRESS":
                casualDressLabel.setText("1");
                break;
            case "FORMALDRESS":
                formalDressLabel.setText("1");
                break;
            case "COAT":
                coatLabel.setText("1");
                break;
            case "FULLSUIT":
                fullSuitLabel.setText("1");
                break;
            case "BATHMAT":
                bathMatLabel.setText("1");
                break;
            case "BLANKET":
                blanketLabel.setText("1");
                break;
            case "COVER":
                coverLabel.setText("1");
                break;
            default:
                accessoryLabel.setText("1");
                break;
        }
        statusLabel.setText(status);
        phonenumLabel.setText(
                String.format("(%s)-%s-%s", phonenum.substring(0, 3), phonenum.substring(3, 6), phonenum.substring(6)));
        nameLabel.setText(name);
        priceLabel.setText("$" + price);
        dateLabel.setText(date.substring(0, 10));
        addressLabel.setText(address);
        commentArea.setText(comment);
    }
}
