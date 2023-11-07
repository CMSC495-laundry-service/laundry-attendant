/*

 * Classname
 * 
 * Version information
 *
 * Date
 * 
 * Copyright notice
 */
package laundryattendant;

import laundryattendant.*;
public class Main {
    public static String hello(){
        return ("Hello World");
    }
    public static void main(String[] args) {
        TicketFactory ticketFactory = new TicketFactory();
        ticketFactory.makeTicket(1,"3221231234","Jone Doe");
        
        // //is size of laundry ticket 1
        System.out.println(ticketFactory.getSize());

        //is instance created
        Ticket ticket =ticketFactory.getTickets().get(0);
        System.out.println(ticket instanceof LaundryTicket);
    }
}