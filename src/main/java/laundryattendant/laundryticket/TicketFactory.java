package laundryattendant.laundryticket;

import types.Type;

import java.util.ArrayList;

import laundryattendant.laundryticket.LaundryTicket;
public class TicketFactory {
    private ArrayList<Ticket> list = new ArrayList<>();

    public void makeTicket(int type,String phonenum,String name){
        //Input validation
        /*
         * Type: 1 <= type <= 5
         * phoneNum: long, phoneNum.length = 10
         * name: varchar(50)
         * if wrong input throw error
         */
        Type cleanType;
        switch (type) {
            case 1:
                cleanType = Type.RAYON;
                break;
            case 2:
                cleanType = Type.SILK;
                break;
            case 3:
                cleanType = Type.LEATHER;
                break;
            case 4:
                cleanType = Type.SUEDE;
                break;
            case 5:
                cleanType = Type.VELVET;
                break;
            case 6:
                cleanType = Type.OTHERS;
                break;
            default:
                throw new Error();
        }
        //log10 and round up to get length of int
        if (phonenum.length() != 10)
            throw new Error();
        for (int i = 0; i < 10; i++) {
            if (!Character.isDigit(phonenum.charAt(i)))
                throw new Error();
        }
        
        // length of name.length > 50 or empty
        if (name.trim().length() == 0)
            throw new Error();
        else if (name.length() > 50)
            throw new Error();

        //assign arguments
        list.add(new LaundryTicket(cleanType, phonenum,name));
    }
    public ArrayList<Ticket> getTickets(){
        return list;
    }
    public int getSize() {
        return list.size();
    }
}
