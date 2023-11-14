package laundryattendant.laundryticket;

import java.util.Date;

import types.*;

public interface Ticket {
    public void updateStatus();

    public Status getStatus();

    public Type getType();

    public String getPhoneNum();

    public int getOrderID();

    public double getPrice();

    public String getName();

    public Date getDateRecieved();
}
