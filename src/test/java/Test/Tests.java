package Test;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import io.github.cdimascio.dotenv.Dotenv;
import laundryattendant.*;
import laundryattendant.laundryticket.LaundryTicket;
import laundryattendant.laundryticket.Ticket;
import laundryattendant.laundryticket.TicketFactory;
import laundryattendant.registernlogin.FormBuilder;
import laundryattendant.registernlogin.LoginForm;
import laundryattendant.registernlogin.RegisterForm;
import types.*;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;

public class Tests {

        @Test
        public void testHelloWorld() {
                assertEquals("Hello World", Main.hello());
        }

        @Test
        public void testIsTicketCreated() {
                // is instance created
                Ticket ticket = TicketFactory.makeTicket(Type.CASUALDRESS, "3221241234", "Jone Doe", null,LocalDate.now().plusDays(5),"This is the test comment");
                assertTrue(ticket instanceof LaundryTicket);

                // are all attributes correct
                assertEquals("Jone Doe", ticket.getName());
                assertEquals(1, ticket.getOrderID());
                assertEquals("3221241234", ticket.getPhoneNum());
                assertEquals(16.95, ticket.getPrice());
                assertEquals(Status.PENDING, ticket.getStatus());
                assertEquals(Type.CASUALDRESS, ticket.getType());
                assertEquals("This is the test comment", ticket.getComment());

                // is status updated
                ticket.updateStatus();
                assertEquals(Status.PROCESSING, ticket.getStatus());
                ticket.updateStatus();
                assertEquals(Status.COMPLETED, ticket.getStatus());
                ticket.updateStatus();
                assertEquals(Status.COMPLETED, ticket.getStatus());
        }

        @Test
        public void testTicketConstraints() {

                // type selection can be only Types enum. No other type is allowed

                // phonenum less than 10 digit
                assertThrows(Error.class, () -> TicketFactory.makeTicket(Type.CASUALDRESS, "32212", "Jone Doe", null,LocalDate.now().plusDays(5), null));

                // phonenum bigger than 10 digit
                assertThrows(Error.class, () -> TicketFactory.makeTicket(Type.CASUALDRESS, "322123412345", "Jone Doe", null,LocalDate.now().plusDays(5),null));

                // phonenum not digit
                assertThrows(Error.class, () -> TicketFactory.makeTicket(Type.CASUALDRESS, "322123123L", "Jone Doe", null,LocalDate.now().plusDays(5), null));

                // name is empty
                assertThrows(Error.class, () -> TicketFactory.makeTicket(Type.CASUALDRESS, "3221231234", "  ", null,LocalDate.now().plusDays(5), null));

                // name is empty 2
                assertThrows(Error.class, () -> TicketFactory.makeTicket(Type.CASUALDRESS, "3221231234", "", null,LocalDate.now().plusDays(5), null));

                // name has more than 50 characters
                assertThrows(Error.class, () -> TicketFactory.makeTicket(Type.CASUALDRESS, "3221231234",
                                "Averylongcharacterthatcontainsveryveryveryverylooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooongword",
                                null,LocalDate.now().plusDays(5), null));
                
                // dateEstimate can be only within 14 days

                // comment has more than 100 characters
                assertThrows(Error.class, () -> TicketFactory.makeTicket(Type.CASUALDRESS, "3221231234", "Jone Doe",
                                null,LocalDate.now().plusDays(5), "Averylongcharacterthatcontainsveryveryveryverylooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooogword"));
        }

        @Test
        public void testRegister() {
                LocalDate now = LocalDate.of(2000, Month.JANUARY, 22);
                RegisterForm registerForm = FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA",
                                "daneKiller",
                                now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge",
                                "1234123412341234", "123");
                // is instance created
                assertTrue(registerForm instanceof RegisterForm);

