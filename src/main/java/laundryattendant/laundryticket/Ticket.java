package laundryattendant.laundryticket;

import java.time.LocalDate;

import types.*;

public interface Ticket {
    public void updateStatus();

    public Status getStatus();

    public Type getType();

    public String getPhoneNum();

    public int getOrderID();

    public double getPrice();

    public String getName();

    public LocalDate getDateRecieved();

    public LocalDate getDateExtimated();

    public String getUsername();

    public String getComment();
}
