package laundryattendant.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TicketController{
    private String username;
    private String type;
    private String status;
    private String phonenum;
    private String name;
    private String price;
    private String date;
    @FXML
    public Label typeLabel;
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
    public void setAll(String type, String status, String phonenum, String name, 
    String price, String date)
    {
        this.type = type;
        this.status = status;
        this.phonenum = phonenum;
        this.name = name;
        this.price = price;
        this.date = date;
        typeLabel.setText(type);
        statusLabel.setText(status);
        phonenumLabel.setText(String.format("(%s)-%s-%s", phonenum.substring(0,3),phonenum.substring(3,6),phonenum.substring(6)));
        nameLabel.setText(name);
        priceLabel.setText("$"+price);
        dateLabel.setText(date);
    }
}