                // are all attributes correct
                assertEquals("Jone", registerForm.getFirstName());
                assertEquals("Doe", registerForm.getLastName());
                assertEquals("1234@gmail.com", registerForm.getEmail());
                assertEquals("12341234aA", registerForm.getPassword());
                assertEquals("daneKiller", registerForm.getUsername());
                assertEquals(LocalDate.of(2000, Month.JANUARY, 22), now);
                assertEquals(Gender.MALE, registerForm.getGender());
                assertEquals("123 st.", registerForm.getAddress());
                assertEquals("Whats your favorite pet", registerForm.getSecurityQuestion());
                assertEquals("doge", ((RegisterForm) registerForm).getSecurityAnswer());
                assertEquals("1234123412341234", ((RegisterForm) registerForm).getBankCard());
                assertEquals("123", ((RegisterForm) registerForm).getCVV());
        }

        @Test
        public void testRegistConstraints() {
                LocalDate now = LocalDate.now();
                // is parameters blank or null
                assertThrows(Error.class,
                                () -> FormBuilder.makeForm("", "", "", "", "", now, "2000-11-12", "", "", "", "", "",
                                                ""));

                // is firstname wrong(0 or >25)
                assertThrows(Error.class,
                                () -> FormBuilder.makeForm("", "Doe", "1234@gmail.com", "12341234aA", "daneKiller",
                                                now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge",
                                                "1234123412341234", "123"));
                assertThrows(Error.class,
                                () -> FormBuilder.makeForm("    ", "Doe", "1234@gmail.com", "12341234aA", "daneKiller",
                                                now, "Male",
                                                "1231234123", "123 st.", "Whats your favorite pet", "doge",
                                                "1234123412341234", "123"));
                assertThrows(Error.class,
                                () -> FormBuilder.makeForm("loooooooooooooooooooooooooooooong", "Doe", "1234@gmail.com",
                                                "12341234aA",
                                                "daneKiller", now, "Male", "1231234123", "123 st.",
                                                "Whats your favorite pet", "doge",
                                                "1234123412341234", "123"));

                // is lastname wrong(0 or >25)
                assertThrows(Error.class,
                                () -> FormBuilder.makeForm("Jone", "", "1234@gmail.com", "12341234aA", "daneKiller",
                                                now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge",
                                                "1234123412341234", "123"));
                assertThrows(Error.class,
                                () -> FormBuilder.makeForm("Jone", "    ", "1234@gmail.com", "12341234aA", "daneKiller",
                                                now, "Male",
                                                "1231234123", "123 st.", "Whats your favorite pet", "doge",
                                                "1234123412341234", "123"));
                assertThrows(Error.class,
                                () -> FormBuilder.makeForm("Jone", "loooooooooooooooooooooooooooooong",
                                                "1234@gmail.com", "12341234aA",
                                                "daneKiller", now, "Male", "1231234123", "123 st.",
                                                "Whats your favorite pet", "doge",
                                                "1234123412341234", "123"));

                // is email wrong(.+@.+\.com)
                assertThrows(Error.class,
                                () -> FormBuilder.makeForm("Jone", "Doe", "", "12341234aA", "daneKiller", now, "Male",
                                                "1231234123", "123 st.", "Whats your favorite pet", "doge",
                                                "1234123412341234", "123"));
                assertThrows(Error.class,
                                () -> FormBuilder.makeForm("Jone", "Doe", "     ", "12341234aA", "daneKiller", now,
                                                "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge",
                                                "1234123412341234", "123"));
                assertThrows(Error.class,
                                () -> FormBuilder.makeForm("Jone", "Doe", "loooooooooooooooooooooooooooooong",
                                                "12341234aA",
                                                "daneKiller", now, "Male", "1231234123", "123 st.",
                                                "Whats your favorite pet", "doge",
                                                "1234123412341234", "123"));

                // is password wrong(1.NOT 9-25 characters 2. NO digit 3. No uppercaseletter 4.
                // NO lowercaseletter)
                assertThrows(Error.class,
                                () -> FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "", "daneKiller", now,
                                                "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge",
                                                "1234123412341234", "123"));
                assertThrows(Error.class,
                                () -> FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com",
                                                "aaaaaaaaaaaaaaaaaaaaaaaaaaa", "daneKiller",
                                                now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge",
                                                "1234123412341234",
                                                "123"));
                assertThrows(Error.class,
                                () -> FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234123a",
                                                "daneKiller", now, "Male",
                                                "1231234123", "123 st.", "Whats your favorite pet", "doge",
                                                "1234123412341234", "123"));
                assertThrows(Error.class,
                                () -> FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234A", "daneKiller",
                                                now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge",
                                                "1234123412341234", "123"));

                // is username wrong
                assertThrows(Error.class,
                                () -> FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", "", now,
                                                "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge",
                                                "1234123412341234", "123"));
                assertThrows(Error.class,
                                () -> FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", " ", now,
                                                "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge",
                                                "1234123412341234", "123"));
                assertThrows(Error.class,
                                () -> FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA",
                                                "loooooooooooooooooooooooooooooong", now, "Male", "1231234123",
                                                "123 st.",
                                                "Whats your favorite pet", "doge", "1234123412341234", "123"));

                // is phonenum wrong(1.NOT digit 2. NOT 10 digit)
                assertThrows(Error.class,
                                () -> FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", "daneKiller",
                                                now, "Male",
                                                "aaaaaaaaaa", "123 st.", "Whats your favorite pet", "doge",
                                                "1234123412341234", "123"));
                assertThrows(Error.class,
                                () -> FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", "daneKiller",
                                                now, "Male",
                                                "123412341234", "123 st.", "Whats your favorite pet", "doge",
                                                "1234123412341234", "123"));

                // is securityQuestion wrong
                assertThrows(Error.class, () -> FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA",
                                "daneKiller", now, "Male", "1231234123", "123 st.", "", "doge", "1234123412341234",
                                "123"));
                assertThrows(Error.class, () -> FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA",
                                "daneKiller", now, "Male", "1231234123", "123 st.", "     ", "doge", "1234123412341234",
                                "123"));
                assertThrows(Error.class,
                                () -> FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", "daneKiller",
                                                now, "Male",
                                                "1231234123", "123 st.",
                                                "loooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong",
                                                "doge",
                                                "1234123412341234", "123"));

                // is securityAnswer wrong
                assertThrows(Error.class,
                                () -> FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", "daneKiller",
                                                now, "Male",
                                                "1231234123", "123 st.", "Whats your favorite pet", "",
                                                "1234123412341234", "123"));
                assertThrows(Error.class,
                                () -> FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", "daneKiller",
                                                now, "Male",
                                                "1231234123", "123 st.", "Whats your favorite pet", "      ",
                                                "1234123412341234", "123"));
                assertThrows(Error.class,
                                () -> FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", "daneKiller",
                                                now, "Male",
                                                "1231234123", "123 st.", "Whats your favorite pet",
                                                "loooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong",
                                                "1234123412341234", "123"));

                // is bankCard wrong
                assertThrows(Error.class, () -> FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA",
                                "daneKiller", now, "Male", "1231234123", "123 st.", "Whats your favorite pet", "doge",
                                "", "123"));
                assertThrows(Error.class,
                                () -> FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", "daneKiller",
                                                now, "Male",
                                                "1231234123", "123 st.", "Whats your favorite pet", "doge", "      ",
                                                "123"));
                assertThrows(Error.class,
                                () -> FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", "daneKiller",
                                                now, "Male",
                                                "1231234123", "123 st.", "Whats your favorite pet", "doge",
                                                "123412341234123412341234", "123"));

                // is cvv wrong
                assertThrows(Error.class,
                                () -> FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", "daneKiller",
                                                now, "Male",
                                                "1231234123", "123 st.", "Whats your favorite pet", "doge",
                                                "1234123412341234", "daa"));
                assertThrows(Error.class,
                                () -> FormBuilder.makeForm("Jone", "Doe", "1234@gmail.com", "12341234aA", "daneKiller",
                                                now, "Male",
                                                "1231234123", "123 st.", "Whats your favorite pet", "doge",
                                                "1234123412341234", "1234"));
        }

        //if test failed, check if the .env file DB_STRING is correct
        @Test
        public void testDBString() {
                // loading dotenv
                Dotenv dotenv = Dotenv.load();
                try {
                        // get from .env file in resources
                        URL url = new URL(dotenv.get("URL"));
                        // connecting to http
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK)
                                fail("Status Code: " + conn.getResponseCode());
                } catch (Exception e) {
                        fail("Connection Failed");
                }
        }

        @Test
        public void testRegisterDB() {
                try {
                        RegisterForm registerForm = FormBuilder.makeForm("Jone", "Doe", "123@example.com", "12341234aA",
                                        "client", LocalDate.now(), "Male", "1231231234", "123 example st.",
                                        "what's your favorite food",
                                        "pancake", "1234123412341234", "123");
                        FormBuilder.append(registerForm);
                } catch (Error e) {
                        assertTrue(true);       //success, user already registered
                }
                 catch (Exception e) {
                        fail("Connection Failed");
                }
        }

        @Test
        public void testLoginDB() {
                try {
                        LoginForm adminForm = FormBuilder.get("admin", "12341234aA");
                        if (!adminForm.isAdmin()) {
                                fail("Should've be admin role");
                        }
                        LoginForm clientForm = FormBuilder.get("client", "12341234aA");
                        if (clientForm.isAdmin()) {
                                fail("Should've be client role");
                        }

                } catch (Exception e) {
                        if (e.getMessage().equals("Status code: 400"))
                                fail("Username or password does not match");
                        else
                                fail("Connection failure");
                }
        }

        @Test
        public void testTicketDB() {
                Ticket ticket = TicketFactory.makeTicket(Type.CASUALDRESS, "3221241234", "Jone Doe", "client",LocalDate.now().plusDays(10),"This is the test comment");
                try {
                      TicketFactory.append(ticket,"12341234aA");  
                } catch (Exception e) {
                        fail("Connection failure");
                }
               
                JSONArray jsonArray= TicketFactory.getTickets("client", "12341234aA");
                boolean found = false;
                for (Object obj : jsonArray) {
                        JSONObject jsonObject= (JSONObject) obj;
                        if (((String)jsonObject.get("type")).equals("CASUALDRESS") && ((String)jsonObject.get("phonenum")).equals("3221241234") 
                        && ((String) jsonObject.get("name")).equals("Jone Doe") && ((String)jsonObject.get("username")).equals("client"))
                                found = true;
                }    
                assertTrue(found);
                
        }

}