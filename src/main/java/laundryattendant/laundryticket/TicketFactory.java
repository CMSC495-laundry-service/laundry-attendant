package laundryattendant.laundryticket;

import types.Type;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TicketFactory {
    private Ticket ticket;

    public void makeTicket(int type, String phonenum, String name, String username) {
        // Input validation
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
        // log10 and round up to get length of int
        if (phonenum.length() != 10)
            throw new Error("Phone number format is wrong");
        for (int i = 0; i < 10; i++) {
            if (!Character.isDigit(phonenum.charAt(i)))
                throw new Error("Phone number format is wrong");
        }

        // length of name.length > 50 or empty
        if (name.trim().length() == 0)
            throw new Error("Name Field can't be empty");
        else if (name.length() > 50)
            throw new Error("Name cannot exceed 50 letters");

        // assign arguments
        ticket = new LaundryTicket(cleanType, phonenum, name, username);

        append();
    }

    private void append() {
        // write data to tickets json file
        JSONParser jsonParser = new JSONParser();
        try
        {
            Path filePath = Paths.get("./src/main/java/laundryattendant/tickets.json");
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(filePath.toString()));
            JSONArray jsonArray = (JSONArray) jsonObject.get("tickets");

            System.out.println(jsonArray);

            JSONObject ticketForm = new JSONObject();
            ticketForm.put("status", ticket.getStatus().toString());
            ticketForm.put("type", ticket.getType().toString());
            ticketForm.put("orderId", ticket.getOrderID());
            ticketForm.put("price",ticket.getPrice() );
            ticketForm.put("name", ticket.getName());
            ticketForm.put("username", ticket.getUsername());
            ticketForm.put("phonenum", ticket.getPhoneNum());
            ticketForm.put("dateReceived", ticket.getDateRecieved().toString());
            ticketForm.put("dateExtimated", ticket.getDateExtimated().toString());

            jsonArray.add(ticketForm);
            System.out.println(jsonArray);

            JSONObject json = new JSONObject();
            json.put("tickets", jsonArray);

            FileWriter file = new FileWriter(filePath.toString());
            file.write(json.toJSONString());
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Ticket getTicket() {
        return ticket;
    }
}
