package laundryattendant.laundryticket;
import java.time.LocalDate;
import java.util.Date;

import types.*;

public class LaundryTicket implements Ticket{
    private static int orderNum =1;

    private Status status = Status.PENDING;
    private final Type type;
    private final String phoneNum;
    private final int orderId;
    private final double price;
    private final String name;
    private final LocalDate dateReceived;
    private LocalDate dateExtimated;
    private String username;
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
     */
    LaundryTicket(Type type, String phoneNum, String name, String username){
        
        //Assigning type and price
        this.type = type;
        switch (type) {
            case RAYON:
                price = 15.00;
                break;
            case SILK:
                price = 30.00;
                break;
            case LEATHER:
                price = 45.00;
                break;
            case SUEDE:
                price = 13.50;
                break;
            case VELVET:
                price = 50.00;
                break;
            default:
                price = 75.00;
        }

        //Assigning phoneNum
        this.phoneNum = phoneNum;

        //Assigning orderId
        orderId =orderNum;
        orderNum++;

        //Assigning name
        this.name = name;

        //Assigning date received
        dateReceived = LocalDate.now();
        dateExtimated = dateReceived.plusDays(5);


        this.username = username;
    }
    public void updateStatus(){
        switch(status) {
            case PENDING:
                status = Status.PROCESSING;
                break;
            default:
                status = Status.COMPLETED;
                break; 
        }
    }
    public Status getStatus(){
        return status;
    }
    public Type getType() {
        return type;
    }
    public String getPhoneNum(){
        return phoneNum;
    }
    public int getOrderID(){
        return orderId;
    }
    public double getPrice(){
        return price;
    }
    public String getName(){
        return name;
    }
    public LocalDate getDateRecieved(){
        return dateReceived;
    }
    public LocalDate getDateExtimated(){
        return dateExtimated;
    }
    public String getUsername() {
        return username;
    }

}
