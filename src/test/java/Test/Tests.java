package Test;

import org.junit.jupiter.api.Test;

import laundryattendant.*;
import laundryattendant.laundryticket.LaundryTicket;
import laundryattendant.laundryticket.Ticket;
import laundryattendant.laundryticket.TicketFactory;
import laundryattendant.registernlogin.RegisterForm;
import types.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Tests {

    @Test
    public void testHelloWorld() {
        assertEquals("Hello World", Main.hello());
    }

    @Test
    public void testIsTicketCreated() {
        TicketFactory ticketFactory = new TicketFactory();
        ticketFactory.makeTicket(1, "3221241234", "Jone Doe");

        // is size of laundry ticket 1
        assertEquals(1, ticketFactory.getSize());

        // is instance created
        Ticket ticket = ticketFactory.getTickets().get(0);
        assertTrue(ticket instanceof LaundryTicket);

        // are all attributes correct
        assertEquals("Jone Doe",ticket.getName());
        assertEquals(1,ticket.getOrderID());
        assertEquals("3221241234",ticket.getPhoneNum());
        assertEquals(15.00,ticket.getPrice());
        assertEquals(Status.PENDING,ticket.getStatus());
        assertEquals(Type.RAYON,ticket.getType());

        // is status updated
        ticket.updateStatus();
        assertEquals(Status.PROCESSING, ticket.getStatus());
        ticket.updateStatus();
        assertEquals(Status.COMPLETED, ticket.getStatus());
        ticket.updateStatus();
        assertEquals(Status.COMPLETED, ticket.getStatus());   
    }

    @Test
    public void testTicketConstraints(){
        
       TicketFactory ticketFactory = new TicketFactory();

       //type number not available(-1)
        assertThrows(Error.class,()->ticketFactory.makeTicket(-1,"3221241234","Jone Doe"));
        
        //type number not available(99)
        assertThrows(Error.class,()->ticketFactory.makeTicket(99,"3221241234","Jone Doe"));

        //phonenum less than 10 digit
        assertThrows(Error.class,()->ticketFactory.makeTicket(1,"32212","Jone Doe"));

        //phonenum bigger than 10 digit
        assertThrows(Error.class,()->ticketFactory.makeTicket(1,"322123412345","Jone Doe"));

        //phonenum not digit
        assertThrows(Error.class,()->ticketFactory.makeTicket(1,"322123123L","Jone Doe"));

        //name is empty
        assertThrows(Error.class,()->ticketFactory.makeTicket(1,"3221231234","  "));

        //name is empty 2
        assertThrows(Error.class,()->ticketFactory.makeTicket(1,"3221231234",""));
        
        //name has more than 50 characters
        assertThrows(Error.class,()->ticketFactory.makeTicket(1,"3221231234","Averylongcharacterthatcontainsveryveryveryverylooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooongword"));


    }

    @Test
    public void testRegister() {
        // RegisterForm registerForm = new RegisterForm("Jone","Doe", "admin@admin.com", "1234", "Admin", new GregorianCalendar(2000,Calendar.JULY,22).getTime(), Gender.MALE, "1234243535", "123 sample ave. Austin, TX, 92394", "What's the name of your pet", "guai", "1234123412341234", 333);
    }
}