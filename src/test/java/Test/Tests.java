package Test;

import org.junit.jupiter.api.Test;

import laundryattendant.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Tests {

    @Test
    public void testHelloWorld() {
        assertEquals("Hello World", Main.hello());
    }

    @Test
    public void testIsTicketCreated() {
        TicketFactory ticketFactory = new TicketFactory();
        ticketFactory.makeTicket(1, "3221241234", "Jone Doe");

        // //is size of laundry ticket 1
        assertEquals(1, ticketFactory.getSize());

        // is instance created
        Ticket ticket = ticketFactory.getTickets().get(0);
        assertTrue(ticket instanceof LaundryTicket);

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
}