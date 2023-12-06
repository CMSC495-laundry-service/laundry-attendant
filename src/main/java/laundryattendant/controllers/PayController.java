package laundryattendant.controllers;

import java.text.DecimalFormat;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import laundryattendant.laundryticket.Ticket;
import laundryattendant.laundryticket.TicketFactory;
import types.Type;

public class PayController extends Controller{
    private ClientController controller;
    @FXML
    public AnchorPane container;
    @FXML
    public DatePicker date;
    @FXML
    public ComboBox<Type> type;
    @FXML
    public Label priceLabel;
    @FXML
    public TextField nameField;
    @FXML
    public TextField phonenumField;
    @FXML
    public HBox alert;
    @FXML
    public Label alertMessage;
    @FXML
    public TextArea commentField;

    @FXML
    public void selectType(ActionEvent event) {
        DecimalFormat df = new DecimalFormat("#.00");
        double itemPrice = TicketFactory.getTypePrice(type.getValue());
        
        priceLabel.setText(String.valueOf(df.format(itemPrice)));
    }
    @FXML
    public void initialize() {
        ObservableList<Type> types = FXCollections.observableArrayList(Type.values());
        type.setItems(types);
        date.setDayCellFactory(datepicker->{
            return new DateCell() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    
                    //disable all dates before current date
                    if (item.isBefore(LocalDate.now().plusDays(1))) {
                        setDisable(true);
                        setStyle("-fx-background-color: #EEEEEE;");
                    }
                    //disable all dates after 14 days
                    if (item.isAfter(LocalDate.now().plusDays(14))) {
                        setDisable(true);
                        setStyle("-fx-background-color: #EEEEEE;");
                    }
                }
            };
        });
    }

    // @FXML
    // public void dateSelection(ActionEvent event) {
    //     System.out.println("dateSelection");
    //     LocalDate current = LocalDate.now();
    //     // if current date is bigger set datepicker to current date
    //     final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
    //         @Override
    //         public DateCell call(final DatePicker datePicker) {
    //             return new DateCell() {
    //                 @Override
    //                 public void updateItem(LocalDate item, boolean empty) {
    //                     super.updateItem(item, empty);
    //                     //localdate to date
                        
    //                     if (item.isBefore(current.plusDays(1))) {
    //                         setDisable(true);
    //                         setStyle("-fx-background-color: #EEEEEE;");
    //                     }
    //                 }
    //             };
    //         }
    //     };
    // }

    @FXML
    public void payEvent(ActionEvent event) {
        try {
            if (date.getValue() == null) throw new Error("Please select a date");
            Ticket ticket = TicketFactory.makeTicket(type.getValue(),phonenumField.getText(),nameField.getText(),getUsername(),date.getValue(),commentField.getText());
            TicketFactory.append(ticket, getPassword());
            container.getChildren().clear();
            Label successMLabel = new Label("Your order has been placed!");
            successMLabel.setStyle("-fx-font-size: 32px;");
            container.getChildren().add(successMLabel);
            controller.refresh();

        } catch (Error e) { 
            alertMessage.setText(e.getMessage());
            alert.setVisible(true);
        } catch (NullPointerException e) {
            if (type.getValue() == null) {
                alertMessage.setText("Please select a type");
                alert.setVisible(true);
            }
        }
    }
    public void setController(Controller controller) {
        this.controller = (ClientController)controller;
    }
}
