package laundryattendant.laundryticket;

import java.time.LocalDate;

import types.*;

public class LaundryTicket implements Ticket {
    private static int orderNum = 1;

    private Status status = Status.PENDING;
    private final Type type;
    private final String phoneNum;
    private final int orderId;
    private final double price;
    private final String name;
    private final LocalDate dateReceived;
    private LocalDate dateExtimated;
    private String username;
    private String comment;

    /*
     * ATTRIBUTE LIST:
     * enum STATUS
     * enum TYPE(USER INPUT)
     * String PHONENUM(USER INPUT)
     * int ORDERID
     * double PRICE
     * string NAME(USER INPUT)
     * date DATERECEIVED
     * date DATEEXTIMATED(GET FROM DATABASE)
     * String USERNAME
     * String COMMENT
     */
    LaundryTicket(Type type, String phoneNum, String name, String username, LocalDate dateEstimate, String comment,double price) {

        // Assigning type and price
        this.type = type;
        this.price = price;
        // switch (type) {
        //     case SHIRTS:
        //         price = 4.50;
        //         break;
        //     case POLO:
        //         price = 4.50;
        //         break;
        //     case BLOUSE:
        //         price = 9.95;
        //         break;
        //     case SWEATHER:
        //         price = 11.95;
        //         break;
        //     case JACKET:
        //         price = 11.95;
        //         break;
        //     case VEST:
        //         price = 11.95;
        //         break;
        //     case PANTS:
        //         price = 11.95;
        //         break;
        //     case SKIRT:
        //         price = 9.95;
        //         break;
        //     case SHORTS:
        //         price = 9.95;
        //         break;
        //     case CASUALDRESS:
        //         price = 16.95;
        //         break;
        //     case FORMALDRESS:
        //         price = 23.90;
        //         break;
        //     case COAT:
        //         price = 11.95;
        //         break;
        //     case FULLSUIT:
        //         price = 23.90;
        //         break;
        //     case BATHMAT:
        //         price = 5.00;
        //         break;
        //     case BLANKET:
        //         price = 15.00;
        //         break;
        //     case COVER:
        //         price = 15.00;
        //         break;
        //     default:
        //         price = 8.00;
        //         break;

        // }

        // Assigning phoneNum
        this.phoneNum = phoneNum;

        // Assigning orderId
        orderId = orderNum;
        orderNum++;

        // Assigning name
        this.name = name;

        // Assigning date received
        dateReceived = LocalDate.now();
        // Assigning date estimated
        dateExtimated = dateEstimate;
        
        // Assigning comment
        this.comment = comment;
        // Assigning username
        this.username = username;

    }

    public void updateStatus() {
        switch (status) {
            case PENDING:
                status = Status.PROCESSING;
                break;
            default:
                status = Status.COMPLETED;
                break;
        }
    }

    public Status getStatus() {
        return status;
    }

    public Type getType() {
        return type;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public int getOrderID() {
        return orderId;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateRecieved() {
        return dateReceived;
    }

    public LocalDate getDateExtimated() {
        return dateExtimated;
    }

    public String getUsername() {
        return username;
    }

    public String getComment(){
        return comment;
    }

}
