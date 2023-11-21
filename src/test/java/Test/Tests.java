package Test;

import org.junit.jupiter.api.Test;

import laundryattendant.*;
import laundryattendant.laundryticket.LaundryTicket;
import laundryattendant.laundryticket.Ticket;
import laundryattendant.laundryticket.TicketFactory;
import laundryattendant.registernlogin.Form;
import laundryattendant.registernlogin.FormBuilder;
import laundryattendant.registernlogin.RegisterForm;
import types.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
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
        ticketFactory.makeTicket(1, "3221241234", "Jone Doe", null);

        // is instance created
        Ticket ticket = ticketFactory.getTicket();
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
        assertThrows(Error.class,()->ticketFactory.makeTicket(-1,"3221241234","Jone Doe",null));
        
        //type number not available(99)
        assertThrows(Error.class,()->ticketFactory.makeTicket(99,"3221241234","Jone Doe",null));

        //phonenum less than 10 digit
        assertThrows(Error.class,()->ticketFactory.makeTicket(1,"32212","Jone Doe",null));

        //phonenum bigger than 10 digit
        assertThrows(Error.class,()->ticketFactory.makeTicket(1,"322123412345","Jone Doe",null));

        //phonenum not digit
        assertThrows(Error.class,()->ticketFactory.makeTicket(1,"322123123L","Jone Doe",null));

        //name is empty
        assertThrows(Error.class,()->ticketFactory.makeTicket(1,"3221231234","  ",null));

        //name is empty 2
        assertThrows(Error.class,()->ticketFactory.makeTicket(1,"3221231234","",null));
        
        //name has more than 50 characters
        assertThrows(Error.class,()->ticketFactory.makeTicket(1,"3221231234","Averylongcharacterthatcontainsveryveryveryverylooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooongword",null));


    }

    @Test
    public void testRegister() {
        LocalDate now = LocalDate.of(2000,Month.JANUARY,22);
        Form registerForm = FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", "daneKiller", now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge", "1234123412341234", "123");
        // is instance created
        assertTrue(registerForm instanceof RegisterForm);

        //are all attributes correct
        assertEquals("Jone Doe",registerForm.getName());
        assertEquals("1234@gmail.com",registerForm.getEmail());
        assertEquals("12341234aA",((RegisterForm)registerForm).getPassword());
        assertEquals("daneKiller",((RegisterForm)registerForm).getUsername());
        assertEquals(LocalDate.of(2000,Month.JANUARY,22),now);
        assertEquals(Gender.MALE,registerForm.getGender());
        assertEquals("123 st.", registerForm.getAddress());
        assertEquals("Whats your favorite pet", registerForm.getSecurityQuestion());
        assertEquals("doge", ((RegisterForm)registerForm).getSecurityAnswer());
        assertEquals("1234123412341234", ((RegisterForm)registerForm).getBankCard());
        assertEquals("123", ((RegisterForm)registerForm).getCVV());
    }

    @Test
    public void testRegistConstraints(){
        LocalDate now = LocalDate.now();
        // is parameters blank or null
        assertThrows(Error.class,()->FormBuilder.makeForm("", "", "", "", "", now, "2000-11-12", "", "", "", "", "", ""));

        // is firstname wrong(0 or >25)
        assertThrows(Error.class, ()->FormBuilder.makeForm("", "Doe", "1234@gmail.com", "12341234aA", "daneKiller", now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge", "1234123412341234", "123"));
        assertThrows(Error.class, ()->FormBuilder.makeForm("    ", "Doe", "1234@gmail.com", "12341234aA", "daneKiller", now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge", "1234123412341234", "123"));
        assertThrows(Error.class, ()->FormBuilder.makeForm("loooooooooooooooooooooooooooooong", "Doe", "1234@gmail.com", "12341234aA", "daneKiller", now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge", "1234123412341234", "123"));        

        // is lastname wrong(0 or >25)
        assertThrows(Error.class, ()->FormBuilder.makeForm("Jone", "", "1234@gmail.com", "12341234aA", "daneKiller", now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge", "1234123412341234", "123"));
        assertThrows(Error.class, ()->FormBuilder.makeForm("Jone", "    ", "1234@gmail.com", "12341234aA", "daneKiller", now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge", "1234123412341234", "123"));
        assertThrows(Error.class, ()->FormBuilder.makeForm("Jone", "loooooooooooooooooooooooooooooong", "1234@gmail.com", "12341234aA", "daneKiller", now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge", "1234123412341234", "123"));        

        // is email wrong(.+@.+\.com)
        assertThrows(Error.class, ()->FormBuilder.makeForm("Jone", "Doe", "", "12341234aA", "daneKiller", now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge", "1234123412341234", "123"));
        assertThrows(Error.class, ()->FormBuilder.makeForm("Jone", "Doe", "     ", "12341234aA", "daneKiller", now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge", "1234123412341234", "123"));
        assertThrows(Error.class, ()->FormBuilder.makeForm("Jone", "Doe", "loooooooooooooooooooooooooooooong", "12341234aA", "daneKiller", now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge", "1234123412341234", "123"));

        // is password wrong(1.NOT 9-25 characters  2. NO digit  3. No uppercaseletter    4. NO lowercaseletter)
        assertThrows(Error.class, ()->FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "", "daneKiller", now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge", "1234123412341234", "123"));
        assertThrows(Error.class, ()->FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "aaaaaaaaaaaaaaaaaaaaaaaaaaa", "daneKiller", now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge", "1234123412341234", "123"));
        assertThrows(Error.class, ()->FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234123a", "daneKiller", now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge", "1234123412341234", "123"));
        assertThrows(Error.class, ()->FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234A", "daneKiller", now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge", "1234123412341234", "123"));

        // is username wrong
        assertThrows(Error.class, ()->FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", "", now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge", "1234123412341234", "123"));
        assertThrows(Error.class, ()->FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", " ", now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge", "1234123412341234", "123"));
        assertThrows(Error.class, ()->FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", "loooooooooooooooooooooooooooooong", now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge", "1234123412341234", "123"));
        
        // is phonenum wrong(1.NOT digit       2. NOT 10 digit)
        assertThrows(Error.class, ()->FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", "daneKiller", now, "Male", "aaaaaaaaaa", "123 st.", "Whats your favorite pet", "doge", "1234123412341234", "123"));
        assertThrows(Error.class, ()->FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", "daneKiller", now, "Male", "123412341234", "123 st.", "Whats your favorite pet", "doge", "1234123412341234", "123"));

        // is securityQuestion wrong
        assertThrows(Error.class, ()->FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", "daneKiller", now, "Male", "1231234123", "123 st.", "", "doge", "1234123412341234", "123"));
        assertThrows(Error.class, ()->FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", "daneKiller", now, "Male", "1231234123", "123 st.", "     ", "doge", "1234123412341234", "123"));
        assertThrows(Error.class, ()->FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", "daneKiller", now, "Male", "1231234123", "123 st.", "loooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong", "doge", "1234123412341234", "123"));
        
        // is securityAnswer wrong
        assertThrows(Error.class, ()->FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", "daneKiller", now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "", "1234123412341234", "123"));
        assertThrows(Error.class, ()->FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", "daneKiller", now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "      ", "1234123412341234", "123"));
        assertThrows(Error.class, ()->FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", "daneKiller", now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "loooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong", "1234123412341234", "123"));

        // is bankCard wrong
        assertThrows(Error.class, ()->FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", "daneKiller", now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge", "", "123"));
        assertThrows(Error.class, ()->FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", "daneKiller", now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge", "      ", "123"));
        assertThrows(Error.class, ()->FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", "daneKiller", now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge", "123412341234123412341234", "123"));

        // is cvv wrong
        assertThrows(Error.class, ()->FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", "daneKiller", now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge", "1234123412341234", "daa"));
        assertThrows(Error.class, ()->FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", "daneKiller", now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge", "1234123412341234", "1234"));
    }
}